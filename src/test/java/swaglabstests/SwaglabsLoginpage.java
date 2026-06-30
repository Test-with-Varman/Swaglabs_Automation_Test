package swaglabstests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SwaglabsLoginpage {
	WebDriver driver;

	public SwaglabsLoginpage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "user-name")
	private WebElement username;

	public WebElement getUsername() {
		return username;

	}

	@FindBy(id = "password")
	private WebElement password;

	public WebElement getPassword() {
		return password;

	}

	@FindBy(id = "login-button")
	private WebElement login;

	public WebElement getLogin() {
		return login;
	}

	@FindBy(xpath = "//h3[@data-test='error']")
	private WebElement errorMessageContainer;

	public String getErrorMessage() {
		return errorMessageContainer.getText();
	}

}
