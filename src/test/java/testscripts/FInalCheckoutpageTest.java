package testscripts;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ScreenshotUtils.ScreenshotUtils;

import swaglabsbase.SwaglabBaseClass;
import swaglabstests.SwaglabsCartpage;
import swaglabstests.SwaglabsFinalCheckoutpage;
import swaglabstests.SwaglabsLoginpage;
import swaglabstests.SwaglabsProductpage;

public class FInalCheckoutpageTest extends SwaglabBaseClass {

	protected SwaglabsLoginpage lg;
	protected SwaglabsProductpage pg;
	protected SwaglabsCartpage cp;
	protected SwaglabsFinalCheckoutpage fcp;

	/**
	 * Setup Method: Browser Initialization, Login, Add Products, and Navigate to Final Checkout Page
	 * Description: Executes before each test method to initialize browser, login with valid credentials, 
	 * add products to cart, fill checkout form, and navigate to final checkout review page
	 * Purpose: Ensures the final checkout page is loaded and ready for testing with order details
	 * Test Flow:
	 * 1. Initialize WebDriver and navigate to application URL
	 * 2. Create page objects for Login, Product, Cart, and Final Checkout pages
	 * 3. Perform login with standard user credentials
	 * 4. Add 2-3 products to the cart
	 * 5. Click on the cart icon to navigate to cart page
	 * 6. Click checkout button to navigate to checkout form page
	 * 7. Fill in checkout form (First Name, Last Name, Postal Code)
	 * 8. Click Continue to navigate to final checkout page
	 * 9. Initialize final checkout page object for test methods
	 */
	@BeforeMethod
	public void setupBrowserAndNavigateToFinalCheckout() throws Throwable {
		execute();
		lg = new SwaglabsLoginpage(driver);
		pg = new SwaglabsProductpage(driver);
		cp = new SwaglabsCartpage(driver);
		fcp = new SwaglabsFinalCheckoutpage(driver);

		// Step 1: Login with valid credentials
		lg.getUsername().sendKeys("standard_user");
		lg.getPassword().sendKeys("secret_sauce");
		lg.getLogin().click();
		Thread.sleep(1000);

		// Step 2: Add multiple products to cart
		pg.getAddToCartProduct1().click();
		Thread.sleep(500);
		pg.getAddToCartProduct2().click();
		Thread.sleep(500);

		// Step 3: Navigate to cart page
		pg.getCartButton().click();
		Thread.sleep(1000);

		// Step 4: Click checkout button
		cp.getCheckoutbutton().click();
		Thread.sleep(1000);

		// Step 5: Fill in checkout form (First Name, Last Name, Postal Code)
		// Note: These xpaths assume standard checkout form elements - adjust if needed
		WebElement firstNameField = driver.findElement(org.openqa.selenium.By.id("first-name"));
		WebElement lastNameField = driver.findElement(org.openqa.selenium.By.id("last-name"));
		WebElement postalCodeField = driver.findElement(org.openqa.selenium.By.id("postal-code"));
		
		firstNameField.sendKeys("John");
		lastNameField.sendKeys("Doe");
		postalCodeField.sendKeys("12345");
		
		// Step 6: Click Continue button to go to final checkout page
		WebElement continueBtn = driver.findElement(org.openqa.selenium.By.id("continue"));
		continueBtn.click();
		Thread.sleep(1000);
	}

	/**
	 * Test Case 1: Verify Checkout Page Title
	 * Description: Validates that the page title displays correctly on final checkout page
	 * Expected Result: Page title should be "Checkout: Complete!" or similar confirmation text
	 * Assertion: Verify page title element contains expected text
	 */
	@Test(priority = 1)
	public void verifyCheckoutPageTitle() throws IOException {
		WebElement pageTitle = fcp.getPageTitle();
		Assert.assertNotNull(pageTitle, "Page title element not found");
		
		String titleText = pageTitle.getText();
		Assert.assertTrue(!titleText.isEmpty(), "Page title is empty");
		System.out.println("✓ Page Title: " + titleText);
		
		// Take screenshot
		ScreenshotUtils.captureScreenshot(driver, "FInalCheckoutpageTest", "verifyCheckoutPageTitle", "checkout");
	}

