package comp3111.coursescraper;

import java.util.Map;
import java.util.HashMap;

/**
 * The Sfq class is implemented to store the information scraped from SFQ URL.
 * It also provide methods for fetching data about SFQ of courses and instructors.
 * 
 * 
 * @author TIAN Xiangan
 */
public class Sfq {
	/**
	 *  Default constructor
	 */
	public Sfq() {};
	
	/**
	 * The SfqCourse class is used to store the SFQ of a course
	 * 
	 * @author TIAN Xiangan
	 */
	private class SfqCourse {
		String code = "";
		double mean = 0;
		double sd = 0;
		int size = 0;
		
		/**
		 *  Default Constructor
		 */
		private SfqCourse() {
			this.code = "";
			this.mean = 0.0;
			this.sd = 0.0;
			this.size = 0;
		}
		
		/**
		 * Constructor
		 * 
		 * @param c course code
		 * @param m mean of the course SFQ
		 * @param s SD of the course SFQ
		 * @param n number of SFQ counted
		 */
		private SfqCourse(String c, double m, double s, int n) {
			this.code = c;
			this.mean = m;
			this.sd = s;
			this.size = n;
		}
	}
	
	/**
	 * The SfqCourse class is used to store the SFQ of a course
	 * 
	 * @author TIAN Xiangan
	 */
	private class SfqInstructor {
		String name = "";
		double mean = 0;
		double sd = 0;
		int size = 0;
		
		/**
		 * Default constructor
		 */
		public SfqInstructor() {
			this.name = "";
			this.mean = 0.0;
			this.sd = 0.0;
			this.size = 0;
		}
		
		/**
		 * Constructor
		 * 
		 * @param n name of the instructor
		 * @param m mean of the instructor SFQ
		 * @param s SD of the instructor SFQ
		 * @param nn number of SFQ counted 
		 */
		public SfqInstructor(String n, double m, double s, int nn) {
			this.name = n;
			this.mean = m;
			this.sd = s;
			this.size = nn;
		}
	}

	private Map<String, SfqCourse> courseSfqList = new HashMap<String, SfqCourse>();
	private Map<String, SfqInstructor> instructorSfqList = new HashMap<String, SfqInstructor>();
	
	/**
	 * Check if SFQ of the course is scraped
	 * 
	 * @param course course code
	 * @return true if the course SFQ is scraped, false otherwise
	 */
	public boolean isScrapedCourse(String course) {
		return courseSfqList.containsKey(course);
	}
	
	/**
	 * Check if SFQ of the instructor is scraped
	 * 
	 * @param name instructor name
	 * @return true if the instructor SFQ is scraped, false otherwise
	 */
	public boolean isScrapedInstructor(String name) {
		return instructorSfqList.containsKey(name);
	}
	
	/**
	 * Fetch the mean of a specific course
	 * 
	 * @param code course code
	 * @return the mean of the course
	 */
	public double getCourseMean(String code) {
		if (courseSfqList.containsKey(code) == false)
			return 0;
		return courseSfqList.get(code).mean;
	}
	
	/**
	 * Fetch the SD of a specific course
	 * 
	 * @param code course code
	 * @return the SD of the course
	 */
	public double getCourseSd(String code) {
		if (courseSfqList.containsKey(code) == false)
			return 0;
		return courseSfqList.get(code).sd;
	}

	/**
	 * Fetch the mean of a specific instructor
	 * 
	 * @param name instructor name
	 * @return the mean of the instructor 
	 */
	public double getInstructorMean(String name) {
		if (instructorSfqList.containsKey(name) == false)
			return 0;
		return instructorSfqList.get(name).mean;
	}
	
	/**
	 * Fetch the mean of a specific instructor
	 * 
	 * @param name instructor name
	 * @return the SD of the instructor
	 */
	public double getInstructorSd(String name) {
		if (instructorSfqList.containsKey(name) == false)
			return 0;
		return instructorSfqList.get(name).sd;
	}
	
	/**
	 * Fetch the size of the hash map for storing the course SFQ
	 * 
	 * @return size of hash map of course SFQ
	 */
	public int getCourseListSize() {
		return courseSfqList.size();
	}
	
	/**
	 * Fetch the size of the hash map for storing the instructor SFQ
	 * 
	 * @return size of hash map of instructor SFQ
	 */
	public int getInstructorListSize() {
		return instructorSfqList.size();
	}
	
	/**
	 * Update the SFQ of a course when scraping the data. If the course has not recorded,
	 * add a new one. If has, update its SFQ.
	 * 
	 * @param code course code
	 * @param mean mean value to be updated
	 * @param sd SD value to be updated
	 * @param size number of the new SFQ to be counted
	 */
	public void addCourseSfq(String code, double mean, double sd, int size) {
		if (courseSfqList.containsKey(code)) {
			int newSize = courseSfqList.get(code).size + size;
			double newMean = (courseSfqList.get(code).mean * courseSfqList.get(code).size + mean * size) / newSize;
			double newSd = (courseSfqList.get(code).sd * courseSfqList.get(code).size + sd * size) / newSize;
			SfqCourse c = new SfqCourse(code, newMean, newSd, newSize);
			courseSfqList.replace(code, c);
		} else {
			SfqCourse c = new SfqCourse(code, mean, sd, size);
			courseSfqList.put(code, c);
		}
	}

	/**
	 * Update the SFQ of an instructor when scraping the data. If the instructor has not recorded,
	 * add a new one. If has, update his/her SFQ.
	 * 
	 * @param name instructor name
	 * @param mean mean value to be updated
	 * @param sd SD value to be updated
	 * @param size number of the new SFQ to be counted
	 */
	public void addInstructorSfq(String name, double mean, double sd, int size) {
		if (instructorSfqList.containsKey(name)) {
			int newSize = instructorSfqList.get(name).size + size;
			double newMean = (instructorSfqList.get(name).mean * instructorSfqList.get(name).size + mean * size)
					/ newSize;
			double newSd = (instructorSfqList.get(name).sd * instructorSfqList.get(name).size + sd * size) / newSize;
			SfqInstructor i = new SfqInstructor(name, newMean, newSd, newSize);
			instructorSfqList.replace(name, i);
		} else {
			SfqInstructor i = new SfqInstructor(name, mean, sd, size);
			instructorSfqList.put(name, i);
		}
	}
	
	/**
	 * Find the course SFQ in the scraped SFQs.
	 * 
	 * @param code course code
	 * @return the array of the two doubles (mean and SD respectively) if the course is scraped, null otherwise
	 */
	public double[] findCourseSfq(String code) {
		if (courseSfqList.containsKey(code)) {
			return new double[] { courseSfqList.get(code).mean, courseSfqList.get(code).sd };
		} else {
			return null;
		}
	}

	/**
	 * Find the course SFQ in the scraped SFQs.
	 * 
	 * @param name instructor name
	 * @return the array of the two doubles (mean and SD respectively) if the instructor is scraped, null otherwise
	 */
	public double[] findInstructorSfq(String name) {
		if (instructorSfqList.containsKey(name)) {
			return new double[] { instructorSfqList.get(name).mean, instructorSfqList.get(name).sd };
		} else {
			return null;
		}
	}
	
	/**
	 *  List the information of instructor SFQs
	 * @return the instructor SFQ String to print
	 */
	public String getInstructorSfq() {
		String[] result = { "" };
		instructorSfqList.forEach((k, v) -> result[0] = result[0] + k + "\n" + "Mean: " + String.format("%.1f", v.mean)
				+ "\n" + "SD:   " + String.format("%.1f", v.sd) + "\n\n");
		return result[0];
	}
}
