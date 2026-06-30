package testscripts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import swaglabsbase.SwaglabBaseClass;
import swaglabstests.SwaglabsLoginpage;
import swaglabstests.SwaglabsProductpage;

public class ProductpageTest extends SwaglabBaseClass {

	protected SwaglabsLoginpage lg;
	protected SwaglabsProductpage pg;

	/**
	 * Setup Method: Browser Initialization and Login Description: Executes before
	 * each test method to initialize the browser, instantiate page objects, and
	 * perform login Purpose: Ensures a fresh browser session with authenticated
	 * user access to product page
	 */
	@BeforeMethod
	public void setupBrowser() throws Throwable {
		execute();
		lg = new SwaglabsLoginpage(driver);
		pg = new SwaglabsProductpage(driver);
		lg.getUsername().sendKeys("standard_user");
		lg.getPassword().sendKeys("secret_sauce");
		lg.getLogin().click();
	}

	/**
	 * Test Case: Add Single Product to Cart Description: Verifies that adding a
	 * single product to the cart correctly updates the cart counter Expected
	 * Result: Cart counter should display "1" after adding one product
	 */
	@Test
	public void addtocart1() {
		pg.getAddToCartProduct1().click();
		String number = pg.getCartnumber().getText();
		System.out.println(number);
		Assert.assertTrue(number.equals("1"));
	}

	/**
	 * Test Case: Add Four Products to Cart Description: Verifies that adding four
	 * different products to the cart correctly updates the cart counter Expected
	 * Result: Cart counter should display "4" after adding four products
	 */
	@Test
	public void addtocart4() {
		pg.getAddToCartProduct1().click();
		pg.getAddToCartProduct2().click();
		pg.getAddToCartProduct3().click();
		pg.getAddToCartProduct4().click();
		String number = pg.getCartnumber().getText();
		System.out.println(number);
		Assert.assertTrue(number.equals("4"));

	}

	/**
	 * Test Case: Add All Products to Cart Description: Verifies that adding all
	 * available products to the cart correctly updates the cart counter Expected
	 * Result: Cart counter should display "6" after adding all six products
	 */
	@Test
	public void addtocartall() {
		pg.getAddToCartProduct1().click();
		pg.getAddToCartProduct2().click();
		pg.getAddToCartProduct3().click();
		pg.getAddToCartProduct4().click();
		pg.getAddToCartProduct5().click();
		pg.getAddToCartProduct6().click();

		String number = pg.getCartnumber().getText();
		System.out.println(number);
		Assert.assertTrue(number.equals("6"));

	}

	/**
	 * Test Case: Dynamic Add and Remove Products from Cart Description: Verifies
	 * that the cart counter dynamically updates correctly when adding and removing
	 * products in multiple steps Test Sequence: 1. Add 2 products - verify count is
	 * 2 2. Add 4 more products - verify count is 6 3. Remove 2 products - verify
	 * count is 4 4. Remove 3 products - verify count is 1 Expected Result: Cart
	 * counter should accurately reflect each addition and removal operation
	 */
	@Test
	public void DynamicAddtoCartNumber() {
		pg.getAddToCartProduct1().click();
		pg.getAddToCartProduct2().click();
		String number = pg.getCartnumber().getText();
		System.out.println(number);
		Assert.assertTrue(number.equals("2"));

		pg.getAddToCartProduct3().click();
		pg.getAddToCartProduct4().click();
		pg.getAddToCartProduct5().click();
		pg.getAddToCartProduct6().click();
		String number2 = pg.getCartnumber().getText();
		System.out.println(number2);
		Assert.assertTrue(number2.equals("6"));

		pg.getRemoveProduct1().click();
		pg.getRemoveProduct2().click();
		String number3 = pg.getCartnumber().getText();
		System.out.println(number3);
		Assert.assertTrue(number3.equals("4"));

		pg.getRemoveProduct3().click();
		pg.getRemoveProduct4().click();
		pg.getRemoveProduct5().click();
		String number4 = pg.getCartnumber().getText();
		System.out.println(number4);
		Assert.assertTrue(number4.equals("1"));
	}

	/**
	 * Test Case: Sort Products A to Z Description: Verifies that the products are
	 * correctly sorted in alphabetical order (A to Z) when the A-Z sort option is
	 * selected Expected Result: Product list should be sorted alphabetically from A
	 * to Z
	 */
	@Test
	public void sortcheckAtoZ() {
		pg.getSort().click();
		pg.getSortAtoZ().click();
		List<String> actualList = new ArrayList<>();

		for (WebElement product : pg.getProducts()) {
			actualList.add(product.getText());
		}

		List<String> expectedList = new ArrayList<>(actualList);
		Collections.sort(expectedList);
		boolean isSorted = actualList.equals(expectedList);
		System.out.println("A to Z Sorted : " + isSorted);
		Assert.assertTrue(isSorted, "Products are not sorted in A to Z order");

	}

