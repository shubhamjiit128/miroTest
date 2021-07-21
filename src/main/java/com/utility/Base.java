package com.utility;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.extentreports.ExtentTestManager;
import com.listeners.LocalDriverManager;
import com.listeners.WebEventListener;

public class Base {

	public static WebDriver driver;
	/**
	 * Method to invoke browser and open application url, returns the current driver
	 * instance
	 * 
	 * @param url
	 * @return driver
	 */
	public CommonFunctions commFunc;
	public static String baseUrl = null;
	public static Properties prop = null;

	public synchronized WebDriver invokeBrowser() throws Exception {
		try {
			driver = LocalDriverManager.getDriver();
			EventFiringWebDriver eventFiringDriver = new EventFiringWebDriver(driver); 
			WebEventListener eventListener = new WebEventListener(); 
			eventFiringDriver.register(eventListener);
			eventFiringDriver.get(JSONReader.getURL(Constants.PROPERTY_FILE)); 
			return eventFiringDriver;

		} catch (Exception e) {
			throw e;
		}
	}



	public static class CommonFunctions {
		WebDriver driver;
		ExtentTest test;
		FluentWait<WebDriver> fluentWait = null;
		WebDriverWait wait = null;
		WebElement element;

		public CommonFunctions(WebDriver driver) {
			this.driver = driver;
		}

		/**
		 * Common method for fluent wait
		 * 
		 * @param timeOutInSeconds
		 * @param pollingEveryInMiliSec
		 * @return fluentWait
		 */
		public FluentWait<WebDriver> getFluentWait() {
			fluentWait = new FluentWait<WebDriver>(driver).withTimeout(Constants.SECONDS)
					.pollingEvery(Constants.MILISECONDS).ignoring(NoSuchElementException.class)
					.ignoring(NoSuchElementException.class).ignoring(ElementNotVisibleException.class)
					.ignoring(StaleElementReferenceException.class).ignoring(NoSuchFrameException.class);
			return fluentWait;
		}

		public synchronized void clickWhenReady(WebElement element) {
			String text = null;
			try {
				wait = new WebDriverWait(driver, Constants.DEFAULT_WAIT_4_ELEMENT);
				wait.until(ExpectedConditions.visibilityOf(element));
				wait.until(ExpectedConditions.elementToBeClickable(element));
				text = element.getText();
				element.click();
				ExtentTestManager.getTest().log(Status.PASS,text+" is clicked.");
			} catch (Exception e) {
				ExtentTestManager.getTest().log(Status.FAIL,text+" could not get clicked");
				throw e;
			}
		}


		public boolean isElementPresent(WebElement element) {
			if (element == null)
				throw new IllegalArgumentException("Locator cannot be Null");
			try {
				fluentWait = getFluentWait();
				fluentWait.until(ExpectedConditions.visibilityOf(element));
				if(element.isDisplayed()) {
					ExtentTestManager.getTest().log(Status.PASS,element.getText()+" is present on webpage.");
				}
				return (element.isDisplayed()) ? true : false;
			} catch (Exception e) {
				ExtentTestManager.getTest().log(Status.FAIL,"Element is not present on webpage.");
				return false;
			}
		}



		public synchronized void sendWhenReady(WebElement element, String value) {
			try {
				wait = new WebDriverWait(driver, Constants.DEFAULT_WAIT_4_ELEMENT);
				WebElement element1 = wait.until(ExpectedConditions.visibilityOf(element));
				element1.sendKeys(String.valueOf(value));
				ExtentTestManager.getTest().log(Status.PASS,element.getText()+" value is entered as :" + value);
			} catch (Exception e) {
				ExtentTestManager.getTest().log(Status.FAIL,"Failed to enter "+element.getText()+" value as :" + value);
				throw e;
			}
		}

	}

	/**
	 * This class contains Soft Assertion validation methods and unimplemented
	 * methods.
	 * 
	 * @author tm012
	 */

	public static class TestSoftAssert extends SoftAssert {

		public List<String> messages = new ArrayList<>();

		@Override
		public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
			messages.add("onAssertFailure");
		}

		@Override
		public void assertAll() {
			try {
				messages.add("assertAll");
				super.assertAll();
			} catch (AssertionError e) {
				throw (e);
			}
		}

		/**
		 * Assertion method to Soft Assert the validation and log the respective result.
		 * 
		 * @param condition,
		 *            failureMessage
		 * @throws IOException
		 * @throws Exception
		 */

		public synchronized void softAssertEquals(int actual, int expected, String failureMessage)
				throws IOException, Exception {

			this.assertEquals(actual, expected, failureMessage);

			if (this.messages.contains("onAssertFailure")) {
				ExtentTestManager.getTest()
				.log(Status.FAIL,
						failureMessage + "<br> FAILED AT METHOD: "
								+ Thread.currentThread().getStackTrace()[2].getMethodName())
				.addScreenCaptureFromPath(
						getScreenShot(Thread.currentThread().getStackTrace()[2].getMethodName(),
								LocalDriverManager.getDriver()));
			} else {
				ExtentTestManager.getTest().log(Status.PASS, failureMessage.replace("not", ""));
			}

			messages.clear();
		}

		public synchronized void softAssertTrue(Boolean condition, String failureMessage)
				throws IOException, Exception {

			this.assertTrue(condition, failureMessage);

			if (this.messages.contains("onAssertFailure")) {
				ExtentTestManager.getTest()
				.log(Status.FAIL,
						failureMessage + "<br> FAILED AT METHOD: "
								+ Thread.currentThread().getStackTrace()[2].getMethodName())
				.addScreenCaptureFromPath(
						getScreenShot(Thread.currentThread().getStackTrace()[2].getMethodName(),
								LocalDriverManager.getDriver()));
			} else {
				ExtentTestManager.getTest().log(Status.PASS, failureMessage.replace("not", ""));
			}

			messages.clear();
		}

	}



	/**
	 * This method will capture the screen and store the image in given path
	 * 
	 * @param imageName
	 * @param driver
	 * @return actualImagePath
	 * @throws Exception
	 */
	public static String getScreenShot(String imageName, WebDriver driver) throws Exception {
		try {
			if (imageName.equals("")) {
				imageName = "blank";
			}
			File image = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String imagelocation = System.getProperty("user.dir") + "\\extentreports\\screenshots\\";
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



}
