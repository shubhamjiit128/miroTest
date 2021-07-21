package test_scripts;

import java.io.IOException;
import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.extentreports.ExtentTestManager;
import com.utility.Base;

import inputs.DataProviderFile;
import inputs.User;
import page_object.SignUpPage;
import page_object.SuccessPage;

public class TestUserSignUp extends Base{
	WebDriver driver;
	SignUpPage signUp;
	TestSoftAssert softAssert;
	SuccessPage successPage;

	@Parameters("browserName")
	@BeforeMethod
	public void setUp(String browserName,Method method) throws Exception{
		driver = invokeBrowser();
		softAssert = new TestSoftAssert();
		signUp = new SignUpPage(driver, softAssert);
		successPage = new SuccessPage(driver, softAssert);
		ExtentTestManager.createTest(method.getName(), method.getAnnotation(Test.class).testName());
		ExtentTestManager.getTest().assignCategory("Script Execution");
		ExtentTestManager.log(("Test started :" + System.currentTimeMillis()));
	}

	@Test(dataProviderClass = DataProviderFile.class, dataProvider = "newUser")
	public void validateNewUserSignupTest(User user) throws IOException, Exception {
		signUp.enterDetails(user).selectTermsAndCondition().submitUserDetailsToSignup();
		softAssert.softAssertTrue(successPage.isCodeFieldDisplayed(),"New user is not able to sign up properly.");
	}

	@Test(dataProviderClass = DataProviderFile.class, dataProvider = "registeredUser")
	public void validateRegisteredUserSignupTest(User user) throws IOException, Exception {
		signUp.enterDetails(user).selectTermsAndCondition().submitUserDetailsToSignup(user);
		softAssert.softAssertTrue(signUp.verifySignUpErrorMessage(),"Registered user is not able to login properly.");
	}
	
	@Test(dataProviderClass = DataProviderFile.class, dataProvider = "invalidUser")
	public void validateInvalidUserSignupTest(User user) throws IOException, Exception {
		signUp.enterDetails(user).selectTermsAndCondition().submitUserDetailsToSignup(user);
		softAssert.softAssertTrue(signUp.verifyInvalidEmailMessage(),"Registered user is not able to login properly.");
	}

}