	/**
	 * Test Case: Sort Products Z to A Description: Verifies that the products are
	 * correctly sorted in reverse alphabetical order (Z to A) when the Z-A sort
	 * option is selected Expected Result: Product list should be sorted in reverse
	 * alphabetical order from Z to A
	 */
	@Test
	public void sortcheckZtoA() {
		pg.getSort().click();
		pg.getSortZtoA().click();
		List<String> actualList = new ArrayList<>();

		for (WebElement product : pg.getProducts()) {
			actualList.add(product.getText());
		}
		System.out.println(actualList);
		List<String> expectedList = new ArrayList<>(actualList);

		Collections.sort(expectedList, Collections.reverseOrder());

		boolean isSorted = actualList.equals(expectedList);

		Assert.assertTrue(isSorted, "Products are not sorted in Z to A order");

	}

	/**
	 * Test Case: Sort Products by Price - Low to High Description: Verifies that
	 * the products are correctly sorted by price in ascending order (Low to High)
	 * when the Low-High sort option is selected Expected Result: Product list
	 * should be sorted by price from lowest to highest value
	 */
	@Test
	public void sortcheckLotohi() throws Throwable {
		pg.getSort().click();
		pg.getSortLotohi().click();
		Thread.sleep(2000);

		List<Double> actualPrices = new ArrayList<>();

		for (WebElement price : pg.getProductprices()) {
			actualPrices.add(Double.parseDouble(price.getText().replace("$", "")));
		}

		List<Double> expectedPrices = new ArrayList<>(actualPrices);

		Collections.sort(expectedPrices);

		Assert.assertEquals(actualPrices, expectedPrices, "Prices are not sorted Low to High");
	}

	/**
	 * Test Case: Sort Products by Price - High to Low Description: Verifies that
	 * the products are correctly sorted by price in descending order (High to Low)
	 * when the High-Low sort option is selected Expected Result: Product list
	 * should be sorted by price from highest to lowest value
	 */
	@Test
	public void sortcheckHiTolo() throws Throwable {
		pg.getSort().click();
		pg.getSortHiTolo().click();
		Thread.sleep(2000);
		List<Double> actualPrices = new ArrayList<>();

		for (WebElement price : pg.getProductprices()) {
			actualPrices.add(Double.parseDouble(price.getText().replace("$", "")));
		}

		List<Double> expectedPrices = new ArrayList<>(actualPrices);

		Collections.sort(expectedPrices, Collections.reverseOrder());

		Assert.assertEquals(actualPrices, expectedPrices, "Prices are not sorted High to Low");
	}

	/**
	 * Test Case: Child Window Verification Description: Verifies that clicking on
	 * social media links (Twitter, Facebook, LinkedIn) opens new child windows with
	 * correct URLs Test Flow: 1. Click Twitter link - verify new window opens with
	 * Twitter URL and close it 2. Click Facebook link - verify new window opens
	 * with Facebook URL and close it 3. Click LinkedIn link - verify new window
	 * opens with LinkedIn URL and close it Expected Result: Each social media link
	 * should open a new child window with the respective URL and window should
	 * close properly
	 */
	@Test
	public void childwindow() throws Throwable {
		SoftAssert a = new SoftAssert();
		String parentWindow = driver.getWindowHandle();

		// Test Twitter link
		pg.getTwitter().click();
		Set<String> allWindows = driver.getWindowHandles();

		// Switch to child window (iterate through windows to find the new one)
		for (String window : allWindows) {
			if (!window.equals(parentWindow)) {
				driver.switchTo().window(window);
				break;
			}
		}

		// Verify Twitter window opened with correct URL
		String currentUrl = driver.getCurrentUrl();
		a.assertTrue(currentUrl.contains("twitter") || currentUrl.contains("X.com") || currentUrl.contains("x.com"),
				"Twitter window URL verification failed. Current URL: " + currentUrl);

		// Close Twitter window and switch back to parent window
		driver.close();
		driver.switchTo().window(parentWindow);
		Thread.sleep(1000);

		// Test Facebook link
		pg.getFacebook().click();
		allWindows = driver.getWindowHandles();

		// Switch to child window
		for (String window : allWindows) {
			if (!window.equals(parentWindow)) {
				driver.switchTo().window(window);
				break;
			}
		}

		// Verify Facebook window opened with correct URL
		String currentUrl2 = driver.getCurrentUrl();
		a.assertTrue(currentUrl2.contains("facebook") || currentUrl2.contains("Facebook"),
				"Facebook window URL verification failed. Current URL: " + currentUrl2);

		// Close Facebook window and switch back to parent window
		driver.close();
		driver.switchTo().window(parentWindow);
		Thread.sleep(1000);

		// Test LinkedIn link
		pg.getLinkedin().click();
		allWindows = driver.getWindowHandles();

		// Switch to child window
		for (String window : allWindows) {
			if (!window.equals(parentWindow)) {
				driver.switchTo().window(window);
				break;
			}
		}

		// Verify LinkedIn window opened with correct URL
		String currentUrl3 = driver.getCurrentUrl();
		a.assertTrue(currentUrl3.contains("linkedin") || currentUrl3.contains("LinkedIn"),
				"LinkedIn window URL verification failed. Current URL: " + currentUrl3);

		// Close LinkedIn window and switch back to parent window
		driver.close();
		driver.switchTo().window(parentWindow);

		// Assert all soft assertions collected during the test
		a.assertAll();

	}

	/**
	 * Teardown Method: Browser Cleanup Description: Executes after each test method
	 * to clean up resources and close the browser Purpose: Ensures proper cleanup
	 * of driver instance to prevent resource leaks
	 */
	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

}
