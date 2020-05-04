package comp3111.coursescraper;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;

import java.util.Random;
import java.util.Vector;
import java.util.List;
public class Controller {

    @FXML
    private Tab tabMain;

    @FXML
    private TextField textfieldTerm;

    @FXML
    private TextField textfieldSubject;

    @FXML
    private Button buttonSearch;

    @FXML
    private TextField textfieldURL;

    @FXML
    private Tab tabStatistic;

    @FXML
    private ComboBox<?> comboboxTimeSlot;

    @FXML
    private Tab tabFilter;

    @FXML
    private Tab tabList;

    @FXML
    private Tab tabTimetable;

    @FXML
    private Tab tabAllSubject;

    @FXML
    private ProgressBar progressbar;

    @FXML
    private TextField textfieldSfqUrl;

    @FXML
    private Button buttonSfqEnrollCourse;

    @FXML
    private Button buttonInstructorSfq;

    @FXML
    private TextArea textAreaConsole;
    
    private Scraper scraper = new Scraper();
    
	private List<Course> v = new Vector<Course>();
	
	private List<Course> filteredCourse = new Vector<Course>();
	
	private List<Section> sectionEnrolled = new Vector<Section>();
        
    @FXML
    void allSubjectSearch() {
    	
    }

    @FXML
    void findInstructorSfq() {
    	buttonInstructorSfq.setDisable(true);
    }

    @FXML
    void findSfqEnrollCourse() {

    }

