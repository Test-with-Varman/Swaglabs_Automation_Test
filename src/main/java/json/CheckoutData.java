package json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO Class for Checkout Data Mapping
 * 
 * This class maps the JSON test data from data.json file for checkout page testing.
 * It uses Jackson annotations to bind JSON properties to Java fields.
 * 
 * Test Data Fields:
 * - testCase: Unique identifier for the test case (e.g., TC01, TC02, etc.)
 * - firstName: First name to be entered in checkout form
 * - lastName: Last name to be entered in checkout form
 * - postalCode: Postal code to be entered in checkout form
 * - expectedResult: Expected outcome (Success or Error)
 * - expectedMessage: Expected error message if result is Error
 */
public class CheckoutData {

	@JsonProperty("testCase")
	private String testCase;

	@JsonProperty("firstName")
	private String firstName;

	@JsonProperty("lastName")
	private String lastName;

	@JsonProperty("postalCode")
	private String postalCode;

	@JsonProperty("expectedResult")
	private String expectedResult;

	@JsonProperty("expectedMessage")
	private String expectedMessage;

	// Default Constructor
	public CheckoutData() {
	}

	// Parameterized Constructor
	public CheckoutData(String testCase, String firstName, String lastName, String postalCode,
			String expectedResult, String expectedMessage) {
		this.testCase = testCase;
		this.firstName = firstName;
		this.lastName = lastName;
		this.postalCode = postalCode;
		this.expectedResult = expectedResult;
		this.expectedMessage = expectedMessage;
	}

	// Getters and Setters
	public String getTestCase() {
		return testCase;
	}

	public void setTestCase(String testCase) {
		this.testCase = testCase;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getExpectedResult() {
		return expectedResult;
	}

	public void setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
	}

	public String getExpectedMessage() {
		return expectedMessage;
	}

	public void setExpectedMessage(String expectedMessage) {
		this.expectedMessage = expectedMessage;
	}

	// toString() method for debugging
	@Override
	public String toString() {
		return "CheckoutData [testCase=" + testCase + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", postalCode=" + postalCode + ", expectedResult=" + expectedResult + ", expectedMessage="
				+ expectedMessage + "]";
	}

}
