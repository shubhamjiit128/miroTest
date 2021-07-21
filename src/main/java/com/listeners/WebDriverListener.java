package com.listeners;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.extentreports.ExtentManager;
import com.extentreports.ExtentTestManager;
import com.utility.Constants;

public class WebDriverListener extends ExternalConfig implements IInvokedMethodListener, ITestListener, ISuiteListener {

	/**
	 * Prop object instance
	 */
	protected static Properties prop;
	public static String env = null;
	public static String brand = null;

	/**
	 * Invoked after the test class is instantiated and before any configuration
	 * method is called 1. Method call to set the suite name before execution
	 * starts and set the brand name for extent report name 2. Loading the
	 * properties file at once, that can be used anywhere on classes.
	 */
	@Override
	public void onStart(ITestContext context) {

		ExtentManager.getSuiteName(context);

	}

	/**
	 * This method would be invoked before any beforeXX/AfterXX methods
	 * Condition: If method is beforeXX/AfterXX then create an instance of the
	 * driver and set the driver
	 * 
	 * @return
	 */
	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult iTestResult) {
		try {
			String browserName=null;

			if (getInstance() == null) {
				prop = getProperties(Constants.BASE_CONFIG_FILE_KEY);
			}

			if (iTestResult.getMethod().isBeforeClassConfiguration()
					|| iTestResult.getMethod().isBeforeMethodConfiguration()) {

				if(System.getProperty("browser")!=null && !(System.getProperty("browser").isEmpty())) {
					browserName = System.getProperty("browser");
				}else {
					browserName = method.getTestMethod().getTestClass().getXmlTest().getLocalParameters()
							.get("browserName");
				}

				System.out.println("Browser Name:" + browserName);
				WebDriver driver = LocalDriverFactory.createInstance(browserName);
				LocalDriverManager.setWebDriver(driver);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method would be invoked after any beforeXX/AfterXX method Condition:
	 * If invoked method is test method then log the results in extent report
	 * and close the driver
	 */
	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult iTestresult) {
		try {
			if (iTestresult.getMethod().isTest()) {
				getresult(iTestresult);
				WebDriver driver = LocalDriverManager.getDriver();
				if (driver != null) {
					driver.quit();
				}
			}
		} catch (Exception e) {
			try {
				throw new Exception(e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Invoked after all the tests have run and all their Configuration methods
	 * have been called.
	 */
	@Override
	public void onFinish(ITestContext context) {
		ExtentManager.getInstance().flush();
	}

	/**
	 * This method will capture the screen and store the image in given path
	 * 
	 * @param imageName
	 * @param driver
	 * @return actualImagePath
	 * @throws Exception
	 */
	public synchronized String getScreenShot(String imageName, WebDriver driver) throws Exception {
		try {
			if (imageName.equals("")) {
				imageName = "blank";
			}
			File image = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String imagelocation = null;
			if (Platform.getCurrent().toString().equalsIgnoreCase("MAC")) {
				imagelocation = System.getProperty("user.dir") + "/extentreports/screenshots/";
			} else if (Platform.getCurrent().toString().contains("WIN")) {
				imagelocation = System.getProperty("user.dir") + "\\extentreports\\screenshots\\";
			}

			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
			String actualImagePath = imagelocation + imageName + "_" + formater.format(calendar.getTime()) + ".png";
			File destFile = new File(actualImagePath);
			FileUtils.copyFile(image, destFile);
			return actualImagePath;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * Method to log test status for pass, fail, skipped, and info
	 * 
	 * @param iTestResult
	 * @throws Exception
	 */
	public synchronized void getresult(ITestResult iTestResult) throws Exception {
		try {
			if (iTestResult.getStatus() == ITestResult.SUCCESS) {
			} else if (iTestResult.getStatus() == ITestResult.SKIP) {
				ExtentTestManager.getTest().log(Status.SKIP, iTestResult.getMethod().getMethodName()
						+ " test is skipped and skip reason is:-" + iTestResult.getThrowable());
			} else if (iTestResult.getStatus() == ITestResult.FAILURE) {
				ExtentTestManager.getTest().log(Status.ERROR,
						iTestResult.getName() + " | Exception: " + iTestResult.getThrowable());
				ExtentTestManager.getTest().addScreenCaptureFromPath(
						getScreenShot(iTestResult.getMethod().getMethodName(), LocalDriverManager.getDriver()));
			} else if (iTestResult.getStatus() == ITestResult.STARTED) {
				ExtentTestManager.getTest().log(Status.INFO, iTestResult.getName() + " test is started");
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * Invoked each time before a test will be invoked.
	 */
	@Override
	public void onTestStart(ITestResult iTestResult) {
	}

	/**
	 * Invoked each time a test succeeds.
	 */
	@Override
	public void onTestSuccess(ITestResult iTestResult) {
	}

	/**
	 * Invoked each time a test fails.
	 */
	@Override
	public void onTestFailure(ITestResult iTestResult) {
	}

	/**
	 * Invoked each time a test is skipped
	 */
	@Override
	public void onTestSkipped(ITestResult iTestResult) {
	}

	/**
	 * Invoked each time a method fails but has been annotated with
	 * successPercentage and this failure still keeps it within the success
	 * percentage requested.
	 */
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
	}


	@Override
	public void onStart(ISuite suite) {

	}

	@Override
	public void onFinish(ISuite suite) {
		//		zipExtentReport();
	}

}