    @FXML
    void search() { 	
    	v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),textfieldSubject.getText());
    	if(v == null) {
    		textAreaConsole.setText("Please enter a valid URL:)");
    	}
    	else 
    		showSearchInfo();
    }
    
    public void showSearchInfo() {
    		filteredCourse = null;
    		filteredCourse = new Vector<Course>();
    		for(Course c: v) {
    			if(allFilterTrue(c)) {
    				filteredCourse.add(c);
    			}
    		}
    		int totalSec = 0;
    		for (Course c : filteredCourse) {
    			totalSec += c.getNumSections();   			
    		}
    		textAreaConsole.setText("Total Number of difference sections in this search: "+totalSec);
    		textAreaConsole.setText(textAreaConsole.getText()+ "\n" + "Total Number of Courses in this search: " + v.size());
    		textAreaConsole.setText(textAreaConsole.getText()+ "\n" + "Instructors who has teaching assignment this term but does not need to teach at Tu 3:10pm: ");
    		String [] instructorList = new String[3*totalSec];
    		String [] noIns = new String[3*totalSec];
    		int totalIns = 0;
    		int totalNo = 0;
    		for (int i = 0; i < 3*totalSec; i++) instructorList[i] = null; 
    		for (int i = 0; i < 3*totalSec; i++) noIns[i] = null; 
    		for(Course c : filteredCourse) {
    			for(int i = 0; i < c.getNumSections(); i++) {
    				boolean available = true;
    				for(int j = 0; j < c.getSection(i).getNumSlots(); j++) {
    					Slot s = c.getSection(i).getSlot(j).clone();
    					if((s.getDay() == 1) && ((s.getStartHour()<=14)||(s.getStartHour()==15 && s.getStartMinute()<10)) && ((s.getEndHour()>15)||(s.getEndHour()==15&&s.getEndMinute()>10))) {
    						available = false;
    						for(int m = 0; m < c.getSection(i).getNumInstructors(); m++) {
    							noIns[totalNo++] = c.getSection(i).getInstructor(m);
    						}
    						break;
    					}
    				}
    				if(c.getSection(i).getNumSlots()==0)
    					available = false;
    				if(available == true) {
        				for(int k = 0; k < c.getSection(i).getNumInstructors(); k++) {
        					boolean exist = false;
        					for(int l = 0; l < totalIns; l++) {
        						if(c.getSection(i).getInstructor(k).equals(instructorList[l])) {
        							exist = true;
        							break;
        						}
        					}
        					boolean inNo = false;
        					for(int n = 0; n < totalNo; n++) {
        						if(noIns[n].equals(c.getSection(i).getInstructor(k))) {
        							inNo = true;
        							break;
        						}
        					}
        					if(exist == false && inNo == false) {
        						instructorList[totalIns++] = c.getSection(i).getInstructor(k);
        					}
        				}
    				}
    			}
    		}
    		for(int i = 0; i < totalIns; i++) {
    			for(int j = i+1; j < totalIns; j++) {
    				if(instructorList[i].compareTo(instructorList[j]) > 0) {
    					String temp = instructorList[i];
    					instructorList[i] = instructorList[j];
    					instructorList[j] = temp;
    				}
    			}
    		}
    		textAreaConsole.setText(textAreaConsole.getText()+"\n");
    		if(totalIns > 0) {
    			for(int i = 0; i < totalIns-1; i++) {
    				textAreaConsole.setText(textAreaConsole.getText()+instructorList[i]+", ");
    			}
    			textAreaConsole.setText(textAreaConsole.getText()+instructorList[totalIns-1]+"\n");
    		}
    		for (Course c : filteredCourse) {
    			String newline = c.getTitle() + "\n";
    			for (int i = 0; i < c.getNumSlots(); i++) {
    				Slot t = c.getSlot(i);
    				newline += t.getSection()+" ";
    				newline += "Slot " + i + ":" + t + "\n";
    			}
    			textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
    		}
    		for (Course c : filteredCourse)
    		for(int i = 0; i < c.getNumSections(); i++) {
				searchSectionEnrolled(c.getSection(i));
			}
    	list();
    }

    //Task2
    
    @FXML
    private Button selectAll;
    
    @FXML
    private CheckBox am;
    
    @FXML
    private CheckBox pm;
    
    @FXML
    private CheckBox monday;
    
    @FXML
    private CheckBox tuesday;
    
    @FXML
    private CheckBox wednesday;
    
    @FXML
    private CheckBox thursday;
    
    @FXML
    private CheckBox friday;
    
    @FXML
    private CheckBox saturday;
    
    @FXML
    private CheckBox commonCore;
    
    @FXML
    private CheckBox noExclusion;
    
    @FXML
    private CheckBox withLabOrTut;
     
    
    /**
     * manipulate the select all button to fulfill the text change and checkbox status change of the requirement of select or button
     */
    @FXML
    void selectOrDeselectAll() {
    	CheckBox[] allCheckBox= {am, pm, monday, tuesday, wednesday, thursday, friday, saturday, commonCore, noExclusion, withLabOrTut};
    	if(selectAll.getText().equals("Select All")) {
    	    for(int i = 0; i< allCheckBox.length; i++) {
    	    	if(!allCheckBox[i].isSelected())
    	    		allCheckBox[i].setSelected(true);
    	    }
    		selectAll.setText("De-select All");
    		filter();
    	}
    	else {
    		for(int i = 0; i< allCheckBox.length; i++) {
    	    	if(allCheckBox[i].isSelected())
    	    		allCheckBox[i].setSelected(false);
    	    }
    		selectAll.setText("Select All");
    		filter();
    	}
    }
    
    /**
     * show the filtered information of course
     */
    @FXML
    private void filter() {
    	textAreaConsole.clear();
    	showSearchInfo();
    }
    
    /**
     * determine whether a course can pass all the required filter
     * @param c the course to be determined
     * @return the boolean result of whether the course pass all the filter requirement
     */
    private boolean allFilterTrue(Course c) {
    	Boolean result = true;
    	if(am.isSelected()) {
    		Boolean allPM = true;
    		for(int i = 0; i < c.getNumSlots(); i++) {
    			if(c.getSlot(i).getStartHour()<12)
    				allPM = false;
    		}
    		if(result)
    	     	result = !allPM;
    	}
    	if(pm.isSelected()) {
    		Boolean allAM = true;
    		for(int i = 0; i < c.getNumSlots(); i++) {
    			if(c.getSlot(i).getEndHour()>=12)
    				allAM = false;
    		}
    		if(result)
    		    result = !allAM;
    	}
    	if(monday.isSelected()) {
    		Boolean noMonday = true;
    		for(int i = 0; i < c.getNumSlots(); i++) {
    			if(c.getSlot(i).getDay()==0)
    				noMonday = false;
    		}
    		if(result)
    		    result = !noMonday;
    	}
    	if(tuesday.isSelected()) {
    		Boolean noTuesday = true;
    		for(int i = 0; i < c.getNumSlots(); i++) {
    			if(c.getSlot(i).getDay()==1)
    				noTuesday = false;
    		}
    		if(result)
    		    result = !noTuesday;
    	}
    	if(wednesday.isSelected()) {
    		Boolean noWednesday = true;
    		for(int i = 0; i < c.getNumSlots(); i++) {
    			if(c.getSlot(i).getDay()==2)
    				noWednesday = false;
    		}
    		if(result)
    		    result = !noWednesday;
    	}
    	if(thursday.isSelected()) {
    		Boolean noThursday = true;
    		for(int i = 0; i < c.getNumSlots(); i++) {
    			if(c.getSlot(i).getDay()==3)
    				noThursday = false;
    		}
    		if(result)
    		    result = !noThursday;
    	}
    	if(friday.isSelected()) {
    		Boolean noFriday = true;
    		for(int i = 0; i < c.getNumSlots(); i++) {
    			if(c.getSlot(i).getDay()==4)
    				noFriday = false;
    		}
    		if(result)
    		    result = !noFriday;
    	}
    	if(saturday.isSelected()) {
    		Boolean noSaturday = true;
    		for(int i = 0; i < c.getNumSlots(); i++) {
    			if(c.getSlot(i).getDay()==5)
    				noSaturday = false;
    		}
    		if(result)
    		    result = !noSaturday;
    	}
    	 if(commonCore.isSelected()) {
            if(result)
    		    result = c.isCommonCore();
    	}
    	
    	if(noExclusion.isSelected()) {
    		if(result)
    		    result = c.getExclusion()=="null";
    	}
    	if(withLabOrTut.isSelected()) {
    		Boolean noLabOrTut = true;
    		for(int i = 0; i < c.getNumSections(); i++) {
    			if(c.getSection(i).isLab()||c.getSection(i).isTutorial())
    				noLabOrTut = false;
    		}
    		if(result)
    		    result = !noLabOrTut;
    	}
    	return result;
    }
    //End of Task 2
    
    //Task 3
    @FXML
    private TableView<ObservedSection> courseTable;
    
    @FXML
    private TableColumn<ObservedSection,String> courseCode;
    
    @FXML
    private TableColumn<ObservedSection,String> section;
    
    @FXML
    private TableColumn<ObservedSection,String> courseName;
    
    @FXML
    private TableColumn<ObservedSection,String> instructor;
    
    @FXML
    private TableColumn<ObservedSection,CheckBox> enroll;
    
    List<ObservedSection> s = new Vector<ObservedSection>();
    ObservableList<ObservedSection> os;
    Boolean firstTimeList = true;
    
    /*
     * to construct the list required
     * the list information can be updated once search clicked or filter information changed
     * the enrollment status can be updated once the checkbox status in the list is changed
     */
    @FXML
    private void list () {
    	s = null;
     	s = new Vector<ObservedSection>();
    	for (int i = 0; i < filteredCourse.size(); i++) {
    		for(int j = 0; j < filteredCourse.get(i).getNumSections(); j++)
    		   s.add(new ObservedSection(filteredCourse.get(i).getSection(j)));
    	}	
    	   
    	if(firstTimeList) {
    		firstTimeList = false;
    		os = FXCollections.observableList(s);  
    	    courseCode.setCellValueFactory(new PropertyValueFactory<ObservedSection,String>("courseCode"));
        	section.setCellValueFactory(new PropertyValueFactory<ObservedSection,String>("sectionCode"));
    	    courseName.setCellValueFactory(new PropertyValueFactory<ObservedSection,String>("courseName"));
        	instructor.setCellValueFactory(new PropertyValueFactory<ObservedSection,String>("instructors"));
        	enroll.setCellValueFactory(new PropertyValueFactory<ObservedSection,CheckBox>("checkBox"));    	
        	courseTable.setItems(os);  
    	}
    	else 
    		os.setAll(s);   		
    	for (ObservedSection sec : s) {
    		sec.getCheckBox().selectedProperty().addListener((ChangeListener<Boolean>) (observable, oldVal, newVal) -> changeEnrollStatus(sec.getSection()));
    	}

    	
    }
    
    /**
     * to change the enroll status of a section
     * @param sec the section to be updated on the enroll status
     */
    public void changeEnrollStatus(Section sec) {
    	if(sec.isEnrolled()){
    		sectionEnrolled.remove(sec);
    		sec.changeEnroll();
    	}
    	else {
    		sectionEnrolled.add(sec);
    		sec.changeEnroll();
    	}
    	showEnrolled();
    }
    
    /**
     * show the enrollment information on the console
     */
    public void showEnrolled() {
    	textAreaConsole.setText("The following sections are enrolled:");
    	for (int i = 0; i < sectionEnrolled.size(); i++) {
    		textAreaConsole.setText(textAreaConsole.getText() + sectionEnrolled.get(i).getCourse().split(" ")[0] + sectionEnrolled.get(i).getCourse().split(" ")[1] + sectionEnrolled.get(i).getCode().split(" ")[0] + ";");
    	}
    }
    
    /**
     * to update the section object in the sectionEnrolled list
     * @param s the section to update
     */
    public void searchSectionEnrolled(Section s) {
		for(int i = 0; i < sectionEnrolled.size(); i++) {    	
			if(sectionEnrolled.get(i).sectionEquals(s)) {
				s.setEnrolled();
			    sectionEnrolled.add(s);
			    sectionEnrolled.remove(i);
			    break;
			}
		}
    }
    //End of Task3
    
}

    
