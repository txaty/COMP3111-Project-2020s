package comp3111.coursescraper;

import org.junit.Test;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;

import comp3111.coursescraper.Course;
import comp3111.coursescraper.Section;
import comp3111.coursescraper.ObservedSection;
import comp3111.coursescraper.Slot;
import javafx.fxml.FXML;
import javafx.scene.Scene;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;


public class ItemTest {
	
	private Scene s;
	
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

	@Test
	public void testSlotToString() {
		Slot s = new Slot();
		s.setDay(0);
		s.setStart("10:30AM");
		s.setEnd("11:50AM");
		s.setVenue("LT");
		assertEquals(s.toString(), "Mo10:30-11:50:LT");
	}
	
	@Test
	public void testSlotGetStart() {
		LocalTime lt = LocalTime.parse("10:00AM", DateTimeFormatter.ofPattern("hh:mma", Locale.US));
		Slot s = new Slot();
		s.setStart("10:00AM");
		assertEquals(s.getStart(), lt);
	}
	
	@Test
	public void testSlotGetEnd() {
		LocalTime lt = LocalTime.parse("10:00AM", DateTimeFormatter.ofPattern("hh:mma", Locale.US));
		Slot s = new Slot();
		s.setEnd("10:00AM");
		assertEquals(s.getEnd(), lt);
	}
	
	@Test
	public void testSetTitle() {
		Course c = new Course();
     	c.setTitle("ABCDE");
		assertEquals(c.getTitle(), "ABCDE");
	}

	@Test
	public void testCourseAddSlot() {
		Course c = new Course();
		c.setNumSlots(0);
		Slot s1 = new Slot();
		Slot s2 = new Slot();
		c.addSlot(s1);
		c.addSlot(s2);
		assertEquals(c.getNumSlots(),2);	
	}
	
	@Test
	public void testCourseAddSlotFull() {
		Course c = new Course();
		c.setNumSlots(20);
		c.addSlot(new Slot());
		assertEquals(c.getNumSlots(), 20);
	}
	
	@Test
	public void testCourseGetSlot1() {
		Course c = new Course();
		Slot s = new Slot();
		s.setDay(0);
		c.addSlot(s);
		assertEquals(c.getSlot(0).getDay(), 0);
	}
	
	@Test
	public void testCourseGetSlot2() {
		Course c = new Course();
		Slot s = new Slot();
		s.setDay(0);
		c.addSlot(s);
		assertEquals(c.getSlot(1), null);
	}
	
	@Test
	public void testCourseGetSlot3() {
		Course c = new Course();
		Slot s = new Slot();
		s.setDay(0);
		c.addSlot(s);
		assertEquals(c.getSlot(-1), null);
	}
	
	@Test
	public void testSetDescription() {
		Course c = new Course();
		c.setDescription("interesting");
		assertEquals(c.getDescription(), "interesting");
	}
	
	@Test
	public void testSetExclusion() {
		Course c = new Course();
		c.setExclusion("null");
		assertEquals(c.getExclusion(),"null");
	}
	
	@Test 
	public void testCourseSetNumSlots() {
		Course c = new Course();
		c.setNumSlots(3);
		assertEquals(c.getNumSlots(),3);
	}
	
	@Test
	public void testCourseAddSection() {
		Course c = new Course();
		c.setNumSections(0);
		Section s1 = new Section();
		Section s2 = new Section();
		c.addSection(s1);
		c.addSection(s2);
		assertEquals(c.getNumSections(),2);	
	}
	
	@Test
	public void testCourseAddSectionFull() {
		Course c = new Course();
		c.setNumSections(20);
        c.addSection(new Section());
        assertEquals(c.getNumSections(),20);
	}
	
	@Test
	public void testCourseGetSection1() {
		Course c = new Course();
		Section s = new Section();
		s.setCode("test");
		c.addSection(s);
		assertEquals(c.getSection(-1), null);
	}
	
	@Test
	public void testCourseGetSection2() {
		Course c = new Course();
		Section s = new Section();
		s.setCode("test");
		c.addSection(s);
		assertEquals(c.getSection(2), null);
	}
	
	@Test
	public void testChangeSection() {
		Course c = new Course();
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
		Course c = new Course();
		c.setNumSections(7);
		assertEquals(c.getNumSections(),7);
	}
	
	@Test
	public void testSetCommonCore() {
		Course c = new Course();
		assertFalse(c.isCommonCore());
		c.setCommonCore(true);
		assertTrue(c.isCommonCore());
	}
	
	
	
	/*------------Task 5 Test------------*/
	
	/*----------End Task 5 Test----------*/
	
	/*------------Task 6 Test------------*/
	@Test
	public void testAddCourseSfq1() {
		Sfq sfq = new Sfq();
		sfq.addCourseSfq("test", 1.1, 2.2, 1);
		assertTrue(sfq.isScrapedCourse("test"));
	}
	
	@Test
	public void testAddCourseSfq2() {
		Sfq sfq = new Sfq();
		sfq.addCourseSfq("test", 1.1, 2.2, 1);
		sfq.addCourseSfq("test", 1.3, 2.2, 1);
		boolean check = Math.abs(sfq.findCourseSfq("test")[0]-1.2) <= 0.000001;
		assertTrue(check);
	}
	
	@Test
	public void testAddInstructorSfq1() {
		Sfq sfq = new Sfq();
		sfq.addInstructorSfq("test", 1.1, 2.2, 1);
		assertTrue(sfq.isScrapedInstructor("test"));
	}
	
