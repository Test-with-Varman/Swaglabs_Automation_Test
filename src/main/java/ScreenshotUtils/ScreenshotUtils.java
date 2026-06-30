package ScreenshotUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;

/**
 * ScreenshotUtils Class
 * Description: Utility class to capture and save screenshots during test execution
 * Purpose: Provides methods to take screenshots and save them in respective test folders with standardized naming convention
 * Naming Convention: testclassname_methodname_timestamp.png
 */
public class ScreenshotUtils {

	// Screenshot folders paths
	private static final String LOGIN_SCREENSHOT_PATH = "screenshots/loginpagescreenshot/";
	private static final String CART_SCREENSHOT_PATH = "screenshots/cartpagescreenshot/";
	private static final String CHECKOUT_SCREENSHOT_PATH = "screenshots/checkoutpagescreenshot/";
	private static final String E2E_SCREENSHOT_PATH = "screenshots/endtoscreenshot/";
	private static final String PRODUCT_SCREENSHOT_PATH = "screenshots/productpagescreenshot/";

	/**
	 * Method: Capture and Save Screenshot
	 * Description: Captures screenshot from the provided WebDriver and saves it to the specified folder
	 * Parameters:
	 *   - driver: WebDriver instance
	 *   - testClassName: Name of the test class (e.g., "LoginTest")
	 *   - methodName: Name of the test method (e.g., "StandardLogin")
	 *   - folderType: Type of folder (login, cart, checkout, e2e, product)
	 * Returns: String - Path of the saved screenshot file
	 * Exception: IOException if file cannot be saved
	 */
	public static String captureScreenshot(WebDriver driver, String testClassName, String methodName, String folderType) throws IOException {
		if (driver == null) {
			System.out.println("⚠ WebDriver is null - cannot capture screenshot");
			return null;
		}

		try {
			// Get screenshot as file
			File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			// Determine the folder path based on folder type
			String folderPath = determineFolderPath(folderType);

			// Create folder if it doesn't exist
			File folder = new File(folderPath);
			if (!folder.exists()) {
				folder.mkdirs();
				System.out.println("✓ Created screenshot folder: " + folderPath);
			}

			// Generate timestamp
			String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());

			// Create screenshot file name with convention: testclassname_methodname_timestamp.png
			String fileName = testClassName + "_" + methodName + "_" + timestamp + ".png";
			String filePath = folderPath + fileName;

			// Copy screenshot to destination folder
			File destFile = new File(filePath);
			FileUtils.copyFile(srcFile, destFile);

			System.out.println("✓ Screenshot captured: " + filePath);
			return filePath;

		} catch (IOException e) {
			System.out.println("✗ Error capturing screenshot: " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Method: Capture Screenshot (Overloaded)
	 * Description: Overloaded method with automatic folder type detection based on test class name
	 * Parameters:
	 *   - driver: WebDriver instance
	 *   - testClassName: Name of the test class
	 *   - methodName: Name of the test method
	 * Returns: String - Path of the saved screenshot file
	 */
	public static String captureScreenshot(WebDriver driver, String testClassName, String methodName) throws IOException {
		// Auto-detect folder type from test class name
		String folderType = detectFolderType(testClassName);
		return captureScreenshot(driver, testClassName, methodName, folderType);
	}

	/**
	 * Method: Determine Folder Path
	 * Description: Determines the folder path based on folder type
	 * Parameters:
	 *   - folderType: Type of folder (login, cart, checkout, e2e, product)
	 * Returns: String - Full path to the screenshot folder
	 */
	private static String determineFolderPath(String folderType) {
		switch (folderType.toLowerCase()) {
		case "login":
			return LOGIN_SCREENSHOT_PATH;
		case "cart":
			return CART_SCREENSHOT_PATH;
		case "checkout":
			return CHECKOUT_SCREENSHOT_PATH;
		case "e2e":
		case "endtoend":
			return E2E_SCREENSHOT_PATH;
		case "product":
			return PRODUCT_SCREENSHOT_PATH;
		default:
			return "screenshots/default/";
		}
	}

	/**
	 * Method: Detect Folder Type from Test Class Name
	 * Description: Automatically detects folder type based on test class name
	 * Parameters:
	 *   - testClassName: Name of the test class
	 * Returns: String - Folder type (login, cart, checkout, e2e, product)
	 */
	private static String detectFolderType(String testClassName) {
		if (testClassName.toLowerCase().contains("login")) {
			return "login";
		} else if (testClassName.toLowerCase().contains("cart")) {
			return "cart";
		} else if (testClassName.toLowerCase().contains("checkout") || testClassName.toLowerCase().contains("final")) {
			return "checkout";
		} else if (testClassName.toLowerCase().contains("endtoend") || testClassName.toLowerCase().contains("e2e")) {
			return "e2e";
		} else if (testClassName.toLowerCase().contains("product")) {
			return "product";
		}
		return "default";
	}

	/**
	 * Method: Clean Old Screenshots
	 * Description: Cleans up old screenshot files from a specific folder (optional cleanup task)
	 * Parameters:
	 *   - folderPath: Path to the folder
	 *   - maxAgeHours: Maximum age of files to keep (in hours)
	 * Returns: void
	 */
	public static void cleanOldScreenshots(String folderPath, int maxAgeHours) {
		try {
			File folder = new File(folderPath);
			if (!folder.exists()) {
				System.out.println("⚠ Folder does not exist: " + folderPath);
				return;
			}

			File[] files = folder.listFiles();
			long currentTime = System.currentTimeMillis();
			long maxAge = maxAgeHours * 60 * 60 * 1000; // Convert hours to milliseconds

			if (files != null) {
				for (File file : files) {
					if (file.isFile() && (currentTime - file.lastModified()) > maxAge) {
						file.delete();
						System.out.println("✓ Deleted old screenshot: " + file.getName());
					}
				}
			}
		} catch (Exception e) {
			System.out.println("✗ Error cleaning old screenshots: " + e.getMessage());
		}
	}

	/**
	 * Method: Print Screenshot Folder Info
	 * Description: Prints information about screenshot folders and their locations
	 * Returns: void
	 */
	public static void printScreenshotFolderInfo() {
		System.out.println("\n========== Screenshot Folder Information ==========");
		System.out.println("Login Page Screenshots: " + LOGIN_SCREENSHOT_PATH);
		System.out.println("Cart Page Screenshots: " + CART_SCREENSHOT_PATH);
		System.out.println("Checkout Page Screenshots: " + CHECKOUT_SCREENSHOT_PATH);
		System.out.println("E2E Test Screenshots: " + E2E_SCREENSHOT_PATH);
		System.out.println("Product Page Screenshots: " + PRODUCT_SCREENSHOT_PATH);
		System.out.println("=====================================================\n");
	}
}
