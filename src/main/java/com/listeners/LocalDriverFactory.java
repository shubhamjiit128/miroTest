package com.listeners;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.extentreports.ExtentManager;
import com.utility.Constants;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * This call contains methods to initiate extent report and web driver
 * initialization for different browsers
 * 
 * @author tm012
 *
 */
public class LocalDriverFactory {

	/**
	 * 1. Method to create instance of extent report 
	 * 2. Create driver instance and return driver
	 * @param browserName
	 * @return driver
	 * @throws Exception
	 */
	public static WebDriver createInstance(String browserName) throws Exception {
		RemoteWebDriver driver = null;

		try {
			/*************** Extent Report *****************/

			if (ExtentManager.getInstance() == null)
				if (Platform.getCurrent().toString().equalsIgnoreCase("MAC")) {
					ExtentManager.createInstance(System.getProperty("user.dir") + "/extentreports/"
							+ ExtentManager.getTestSuiteName() + "ExecutionReport" + ".html");
				} else if (Platform.getCurrent().toString().contains("WIN")) {
					ExtentManager.createInstance(System.getProperty("user.dir") + "\\extentreports\\"
							+ ExtentManager.getTestSuiteName() + "ExecutionReport" + ".html");
				}

			/********************** Driver Initialization *******************/

			if (browserName.toLowerCase().contains("chrome")) {
				WebDriverManager.chromedriver().setup();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("disable-infobars");
				driver = new ChromeDriver();
			} else if (browserName.toLowerCase().contains("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			} else if (browserName.toLowerCase().contains("edge")) {
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
			}

			if (driver != null) {
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
				driver.manage().window().maximize();
			}
		} catch (Exception e) {
			throw e;
		}
		return driver;
	}

}