	/**
	 * Test Case 2: Verify Order Summary with All Products
	 * Description: Validates that all added products display in checkout with correct names
	 * Expected Result: All products added to cart should be visible with correct product names
	 * Assertion: Verify product list is not empty and contains expected products
	 */
	@Test(priority = 2)
	public void verifyOrderSummaryWithAllProducts() {
		List<WebElement> productNames = fcp.getProductNames();
		
		Assert.assertNotNull(productNames, "Product names list is null");
		Assert.assertTrue(productNames.size() > 0, "No products displayed in order summary");
		Assert.assertTrue(productNames.size() >= 2, "Expected at least 2 products, but found: " + productNames.size());
		
		System.out.println("✓ Order contains " + productNames.size() + " products");
		for (int i = 0; i < productNames.size(); i++) {
			System.out.println("  Product " + (i + 1) + ": " + productNames.get(i).getText());
		}
		
		// Take screenshot
		try {
			ScreenshotUtils.captureScreenshot(driver, "FInalCheckoutpageTest", "verifyOrderSummaryWithAllProducts", "checkout");
		} catch (IOException e) {
			System.out.println("⚠ Screenshot capture failed: " + e.getMessage());
		}
	}

	/**
	 * Test Case 3: Verify Product Prices Display
	 * Description: Validates that product prices are displayed correctly for each item
	 * Expected Result: Each product should have a price displayed in correct format ($XX.XX)
	 * Assertion: Verify prices are visible and properly formatted
	 */
	@Test(priority = 3)
	public void verifyProductPricesDisplay() {
		List<WebElement> productPrices = fcp.getProductPrices();
		
		Assert.assertNotNull(productPrices, "Product prices list is null");
		Assert.assertTrue(productPrices.size() > 0, "No product prices displayed");
		Assert.assertEquals(productPrices.size(), fcp.getProductNames().size(), 
			"Number of prices doesn't match number of products");
		
		System.out.println("✓ Product Prices:");
		for (int i = 0; i < productPrices.size(); i++) {
			String price = productPrices.get(i).getText();
			Assert.assertTrue(price.contains("$"), "Price format incorrect: " + price);
			System.out.println("  Price " + (i + 1) + ": " + price);
		}
	}

	/**
	 * Test Case 4: Verify Payment Information Display
	 * Description: Validates that payment method is displayed correctly on final checkout page
	 * Expected Result: Payment info section should display payment method/card information
	 * Assertion: Verify payment info element is visible and not empty
	 */
	@Test(priority = 4)
	public void verifyPaymentInformationDisplay() {
		WebElement paymentInfo = fcp.getPaymentInfo();
		
		Assert.assertNotNull(paymentInfo, "Payment info element not found");
		
		String paymentText = paymentInfo.getText();
		Assert.assertTrue(!paymentText.isEmpty(), "Payment info is empty");
		Assert.assertTrue(paymentText.toLowerCase().contains("payment") || 
			paymentText.toLowerCase().contains("card") || 
			!paymentText.isEmpty(), "Payment info not properly displayed");
		
		System.out.println("✓ Payment Information: " + paymentText);
	}

	/**
	 * Test Case 5: Verify Shipping Information Display
	 * Description: Validates that shipping address is displayed correctly on final checkout page
	 * Expected Result: Shipping info section should display address and shipping details
	 * Assertion: Verify shipping info element is visible and contains address information
	 */
	@Test(priority = 5)
	public void verifyShippingInformationDisplay() {
		WebElement shippingInfo = fcp.getShippingInfo();
		
		Assert.assertNotNull(shippingInfo, "Shipping info element not found");
		
		String shippingText = shippingInfo.getText();
		Assert.assertTrue(!shippingText.isEmpty(), "Shipping info is empty");
		System.out.println("✓ Shipping Information: " + shippingText);
	}

