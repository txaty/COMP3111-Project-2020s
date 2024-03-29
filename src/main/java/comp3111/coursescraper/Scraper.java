package comp3111.coursescraper;

import java.net.URLEncoder;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.DomText;
import java.util.Vector;

import org.apache.commons.lang3.ArrayUtils;

/**
 * WebScraper provide a sample code that scrape web content. After it is constructed, you can call the method scrape with a keyword, 
 * the client will go to the default url and parse the page by looking at the HTML DOM.  
 * <br>
 * In this particular sample code, it access to HKUST class schedule and quota page (COMP). 
 * <br>
 * https://w5.ab.ust.hk/wcq/cgi-bin/1830/subject/COMP
 *  <br>
 * where 1830 means the third spring term of the academic year 2018-19 and COMP is the course code begins with COMP.
 * <br>
 * Assume you are working on Chrome, paste the url into your browser and press F12 to load the source code of the HTML. You might be freak
 * out if you have never seen a HTML source code before. Keep calm and move on. Press Ctrl-Shift-C (or CMD-Shift-C if you got a mac) and move your
 * mouse cursor around, different part of the HTML code and the corresponding the HTML objects will be highlighted. Explore your HTML page from
 * body &rarr; div id="classes" &rarr; div class="course" &rarr;. You might see something like this:
 * <br>
 * <pre>
 * {@code
 * <div class="course">
 * <div class="courseanchor" style="position: relative; float: left; visibility: hidden; top: -164px;"><a name="COMP1001">&nbsp;</a></div>
 * <div class="courseinfo">
 * <div class="popup attrword"><span class="crseattrword">[3Y10]</span><div class="popupdetail">CC for 3Y 2010 &amp; 2011 cohorts</div></div><div class="popup attrword"><span class="crseattrword">[3Y12]</span><div class="popupdetail">CC for 3Y 2012 cohort</div></div><div class="popup attrword"><span class="crseattrword">[4Y]</span><div class="popupdetail">CC for 4Y 2012 and after</div></div><div class="popup attrword"><span class="crseattrword">[DELI]</span><div class="popupdetail">Mode of Delivery</div></div>	
 *    <div class="courseattr popup">
 * 	    <span style="font-size: 12px; color: #688; font-weight: bold;">COURSE INFO</span>
 * 	    <div class="popupdetail">
 * 	    <table width="400">
 *         <tbody>
 *             <tr><th>ATTRIBUTES</th><td>Common Core (S&amp;T) for 2010 &amp; 2011 3Y programs<br>Common Core (S&amp;T) for 2012 3Y programs<br>Common Core (S&amp;T) for 4Y programs<br>[BLD] Blended learning</td></tr><tr><th>EXCLUSION</th><td>ISOM 2010, any COMP courses of 2000-level or above</td></tr><tr><th>DESCRIPTION</th><td>This course is an introduction to computers and computing tools. It introduces the organization and basic working mechanism of a computer system, including the development of the trend of modern computer system. It covers the fundamentals of computer hardware design and software application development. The course emphasizes the application of the state-of-the-art software tools to solve problems and present solutions via a range of skills related to multimedia and internet computing tools such as internet, e-mail, WWW, webpage design, computer animation, spread sheet charts/figures, presentations with graphics and animations, etc. The course also covers business, accessibility, and relevant security issues in the use of computers and Internet.</td>
 *             </tr>	
 *          </tbody>
 *      </table>
 * 	    </div>
 *    </div>
 * </div>
 *  <h2>COMP 1001 - Exploring Multimedia and Internet Computing (3 units)</h2>
 *  <table class="sections" width="1012">
 *   <tbody>
 *    <tr>
 *        <th width="85">Section</th><th width="190" style="text-align: left">Date &amp; Time</th><th width="160" style="text-align: left">Room</th><th width="190" style="text-align: left">Instructor</th><th width="45">Quota</th><th width="45">Enrol</th><th width="45">Avail</th><th width="45">Wait</th><th width="81">Remarks</th>
 *    </tr>
 *    <tr class="newsect secteven">
 *        <td align="center">L1 (1765)</td>
 *        <td>We 02:00PM - 03:50PM</td><td>Rm 5620, Lift 31-32 (70)</td><td><a href="/wcq/cgi-bin/1830/instructor/LEUNG, Wai Ting">LEUNG, Wai Ting</a></td><td align="center">67</td><td align="center">0</td><td align="center">67</td><td align="center">0</td><td align="center">&nbsp;</td></tr><tr class="newsect sectodd">
 *        <td align="center">LA1 (1766)</td>
 *        <td>Tu 09:00AM - 10:50AM</td><td>Rm 4210, Lift 19 (67)</td><td><a href="/wcq/cgi-bin/1830/instructor/LEUNG, Wai Ting">LEUNG, Wai Ting</a></td><td align="center">67</td><td align="center">0</td><td align="center">67</td><td align="center">0</td><td align="center">&nbsp;</td>
 *    </tr>
 *   </tbody>
 *  </table>
 * </div>
 *}
 *</pre>
 * <br>
 * The code 
 * <pre>
 * {@code
 * List<?> items = (List<?>) page.getByXPath("//div[@class='course']");
 * }
 * </pre>
 * extracts all result-row and stores the corresponding HTML elements to a list called items. Later in the loop it extracts the anchor tag 
 * &lsaquo; a &rsaquo; to retrieve the display text (by .asText()) and the link (by .getHrefAttribute()).   
 * 
 *
 */

