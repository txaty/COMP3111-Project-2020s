package comp3111.coursescraper;

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
	Course c = new Course();
	ObservedSection os = new ObservedSection();
	
	@Test
	public void testSetTitle() {
     	c.setTitle("ABCDE");
		assertEquals(c.getTitle(), "ABCDE");
	}

	@Test
	public void testCourseAddSlot() {
		c = new Course();
		c.setNumSlots(0);
		Slot s1 = new Slot();
		Slot s2 = new Slot();
		s1.setDay(0);
		s2.setDay(1);
		c.addSlot(s1);
		c.addSlot(s2);
		assertEquals(c.getSlot(0).getDay(),0);
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
	public void testCourseAddSectionFull() {
		c.setNumSections(20);
        c.addSection(new Section());
        assertEquals(c.getNumSections(),20);
	}
	
	@Test
	public void testChangeSection() {
		c = new Course();
		Section s1 = new Section();
		Section s2 = new Section();
		s1.setCode("L1");
		s2.setCode("L2");
		c.addSection(s1);
		assertEquals(c.getSection(3),null);
		assertEquals(c.getSection(0).getCode(),"L1");
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
	
	@Test
	public void testOSConstructor() {
		Section sec = new Section();
		sec.setCode("L1(1234)");
		sec.setCourse("COMP 3111 Software Engineering");
		String[] ins = new String[1];
		ins[0] = "Kenneth Leung";
		sec.setInstructors(ins);
		sec.setNumInstructors(1);
		sec.setEnrolled();
		ObservedSection nos = new ObservedSection(sec);
		assertEquals(nos.getCourseCode(),"COMP3111");
		assertEquals(nos.getCourseName(),"Software Engineering");
		assertEquals(nos.getInstructors(),"Kenneth Leung/n");
		assertEquals(nos.getSectionCode(),"L1(1234)");
		assertTrue(nos.getCheckBox().isSelected());
		assertEquals(nos.getSection().getCode(),"L1(1234)");
	}
	
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
}
