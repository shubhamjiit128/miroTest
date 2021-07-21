package com.extentreports;

import org.openqa.selenium.Platform;
import org.testng.ITestContext;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * This class contains all the methods related to extent report configuration
 * @author tm012
 *
 */
public class ExtentManager {

	static ExtentReports extent;
	private  static String testContext = new String();
	private static String retry = new String();

	/**
	 * Method to return extent report instance
	 * @return extent
	 */
	public static ExtentReports getInstance() {
		return extent;
	}

	/**
	 * Method to return test context as a suite name
	 * @return testContext
	 */
	public synchronized static String getTestSuiteName() {
		return testContext;
	}



	/**
	 * Method to get suite name  from test context and set to testContext
	 * @param ctx
	 */
	public synchronized static void getSuiteName(ITestContext ctx){
		testContext = ctx.getCurrentXmlTest().getSuite().getName();
	}


	/**
	 * ----------------------------Extent Report configuration---------------------------------
	 * This method will create an instance of extent report and set few properties for extent report
	 * @param fileName
	 * @return extent
	 */
	public static synchronized ExtentReports createInstance(String extentHtmlFileName) {
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(extentHtmlFileName);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		extent.setSystemInfo("OS", Platform.getCurrent().toString());
		extent.setSystemInfo("Host Name", "Shubham");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));

		htmlReporter.config().setDocumentTitle(getTestSuiteName() + " Automation Status Report");
		htmlReporter.config().setReportName(getTestSuiteName() + " Automation Status Report");
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setAutoCreateRelativePathMedia(true);
		htmlReporter.config().setCSS("css-string");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setJS("js-string");
		htmlReporter.config().setProtocol(Protocol.HTTPS);
		htmlReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");

		return extent;
	}

}
