package page_object;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.extentreports.ExtentTestManager;
import com.utility.Base;

public class SuccessPage extends Base{
	
	WebDriver driver;
	ExtentTest test;
	SoftAssert softassert;
	TestSoftAssert softAssert;
	
	@FindBy(id = "code")
	public WebElement nameField;
	
	public SuccessPage(WebDriver driver, TestSoftAssert softAssert) {
		this.test = ExtentTestManager.getTest();
		this.driver = driver;
		this.softAssert = softAssert;
		PageFactory.initElements(driver, this);
		commFunc = new CommonFunctions(driver);
	}
	
	
	  public boolean isCodeFieldDisplayed() {
		  	if(commFunc.isElementPresent(nameField)) {
		  		return true;
		  	}
		  	else
			return false;
		  }

}
