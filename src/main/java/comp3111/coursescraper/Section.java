package comp3111.coursescraper;

public class Section {
	private String code;
	private String course;
	private String [] instructors;
	private int numInstructors;
	private Slot [] slots;
	private int numSlots;
	private static final int MAX_SLOT = 3;
	private boolean enrolled = false;
	
	/**
	 * Section default constructor
	 */
	public Section() {
		slots = new Slot[MAX_SLOT];
		for (int i = 0; i < MAX_SLOT; i++) slots[i] = null;
		code = null;
		course = null;
		instructors = new String[5];
		for (int i = 0; i < 5; i++) instructors[i] = null;
		numInstructors = 0;
		numSlots = 0;
	}
	
	/**
	 * 
	 * @return the cloned Section
	 */
	public Section clone() {
		Section s = new Section();
		s.code = this.code;
		s.course = this.course;
		s.instructors = this.instructors;
		s.numInstructors = this.numInstructors;
		s.numSlots = this.numSlots;
		s.slots = this.slots;
		return s;
	}
	
	/**
	 * 
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * 
	 * @return the course title
	 */
	public String getCourse() {
		return course;
	}
	
	/**
	 * 
	 * @param i the index of the instructor
	 * @return the instructor
	 */
	public String getInstructor(int i) {
		return instructors[i];
	}
	
	/**
	 * 
	 * @param c the code to set
	 */
	public void setCode(String c) {
		code = c;
	}
	
	/**
	 * 
	 * @param c the course to set
	 */
	public void setCourse(String c) {
		course = c;
	}
	
	/**
	 * 
	 * @param i the instructors to set
	 */
	public void setInstructors(String [] i) {
		instructors = i;
	}
	
	/**
	 * 
	 * @return the number of instructors
	 */
	public int getNumInstructors() {
		return numInstructors;
	}
	
	/**
	 * 
	 * @param n the number of instructors to set
	 */
	public void setNumInstructors(int n) {
		numInstructors = n;
	}
	
	/**
	 * 
	 * @param i the index of slot to get
	 * @return the slot
	 */
	public Slot getSlot(int i) {
		if((i>=0)&&(i<numSlots))
			return slots[i];
		else
			return null;
	}

	/**
	 * 
	 * @param s the slot to add
	 */
	public void addSlot(Slot s) {
		if (numSlots >= MAX_SLOT)
			return;
		slots[numSlots++] = s.clone();
	}

	/**
	 * 
	 * @return the number of slots
	 */
	public int getNumSlots() {
		return numSlots;
	}

	/**
	 * 
	 * @param n the number of slots to set
	 */
	public void setNumSlots(int n) {
		numSlots = n;
	}
	
	/**
	 * change the enrollment status
	 */
	public void changeEnroll() {
		enrolled = !enrolled;
	}
	
	/**
	 * enroll this section
	 */
	public void setEnrolled() {
		enrolled = true;
	}
	
	/**
	 * 
	 * @return whether this section is enrolled or not
	 */
	public boolean isEnrolled() {
		return enrolled;
	}
	
	/**
	 * 
	 * @return whether this section is lab or not
	 */
	public boolean isLab() {
		return code.charAt(0)=='L' && code.charAt(1)=='A' ;
	}
	
	/**
	 * 
	 * @return whether this section is tutorial or not
	 */
	public boolean isTutorial() {
		return code.charAt(0)=='T';
	}
	
	/**
	 * to check whether another section is the same as this one
	 * @param s the another section to be checked
	 * @return whether the two sections are the same
	 */
	public boolean sectionEquals(Section s) {
		if(code.equals(s.code) && course.equals(s.course))
			return true;
		return false;
	}
}