	@Test
	public void testAddInstructorSfq2() {
		Sfq sfq = new Sfq();
		sfq.addInstructorSfq("test", 1.1, 2.2, 1);
		sfq.addInstructorSfq("test", 1.3, 2.2, 1);
		boolean check = Math.abs(sfq.findInstructorSfq("test")[0]-1.2) <= 0.000001;
		assertTrue(check);
	}
	
	@Test
	public void testFindCourseSfq() {
		Sfq sfq = new Sfq();
		assertEquals(sfq.findCourseSfq("test"), null);
	}
	
	@Test
	public void testFindInstructorSfq() {
		Sfq sfq = new Sfq();
		assertEquals(sfq.findInstructorSfq("test"), null);
	}
	
	@Test
	public void testGetCourseMean1() {
		Sfq sfq = new Sfq();
		sfq.addCourseSfq("test", 1.1, 2.2, 1);
		boolean check = Math.abs(sfq.getCourseMean("test1")-0.0) <= 0.000001;
		assertTrue(check);
	}
	
	@Test
	public void testGetCourseMean2() {
		Sfq sfq = new Sfq();
		sfq.addCourseSfq("test", 1.1, 2.2, 1);
		boolean check = Math.abs(sfq.getCourseMean("test")-1.1) <= 0.000001;
		assertTrue(check);
	}
	
	@Test
	public void testGetInstructorMean1() {
		Sfq sfq = new Sfq();
		sfq.addInstructorSfq("test", 1.1, 2.2, 1);
		boolean check = Math.abs(sfq.getInstructorMean("test1")-0.0) <= 0.000001;
		assertTrue(check);
	}
	
	@Test
	public void testGetInstructorMean2() {
		Sfq sfq = new Sfq();
		sfq.addInstructorSfq("test", 1.1, 2.2, 1);
		boolean check = Math.abs(sfq.getInstructorMean("test")-1.1) <= 0.000001;
		assertTrue(check);
	}
	
	@Test
	public void testGetCourseSd1() {
		Sfq sfq = new Sfq();
		sfq.addCourseSfq("test", 1.1, 2.2, 1);
		boolean check = Math.abs(sfq.getCourseSd("test1")-0.0) <= 0.000001;
		assertTrue(check);
	}
	
	@Test
	public void testGetCourseSd2() {
		Sfq sfq = new Sfq();
		sfq.addCourseSfq("test", 1.1, 2.2, 1);
		boolean check = Math.abs(sfq.getCourseSd("test")-2.2) <= 0.000001;
		assertTrue(check);
	}
	
	@Test
	public void testGetInstructorSd1() {
		Sfq sfq = new Sfq();
		sfq.addInstructorSfq("test", 1.1, 2.2, 1);
		boolean check = Math.abs(sfq.getInstructorSd("test1")-0.0) <= 0.000001;
		assertTrue(check);
	}
	
	@Test
	public void testGetInstructorSd2() {
		Sfq sfq = new Sfq();
		sfq.addInstructorSfq("test", 1.1, 2.2, 1);
		boolean check = Math.abs(sfq.getInstructorSd("test")-2.2) <= 0.000001;
		assertTrue(check);
	}
	
	@Test
	public void testGetCourseListSize() {
		Sfq sfq = new Sfq();
		assertEquals(sfq.getCourseListSize(), 0);
	}
	
	@Test
	public void testGetInstructorListSize() {
		Sfq sfq = new Sfq();
		assertEquals(sfq.getInstructorListSize(), 0);
	}
	
	@Test
	public void testGetInstructorSfq() {
		Sfq sfq = new Sfq();
		sfq.addInstructorSfq("test", 1.2, 1.1, 1);
		assertEquals(sfq.getInstructorSfq(), "test\nMean: 1.2\nSD:   1.1\n\n");
	}
	
	@Test
	public void testScrape1() {
		Scraper s = new Scraper();
		List<Course> c = s.scrape("https://w5.ab.ust.hk/wcq/cgi-bin/", "1910", "COMP");
		assertEquals(c.size(), 62);
	}
	
	@Test
	public void testScrape2() {
		Scraper s = new Scraper();
		List<Course> c = s.scrape("https://w5.ab.ust.hk/wcq/cgi-bin/", "1910", "MGMT");
		assertEquals(c.size(), 30);
	}
	
	@Test
	public void testScrape3() {
		Scraper s = new Scraper();
		List<Course> c = s.scrape("https://w5.ab.ust.hk/wcq/cgi-bin/", "1910", "---");
		assertEquals(c, null);
	}
	
	@Test
	public void testScrape4() {
		Scraper s = new Scraper();
		List<String> c = s.scrape("https://w5.ab.ust.hk/wcq/cgi-bin/", "1910");
		assertEquals(c.size(), 75);
	}
	
	@Test
	public void testScrape5() {
		Scraper s = new Scraper();
		List<String> c = s.scrape("https://w5.ab.ust.hk/wcq/cgi-bin/", "----");
		assertEquals(c, null);
	}
	
	@Test
	public void testScrapeSfq1() {
		Scraper s = new Scraper();
		Sfq sfq = s.scrapeSfq("file:\\\\D:\\Desktop\\3111Proj\\SchoolSummaryReport.html  " );
		assertEquals(sfq.getCourseListSize(), 197);
	}
	
	@Test
	public void testScrapeSfq2() {
		Scraper s = new Scraper();
		Sfq sfq = s.scrapeSfq(" ");	
		assertEquals(sfq, null);
	}
	/*----------End Task 6 Test----------*/

}

