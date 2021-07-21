package com.extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

/**
 * This class contains methods to create/get instance of extent report
 * 
 * @author tm012
 *
 */
public class ExtentTestManager {

	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	private static ExtentReports extent = ExtentManager.getInstance();
	static ExtentTest test;

	/**
	 * Method to get extent test instance and return
	 * 
	 * @return extent
	 */
	public synchronized static ExtentTest getTest() {
		return (ExtentTest) extentTest.get();
	}

	/**
	 * Method to remove extent test logs
	 * 
	 * @return extent
	 */
	public synchronized static void removeTest() {
		extent.removeTest(test);
	}

	/**
	 * Method to create extent test instance
	 * 
	 * @param name
	 * @param description
	 * @return extent
	 */
	public synchronized static ExtentTest createTest(String name, String description) {
		test = extent.createTest(name, description);
		extentTest.set(test);
		return getTest();
	}

	/**
	 * Method to log info message on reporter
	 * 
	 * @param message
	 */
	public synchronized static void log(String message) {
		getTest().info(message);
	}
//
//	public synchronized static void logJsonInfo(String assertError, String pageName, String message) {
//		try {
//			Markup jsonMarkup = MarkupHelper.createCodeBlock(message, CodeLanguage.JSON);
//			Markup accessibilityError = MarkupHelper.createCodeBlock(assertError);
//			Markup accessibilityLabel = MarkupHelper.createLabel("Accessibility Issue | Page Name : " + pageName, ExtentColor.BLUE);
//
//			getTest().info("<details>" + "<summary>" + "<b>" + "<font color=" + "red>"
//					+ accessibilityLabel.getMarkup() + " | Detailed Violations : Click on the link to see JSON message" + "</font>" + "</b >" + "</summary>"
//					+ "<br>" + "<h6>" + "<b>" + jsonMarkup.getMarkup() + "</b>"
//					+ "</h6>" + "</br>" + "</details>");
//
//			getTest().info("<details>" + "<summary>" + "<b>" + "<font color=" + "red>"
//					+ accessibilityLabel.getMarkup() + " | Assertion Error : Click on the link to see message" + "</font>" + "</b >" + "</summary>"
//					+ "<br>" + "<h6>" + "<b>" + accessibilityError.getMarkup() + "</b>"
//					+ "</h6>" + "</br>" + "</details>");
//		} catch (Exception e) {
//		}
//	}

}
