package comp3111.coursescraper;

public class Course {
	private static final int DEFAULT_MAX_SLOT = 20;
	private static final int DEFAULT_MAX_SECTION = 20;
	
	private String title ; 
	private String description;
	private String exclusion;
	private Slot [] slots;
	private int numSlots;
	private Section [] sections;
	private int numSections;

	private boolean commonCore = false;

	
	/**
	 * Course default constructor
	 */
	public Course() {
		slots = new Slot[DEFAULT_MAX_SLOT];
		for (int i = 0; i < DEFAULT_MAX_SLOT; i++) slots[i] = null;
		sections = new Section[DEFAULT_MAX_SECTION];
		for (int i = 0; i < DEFAULT_MAX_SECTION; i++) sections[i] = null;
		numSlots = 0;
		numSections = 0;
	}
	
	/**
	 * 
	 * @param s slot to add
	 */
	public void addSlot(Slot s) {
		if (numSlots >= DEFAULT_MAX_SLOT)
			return;
		slots[numSlots++] = s.clone();
	}
	
	/**
	 * 
	 * @param i index of the slot we want
	 * @return the slot
	 */
	public Slot getSlot(int i) {
		if (i >= 0 && i < numSlots)
			return slots[i];
		return null;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the exclusion
	 */
	public String getExclusion() {
		return exclusion;
	}

	/**
	 * @param exclusion the exclusion to set
	 */
	public void setExclusion(String exclusion) {
		this.exclusion = exclusion;
	}

	/**
	 * @return the numSlots
	 */
	public int getNumSlots() {
		return numSlots;
	}

	/**
	 * @param numSlots the numSlots to set
	 */
	public void setNumSlots(int numSlots) {
		this.numSlots = numSlots;
	}
	
	/**
	 * 
	 * @param s the section want to add
	 */
	public void addSection(Section s) {
		if (numSections >= DEFAULT_MAX_SECTION)
			return;
		sections[numSections++] = s.clone();
	}
	
	/**
	 * 
	 * @param i the index of section we want to get
	 * @return the section
	 */

	public Section getSection(int i) {
		if (i >= 0 && i < numSections)
			return sections[i];
		return null;
	}
	

	/**
	 * 
	 * @param s the section we want to change the last section to
	 */
	public void changeSection(Section s) {
		sections[numSections-1] = s;
	}
	
	/**
	 * 
	 * @return the number of sections
	 */
	public int getNumSections() {
		return numSections;
	}

	/**
	 * 
	 * @param n the number of sections to set
	 */
	public void setNumSections(int n) {
		numSections = n;
	}
	

	/**
	 * @return whether the course is a common core
	 */
	public boolean isCommonCore() {
		return commonCore;
	}
	
	/**
	 * @param b the boolean result whether the course is a common core to set
	 */
	public void setCommonCore(Boolean b) {
        commonCore = b;
	}
}

