package com.listeners;

import org.openqa.selenium.WebDriver;

public class LocalDriverManager {
	private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

	public synchronized static WebDriver getDriver() {
		return webDriver.get();
	}

	public synchronized static void setWebDriver(WebDriver driver) {
		webDriver.set(driver);
	}
}
