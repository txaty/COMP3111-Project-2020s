/**
 * The ObservedSection Class is implemented to allow the section information to be shown on List, 
 * together with a checkbox used for the enrollment procedure
 * 
 * @author Chen Ziwei
 */
package comp3111.coursescraper;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

public class ObservedSection {
	private SimpleStringProperty courseCode;
	private SimpleStringProperty sectionCode;
	private SimpleStringProperty courseName;
	private SimpleStringProperty instructors;
	private CheckBox checkBox = new CheckBox();
	private Section section;
	
	
	/**
	 * Default Constructor;
	 */
	public ObservedSection() {}
	
	/**
	 * @param s the section to be observed
	 */
	public ObservedSection (Section s) {
		String courseTitle=s.getCourse();
		courseCode = new SimpleStringProperty(courseTitle.split(" ")[0]+courseTitle.split(" ")[1]);
		sectionCode = new SimpleStringProperty(s.getCode());
		courseName = new SimpleStringProperty(courseTitle.split(" ",3)[2]);
		String allInstructors = "";
		for(int i = 0; i < s.getNumInstructors(); i++)
			allInstructors += s.getInstructor(i) + "\n";
		instructors = new SimpleStringProperty(allInstructors);
		checkBox.setSelected(s.isEnrolled());
		section = s;
	}
	
	/**
	 * @return the course code
	 */
	public String getCourseCode() {
		return courseCode.get();
	}
	
	
	/**
	 * @return the section code
	 */
	public String getSectionCode() {
		return sectionCode.get();
	}
	
	/**
	 * @return the course name
	 */
	public String getCourseName() {
		return courseName.get();
	}
	
	/**
	 * @return the instructors
	 */
	public String getInstructors(){
		return instructors.get();
	}
	
	/**
	 * @return the checkbox 
	 */
	public CheckBox getCheckBox() {
		return checkBox;
	}
	
	/**
	 * @param cc the course code to set
	 */
    public void setCourseCode(String cc) {
    	courseCode.set(cc);
    }
	
    /**
     * @param sc the section code to set
     */
    public void setSectionCode(String sc) {
    	sectionCode.set(sc);
    }
    
    /**
     * @param cn the course name to set
     */
    public void setCourseName(String cn) {
    	courseName.set(cn);
    }
    
    /**
     * @param is the instructors to set
     */
    public void setInstructors(String is) {
    	instructors.set(is);
    }
    
    /** 
     * @param cb the checkbox to set
     */
    public void setCheckBox(CheckBox cb) {
    	checkBox = cb;
    }
    
    /**
     * @return the original section
     */
    public Section getSection() {
    	return section;
    }
    
    /**
     * @return the property of course code
     */
    public SimpleStringProperty courseCodeProperty() {
    	return courseCode;
    }
    
    /**
     * @return the property of section code
     */
    public SimpleStringProperty SectionCodeProperty() {
    	return sectionCode;
    }
   
    /**
     * @return the property of course name
     */
    public SimpleStringProperty courseNameProperty() {
    	return courseName;
    }
    
    /**
     * @return the property of instructors
     */
    public SimpleStringProperty instructorsProperty() {
    	return instructors;
    }

}