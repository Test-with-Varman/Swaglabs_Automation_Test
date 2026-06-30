package swaglabstests;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SwaglabsFinalCheckoutpage {
	WebDriver driver;
	public SwaglabsFinalCheckoutpage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
}
	@FindBy(xpath="//span[@data-test='title']")
	private WebElement pageTitle;

	@FindBy(xpath="//div[@class='inventory_item_name']")
	private List<WebElement> productNames;

	@FindBy(xpath="//div[@class='inventory_item_price']")
	private List<WebElement> productPrices;

	@FindBy(xpath="//div[@class='cart_quantity']")
	private List<WebElement> quantities;

	@FindBy(xpath="//div[@data-test='payment-info-value']")
	private WebElement paymentInfo;

	@FindBy(xpath="//div[@data-test='shipping-info-value']")
	private WebElement shippingInfo;

	@FindBy(xpath="//div[@data-test='subtotal-label']")
	private WebElement itemTotal;

	@FindBy(xpath="//div[@data-test='tax-label']")
	private WebElement tax;

	@FindBy(xpath="//div[@data-test='total-label']")
	private WebElement total;

	@FindBy(id="cancel")
	private WebElement cancelBtn;

	@FindBy(id="finish")
	private WebElement finishBtn;
	public WebElement getPageTitle() {
		return pageTitle;
	}

	public List<WebElement> getProductNames() {
		return productNames;
	}

	public List<WebElement> getProductPrices() {
		return productPrices;
	}

	public List<WebElement> getQuantities() {
		return quantities;
	}

	public WebElement getPaymentInfo() {
		return paymentInfo;
	}

	public WebElement getShippingInfo() {
		return shippingInfo;
	}

	public WebElement getItemTotal() {
		return itemTotal;
	}

	public WebElement getTax() {
		return tax;
	}

	public WebElement getTotal() {
		return total;
	}

	public WebElement getCancelBtn() {
		return cancelBtn;
	}

	public WebElement getFinishBtn() {
		return finishBtn;
	}

}
