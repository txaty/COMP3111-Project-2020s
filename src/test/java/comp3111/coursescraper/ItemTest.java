import org.junit.Test;

import comp3111.coursescraper.Course;
import comp3111.coursescraper.Section;
import comp3111.coursescraper.ObservedSection;
import comp3111.coursescraper.Slot;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import static org.junit.Assert.*;

import org.junit.Before;

public class ItemTest {
	
	@Test
	public void testGetStartHour() {
		Slot s = new Slot();
		s.setStart("09:00AM");
		assertEquals(s.getStartHour(), 9);
	}
	
	@Test
	public void testGetStartMinute() {
		Slot s = new Slot();
		s.setStart("09:00AM");
		assertEquals(s.getStartMinute(), 0);
	}
	
	@Test
	public void testGetEndHour() {
		Slot s = new Slot();
		s.setEnd("09:00AM");
		assertEquals(s.getEndHour(), 9);
	}
	
	@Test
	public void testGetEndMinute() {
		Slot s = new Slot();
		s.setEnd("09:00AM");
		assertEquals(s.getEndMinute(), 0);
	}
	
	@Test
	public void testSetVenue() {
		Slot s = new Slot();
		s.setVenue("haha");
		assertEquals(s.getVenue(), "haha");
	}
	
	@Test
	public void testSetDay() {
		Slot s = new Slot();
		s.setDay(1);
		assertEquals(s.getDay(), 1);
	}
	
	@Test
	public void testSetSection() {
			Slot s = new Slot();
			s.setSection("haha");
			assertEquals(s.getSection(), "haha");
	}
	
	@Test
	public void testSetCode() {
		Section s = new Section();
		s.setCode("haha");
		assertEquals(s.getCode(), "haha");
	}
	
	@Test
	public void testSetCourse() {
		Section s = new Section();
		s.setCourse("haha");
		assertEquals(s.getCourse(), "haha");
	}
	
	@Test
	public void testSetInstructors() {
		String[] ins = {"ins1", "ins2"};
		Section s = new Section();
		s.setInstructors(ins);
		assertEquals(s.getInstructor(0), "ins1");
	}
	
	@Test
	public void testSetNumInstructors() {
		Section s = new Section();
		s.setNumInstructors(3);
		assertEquals(s.getNumInstructors(), 3);
	}
	
	@Test
	public void testSetNumSlots() {
		Section s = new Section();
		s.setNumSlots(3);
		assertEquals(s.getNumSlots(), 3);
	}
	
	@Test
	public void testAddSlot1() {
		Section sec = new Section();
		Slot s = new Slot();
		s.setDay(1);
		s.setVenue("haha");
		s.setSection("haha");
		s.setStart("09:00AM");
		s.setEnd("09:00AM");
		sec.addSlot(s);
		assertEquals(sec.getSlot(0).getVenue(), "haha");
	}
	
	@Test
	public void testAddSlot2() {
		Section sec = new Section();
		Slot s1 = new Slot();
		Slot s2 = new Slot();
		Slot s3 = new Slot();
		Slot s4 = new Slot();
		sec.addSlot(s1);
		sec.addSlot(s2);
		sec.addSlot(s3);
		sec.addSlot(s4);
		assertEquals(sec.getNumSlots(), 3);
	}
	
	@Test
	public void testSetEnrolled() {
		Section s = new Section();
		s.setEnrolled();
		assertEquals(s.isEnrolled(), true);
	}
	
	@Test 
	public void testChangeEnroll1() {
		Section s = new Section();
		s.changeEnroll();
		assertEquals(s.isEnrolled(), true);
	}
	
	@Test
	public void testChangeEnroll2() {
		Section s = new Section();
		s.changeEnroll();
		s.changeEnroll();
		assertEquals(s.isEnrolled(), false);
	}
	
	@Test
	public void testIsLab1() {
		Section s = new Section();
		s.setCode("LA1");
		assertEquals(s.isLab(), true);
	}
	
	@Test
	public void testIsLab2() {
		Section s = new Section();
		s.setCode("R1");
		assertEquals(s.isLab(), false);
	}
	
	@Test
	public void testIsLab3() {
		Section s = new Section();
		s.setCode("L1");
		assertEquals(s.isLab(), false);
	}
	
	@Test
	public void testIsTutorial1() {
		Section s = new Section();
		s.setCode("T1");
		assertEquals(s.isTutorial(),true);
	}
	
	@Test
	public void testIsTutorial2() {
		Section s = new Section();
		s.setCode("R1");
		assertEquals(s.isTutorial(),false);
	}
	
	@Test
	public void testSectionEquals1() {
		Section s1 = new Section();
		Section s2 = new Section();
		s1.setCode("code");
		s1.setCourse("course");
		s2 = s1.clone();
		assertEquals(s1.sectionEquals(s2), true);
	}
	