public class Scraper {
	private WebClient client;

	/**
	 * Default Constructor
	 */
	public Scraper() {
		client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
	}
	
	/**
	 * Add slot to the section, and add section to the course.
	 * 
	 * @param e scraped HTML content
	 * @param c course for adding section
	 * @param secondRow setting true if checking the second row, false otherwise
	 * @param sec section code
	 * @return true if the section is valid, false otherwise
	 */
	private boolean addSlotAndSection(HtmlElement e, Course c, boolean secondRow, String sec) {
		String times[] = e.getChildNodes().get(secondRow ? 0 : 3).asText().split(" ");
		int counter = 0;
/*----------Resolve different time layout----------*/
//		while (counter < times.length) {
//			times[counter].trim();
//			if (times[counter].indexOf('-') == 2) {
//				String timeList[] = times[counter].split("\n");
//				if (timeList.length == 2) {
//					times[counter] = timeList[1];
//					counter++;
//				} else {
//					times = ArrayUtils.remove(times, counter);
//				}
//			} else {
//				counter++;
//			}
//		}
		String venue = e.getChildNodes().get(secondRow ? 1 : 4).asText();
		if (secondRow) {
			counter = 0;
			while (times[counter].length() < 2) {
				counter++;
			}
			if (times[counter].equals("TBA"))
				return true;
			for (int j = 0; j < times[counter].length(); j += 2) {
				String code = times[counter].substring(j, j + 2);
				if (Slot.DAYS_MAP.get(code) == null)
					break;
				Slot s = new Slot();
				s.setDay(Slot.DAYS_MAP.get(code));
				s.setStart(times[counter + 1]);
				s.setEnd(times[counter + 3]);
				s.setVenue(venue);
				s.setSection(sec);
				Section newSec = c.getSection(c.getNumSections() - 1).clone();
				newSec.addSlot(s);
				c.changeSection(newSec);
				c.addSlot(s);
			}
			return true;
		} else {
			List<?> centerList = (List<?>) e.getByXPath(".//td[@align='center']");
			HtmlElement center = (HtmlElement) centerList.get(0);
			String section = center.asText();
			if ((section.charAt(0) == 'L') || (section.charAt(0) == 'T')) {
				Section se = new Section();
				se.setCode(section);
				se.setCourse(c.getTitle());
				String instructor[] = e.getChildNodes().get(5).asText().split("\n");
				if (instructor[0].equals("TBA") == false) {
					se.setInstructors(instructor);
					se.setNumInstructors(instructor.length);
				} else {
					se.setInstructors(null);
					se.setNumInstructors(0);
				}
				if (times[0].equals("TBA")) {
					c.addSection(se);
					return true;
				}
				counter = 0;
				while (times[counter].length() < 2) {
					counter++;
				}
				for (int j = 0; j < times[counter].length(); j += 2) {
					String code = times[counter].substring(j, j + 2);
					if (Slot.DAYS_MAP.get(code) == null)
						break;
					Slot s = new Slot();
					s.setDay(Slot.DAYS_MAP.get(code));
					s.setStart(times[counter + 1]);
					s.setEnd(times[counter + 3]);
					s.setVenue(venue);
					s.setSection(section);
					se.addSlot(s);
					c.addSlot(s);
				}
				c.addSection(se);
				return true;
			}
			return false;
		}

	}

