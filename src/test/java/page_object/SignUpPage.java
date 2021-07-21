package page_object;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.extentreports.ExtentTestManager;
import com.utility.Base;

import inputs.User;

public class SignUpPage extends Base{

	WebDriver driver;
	ExtentTest test;
	SoftAssert softassert;
	TestSoftAssert softAssert;

	public SignUpPage(WebDriver driver, TestSoftAssert softAssert) {
		this.test = ExtentTestManager.getTest();
		this.driver = driver;
		this.softAssert = softAssert;
		PageFactory.initElements(driver, this);
		commFunc = new CommonFunctions(driver);
	}


	@FindBy(xpath = "//*[@data-autotest-id='mr-form-signup-name-1']")
	public WebElement nameField;

	@FindBy(xpath = "//*[@data-autotest-id='mr-form-signup-email-1']")
	public WebElement emailField;


	@FindBy(xpath = "//*[@data-autotest-id='mr-form-signup-password-1']")
	public WebElement passWordField;


	@FindBy(css = ".signup__checkbox-wrap:nth-child(1) .mr-checkbox-1__icon")
	public WebElement termsAndConditionCheckbox;


	@FindBy(xpath = "//*[@data-autotest-id='mr-form-signup-btn-start-1']")
	public WebElement signupButton;


	@FindBy(xpath = "//*[@data-autotest-id='mr-error-emailnotunique-1']")
	public WebElement singupErrorMessageForRegisteredEmail;
	
	@FindBy(xpath = "//div[@id='emailformat']")
	public WebElement invalidEmailAddress;


	public SignUpPage enterDetails(User user) {
		commFunc.sendWhenReady(nameField, user.getName());
		commFunc.sendWhenReady(emailField, user.getEmail());
		commFunc.sendWhenReady(passWordField, user.getPassword());
		return this;
	}

	public SignUpPage selectTermsAndCondition() {
		commFunc.clickWhenReady(termsAndConditionCheckbox);
		return this;
	}

	public SignUpPage submitUserDetailsToSignup() {
		commFunc.clickWhenReady(signupButton);
		return this;
	}

	public SignUpPage submitUserDetailsToSignup(User user) {
		commFunc.clickWhenReady(signupButton);
		return this;
	}

	public boolean verifySignUpErrorMessage() {
		if(commFunc.isElementPresent(singupErrorMessageForRegisteredEmail)) {
			return true;
		}
		else
			return false;
	}
	
	public boolean verifyInvalidEmailMessage() {
		if(commFunc.isElementPresent(invalidEmailAddress)) {
			return true;
		}
		else
			return false;
	}

}
