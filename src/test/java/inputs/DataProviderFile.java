package inputs;

import org.testng.annotations.DataProvider;

import com.utility.ExcelUtils;
import com.utility.Constants; 


public class DataProviderFile {
	User user;

	
	private static final User NEW_USER = new User(Constants.userName, generateEmail(Constants.EMAIL_PREFIX), Constants.PASSWORD); 

	private static final User REGISTERED_USER =new User(ExcelUtils.getTestData("FieldValue","username").get(0), ExcelUtils.getTestData("FieldValue", "validemail").get(0), Constants.PASSWORD);
	
	private static final User INVALID_USER =new User(ExcelUtils.getTestData("FieldValue", "username").get(0), ExcelUtils.getTestData("FieldValue", "invalidemail").get(0), Constants.PASSWORD);
	

	@DataProvider(name = "newUser", parallel = true)
	public static Object[] getNewUser() {
		return new Object[]{
				NEW_USER,
		};
	}

	@DataProvider(name = "registeredUser", parallel = true)
	public static Object[] getRegisteredUser() {
		return new Object[]{
				REGISTERED_USER,
		};
	}
	
	@DataProvider(name = "invalidUser", parallel = true)
	public static Object[] getInvalidUser() {
		return new Object[]{
				INVALID_USER,
		};
	}

	private static String generateEmail(String domain) {
		return Constants.EMAIL_PREFIX + System.currentTimeMillis()+ "@" + Constants.DOMAIN;
	}
}