	/**
	 * Scrape the course information according to URL, term and subject.
	 * 
	 * @param baseurl base URL
	 * @param term term to be scrapped
	 * @param sub subject to be scrapped
	 * @return return the result of the courst list is success, null otherwise
	 */
	public List<Course> scrape(String baseurl, String term, String sub) {
		try {
			HtmlPage page = client.getPage(baseurl + "/" + term + "/subject/" + sub);
			List<?> items = (List<?>) page.getByXPath("//div[@class='course']");
			Vector<Course> result = new Vector<Course>();
			for (int i = 0; i < items.size(); i++) {
				Course c = new Course();

				HtmlElement htmlItem = (HtmlElement) items.get(i);
				HtmlElement title = (HtmlElement) htmlItem.getFirstByXPath(".//h2");
				c.setTitle(title.asText());
				List<?> popupdetailslist = (List<?>) htmlItem.getByXPath(".//div[@class='popupdetail']/table/tbody/tr");
				HtmlElement exclusion = null;
				HtmlElement commonCore = null;
				for (HtmlElement e : (List<HtmlElement>) popupdetailslist) {
					HtmlElement t = (HtmlElement) e.getFirstByXPath(".//th");
					HtmlElement d = (HtmlElement) e.getFirstByXPath(".//td");
					if (t.asText().equals("ATTRIBUTES")) {
						commonCore = d;
					}
					if (t.asText().equals("EXCLUSION")) {
						exclusion = d;
					}
				}
				c.setExclusion((exclusion == null ? "null" : exclusion.asText()));
				c.setCommonCore(!(commonCore == null));
				List<?> sections = (List<?>) htmlItem.getByXPath(".//tr[contains(@class,'newsect')]");
				for (HtmlElement e : (List<HtmlElement>) sections) {
					boolean a = addSlotAndSection(e, c, false, null);
					e = (HtmlElement) e.getNextSibling();
					if (e != null && !e.getAttribute("class").contains("newsect") && a == true && c.getNumSlots() >= 1) {
						addSlotAndSection(e, c, true, c.getSlot(c.getNumSlots() - 1).getSection());
					}
				}
				result.add(c);
			}
			client.close();
			return result;
		} catch (com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException e) {
			return null;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	// For task 5, All Subject Search
	/**
	 * For all subject search purpose. Find the list of subjects according to the given URL and term.
	 * 
	 * @param baseurl base URL
	 * @param term term to be scrapped
	 * @return the list of subject strings, null otherwise
	 */
	public List<String> scrape(String baseurl, String term) {
		try {
			HtmlPage page = client.getPage(baseurl + "/" + term + "/");

			List<?> items = (List<?>) page.getByXPath("//div[@class='depts']/a");

			Vector<String> result = new Vector<String>();

			for (int i = 0; i < items.size(); i++) {
				String s = new String();
				HtmlElement htmlItem = (HtmlElement) items.get(i);
				s = htmlItem.asText();
				result.add(s);
			}
			return result;
		} catch (com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException e) {
			return null;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	// For task 6, SFQ
	/**
	 * Check if the HTML element is a course code
	 *  
	 * @param ifCourseCode HTML element
	 * @return true is the HTML element is a course code, false otherwise
	 */
	private boolean isCourseCode(HtmlElement ifCourseCode) {
		if (ifCourseCode == null)
			return false;
		String courseCode = ((HtmlElement) ifCourseCode).asText().trim();
		if (courseCode.length() >= 9 && Character.isDigit(courseCode.toCharArray()[5])
				&& Character.isDigit(courseCode.toCharArray()[6]) && Character.isDigit(courseCode.toCharArray()[7])
				&& Character.isDigit(courseCode.toCharArray()[8])) {
			return true;
		}
		return false;
	}
	
	/**
	 * Fetch the mean and SD for a course or an instructor
	 * 
	 * @param s course code or instructor name
	 * @return an array of two doubles (mean and SD respectively)
	 */
	private double[] getMeanAndSd(String s) {
		String firstSplit[] = s.split("\n");
		String secondSplit[] = firstSplit[0].split("\\(");
		double mean = 0.0;
		try {
			mean = Double.parseDouble(secondSplit[0]);
		} catch (NumberFormatException e) {
		}
		double sd = 0.0;
		String thridSplit[] = secondSplit[1].split("\\)");
		try {
			sd = Double.parseDouble(thridSplit[0]);
		} catch (NumberFormatException e) {
		}
		return new double[] { mean, sd };
	}
	
	/**
	 * Scrape the SFQ from the given URL.
	 * 
	 * @param urlString URL for scraping
	 * @return the Sfq object with information stored if success, null otherwise
	 */
	public Sfq scrapeSfq(String urlString) {
		try {
			HtmlPage page = client.getPage(urlString);
			Sfq sfq = new Sfq();
			List<?> listItems = (List<?>) page.getByXPath("//tr");
			int counter = 0;
			boolean isNewCourseCode = false;
			while (counter < listItems.size()) {
				HtmlElement rowItem = (HtmlElement) listItems.get(counter);
				List<?> ifCourseCheck = (List<?>) rowItem.getByXPath(".//td[@colspan='3']");
				if (ifCourseCheck.size() != 0 && isCourseCode(((HtmlElement) ifCourseCheck.get(0)))) {
					HtmlElement e;
					isNewCourseCode = false;
					do {
						counter++;
						e = (HtmlElement) listItems.get(counter);
						List<?> columnItems = (List<?>) e.getByXPath(".//td");
						isNewCourseCode = isCourseCode(e.getFirstByXPath(".//td[@colspan='3']"));
						if (isNewCourseCode == false) {
							if (columnItems.size() < 5
									|| ((HtmlElement) columnItems.get(0)).asText().trim().matches("(.*)Overall(.*)")) {
								counter++;
								continue;
							}
							String courseCodeChecker1 = ((HtmlElement) columnItems.get(1)).asText().trim();
							String courseCodeChecker2 = ((HtmlElement) columnItems.get(2)).asText().trim();
							if (courseCodeChecker1.isBlank() == false) {
								double result[] = getMeanAndSd(((HtmlElement) columnItems.get(3)).asText().trim());
								int size = 1;
								if (result[0] == 0 && result[1] == 0)
									size = 0;
								sfq.addCourseSfq(((HtmlElement) ifCourseCheck.get(0)).asText().trim(), result[0],
										result[1], size);
							} else if (courseCodeChecker2.isBlank() == false) {
								double result[] = getMeanAndSd(((HtmlElement) columnItems.get(4)).asText().trim());
								int size = 1;
								if (result[0] == 0 && result[1] == 0)
									size = 0;
								sfq.addInstructorSfq(((HtmlElement) columnItems.get(2)).asText().trim(), result[0],
										result[1], size);
							}
						}
					} while (e != null && counter < listItems.size() - 1 && isNewCourseCode == false);
				}
				if (isNewCourseCode == false)
					counter++;
			}
			return sfq;
		} catch (com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException e) {
			return null;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
}