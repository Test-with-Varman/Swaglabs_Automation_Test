package testscripts;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import json.CheckoutData;
import json.CheckoutDataReader;
import swaglabsbase.SwaglabBaseClass;
import swaglabstests.SwagInfopage;
import swaglabstests.SwaglabsCartpage;
import swaglabstests.SwaglabsLoginpage;
import swaglabstests.SwaglabsProductpage;

/**
 * Data-Driven Test Class for Swag Labs Checkout Page
 * 
 * Purpose: Test checkout page functionality using JSON-based test data
 * Approach: DataProvider approach with parameterized test data from data.json
 * 
 * Test Flow: 1. @BeforeMethod: Initialize browser, login, add products to cart,
 * navigate to checkout 2. @Test: Execute parameterized tests with data from
 * data.json 3. @AfterMethod: Close browser and cleanup resources
 * 
 * Dependencies: - CheckoutDataReader: Reads test data from JSON -
 * SwaglabsCheckoutpage: Page Object for checkout page - TestNG DataProvider:
 * For parameterized test execution
 */
public class infopageTest extends SwaglabBaseClass {

	protected SwaglabsLoginpage lg;
	protected SwaglabsProductpage pg;
	protected SwaglabsCartpage cp;
	protected SwagInfopage ifp;
	protected CheckoutDataReader dataReader;

	/**
	 * Setup Method: Browser Initialization, Login, Add Products to Cart and
	 * Navigate to Checkout Page
	 * 
	 * Description: Executes before each test method to initialize browser, login
	 * with valid credentials, add selected products to cart, and navigate to
	 * checkout page for testing
	 * 
	 * Purpose: Ensures the checkout page is loaded and ready for testing with
	 * products already added
	 * 
	 * Test Flow: 1. Initialize WebDriver and navigate to application URL 2. Create
	 * page objects for Login, Product, Cart and Checkout pages 3. Perform login
	 * with standard user credentials 4. Add 3 products to the cart (Product 1,
	 * Product 2, Product 3) 5. Click on the cart icon to navigate to cart page 6.
	 * Click checkout button to navigate to checkout page 7. Verify checkout page is
	 * loaded with form visible 8. Initialize data reader for JSON test data
	 */
	@BeforeMethod
	public void setupBrowserAndNavigateToCheckout() throws Throwable {
		// Step 1: Initialize WebDriver and navigate to URL
		execute();

		// Step 2: Initialize Page Objects
		lg = new SwaglabsLoginpage(driver);
		pg = new SwaglabsProductpage(driver);
		cp = new SwaglabsCartpage(driver);
		ifp = new SwagInfopage(driver);
		dataReader = new CheckoutDataReader();

		// Step 3: Login with standard user credentials
		lg.getUsername().sendKeys("standard_user");
		lg.getPassword().sendKeys("secret_sauce");
		lg.getLogin().click();
		Thread.sleep(2000);

		// Step 4: Add multiple products to cart
		pg.getAddToCartProduct1().click();
		Thread.sleep(300);
		pg.getAddToCartProduct2().click();
		Thread.sleep(300);
		pg.getAddToCartProduct3().click();
		Thread.sleep(500);

		// Step 5: Navigate to cart page
		pg.getCartButton().click();
		Thread.sleep(1500);
		String cartUrl = driver.getCurrentUrl();
		Assert.assertTrue(cartUrl.contains("cart"), "Failed to navigate to cart page");

		// Step 6: Click checkout button to navigate to checkout page
		cp.getCheckoutbutton().click();
		Thread.sleep(2000);
		String checkoutUrl = driver.getCurrentUrl();
		Assert.assertTrue(checkoutUrl.contains("checkout"), "Failed to navigate to checkout page");

		// Step 7: Verify checkout page form is visible
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(ifp.getFirstname()));

