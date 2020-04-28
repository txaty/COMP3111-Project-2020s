package comp3111.coursescraper;

public class Section {
	private String code;
	private String course;
	private String [] instructors;
	private int numInstructors;
	private Slot [] slots;
	private int numSlots;
	private static final int MAX_SLOT = 3;
	
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
	
	public String getCode() {
		return code;
	}
	
	public String course() {
		return course;
	}
	
	public String getInstructor(int i) {
		return instructors[i];
	}
	
	public void setCode(String c) {
		code = c;
	}
	
	public void setCourse(String c) {
		course = c;
	}
	
	public void setInstructors(String [] i) {
		instructors = i;
	}
	
	public int getNumInstructors() {
		return numInstructors;
	}
	
	public void setNumInstructors(int n) {
		numInstructors = n;
	}
	
	public Slot getSlot(int i) {
		if((i>=0)&&(i<numSlots))
			return slots[i];
		else
			return null;
	}
	
	public void addSlot(Slot s) {
		if (numSlots >= MAX_SLOT)
			return;
		slots[numSlots++] = s.clone();
	}
	
	public int getNumSlots() {
		return numSlots;
	}
	
	public void setNumSlots(int n) {
		numSlots = n;
	}
}