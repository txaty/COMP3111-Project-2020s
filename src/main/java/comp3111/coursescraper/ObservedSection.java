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
	
	public String getCourseCode() {
		return courseCode.get();
	}
	
	public String getSectionCode() {
		return sectionCode.get();
	}
	
	public String getCourseName() {
		return courseName.get();
	}
	
	public String getInstructors(){
		return instructors.get();
	}
	
	public CheckBox getCheckBox() {
		return checkBox;
	}
	
    public void setCourseCode(String cc) {
    	courseCode.set(cc);
    }
	
    public void setSectionCode(String sc) {
    	sectionCode.set(sc);
    }
    
    public void setCourseName(String cn) {
    	courseName.set(cn);
    }
    
    public void setInstructors(String is) {
    	instructors.set(is);
    }

    public void setCheckBox(CheckBox cb) {
    	checkBox = cb;
    }

    public Section getSection() {
    	return section;
    }
    
    public SimpleStringProperty courseCodeProperty() {
    	return courseCode;
    }
    
    public SimpleStringProperty SectionCodeProperty() {
    	return sectionCode;
    }
   
    public SimpleStringProperty courseNameProperty() {
    	return courseName;
    }

    public SimpleStringProperty instructorsProperty() {
    	return instructors;
    }

}