		// Step 8: Initialize data reader

	}

	/**
	 * DataProvider Method for Checkout Form Data
	 * 
	 * Provides test data from data.json file to parameterized test methods Each row
	 * becomes a separate test execution with different data
	 * 
	 * @return 2D Object array with CheckoutData as parameters
	 * @throws IOException if JSON file reading fails
	 */
	@DataProvider(name = "checkoutDataProvider")
	public Object[][] checkoutDataProvider() throws IOException {
		List<CheckoutData> testDataList = dataReader.getAllCheckoutData();
		Object[][] dataArray = new Object[testDataList.size()][1];

		for (int i = 0; i < testDataList.size(); i++) {
			dataArray[i][0] = testDataList.get(i);
		}

		System.out.println("\n>>> DataProvider prepared " + testDataList.size() + " test scenarios\n");
		return dataArray;
	}

	/**
	 * Test Case 01: Verify Checkout Form Display Description: Validates that
	 * checkout page displays all required form fields Expected Result: All form
	 * fields should be visible and enabled Assertion: Verify visibility and
	 * enablement of firstName, lastName, postalCode fields
	 */
	@Test(priority = 1, description = "Verify checkout form displays all required fields")
	public void verifyCheckoutFormDisplay() {
		System.out.println("TEST: Verify Checkout Form Display");
		Assert.assertTrue(ifp.getFirstname().isDisplayed(), "First Name field not visible");
		Assert.assertTrue(ifp.getLastname().isDisplayed(), "Last Name field not visible");
		Assert.assertTrue(ifp.getPostalcode().isDisplayed(), "Postal Code field not visible");
		Assert.assertTrue(ifp.getContnue().isDisplayed(), "Continue button not visible");
		System.out.println("✓ All form fields are displayed and ready for input\n");
	}

	/**
	 * Test Case 02: Data-Driven Checkout Form Submission Description: Tests
	 * checkout form submission with various data sets from JSON Expected Result:
	 * Form should be submitted successfully with valid data Test Data:
	 * Parameterized from data.json (10 test scenarios) Assertion: Verify successful
	 * form submission or error message display
	 */
	@Test(dataProvider = "checkoutDataProvider", priority = 2, description = "Submit checkout form with JSON test data")
	public void testCheckoutFormSubmissionWithData(CheckoutData testData) {
		System.out.println("TEST: Data-Driven Checkout Form Submission");
		System.out.println("Executing: " + testData.getTestCase());
		System.out.println("Data: " + testData.toString());

		try {
			// Clear fields before entering data
			ifp.getFirstname().clear();
			ifp.getLastname().clear();
			ifp.getPostalcode().clear();

			// Enter test data
			ifp.getFirstname().sendKeys(testData.getFirstName());
			ifp.getLastname().sendKeys(testData.getLastName());
			ifp.getPostalcode().sendKeys(testData.getPostalCode());

			System.out.println("Data entered - First Name: " + testData.getFirstName() + ", Last Name: "
					+ testData.getLastName() + ", Postal Code: " + testData.getPostalCode());

			// Click continue button
			ifp.getContnue().click();
			Thread.sleep(2000);

			// Verify expected result
			String currentUrl = driver.getCurrentUrl();

			if ("Success".equalsIgnoreCase(testData.getExpectedResult())) {
				Assert.assertTrue(currentUrl.contains("checkout-step-two"),
						testData.getTestCase() + ": Should navigate to checkout step 2. Current URL: " + currentUrl);
				System.out.println("✓ " + testData.getTestCase() + ": Form submitted successfully. URL: " + currentUrl);
			} else {
				Assert.assertTrue(currentUrl.contains("checkout-step-one"),
						testData.getTestCase() + ": Should remain on checkout step 1. Current URL: " + currentUrl);
				System.out.println("✓ " + testData.getTestCase() + ": Form validation working correctly");
			}

		} catch (Exception e) {
			System.out.println("✗ " + testData.getTestCase() + ": Test execution failed");
			Assert.fail(testData.getTestCase() + " failed with exception: " + e.getMessage());
		}

		System.out.println();
	}

	/**
	 * Test Case 03: Verify First Name Field Validation Description: Tests
	 * validation of first name field with empty and valid values Expected Result:
	 * Should reject empty first name and accept valid names
	 */
	@Test(priority = 3, description = "Verify first name field validation")
	public void verifyFirstNameValidation() {
		System.out.println("TEST: Verify First Name Field Validation");

		try {
			// Test 1: Empty first name
			ifp.getFirstname().clear();
			ifp.getLastname().clear();
			ifp.getPostalcode().clear();

			ifp.getLastname().sendKeys("TestLast");
			ifp.getPostalcode().sendKeys("123456");
			ifp.getContnue().click();
			Thread.sleep(1500);

			String url = driver.getCurrentUrl();
			Assert.assertTrue(url.contains("checkout-step-one"), "Empty first name should be rejected");
			System.out.println("✓ Empty first name correctly rejected");

			// Test 2: Valid first name
			ifp.getFirstname().clear();
			ifp.getFirstname().sendKeys("John");
			ifp.getContnue().click();
			Thread.sleep(1500);

			url = driver.getCurrentUrl();
			Assert.assertTrue(url.contains("checkout-step-two"), "Valid first name should be accepted");
			System.out.println("✓ Valid first name correctly accepted\n");

		} catch (Exception e) {
			Assert.fail("First name validation test failed: " + e.getMessage());
		}
	}

	/**
	 * Test Case 04: Verify Last Name Field Validation Description: Tests validation
	 * of last name field with empty and valid values Expected Result: Should reject
	 * empty last name and accept valid names
	 */
	@Test(priority = 4, description = "Verify last name field validation")
	public void verifyLastNameValidation() {
		System.out.println("TEST: Verify Last Name Field Validation");

		try {
			// Reload checkout page
			driver.navigate().back();
			Thread.sleep(1500);
			cp.getCheckoutbutton().click();
			Thread.sleep(2000);

			// Test 1: Empty last name
			ifp.getFirstname().clear();
			ifp.getLastname().clear();
			ifp.getPostalcode().clear();

			ifp.getFirstname().sendKeys("John");
			ifp.getPostalcode().sendKeys("123456");
			ifp.getContnue().click();
			Thread.sleep(1500);

			String url = driver.getCurrentUrl();
			Assert.assertTrue(url.contains("checkout-step-one"), "Empty last name should be rejected");
			System.out.println("✓ Empty last name correctly rejected");

			// Test 2: Valid last name
			ifp.getLastname().sendKeys("Doe");
			ifp.getContnue().click();
			Thread.sleep(1500);

			url = driver.getCurrentUrl();
			Assert.assertTrue(url.contains("checkout-step-two"), "Valid last name should be accepted");
			System.out.println("✓ Valid last name correctly accepted\n");

		} catch (Exception e) {
			Assert.fail("Last name validation test failed: " + e.getMessage());
		}
	}

	/**
	 * Test Case 05: Verify Postal Code Field Validation Description: Tests
	 * validation of postal code field with empty and valid values Expected Result:
	 * Should reject empty postal code and accept valid postal codes
	 */
	@Test(priority = 5, description = "Verify postal code field validation")
	public void verifyPostalCodeValidation() {
		System.out.println("TEST: Verify Postal Code Field Validation");

		try {
			// Reload checkout page
			driver.navigate().back();
			Thread.sleep(1500);
			cp.getCheckoutbutton().click();
			Thread.sleep(2000);

			// Test 1: Empty postal code
			ifp.getFirstname().clear();
			ifp.getLastname().clear();
			ifp.getPostalcode().clear();

			ifp.getFirstname().sendKeys("John");
			ifp.getLastname().sendKeys("Doe");
			ifp.getContnue().click();
			Thread.sleep(1500);

			String url = driver.getCurrentUrl();
			Assert.assertTrue(url.contains("checkout-step-one"), "Empty postal code should be rejected");
			System.out.println("✓ Empty postal code correctly rejected");

			// Test 2: Valid postal code
			ifp.getPostalcode().sendKeys("123456");
			ifp.getContnue().click();
			Thread.sleep(1500);

			url = driver.getCurrentUrl();
			Assert.assertTrue(url.contains("checkout-step-two"), "Valid postal code should be accepted");
			System.out.println("✓ Valid postal code correctly accepted\n");

		} catch (Exception e) {
			Assert.fail("Postal code validation test failed: " + e.getMessage());
		}
	}

	/**
	 * Test Case 06: Verify Back Button on Checkout Description: Tests that back
	 * button navigates back to cart page Expected Result: Should navigate to cart
	 * page
	 */
	@Test(priority = 6, description = "Verify back button navigation")
	public void verifyBackButtonNavigation() {
		System.out.println("TEST: Verify Back Button Navigation");

		try {
			// Reload checkout page
			driver.navigate().back();
			Thread.sleep(1500);
			cp.getCheckoutbutton().click();
			Thread.sleep(2000);

			ifp.getInfocancelbutton().click();
			Thread.sleep(1500);

			String url = driver.getCurrentUrl();
			Assert.assertTrue(url.contains("cart"), "Back button should navigate to cart page. URL: " + url);
			System.out.println("✓ Back button successfully navigated to cart page\n");

		} catch (Exception e) {
			Assert.fail("Back button navigation test failed: " + e.getMessage());
		}
	}

	/**
	 * Test Case 07: Verify Form Submission with Valid Data Complete Flow
	 * Description: Tests complete flow from checkout form to overview page Expected
	 * Result: Should navigate to checkout step 2 (overview page)
	 */
	@Test(priority = 7, description = "Verify complete checkout form submission flow")
	public void verifyCompleteCheckoutFormSubmissionFlow() {
		System.out.println("TEST: Verify Complete Checkout Form Submission Flow");

		try {
			// Reload checkout page
			driver.navigate().back();
			Thread.sleep(1500);
			cp.getCheckoutbutton().click();
			Thread.sleep(2000);

			// Clear and fill form with valid data
			ifp.getFirstname().clear();
			ifp.getLastname().clear();
			ifp.getPostalcode().clear();

			ifp.getFirstname().sendKeys("Keerthi");
			ifp.getLastname().sendKeys("Vasan");
			ifp.getPostalcode().sendKeys("600001");

			System.out.println("Form filled with valid data: Keerthi Vasan, 600001");

			// Submit form
			ifp.getContnue().click();
			Thread.sleep(2000);

			// Verify navigation to step 2
			String url = driver.getCurrentUrl();
			Assert.assertTrue(url.contains("checkout-step-two"),
					"Should navigate to checkout overview page. URL: " + url);
			System.out.println("✓ Successfully navigated to checkout overview page");
			System.out.println("✓ Complete form submission flow working correctly\n");

		} catch (Exception e) {
			Assert.fail("Complete checkout flow test failed: " + e.getMessage());
		}
	}

	/**
	 * Teardown Method: Browser Cleanup
	 * 
	 * Description: Executes after each test method to clean up resources and close
	 * the browser
	 * 
	 * Purpose: Ensures proper cleanup of driver instance to prevent resource leaks
	 */
	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
			System.out.println("Browser closed and resources cleaned up\n");
		}
	}

}