	@Test
	public void testSectionEquals2() {
		Section s1 = new Section();
		Section s2 = new Section();
		s1.setCode("code");
		s1.setCourse("course");
		s2.setCode("haha");
		assertEquals(s1.sectionEquals(s2), false);
	}
	
	@Test
	public void testSectionEquals3() {
		Section s1 = new Section();
		Section s2 = new Section();
		s1.setCourse("course");
		s1.setCode("code");
		s2.setCourse("course");
		assertEquals(s1.sectionEquals(s2), false);
	}
	
	@Test
	public void testSectionEquals4() {
		Section s1 = new Section();
		Section s2 = new Section();
		s1.setCourse("course");
		s1.setCode("code");
		s2.setCode("code");
		assertEquals(s1.sectionEquals(s2), false);
	}
	
	@Test
	public void testGetSlot1() {
		Section sec = new Section();
		Slot s = new Slot();
		sec.addSlot(s);
		assertEquals(sec.getSlot(1),null);
	}
	
	@Test
	public void testGetSlot2() {
		Section sec = new Section();
		assertEquals(sec.getSlot(-1), null);
	}
	
	Course c = new Course();
	//ObservedSection os = new ObservedSection();
	
	@Test
	public void testOSConstructor() {
		Section sec = new Section();
		sec.setCode("L1(1234)");
		sec.setCourse("COMP 3111 Software Engineering");
		sec.setInstructors(new String[] {"Kenneth Leung"});
		sec.setNumInstructors(1);
		sec.setEnrolled();
		ObservedSection nos = new ObservedSection(sec);
		assertEquals(nos.getCourseCode(),"COMP3111");
		assertEquals(nos.getCourseName(),"Software Engineering");
		assertEquals(nos.getInstructors(),"Kenneth Leung");
		assertEquals(nos.getSectionCode(),"L1(1234)");
		assertTrue(nos.getCheckBox().isSelected());
	}
	
	@Test
	public void testSetTitle() {
     	c.setTitle("ABCDE");
		assertEquals(c.getTitle(), "ABCDE");
	}

	@Test
	public void testCourseAddSlot() {
		c.setNumSlots(0);
		Slot s1 = new Slot();
		Slot s2 = new Slot();
		c.addSlot(s1);
		c.addSlot(s2);
		assertEquals(c.getNumSlots(),2);	
	}
	
	@Test
	public void testCourseAddSlotFull() {
		c.setNumSlots(20);
		c.addSlot(new Slot());
		assertEquals(c.getNumSlots(), 20);
	}
	
	@Test
	public void testSetDescription() {
		c.setDescription("interesting");
		assertEquals(c.getDescription(), "interesting");
	}
	
	@Test
	public void testSetExclusion() {
		c.setExclusion("null");
		assertEquals(c.getExclusion(),"null");
	}
	
	@Test 
	public void testCourseSetNumSlots() {
		c.setNumSlots(3);
		assertEquals(c.getNumSlots(),3);
	}
	@Test
	public void testCourseAddSection() {
		c.setNumSections(0);
		Section s1 = new Section();
		Section s2 = new Section();
		c.addSection(s1);
		c.addSection(s2);
		assertEquals(c.getNumSections(),2);	
	}
	
	@Test
	public void testChangeSection() {
		c = new Course();
		Section s1 = new Section();
		Section s2 = new Section();
		s1.setCode("L1");
		s2.setCode("L2");
		c.addSection(s1);
		c.changeSection(s2);
		assertEquals(c.getSection(0).getCode(),"L2");
	}
	
	@Test
	public void testSetNumSections() {
		c.setNumSections(7);
		assertEquals(c.getNumSections(),7);
	}
	
	@Test
	public void testSetCommonCore() {
		assertFalse(c.isCommonCore());
		c.setCommonCore(true);
		assertTrue(c.isCommonCore());
	}
	/*
	@Test
	public void testOSSetSectionCode() {
		os.setSectionCode("L2(1235)");
		assertEquals(os.getSectionCode(),"L2(1235)");
	}
	
	@Test
	public void testOSSetCourseCode() {
		os.setCourseCode("COMP3511");
		assertEquals(os.getCourseCode(),"COMP3511");
	}
	
	@Test
	public void testOSSetCourseName() {
		os.setCourseName("Operating System");
		assertEquals(os.getCourseName(),"Operating System");
	}
	
	@Test
	public void testOSSetInstructors() {
		os.setInstructors("Li Bo\nChen Kai");
		assertEquals(os.getInstructors(), "Li Bo\nChen Kai");
	}
	
	@Test
	public void testOSSetCheckBox() {
		os.setCheckBox(new CheckBox());
		assertFalse(os.getCheckBox().isSelected());
	}
	*/

}

