/**
 * 
 * You might want to uncomment the following code to learn testFX. Sorry, no tutorial session on this.
 * 
 */
package comp3111.coursescraper;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;


public class FxTest extends ApplicationTest {

	private Scene s;
	private FXMLLoader loader;
	
	@Override
	public void start(Stage stage) throws Exception {
    	loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/ui.fxml"));
   		VBox root = (VBox) loader.load();
   		Scene scene =  new Scene(root);
   		stage.setScene(scene);
   		stage.setTitle("Course Scraper");
   		stage.show();
   		s = scene;
	}

	
//	@Test
//	public void testListEnrollCourseSfqButtionDisable1() {
//		clickOn("#tabSfq");
//		Button b = (Button)s.lookup("#buttonSfqEnrollCourse");
//		assertTrue(b.isDisabled());
//	}
	/*---------- Task 5, 6----------*/
	@Test
	public void testListEnrollCourseSfqButtionDisable() {
		clickOn("#tabMain");
		sleep(1000);
		clickOn("#buttonSearch");
		sleep(1000);
		clickOn("#tabSfq");
		sleep(1000);
		clickOn("#buttonSfqEnrollCourse");
		sleep(1000);
		Button b = (Button)s.lookup("#buttonSfqEnrollCourse");
		assertFalse(b.isDisabled());
	}
	
	@Test
	public void testListEnrollInstructorSfqButtionDisable1() {
		clickOn("#tabSfq");
		clickOn("#buttonInstructorSfq");
		Button b = (Button)s.lookup("#buttonInstructorSfq");
		sleep(1000);
		assertFalse(b.isDisabled());
	}
	
	@Test
	public void testListEnrollInstructorSfqButtionDisable2() {
		clickOn("#tabSfq");
		clickOn("#buttonInstructorSfq");
		sleep(2000);
		clickOn("#buttonInstructorSfq");
		Button b = (Button)s.lookup("#buttonInstructorSfq");
		sleep(1000);
		assertFalse(b.isDisabled());
	}
	
	@Test
	public void testAllSubjectSearch1() {
		sleep(3000);
		Controller c = loader.getController();
		c.allSubjectSearch();
		sleep(30000);
		c.allSubjectSearch();
		assertEquals(c.getNumSubjects(), 75);
	}
	
	@Test
	public void testAllSubjectSearch2() {
		Controller c = loader.getController();
		assertEquals(c.getNumSubjects(), 0);
	}
	/*----------End Task 5, 6----------*/
	
	@Test
	public void testSelectAll() {
		clickOn("#tabMain");
		sleep(500);
		clickOn("#buttonSearch");
		sleep(2000);
		clickOn("#tabFilter");
		sleep(100);
		clickOn("#selectAll");
		sleep(2000);
		clickOn("#tabList");
		sleep(500);
		clickOn("#tabTimetable");
		sleep(500);
		clickOn("#tabFilter");
		sleep(500);
		clickOn("#selectAll");
		assertTrue(true);
	}
	
//	@Test
//	public void testChangeTimeTable() {
//		Controller c = loader.getController();
//		Section sec = new Section();
//		sec.setCode("L1(1234)");
//		sec.setCourse("COMP 3111 Software Engineering");
//		sec.setInstructors(new String[] {"Kenneth Leung"});
//		sec.setNumInstructors(1);
//		sec.setEnrolled();
//		Slot s = new Slot();
//		s.setDay(0);
//		s.setStart("10:00AM");
//		s.setEnd("11:00AM");
//		s.setSection("test");
//		s.setVenue("test");
//		sec.addSlot(s);
//		c.changeTimetable(sec);
//		assertTrue(true);
//	}
	
	@Test
	public void testOSConstructor() throws Exception{
		Section sec = new Section();
		sec.setCode("L1(1234)");
		sec.setCourse("COMP 3111 Software Engineering");
		sec.setInstructors(new String[] {"Kenneth Leung"});
		sec.setNumInstructors(1);
		sec.setEnrolled();
		ObservedSection nos = new ObservedSection(sec);
		assertEquals(nos.getCourseCode(),"COMP3111");
		assertEquals(nos.getCourseName(),"Software Engineering");
		assertEquals(nos.getInstructors(),"Kenneth Leung\n");
		assertEquals(nos.getSectionCode(),"L1(1234)");
		assertTrue(nos.getCheckBox().isSelected());
	}
	
	@Test
	public void testOSSetSectionCode() {
		ObservedSection os = new ObservedSection();
		os.setSectionCode("L2(1235)");
		assertEquals(os.getSectionCode(),"L2(1235)");
	}
	
	@Test
	public void testOSSetCourseCode() {
		ObservedSection os = new ObservedSection();
		os.setCourseCode("COMP3511");
		assertEquals(os.getCourseCode(),"COMP3511");
	}
	
	@Test
	public void testOSSetCourseName() {
		ObservedSection os = new ObservedSection();
		os.setCourseName("Operating System");
		assertEquals(os.getCourseName(),"Operating System");
	}
	
	@Test
	public void testOSSetInstructors() {
		ObservedSection os = new ObservedSection();
		os.setInstructors("Li Bo\nChen Kai");
		assertEquals(os.getInstructors(), "Li Bo\nChen Kai");
	}
	
	@Test
	public void testOSSetCheckBox() {
		ObservedSection os = new ObservedSection();
		os.setCheckBox(new CheckBox());
		assertFalse(os.getCheckBox().isSelected());
	}
	
	@Test
	public void testOSSectionCodeProperty() {
		SimpleStringProperty ssp = new SimpleStringProperty();
		ObservedSection os = new ObservedSection();
		ssp.setValue("test");
		os.setSectionCode("test");
		assertEquals(os.SectionCodeProperty().getValue(), ssp.getValue());
	}
	
	@Test
	public void testOSCourseCodeProperty() {
		SimpleStringProperty ssp = new SimpleStringProperty();
		ObservedSection os = new ObservedSection();
		ssp.setValue("test");
		os.setCourseCode("test");
		assertEquals(os.courseCodeProperty().getValue(), ssp.getValue());
	}
	
	@Test
	public void testOSCourseNameProperty() {
		SimpleStringProperty ssp = new SimpleStringProperty();
		ObservedSection os = new ObservedSection();
		ssp.setValue("test");
		os.setCourseName("test");
		assertEquals(os.courseNameProperty().getValue(), ssp.getValue());
	}
	
	@Test
	public void testOSInstructorsProperty() {
		SimpleStringProperty ssp = new SimpleStringProperty();
		ObservedSection os = new ObservedSection();
		ssp.setValue("test");
		os.setInstructors("test");
		assertEquals(os.instructorsProperty().getValue(), ssp.getValue());
	}
	
	@Test
	public void testOSGetSection() {
		Section s = new Section();
		s.setCourse("ACCT 2010 - Principles of Accounting I (3 units)");
		ObservedSection os = new ObservedSection(s);
		assertEquals(os.getSection().getCourse(), "ACCT 2010 - Principles of Accounting I (3 units)");
	}
}