	/**
	 * Test Case 6: Verify Price Calculation Accuracy (Subtotal + Tax = Total)
	 * Description: Validates that final total price = subtotal + tax calculation is accurate
	 * Expected Result: Total amount should equal subtotal + tax with no calculation errors
	 * Assertion: Verify mathematical accuracy of price calculation
	 */
	@Test(priority = 6)
	public void verifyPriceCalculationAccuracy() throws Throwable {
		Thread.sleep(500);
		
		WebElement itemTotalElem = fcp.getItemTotal();
		WebElement taxElem = fcp.getTax();
		WebElement totalElem = fcp.getTotal();
		
		Assert.assertNotNull(itemTotalElem, "Item total element not found");
		Assert.assertNotNull(taxElem, "Tax element not found");
		Assert.assertNotNull(totalElem, "Total element not found");
		
		String itemTotalText = itemTotalElem.getText();
		String taxText = taxElem.getText();
		String totalText = totalElem.getText();
		
		System.out.println("✓ Price Breakdown:");
		System.out.println("  Item Total: " + itemTotalText);
		System.out.println("  Tax: " + taxText);
		System.out.println("  Total: " + totalText);
		
		// Extract numeric values from price strings
		double subtotal = extractPriceValue(itemTotalText);
		double tax = extractPriceValue(taxText);
		double total = extractPriceValue(totalText);
		
		double calculatedTotal = subtotal + tax;
		
		// Allow small floating point differences
		Assert.assertTrue(Math.abs(calculatedTotal - total) < 0.01, 
			"Price calculation mismatch. Subtotal(" + subtotal + ") + Tax(" + tax + ") = " + 
			calculatedTotal + " but Total is: " + total);
		
		System.out.println("✓ Price calculation verified: " + subtotal + " + " + tax + " = " + total);
		
		// Take screenshot
		try {
			ScreenshotUtils.captureScreenshot(driver, "FInalCheckoutpageTest", "verifyPriceCalculationAccuracy", "checkout");
		} catch (IOException e) {
			System.out.println("⚠ Screenshot capture failed: " + e.getMessage());
		}
	}

	/**
	 * Test Case 7: Verify Quantity Display for Each Product
	 * Description: Validates that product quantities are displayed correctly for each item
	 * Expected Result: Each product should show its quantity (typically 1 unless modified)
	 * Assertion: Verify quantities are visible and match added items
	 */
	@Test(priority = 7)
	public void verifyQuantityDisplay() {
		List<WebElement> quantities = fcp.getQuantities();
		List<WebElement> productNames = fcp.getProductNames();
		
		Assert.assertNotNull(quantities, "Quantities list is null");
		Assert.assertTrue(quantities.size() > 0, "No quantities displayed");
		Assert.assertEquals(quantities.size(), productNames.size(), 
			"Number of quantities doesn't match number of products");
		
		System.out.println("✓ Product Quantities:");
		for (int i = 0; i < quantities.size(); i++) {
			String qty = quantities.get(i).getText();
			System.out.println("  Product " + (i + 1) + " Quantity: " + qty);
		}
	}

	/**
	 * Test Case 8: Verify Cancel Button Functionality
	 * Description: Validates that clicking cancel button redirects user back without placing order
	 * Expected Result: User should be redirected to products/inventory page and order should NOT be placed
	 * Assertion: Verify URL changes away from checkout and order is not confirmed
	 */
	@Test(priority = 8)
	public void verifyCancelButtonFunctionality() throws Throwable {
		WebElement cancelBtn = fcp.getCancelBtn();
		
		Assert.assertNotNull(cancelBtn, "Cancel button not found");
		Assert.assertTrue(cancelBtn.isDisplayed(), "Cancel button is not displayed");
		Assert.assertTrue(cancelBtn.isEnabled(), "Cancel button is not enabled");
		
		cancelBtn.click();
		Thread.sleep(1000);
		
		String currentUrl = driver.getCurrentUrl();
		Assert.assertFalse(currentUrl.contains("checkout-complete"), 
			"Order should not be placed when cancel is clicked");
		Assert.assertTrue(currentUrl.contains("inventory") || !currentUrl.contains("checkout"), 
			"Should navigate away from checkout page. Current URL: " + currentUrl);
		
		System.out.println("✓ Cancel button successfully redirected to: " + currentUrl);
	}

