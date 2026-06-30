package testscripts;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import ExcelUtils.ExcelUtils;
import ExcelUtils.interactiveExcel;
import ScreenshotUtils.ScreenshotUtils;
import swaglabsbase.SwaglabBaseClass;
import swaglabstests.SwaglabsLoginpage;
import swaglabstests.SwaglabsProductpage;

public class LoginTest extends SwaglabBaseClass {

	protected SwaglabsLoginpage lg;
	protected SwaglabsProductpage pg;

	/**
	 * Setup Method: Browser Initialization
	 * Description: Executes before each test method to initialize the browser and instantiate page objects
	 * Purpose: Ensures a fresh browser session and resets page object references for each test
	 */
	@BeforeMethod
	public void setupBrowser() throws Throwable {
		execute();
		lg = new SwaglabsLoginpage(driver);
		pg = new SwaglabsProductpage(driver);
		

	}

	/**
	 * Test Case: Standard Login
	 * Description: Verifies that a standard user can successfully log in with valid credentials
	 * Expected Result: User should be redirected to the inventory page after successful login
	 */
	@Test(priority = 1)
	public void StandardLogin() throws Throwable {
		

		lg.getUsername().sendKeys("standard_user");
		lg.getPassword().sendKeys("secret_sauce");
		lg.getLogin().click();

		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.contains("inventory"), "Standard login failed!");
		
		// Take screenshot
		try {
			ScreenshotUtils.captureScreenshot(driver, "LoginTest", "StandardLogin", "login");
		} catch (IOException e) {
			System.out.println("⚠ Screenshot capture failed: " + e.getMessage());
		}
	}

	/**
	 * Test Case: Invalid Login
	 * Description: Verifies that an appropriate error message is displayed when invalid credentials are provided
	 * Expected Result: Error message "Epic sadface: Username and password do not match any user in this service" should be displayed
	 */
	@Test
	public void invalidLogin() throws Throwable {

		lg.getUsername().sendKeys("invalidinput");
		lg.getPassword().sendKeys("invalidinput");
		lg.getLogin().click();

		String actualError = lg.getErrorMessage();
		Assert.assertEquals(actualError, "Epic sadface: Username and password do not match any user in this service");
		
		// Take screenshot
		try {
			ScreenshotUtils.captureScreenshot(driver, "LoginTest", "invalidLogin", "login");
		} catch (IOException e) {
			System.out.println("⚠ Screenshot capture failed: " + e.getMessage());
		}
	}

	/**
	 * Test Case: Blank Login
	 * Description: Verifies the application behavior when both username and password fields are left blank
	 * Expected Result: User should receive an appropriate error message for blank login attempt
	 */
	@Test
	public void blankLogin() throws Throwable {
		lg.getUsername().sendKeys("");
		lg.getPassword().sendKeys("");
		lg.getLogin().click();

		String actualError = lg.getErrorMessage();
		Assert.assertEquals(actualError, "login successfully");
		System.out.println(actualError);

	}

	/**
	 * Test Case: Case Sensitivity Check
	 * Description: Verifies that the login system is case-sensitive and rejects uppercase credentials
	 * Expected Result: Login should fail with error message containing "Epic sadface" when uppercase credentials are used
	 */
	@Test
	public void caseCheckLogin() throws Throwable {
		String upperUser = "standard_user".toUpperCase();
		String upperPass = "secret_sauce".toUpperCase();

		lg.getUsername().sendKeys(upperUser);
		lg.getPassword().sendKeys(upperPass);
		lg.getLogin().click();

		String actualError = lg.getErrorMessage();
		Assert.assertTrue(actualError.contains("Epic sadface"), "Case sensitivity failed!");
	}

	/**
	 * DataProvider: Excel User Data
	 * Description: Retrieves login test data (username and password combinations) from Excel file
	 * Purpose: Provides multiple sets of test data to the dataDrivenLoginTest method for parameterized testing
	 * Returns: 2D Object array containing username and password pairs
	 */
	@DataProvider(name = "ExcelUserData")
	public Object[][] getLoginData() throws Throwable {
		return ExcelUtils.getTestData();
	}

	/**
	 * Test Case: Data-Driven Login Test
	 * Description: Performs login and logout operations using multiple test data sets from Excel file
	 * DataProvider: ExcelUserData (reads credentials from Excel)
	 * Expected Result: All test cases with different user data should successfully log in, navigate to inventory, and log out
	 */
	@Test(dataProvider = "ExcelUserData")
	public void dataDrivenLoginTest(String username, String password) {
		lg.getUsername().sendKeys(username);
		lg.getPassword().sendKeys(password);
		lg.getLogin().click();
		pg.getBurger().click();
		pg.getLogout().click();

	}

	/**
	 * Test Case: Interactive Login Test
	 * Description: Performs login using credentials fetched from interactive Excel utility which allows runtime data selection
	 * Expected Result: User should be successfully logged in and redirected to the inventory page
	 */
	@Test
	public void interactiveLogin() throws Throwable {

		String[] credentials = interactiveExcel.getInteractiveUserData();

		String username = credentials[0];
		String password = credentials[1];

		lg.getUsername().clear();
		lg.getUsername().sendKeys(username);

		lg.getPassword().clear();
		lg.getPassword().sendKeys(password);

		lg.getLogin().click();

		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.contains("inventory"), "Login failed for user: " + username);
	}

	/**
	 * Teardown Method: Browser Cleanup
	 * Description: Executes after each test method to clean up resources and close the browser
	 * Purpose: Ensures proper cleanup of driver instance to prevent resource leaks
	 */
	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}