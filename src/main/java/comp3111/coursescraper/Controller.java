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

import java.util.Vector;
import java.util.List;

import javafx.application.Platform;

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

	// Task 5 parameters
	private List<String> sl = new Vector<String>();

	private boolean allSubSearchActivated = false;

	private int ALL_SUBJECT_COUNT = 0;

	private int TOTAL_NUMBER_OF_COURSES = 0;

	private double PROGRESS_INDEX = 0;

	private Thread allSubSearchThread;

	private int threadCounter = 0;

	private boolean isAllSearched = false;

	@FXML
	private Button allSubSearchButton;

	// Task 6 parameters
	private Sfq sfqHandler = new Sfq();

	/*----------Task 5----------*/
	@FXML
	void allSubjectSearch() {
		if (!allSubSearchActivated) {
			sl = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText());
			if (sl == null) {
				allSubSearchActivated = false;
				System.out.println("Error Occurred");
				return;
			}
			ALL_SUBJECT_COUNT = sl.size();
			textAreaConsole.setText("Total Number of Categories/Code Prefix: " + ALL_SUBJECT_COUNT);
			allSubSearchActivated = true;
		} else {
			allSubSearchActivated = false;
			TOTAL_NUMBER_OF_COURSES = 0;
			v.clear();
			PROGRESS_INDEX = 0;
			allSubSearchButton.setDisable(true);
			allSubSearchThread = new Thread(new Runnable() {
				@Override
				public void run() {
					Runnable updater = new Runnable() {
						@SuppressWarnings("deprecation")
						@Override
						public void run() {
							if (threadCounter < sl.size()) {
								String subStr = sl.get(threadCounter);
								List<Course> courseOfSubject = scraper.scrape(textfieldURL.getText(),
										textfieldTerm.getText(), subStr);
								if (courseOfSubject != null) {
									if (courseOfSubject.size() != 0) {
										v.addAll(courseOfSubject);
										TOTAL_NUMBER_OF_COURSES += courseOfSubject.size();
										System.out.println(subStr + " is done");
										PROGRESS_INDEX = 1.0 * (threadCounter + 1) / ALL_SUBJECT_COUNT;
									}
								}
							} else {
								textAreaConsole.setText("Total Number of Courses fetched: " + TOTAL_NUMBER_OF_COURSES);
								allSubSearchButton.setDisable(false);
								buttonSfqEnrollCourse.setDisable(false);
								isAllSearched = true;
								allSubSearchThread.stop();
							}
							progressbar.setProgress(PROGRESS_INDEX);
							threadCounter++;
						}
					};
					while (true) {
						try {
							Thread.sleep(300);
						} catch (InterruptedException ex) {
						}
						Platform.runLater(updater);
					}
				}

			});
			allSubSearchThread.start();
		}
	}

	@FXML
	void triggerTabsAfterAllSubSearch() {
		if (isAllSearched) {
			showSearchInfo(false);
			isAllSearched = false;
		}
	}
	/*----------End of Task 5----------*/

	/*----------Task 6----------*/
	@FXML
	void tabSfqCheck() {
		if (v.size() == 0)
			buttonSfqEnrollCourse.setDisable(true);
	}

	@FXML
	void findInstructorSfq() {
		buttonInstructorSfq.setDisable(true);
		if (sfqHandler.getCourseListSize() == 0 && sfqHandler.getInstructorListSize() == 0) {
			sfqHandler = scraper.scrapeSfq(textfieldSfqUrl.getText());
		}
		textAreaConsole.setText(sfqHandler.getInstructorSfq());
	}

	@FXML
	void findSfqEnrollCourse() {
		buttonSfqEnrollCourse.setDisable(true);
		if (sfqHandler.getCourseListSize() == 0 && sfqHandler.getInstructorListSize() == 0) {
			sfqHandler = scraper.scrapeSfq(textfieldSfqUrl.getText());
		}
		String consoleText = "";
		for (Section section : sectionEnrolled) {
			String courseCode = section.getCourse().split("-")[0].trim();
			double[] result = sfqHandler.findCourseSfq(courseCode);
			if (result != null) {
				consoleText = consoleText + courseCode + "\n" + "Mean: " + String.format("%.1f", result[0]) + "\n"
						+ "SD:   " + String.format("%.1f", result[1]) + "\n\n";
			}
		}
		buttonSfqEnrollCourse.setDisable(false);
		textAreaConsole.setText(consoleText);
	}
	/*----------End of Task 6----------*/

	// Task 1
	/**
	 * This search method invokes the search function.
	 */
	@FXML
	void search() {
		v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(), textfieldSubject.getText());
		if (v == null) {
			textAreaConsole.setText("Please enter a valid URL:)");
		} else {
			buttonSfqEnrollCourse.setDisable(false);
			showSearchInfo(true);
		}
	}

	public void showSearchInfo(boolean allSubSearch) {
		filteredCourse = null;
		filteredCourse = new Vector<Course>();
		for (Course c : v) {
			if (allFilterTrue(c)) {
				filteredCourse.add(c);
			}
		}
		int totalSec = 0;
		for (Course c : filteredCourse) {
			totalSec += c.getNumSections();
		}
		if (allSubSearch) {
			sl = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText());
			if (sl == null) {
				allSubSearchActivated = false;
				System.out.println("Error Occurred");
				return;
			}
			ALL_SUBJECT_COUNT = sl.size();
			textAreaConsole.setText(
					"Total Number of Categories/Code Prefix: " + ALL_SUBJECT_COUNT + " (for All Subject Search)\n");
			allSubSearchActivated = true;
		} else {
			textAreaConsole.setText("");
		}
		int totalNumCourses = 0;
		for (Course c : v) {
			if (c.getNumSections() > 0)
				totalNumCourses++;
		}
		textAreaConsole
				.setText(textAreaConsole.getText() + "Total Number of difference sections in this search: " + totalSec);
		textAreaConsole.setText(
				textAreaConsole.getText() + "\n" + "Total Number of Courses in this search: " + totalNumCourses);
		textAreaConsole.setText(textAreaConsole.getText() + "\n"
				+ "Instructors who has teaching assignment this term but does not need to teach at Tu 3:10pm: ");
		String[] instructorList = new String[3 * totalSec];
		String[] noIns = new String[3 * totalSec];
		int totalIns = 0;
		int totalNo = 0;
		for (int i = 0; i < 3 * totalSec; i++)
			instructorList[i] = null;
		for (int i = 0; i < 3 * totalSec; i++)
			noIns[i] = null;
		for (Course c : filteredCourse) {
			for (int i = 0; i < c.getNumSections(); i++) {
				boolean available = true;
				for (int j = 0; j < c.getSection(i).getNumSlots(); j++) {
					Slot s = c.getSection(i).getSlot(j).clone();
					if ((s.getDay() == 1)
							&& ((s.getStartHour() <= 14) || (s.getStartHour() == 15 && s.getStartMinute() < 10))
							&& ((s.getEndHour() > 15) || (s.getEndHour() == 15 && s.getEndMinute() > 10))) {
						available = false;
						for (int m = 0; m < c.getSection(i).getNumInstructors(); m++) {
							noIns[totalNo++] = c.getSection(i).getInstructor(m);
						}
						break;
					}
				}
				if (c.getSection(i).getNumSlots() == 0)
					available = false;
				if (available == true) {
					for (int k = 0; k < c.getSection(i).getNumInstructors(); k++) {
						boolean exist = false;
						for (int l = 0; l < totalIns; l++) {
							if (c.getSection(i).getInstructor(k).equals(instructorList[l])) {
								exist = true;
								break;
							}
						}
						boolean inNo = false;
						for (int n = 0; n < totalNo; n++) {
							if (noIns[n].equals(c.getSection(i).getInstructor(k))) {
								inNo = true;
								break;
							}
						}
						if (exist == false && inNo == false) {
							instructorList[totalIns++] = c.getSection(i).getInstructor(k);
						}
					}
				}
			}
		}
		for (int i = 0; i < totalIns; i++) {
			for (int j = i + 1; j < totalIns; j++) {
				if (instructorList[i].compareTo(instructorList[j]) > 0) {
					String temp = instructorList[i];
					instructorList[i] = instructorList[j];
					instructorList[j] = temp;
				}
			}
		}
		textAreaConsole.setText(textAreaConsole.getText() + "\n");
		if (totalIns > 0) {
			for (int i = 0; i < totalIns - 1; i++) {
				textAreaConsole.setText(textAreaConsole.getText() + instructorList[i] + ", ");
			}
			textAreaConsole.setText(textAreaConsole.getText() + instructorList[totalIns - 1] + "\n");
		}
		for (Course c : filteredCourse) {
			String newline = c.getTitle() + "\n";
			for (int i = 0; i < c.getNumSlots(); i++) {
				Slot t = c.getSlot(i);
				newline += t.getSection() + " ";
				newline += "Slot " + i + ":" + t + "\n";
			}
			textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
		}
		for (Course c : filteredCourse)
			for (int i = 0; i < c.getNumSections(); i++) {
				searchSectionEnrolled(c.getSection(i));
			}
		list();
	}

	// Task2

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
	 * manipulate the select all button to fulfill the text change and checkbox
	 * status change of the requirement of select or button
	 */
	@FXML
	void selectOrDeselectAll() {
		CheckBox[] allCheckBox = { am, pm, monday, tuesday, wednesday, thursday, friday, saturday, commonCore,
				noExclusion, withLabOrTut };
		if (selectAll.getText().equals("Select All")) {
			for (int i = 0; i < allCheckBox.length; i++) {
				if (!allCheckBox[i].isSelected())
					allCheckBox[i].setSelected(true);
			}
			selectAll.setText("De-select All");
			filter();
		} else {
			for (int i = 0; i < allCheckBox.length; i++) {
				if (allCheckBox[i].isSelected())
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
		showSearchInfo(false);
	}

	/**
	 * determine whether a course can pass all the required filter
	 * 
	 * @param c the course to be determined
	 * @return the boolean result of whether the course pass all the filter
	 *         requirement
	 */
	private boolean allFilterTrue(Course c) {
		Boolean result = true;
		if (am.isSelected()) {
			Boolean allPM = true;
			for (int i = 0; i < c.getNumSlots(); i++) {
				if (c.getSlot(i).getStartHour() < 12)
					allPM = false;
			}
			if (result)
				result = !allPM;
		}
		if (pm.isSelected()) {
			Boolean allAM = true;
			for (int i = 0; i < c.getNumSlots(); i++) {
				if (c.getSlot(i).getEndHour() >= 12)
					allAM = false;
			}
			if (result)
				result = !allAM;
		}
		if (monday.isSelected()) {
			Boolean noMonday = true;
			for (int i = 0; i < c.getNumSlots(); i++) {
				if (c.getSlot(i).getDay() == 0)
					noMonday = false;
			}
			if (result)
				result = !noMonday;
		}
		if (tuesday.isSelected()) {
			Boolean noTuesday = true;
			for (int i = 0; i < c.getNumSlots(); i++) {
				if (c.getSlot(i).getDay() == 1)
					noTuesday = false;
			}
			if (result)
				result = !noTuesday;
		}
		if (wednesday.isSelected()) {
			Boolean noWednesday = true;
			for (int i = 0; i < c.getNumSlots(); i++) {
				if (c.getSlot(i).getDay() == 2)
					noWednesday = false;
			}
			if (result)
				result = !noWednesday;
		}
		if (thursday.isSelected()) {
			Boolean noThursday = true;
			for (int i = 0; i < c.getNumSlots(); i++) {
				if (c.getSlot(i).getDay() == 3)
					noThursday = false;
			}
			if (result)
				result = !noThursday;
		}
		if (friday.isSelected()) {
			Boolean noFriday = true;
			for (int i = 0; i < c.getNumSlots(); i++) {
				if (c.getSlot(i).getDay() == 4)
					noFriday = false;
			}
			if (result)
				result = !noFriday;
		}
		if (saturday.isSelected()) {
			Boolean noSaturday = true;
			for (int i = 0; i < c.getNumSlots(); i++) {
				if (c.getSlot(i).getDay() == 5)
					noSaturday = false;
			}
			if (result)
				result = !noSaturday;
		}
		/*
		 * Please uncomment this code after c.isCommerCore implemented
		 * if(commonCore.isSelected()) { if(result) result = c.isCommonCore(); }
		 */

		if (noExclusion.isSelected()) {
			if (result)
				result = c.getExclusion() == "null";
		}
		if (withLabOrTut.isSelected()) {
			Boolean noLabOrTut = true;
			for (int i = 0; i < c.getNumSections(); i++) {
				if (c.getSection(i).isLab() || c.getSection(i).isTutorial())
					noLabOrTut = false;
			}
			if (result)
				result = !noLabOrTut;
		}
		return result;
	}
	// End of Task 2

	// Task 3
	@FXML
	private TableView<ObservedSection> courseTable;

	@FXML
	private TableColumn<ObservedSection, String> courseCode;

	@FXML
	private TableColumn<ObservedSection, String> section;

	@FXML
	private TableColumn<ObservedSection, String> courseName;

	@FXML
	private TableColumn<ObservedSection, String> instructor;

	@FXML
	private TableColumn<ObservedSection, CheckBox> enroll;

	List<ObservedSection> s = new Vector<ObservedSection>();
	ObservableList<ObservedSection> os;
	Boolean firstTimeList = true;

	/*
	 * to construct the list required the list information can be updated once
	 * search clicked or filter information changed the enrollment status can be
	 * updated once the checkbox status in the list is changed
	 */
	@FXML
	private void list() {
		s = null;
		s = new Vector<ObservedSection>();
		for (int i = 0; i < filteredCourse.size(); i++) {
			for (int j = 0; j < filteredCourse.get(i).getNumSections(); j++)
				s.add(new ObservedSection(filteredCourse.get(i).getSection(j)));
		}

		if (firstTimeList) {
			firstTimeList = false;
			os = FXCollections.observableList(s);
			courseCode.setCellValueFactory(new PropertyValueFactory<ObservedSection, String>("courseCode"));
			section.setCellValueFactory(new PropertyValueFactory<ObservedSection, String>("sectionCode"));
			courseName.setCellValueFactory(new PropertyValueFactory<ObservedSection, String>("courseName"));
			instructor.setCellValueFactory(new PropertyValueFactory<ObservedSection, String>("instructors"));
			enroll.setCellValueFactory(new PropertyValueFactory<ObservedSection, CheckBox>("checkBox"));
			courseTable.setItems(os);
		} else
			os.setAll(s);
		for (ObservedSection sec : s) {
			sec.getCheckBox().selectedProperty().addListener(
					(ChangeListener<Boolean>) (observable, oldVal, newVal) -> changeTimetable(sec.getSection()));

			sec.getCheckBox().selectedProperty().addListener(
					(ChangeListener<Boolean>) (observable, oldVal, newVal) -> changeEnrollStatus(sec.getSection()));
		}

	}

	/**
	 * to change the enroll status of a section
	 * 
	 * @param sec the section to be updated on the enroll status
	 */
	public void changeEnrollStatus(Section sec) {
		if (sec.isEnrolled()) {
			sectionEnrolled.remove(sec);
			sec.changeEnroll();
		} else {
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
			textAreaConsole.setText(textAreaConsole.getText() + sectionEnrolled.get(i).getCourse().split(" ")[0]
					+ sectionEnrolled.get(i).getCourse().split(" ")[1] + sectionEnrolled.get(i).getCode().split(" ")[0]
					+ ";");
		}
	}

	/**
	 * to update the section object in the sectionEnrolled list
	 * 
	 * @param s the section to update
	 */
	public void searchSectionEnrolled(Section s) {
		for (int i = 0; i < sectionEnrolled.size(); i++) {
			if (sectionEnrolled.get(i).sectionEquals(s)) {
				s.setEnrolled();
				sectionEnrolled.add(s);
				sectionEnrolled.remove(i);
				break;
			}
		}
	}
	// End of Task3

	// Task4
	private List<Label> labels = new Vector<Label>();

	private int numLabels = 0;

	private Color colors[] = { Color.BLUE, Color.RED, Color.GREEN, Color.MAGENTA, Color.CYAN, Color.GRAY, Color.ORANGE,
			Color.PINK, Color.YELLOW, Color.BLUEVIOLET, Color.CORNFLOWERBLUE, Color.DARKRED, Color.DARKSALMON,
			Color.DARKCYAN, Color.AQUAMARINE, Color.BURLYWOOD, Color.CHOCOLATE };

	private boolean colorUsed[] = { false, false, false, false, false, false, false, false, false, false, false, false,
			false, false, false, false, false };

	/**
	 * 
	 * @param sec the section to add or delete
	 */
	public void changeTimetable(Section sec) {
		if (sec.getNumSlots() > 0) {
			AnchorPane ap = (AnchorPane) tabTimetable.getContent();
			if (sectionEnrolled.contains(sec)) {
				int index = 0;
				String Coursecode[] = sec.getCourse().split(" ");
				String Sectioncode[] = sec.getCode().split(" ");
				int day = sec.getSlot(0).getDay();
				int startH = sec.getSlot(0).getStartHour();
				int startM = sec.getSlot(0).getStartMinute();
				int endH = sec.getSlot(0).getEndHour();
				int endM = sec.getSlot(0).getEndMinute();
				double start = startH + startM / 60.0;
				double end = endH + endM / 60.0;
				double startY = 20 * start - 140;
				double height = (end - start) * 20;
				for (int i = 0; i < numLabels; i++) {
					if (height >= 36) {
						if (labels.get(i).getText().equals(Coursecode[0] + Coursecode[1] + "\n" + Sectioncode[0])) {
							index = i;
							break;
						}
					} else {
						if (labels.get(i).getText().equals(Coursecode[0] + Coursecode[1] + " " + Sectioncode[0])) {
							index = i;
							break;
						}
					}
				}
				Color c = (Color) labels.get(index).getBackground().getFills().get(0).getFill();
				for (int i = 0; i < 17; i++) {
					if (c.getRed() == colors[i].getRed() && c.getGreen() == colors[i].getGreen()
							&& c.getBlue() == colors[i].getBlue()) {
						colorUsed[i] = false;
						break;
					}
				}
				for (int i = 0; i < sec.getNumSlots(); i++) {
					ap.getChildren().remove(labels.get(index + i));
				}
				for (int i = sec.getNumSlots(); i > 0; i--) {
					labels.remove(labels.get(index + i - 1));
					numLabels--;
				}
			} else {
				for (int i = 0; i < 17; i++) {
					if (colorUsed[i] == false) {
						for (int j = 0; j < sec.getNumSlots(); j++) {
							String Coursecode[] = sec.getCourse().split(" ");
							String Sectioncode[] = sec.getCode().split(" ");
							int day = sec.getSlot(j).getDay();
							int startH = sec.getSlot(j).getStartHour();
							int startM = sec.getSlot(j).getStartMinute();
							int endH = sec.getSlot(j).getEndHour();
							int endM = sec.getSlot(j).getEndMinute();
							double start = startH + startM / 60.0;
							double end = endH + endM / 60.0;
							double startY = 20 * start - 140;
							double height = (end - start) * 20;
							Label randomLabel = null;
							if (height >= 36) {
								randomLabel = new Label(Coursecode[0] + Coursecode[1] + "\n" + Sectioncode[0]);
							} else {
								randomLabel = new Label(Coursecode[0] + Coursecode[1] + " " + Sectioncode[0]);
							}
							Color color = new Color(colors[i].getRed(), colors[i].getGreen(), colors[i].getBlue(), 0.5);
							randomLabel.setBackground(
									new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
							randomLabel.setLayoutX((day + 1) * 100);
							randomLabel.setLayoutY(startY);
							randomLabel.setMinWidth(100.0);
							randomLabel.setMaxWidth(100.0);
							randomLabel.setMinHeight(height);
							randomLabel.setMaxHeight(height);
							labels.add(randomLabel);
							numLabels++;
							ap.getChildren().addAll(randomLabel);
						}
						colorUsed[i] = true;
						break;
					}
				}
			}
		}
	}

}
