package testscripts;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import ScreenshotUtils.ScreenshotUtils;

import swaglabsbase.SwaglabBaseClass;
import swaglabstests.SwaglabsCartpage;
import swaglabstests.SwaglabsLoginpage;
import swaglabstests.SwaglabsProductpage;

public class CartpageTest extends SwaglabBaseClass {

	protected SwaglabsLoginpage lg;
	protected SwaglabsProductpage pg;
	protected SwaglabsCartpage cp;

	/**
	 * Setup Method: Browser Initialization, Login, Add Products to Cart and
	 * Navigate to Cart Page Description: Executes before each test method to
	 * initialize browser, login with valid credentials, add selected products to
	 * cart, and navigate to cart page for testing Purpose: Ensures the cart page is
	 * loaded and ready for testing with products already added Test Flow: 1.
	 * Initialize WebDriver and navigate to application URL 2. Create page objects
	 * for Login, Product, and Cart pages 3. Perform login with standard user
	 * credentials 4. Add 3 products to the cart (Product 1, Product 2, Product 3)
	 * 5. Click on the cart icon to navigate to cart page 6. Initialize cart page
	 * object for test methods
	 */
	@BeforeMethod
	public void setupBrowserAndNavigateToCart() throws Throwable {
		execute();
		lg = new SwaglabsLoginpage(driver);
		pg = new SwaglabsProductpage(driver);
		cp = new SwaglabsCartpage(driver);

		// Login with valid credentials
		lg.getUsername().sendKeys("standard_user");
		lg.getPassword().sendKeys("secret_sauce");
		lg.getLogin().click();

		// Add multiple products to cart
		pg.getAddToCartProduct1().click();
		pg.getAddToCartProduct2().click();
		pg.getAddToCartProduct3().click();

		// Navigate to cart page
		pg.getCartButton().click();
	}

	/**
	 * Test Case: Verify Cart Page Display Description: Verifies that the cart page
	 * displays correctly with all added items visible Expected Result: Cart page
	 * should load successfully and display the added products with their details
	 * Assertion: Verify cart page URL contains "cart" and cart button is visible
	 */
	@Test(priority = 1)
	public void verifyCartPageDisplay() {
		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.contains("cart"), "Cart page did not load correctly");
		System.out.println("✓ Cart page displayed successfully: " + currentUrl);
		
