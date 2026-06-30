package swaglabstests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SwagInfopage {
	WebDriver driver;

	public SwagInfopage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "first-name")
	private WebElement firstname;
	
	public WebElement getFirstname() {
		return firstname;
	}

	public WebElement getLastname() {
		return lastname;
	}

	public WebElement getPostalcode() {
		return postalcode;
	}

	@FindBy(id = "last-name")
	private WebElement lastname;
	
	@FindBy(id = "postal-code")
	private WebElement postalcode;
	
	@FindBy(id="continue")
	private WebElement contnue;

	public WebElement getContnue() {
		return contnue;
	}
	@FindBy(id="cancel")
	private WebElement infocancelbutton;

	public WebElement getInfocancelbutton() {
		return infocancelbutton;
	}


}