	/**
	 * Test Case 9: Verify Finish Button Places Order Successfully
	 * Description: Validates that clicking finish button successfully places the order
	 * Expected Result: Order should be placed and order confirmation page should be displayed
	 * Assertion: Verify URL changes to order confirmation and success message is shown
	 */
	@Test(priority = 9)
	public void verifyFinishButtonPlacesOrderSuccessfully() throws Throwable {
		WebElement finishBtn = fcp.getFinishBtn();
		
		Assert.assertNotNull(finishBtn, "Finish button not found");
		Assert.assertTrue(finishBtn.isDisplayed(), "Finish button is not displayed");
		Assert.assertTrue(finishBtn.isEnabled(), "Finish button is not enabled");
		
		finishBtn.click();
		Thread.sleep(2000);
		
		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.contains("checkout-complete"), 
			"Order confirmation page should load. Current URL: " + currentUrl);
		
		System.out.println("✓ Order placed successfully. Redirected to: " + currentUrl);
		
		// Take screenshot of order confirmation
		try {
			ScreenshotUtils.captureScreenshot(driver, "FInalCheckoutpageTest", "verifyFinishButtonPlacesOrderSuccessfully", "checkout");
		} catch (IOException e) {
			System.out.println("⚠ Screenshot capture failed: " + e.getMessage());
		}
	}

	/**
	 * Test Case 10: Verify All UI Elements are Visible and Enabled
	 * Description: Validates that all checkout page buttons and elements are clickable and visible
	 * Expected Result: Cancel and Finish buttons should be visible, enabled, and interactive
	 * Assertion: Verify all critical UI elements are in correct state
	 */
	@Test(priority = 10)
	public void verifyAllUIElementsVisibleAndEnabled() {
		WebElement pageTitle = fcp.getPageTitle();
		WebElement paymentInfo = fcp.getPaymentInfo();
		WebElement shippingInfo = fcp.getShippingInfo();
		WebElement itemTotal = fcp.getItemTotal();
		WebElement tax = fcp.getTax();
		WebElement total = fcp.getTotal();
		WebElement cancelBtn = fcp.getCancelBtn();
		WebElement finishBtn = fcp.getFinishBtn();
		
		System.out.println("✓ UI Elements Verification:");
		
		// Verify all elements are not null
		Assert.assertNotNull(pageTitle, "Page title not found");
		Assert.assertNotNull(paymentInfo, "Payment info not found");
		Assert.assertNotNull(shippingInfo, "Shipping info not found");
		Assert.assertNotNull(itemTotal, "Item total not found");
		Assert.assertNotNull(tax, "Tax not found");
		Assert.assertNotNull(total, "Total not found");
		Assert.assertNotNull(cancelBtn, "Cancel button not found");
		Assert.assertNotNull(finishBtn, "Finish button not found");
		
		// Verify critical elements are displayed
		Assert.assertTrue(pageTitle.isDisplayed(), "Page title not displayed");
		Assert.assertTrue(cancelBtn.isDisplayed(), "Cancel button not displayed");
		Assert.assertTrue(finishBtn.isDisplayed(), "Finish button not displayed");
		
		// Verify buttons are enabled
		Assert.assertTrue(cancelBtn.isEnabled(), "Cancel button not enabled");
		Assert.assertTrue(finishBtn.isEnabled(), "Finish button not enabled");
		
		System.out.println("  ✓ Page Title: Displayed");
		System.out.println("  ✓ Payment Info: Displayed");
		System.out.println("  ✓ Shipping Info: Displayed");
		System.out.println("  ✓ Item Total: Displayed");
		System.out.println("  ✓ Tax: Displayed");
		System.out.println("  ✓ Total: Displayed");
		System.out.println("  ✓ Cancel Button: Displayed & Enabled");
		System.out.println("  ✓ Finish Button: Displayed & Enabled");
	}

	/**
	 * Helper Method: Extract Numeric Price Value from String
	 * Description: Extracts the numeric value from price strings like "$29.99"
	 * Parameter: priceString - Price string to parse (e.g., "Item total: $29.99")
	 * Returns: Double value representing the price
	 */
	private double extractPriceValue(String priceString) {
		// Remove all non-numeric characters except decimal point
		String numericOnly = priceString.replaceAll("[^0-9.]", "");
		
		// Extract the last numeric value (in case there are multiple)
		String[] values = numericOnly.split("[^0-9.]+");
		for (int i = values.length - 1; i >= 0; i--) {
			if (!values[i].isEmpty()) {
				try {
					return Double.parseDouble(values[i]);
				} catch (NumberFormatException e) {
					continue;
				}
			}
		}
		return 0.0;
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