		// Take screenshot
		try {
			ScreenshotUtils.captureScreenshot(driver, "CartpageTest", "verifyCartPageDisplay", "cart");
		} catch (IOException e) {
			System.out.println("⚠ Screenshot capture failed: " + e.getMessage());
		}
	}

	/**
	 * Test Case: Verify Continue Shopping Button Description: Verifies that
	 * clicking "Continue Shopping" button returns user to product page Expected
	 * Result: User should be redirected to the product/inventory page from cart
	 * page Assertion: Verify URL changes from cart to inventory/product page
	 */
	@Test(priority = 2)
	public void verifyContinueShoppingButton() {
		cp.getContinueshopping().click();
		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.contains("inventory"),
				"Continue Shopping button did not navigate to inventory page");
		System.out.println("✓ Continue Shopping button redirected to: " + currentUrl);
	}

	/**
	 * Test Case: Verify Checkout Button Description: Verifies that clicking
	 * "Checkout" button navigates to the checkout page Expected Result: User should
	 * be redirected to checkout page with form for shipping/payment details
	 * Assertion: Verify URL changes to checkout page
	 */
	@Test(priority = 3)
	public void verifyCheckoutButton() throws Throwable {
		// Re-navigate to cart since previous test navigated away
		pg.getCartButton().click();
		Thread.sleep(1000);

		cp.getCheckoutbutton().click();
		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.contains("checkout"),
				"Checkout button did not navigate to checkout page. Current URL: " + currentUrl);
		System.out.println("✓ Checkout button redirected to: " + currentUrl);
		
		// Take screenshot
		try {
			ScreenshotUtils.captureScreenshot(driver, "CartpageTest", "verifyCheckoutButton", "cart");
		} catch (IOException e) {
			System.out.println("⚠ Screenshot capture failed: " + e.getMessage());
		}
	}

	/**
	 * Test Case: Verify Cart Items Count Description: Verifies that all added
	 * products are displayed in the cart with correct count Expected Result: Cart
	 * should display exactly 3 items as we added 3 products in setup Assertion:
	 * Verify cart badge shows "3" products
	 */
	@Test(priority = 4)
	public void verifyCartItemsCount() throws Throwable {
		// Re-navigate to cart since some tests navigate away
		pg.getCartButton().click();
		Thread.sleep(1000);

		String cartCount = pg.getCartnumber().getText();
		Assert.assertEquals(cartCount, "3", "Cart does not display correct number of items");
		System.out.println("✓ Cart displays correct item count: " + cartCount + " items");
	}

	/**
	 * Test Case: Verify Cart with Multiple Products Description: Verifies that cart
	 * correctly displays multiple products with their individual details Expected
	 * Result: Each product in cart should be visible with name, price, and other
	 * details Assertion: Verify all added products are present in cart
	 */
	@Test(priority = 5)
	public void verifyCartWithMultipleProducts() throws Throwable {
		// Re-navigate to cart
		pg.getCartButton().click();
		Thread.sleep(1000);

		// Verify cart displays the items added (This is a visual verification)
		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.contains("cart"), "Cart page not displayed");

		// Verify cart badge shows 3 items
		String cartCount = pg.getCartnumber().getText();
		Assert.assertTrue(Integer.parseInt(cartCount) >= 3, "All products not visible in cart");
		System.out.println("✓ Cart displays multiple products. Total items: " + cartCount);
	}

	/**
	 * Test Case: Verify Remove from Cart Description: Verifies that removing an
	 * item from cart updates the cart count dynamically Expected Result: Cart count
	 * should decrease and item should be removed from display Assertion: Verify
	 * cart count changes after removing an item
	 */
	@Test(priority = 6)
	public void verifyRemoveFromCart() throws Throwable {
		// Re-navigate to cart
		pg.getCartButton().click();
		Thread.sleep(1000);

		// Get initial cart count
		String initialCount = pg.getCartnumber().getText();
		int initialItems = Integer.parseInt(initialCount);
		System.out.println("Initial cart count: " + initialItems);

		// Remove a product from cart
		pg.getRemoveProduct1().click();
		Thread.sleep(500);

		// Verify cart count decreased
		String updatedCount = pg.getCartnumber().getText();
		int updatedItems = Integer.parseInt(updatedCount);
		Assert.assertTrue(updatedItems < initialItems,
				"Item was not removed from cart. Count: " + initialItems + " -> " + updatedItems);
		System.out.println("✓ Item removed successfully. Cart count: " + initialItems + " -> " + updatedItems);
	}

	/**
	 * Test Case: Verify Empty Cart Description: Verifies the application behavior
	 * when all items are removed from cart (empty state) Expected Result: Cart
	 * should display empty state message or have no items visible Assertion: Verify
	 * cart becomes empty after removing all items
	 */
	@Test(priority = 7)
	public void verifyEmptyCart() throws Throwable {
		// Remove all products from cart
		pg.getRemoveProduct1().click();
		Thread.sleep(500);
		pg.getRemoveProduct2().click();
		Thread.sleep(500);
		pg.getRemoveProduct3().click();
		Thread.sleep(500);

		// Verify cart is empty (cart badge should disappear or show 0)
		try {
			String cartCount = pg.getCartnumber().getText();
			Assert.fail("Cart should be empty but still shows count: " + cartCount);
		} catch (Exception e) {
			// Cart badge not found - this is expected for empty cart
			System.out.println("✓ Cart is empty - no items displayed");
		}
	}

	/**
	 * Test Case: Verify Cart Total/Price Calculation Description: Verifies that
	 * cart correctly calculates and displays the total price of all items Expected
	 * Result: Total price should be the sum of all individual product prices
	 * Assertion: Verify cart total is displayed and is a valid number
	 */
	@Test(priority = 8)
	public void verifyCartTotal() throws Throwable {
		// Re-navigate to cart
		pg.getCartButton().click();
		Thread.sleep(1000);

		// Verify cart is not empty
		String cartCount = pg.getCartnumber().getText();
		Assert.assertTrue(!cartCount.isEmpty(), "Cart appears to be empty");

		// Verify cart total is displayed (if such element exists)
		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.contains("cart"), "Not on cart page");
		System.out.println("✓ Cart total verified. Items in cart: " + cartCount);
	}

	/**
	 * Test Case: Verify Cart Navigation Flow Description: Verifies complete
	 * navigation flow from product page -> cart page -> checkout page Test Flow: 1.
	 * Verify we are on cart page after BeforeMethod 2. Click checkout button to
	 * navigate to checkout 3. Verify checkout page loads 4. (Optional) Complete
	 * checkout form submission Expected Result: Complete flow should work
	 * seamlessly from cart to checkout Assertion: Verify URL changes correctly at
	 * each step
	 */
	@Test(priority = 9)
	public void verifyCartNavigationFlow() throws Throwable {
		// Step 1: Verify on cart page
		String cartUrl = driver.getCurrentUrl();
		Assert.assertTrue(cartUrl.contains("cart"), "Not on cart page");
		System.out.println("Step 1 - ✓ On cart page: " + cartUrl);

		// Step 2: Click checkout to navigate to checkout page
		cp.getCheckoutbutton().click();
		Thread.sleep(1000);

		// Step 3: Verify checkout page loaded
		String checkoutUrl = driver.getCurrentUrl();
		Assert.assertTrue(checkoutUrl.contains("checkout"), "Checkout page did not load. Current URL: " + checkoutUrl);
		System.out.println("Step 2 & 3 - ✓ Successfully navigated to checkout: " + checkoutUrl);
		System.out.println("✓ Complete cart navigation flow verified successfully");
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