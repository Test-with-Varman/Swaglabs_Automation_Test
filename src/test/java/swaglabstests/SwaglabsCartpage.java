package swaglabstests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SwaglabsCartpage {
	WebDriver driver;

	public SwaglabsCartpage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "checkout")
	private WebElement checkout;

	public WebElement getCheckoutbutton() {
		return checkout;
	}

	@FindBy(id = "continue-shopping")
	private WebElement continueshopping;

	public WebElement getContinueshopping() {
		return continueshopping;
	}

}
