package json;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility Class for Reading and Parsing Checkout Test Data from JSON
 * 
 * This class provides methods to read and parse the data.json file which contains
 * checkout test data. It uses Jackson ObjectMapper for JSON parsing.
 * 
 * Features:
 * - Read all checkout test data from JSON file
 * - Get test data by index
 * - Get specific test data by test case ID
 * - List all test data
 * 
 * Usage:
 * CheckoutDataReader reader = new CheckoutDataReader();
 * List<CheckoutData> allData = reader.getAllCheckoutData();
 * CheckoutData singleData = reader.getCheckoutDataByIndex(0);
 */
public class CheckoutDataReader {

	private ObjectMapper objectMapper;

	public CheckoutDataReader() {
		this.objectMapper = new ObjectMapper();
	}

	/**
	 * Reads all checkout test data from data.json file
	 * 
	 * @return List of CheckoutData objects
	 * @throws IOException if file is not found or JSON parsing fails
	 */
	public List<CheckoutData> getAllCheckoutData() throws IOException {
		List<CheckoutData> checkoutDataList = new ArrayList<>();

		try (InputStream is = getClass().getClassLoader().getResourceAsStream("data.json")) {
			if (is == null) {
				throw new IOException("data.json not found on classpath");
			}
			// Read the JSON from classpath resource
			JsonNode rootNode = objectMapper.readTree(is);

			// Get the checkoutData array from JSON
			JsonNode checkoutDataArray = rootNode.get("checkoutData");

			if (checkoutDataArray != null && checkoutDataArray.isArray()) {
				// Iterate through each element in the array
				for (JsonNode dataNode : checkoutDataArray) {
					CheckoutData data = objectMapper.treeToValue(dataNode, CheckoutData.class);
					checkoutDataList.add(data);
				}
				System.out.println("✓ Successfully loaded " + checkoutDataList.size() + " test data from JSON");
			} else {
				System.out.println("⚠ Warning: 'checkoutData' array not found in JSON");
			}

		} catch (IOException e) {
			System.out.println("✗ Error: Failed to read JSON from classpath resource: data.json");
			throw new IOException("Unable to read checkout data from JSON resource: " + e.getMessage(), e);
		}

		return checkoutDataList;
	}

	/**
	 * Gets checkout data by index
	 * 
	 * @param index the index of the test data (0-based)
	 * @return CheckoutData object at the specified index
	 * @throws IOException if file read fails
	 * @throws IndexOutOfBoundsException if index is out of range
	 */
	public CheckoutData getCheckoutDataByIndex(int index) throws IOException {
		List<CheckoutData> allData = getAllCheckoutData();

		if (index < 0 || index >= allData.size()) {
			throw new IndexOutOfBoundsException(
					"Index " + index + " is out of bounds. Available data: " + allData.size());
		}

		return allData.get(index);
	}

	/**
	 * Gets checkout data by test case ID
	 * 
	 * @param testCaseId the test case ID (e.g., "TC01")
	 * @return CheckoutData object matching the test case ID, or null if not found
	 * @throws IOException if file read fails
	 */
	public CheckoutData getCheckoutDataByTestCaseId(String testCaseId) throws IOException {
		List<CheckoutData> allData = getAllCheckoutData();

		for (CheckoutData data : allData) {
			if (data.getTestCase().equalsIgnoreCase(testCaseId)) {
				System.out.println("✓ Found test case: " + testCaseId);
				return data;
			}
		}

		System.out.println("⚠ Warning: Test case " + testCaseId + " not found");
		return null;
	}

	/**
	 * Gets total count of test data available
	 * 
	 * @return total number of test cases
	 * @throws IOException if file read fails
	 */
	public int getTotalTestCaseCount() throws IOException {
		return getAllCheckoutData().size();
	}

	/**
	 * Displays all test data in a formatted manner
	 * 
	 * @throws IOException if file read fails
	 */
	public void displayAllTestData() throws IOException {
		List<CheckoutData> allData = getAllCheckoutData();
		System.out.println("\n========== CHECKOUT TEST DATA ==========");
		for (CheckoutData data : allData) {
			System.out.println(data.toString());
		}
		System.out.println("========================================\n");
	}

}
