package comp3111.coursescraper;

import java.util.Map;
import java.util.HashMap;

public class Sfq {
		private class SfqCourse {
			String code = "";
			double mean = 0;
			double sd = 0;
			int size = 0;
			
			public SfqCourse(String c, double m, double s, int n) {
				this.code = c;
				this.mean = m;
				this.sd = s;
				this.size = n;
			}
		}
		
		private class SfqInstructor {
			String name = "";
			double mean = 0;
			double sd = 0;
			int size = 0;
			
			public SfqInstructor(String n, double m, double s, int nn) {
				this.name = n;
				this.mean = m;
				this.sd = s;
				this.size = nn;
			}
		}
		
		private Map<String, SfqCourse> courseSfqList = new HashMap<String, SfqCourse>();
		private Map<String, SfqInstructor> instructorSfqList = new HashMap<String, SfqInstructor>();
		
		public boolean isScrapedCourse(String course) {
			return courseSfqList.containsKey(course);
		}
		
		public boolean isScrapedInstructor(String name) {
			return instructorSfqList.containsKey(name);
		}
		
		public double getCourseMean(String code) {
			if (courseSfqList.containsKey(code) == false)
				return 0;
			return courseSfqList.get(code).mean;
		}
		
		public double getCourseSd(String code) {
			if (courseSfqList.containsKey(code) == false)
				return 0;
			return courseSfqList.get(code).sd;
		}
		
		public double getInstructorMean(String name) {
			if (instructorSfqList.containsKey(name) == false)
				return 0;
			return instructorSfqList.get(name).mean;
		}
		
		public double getInstructorSd(String name) {
			if (instructorSfqList.containsKey(name) == false)
				return 0;
			return instructorSfqList.get(name).sd;
		}
		
		public int getCourseListSize() {
			return courseSfqList.size();
		}
		
		public int getInstructorListSize() {
			return instructorSfqList.size();
		}
		
		public void addCourseSfq(String code, double mean, double sd, int size) {
			if (courseSfqList.containsKey(code)) {
				int newSize = courseSfqList.get(code).size + size;
				double newMean = (courseSfqList.get(code).mean*courseSfqList.get(code).size + mean*size) / newSize;
				double newSd = (courseSfqList.get(code).sd*courseSfqList.get(code).size + sd*size) / newSize;
				SfqCourse c = new SfqCourse(code, newMean, newSd, newSize);
				courseSfqList.replace(code, c);
			} else {
				SfqCourse c = new SfqCourse(code, mean, sd, size);
				courseSfqList.put(code, c);
			}
		}
		
		public void addInstructorSfq(String name, double mean, double sd, int size) {
			if (instructorSfqList.containsKey(name)) {
				int newSize = instructorSfqList.get(name).size + size;
				double newMean = (instructorSfqList.get(name).mean*instructorSfqList.get(name).size + mean*size) / newSize;
				double newSd = (instructorSfqList.get(name).sd*instructorSfqList.get(name).size + sd*size) / newSize;
				SfqInstructor i = new SfqInstructor(name, newMean, newSd, newSize);
				instructorSfqList.replace(name, i);
			} else {
				SfqInstructor i = new SfqInstructor(name, mean, sd, size);
				instructorSfqList.put(name, i);
			}
		}
		
		public double[] findCourseSfq(String code) {
			if (courseSfqList.containsKey(code)) {
				return new double[] {courseSfqList.get(code).mean, courseSfqList.get(code).sd};
			} else {
				return null;
			}
		}
		
		public double[] findInstructorSfq(String name) {
			if (instructorSfqList.containsKey(name)) {
				return new double[] {instructorSfqList.get(name).mean, instructorSfqList.get(name).sd};
			} else {
				return null;
			}
		}
		
		public String getInstructorSfq() {
			String[] result = {""};
			instructorSfqList.forEach((k, v) -> 
				result[0] = result[0] + k + "\n"
					+ "Mean: " + String.format("%.1f", v.mean) + "\n"
					+ "SD:   " + String.format("%.1f", v.sd) + "\n\n"
				);
			return result[0];
		}
}
