package comp3111.coursescraper;

import java.awt.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;

import java.util.Random;
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
    	
    	List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),textfieldSubject.getText());
    	if(v == null) {
    		textAreaConsole.setText("Please enter a valid URL:)");
    	}
    	else { 
    		int totalSec = 0;
    		for (Course c : v) {
    			totalSec += c.getNumSections();
    		}
    		textAreaConsole.setText("Total Number of difference sections in this search: "+totalSec);
    		textAreaConsole.setText(textAreaConsole.getText()+ "\n" + "Total Number of Courses in this search: " + v.size());
    		textAreaConsole.setText(textAreaConsole.getText()+ "\n" + "Instructors who has teaching assignment this term but does not need to teach at Tu 3:10pm: ");
    		String [] instructorList = new String[3*totalSec];
    		int totalIns = 0;
    		for (int i = 0; i < 3*totalSec; i++) instructorList[i] = null; 
    		for(Course c : v) {
    			for(int i = 0; i < c.getNumSections(); i++) {
    				boolean available = true;
    				for(int j = 0; j < c.getSection(i).getNumSlots(); j++) {
    					Slot s = c.getSection(i).getSlot(j).clone();
    					if((s.getDay() == 1) && ((s.getStartHour()<=14)||(s.getStartHour()==15 && s.getStartMinute()<10)) && ((s.getEndHour()>15)||(s.getEndHour()==15&&s.getEndMinute()>10))) {
    						available = false;
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
        					if(exist == false) {
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
    		for (Course c : v) {
    			String newline = c.getTitle() + "\n";
    			for (int i = 0; i < c.getNumSlots(); i++) {
    				Slot t = c.getSlot(i);
    				newline += t.getSection()+" ";
    				newline += "Slot " + i + ":" + t + "\n";
    			}
    			textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
    		}
    	
    		//Add a random block on Saturday
    		AnchorPane ap = (AnchorPane)tabTimetable.getContent();
    		Label randomLabel = new Label("COMP1022\nL1");
    		Random r = new Random();
    		double start = (r.nextInt(10) + 1) * 20 + 40;

    		randomLabel.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
    		randomLabel.setLayoutX(600.0);
    		randomLabel.setLayoutY(start);
    		randomLabel.setMinWidth(100.0);
    		randomLabel.setMaxWidth(100.0);
    		randomLabel.setMinHeight(60);
    		randomLabel.setMaxHeight(60);
    
    		ap.getChildren().addAll(randomLabel);
    	
    	
    	}
    }

}
