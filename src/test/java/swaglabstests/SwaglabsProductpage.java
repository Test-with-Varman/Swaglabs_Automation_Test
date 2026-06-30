package swaglabstests;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SwaglabsProductpage {

	WebDriver driver;

	public SwaglabsProductpage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	// ADD TO CART

	@FindBy(id = "add-to-cart-sauce-labs-backpack")
	private WebElement addToCartProduct1;

	@FindBy(id = "add-to-cart-sauce-labs-bike-light")
	private WebElement addToCartProduct2;

	@FindBy(id = "add-to-cart-sauce-labs-bolt-t-shirt")
	private WebElement addToCartProduct3;

	@FindBy(id = "add-to-cart-sauce-labs-fleece-jacket")
	private WebElement addToCartProduct4;

	@FindBy(id = "add-to-cart-sauce-labs-onesie")
	private WebElement addToCartProduct5;

	@FindBy(id = "add-to-cart-test.allthethings()-t-shirt-(red)")
	private WebElement addToCartProduct6;

	// REMOVE

	@FindBy(id = "remove-sauce-labs-backpack")
	private WebElement removeProduct1;

	@FindBy(id = "remove-sauce-labs-bike-light")
	private WebElement removeProduct2;

	@FindBy(id = "remove-sauce-labs-bolt-t-shirt")
	private WebElement removeProduct3;

	@FindBy(id = "remove-sauce-labs-fleece-jacket")
	private WebElement removeProduct4;

	@FindBy(id = "remove-sauce-labs-onesie")
	private WebElement removeProduct5;

	@FindBy(id = "remove-test.allthethings()-t-shirt-(red)")
	private WebElement removeProduct6;

	// VIEW PRODUCTS

	@FindBy(xpath = "//div[.='Sauce Labs Backpack']")
	private WebElement viewProduct1;

	@FindBy(xpath = "//div[.='Sauce Labs Bike Light']")
	private WebElement viewProduct2;

	@FindBy(xpath = "//div[.='Sauce Labs Bolt T-Shirt']")
	private WebElement viewProduct3;

	@FindBy(xpath = "//div[.='Sauce Labs Fleece Jacket']")
	private WebElement viewProduct4;

	@FindBy(xpath = "//div[.='Sauce Labs Onesie']")
	private WebElement viewProduct5;

	@FindBy(xpath = "//div[.='Test.allTheThings() T-Shirt (Red)']")
	private WebElement viewProduct6;

	public WebElement getAddToCartProduct1() {
		return addToCartProduct1;
	}

	public WebElement getAddToCartProduct2() {
		return addToCartProduct2;
	}

	public WebElement getAddToCartProduct3() {
		return addToCartProduct3;
	}

	public WebElement getAddToCartProduct4() {
		return addToCartProduct4;
	}

	public WebElement getAddToCartProduct5() {
		return addToCartProduct5;
	}

	public WebElement getAddToCartProduct6() {
		return addToCartProduct6;
	}

	public WebElement getRemoveProduct1() {
		return removeProduct1;
	}

	public WebElement getRemoveProduct2() {
		return removeProduct2;
	}

	public WebElement getRemoveProduct3() {
		return removeProduct3;
	}

	public WebElement getRemoveProduct4() {
		return removeProduct4;
	}

	public WebElement getRemoveProduct5() {
		return removeProduct5;
	}

	public WebElement getRemoveProduct6() {
		return removeProduct6;
	}

	public WebElement getViewProduct1() {
		return viewProduct1;
	}

	public WebElement getViewProduct2() {
		return viewProduct2;
	}

	public WebElement getViewProduct3() {
		return viewProduct3;
	}

	public WebElement getViewProduct4() {
		return viewProduct4;
	}

	public WebElement getViewProduct5() {
		return viewProduct5;
	}

	public WebElement getViewProduct6() {
		return viewProduct6;
	}

	public WebElement getAbout() {
		return about;
	}

	public WebElement getReset() {
		return reset;
	}

	@FindBy(className = "shopping_cart_link")
	private WebElement cartButton;

	public WebElement getCartButton() {
		return cartButton;
	}

	@FindBy(xpath = "//button[.='Open Menu']")
	private WebElement burger;

	public WebElement getBurger() {
		return burger;
	}

	@FindBy(id = "logout_sidebar_link")
	private WebElement logout;

	public WebElement getLogout() {
		return logout;
	}

	@FindBy(className = "product_sort_container")
	private WebElement sort;

	public WebElement getSort() {
		return sort;
	}

	@FindBy(xpath = "//option[@value='az']")
	private WebElement SortAtoZ;

	@FindBy(xpath = "//option[@value='za']")
	private WebElement SortZtoA;

	@FindBy(xpath = "//option[.='Price (high to low)']")
	private WebElement SortHiTolo;

	@FindBy(xpath = "//option[@value='lohi']")
	private WebElement SortLotohi;

	public WebElement getSortAtoZ() {
		return SortAtoZ;
	}

	public WebElement getSortZtoA() {
		return SortZtoA;
	}

	public WebElement getSortHiTolo() {
		return SortHiTolo;
	}

	public WebElement getSortLotohi() {
		return SortLotohi;
	}

	@FindBy(linkText = "Twitter")

	private WebElement twitter;

	public WebElement getTwitter() {
		return twitter;
	}

	public WebElement getFacebook() {
		return facebook;
	}

	public WebElement getLinkedin() {
		return linkedin;
	}

	@FindBy(linkText = "Facebook")
	private WebElement facebook;
	@FindBy(linkText = "LinkedIn")
	private WebElement linkedin;

	@FindBy(linkText = "About")
	private WebElement about;

	@FindBy(id = "reset_sidebar_link")
	private WebElement reset;

	@FindBy(xpath = "//span[@data-test='shopping-cart-badge']")
	private WebElement cartnumber;

	public WebElement getCartnumber() {
		return cartnumber;
	}

	@FindBy(xpath = "//div[@class='inventory_item_price'and text()='29.99']")
	private WebElement product1price;

	@FindBy(xpath = "//div[@class='inventory_item_price'and text()='9.99']")
	private WebElement product2price;

	@FindBy(xpath = "//div[@class='inventory_item_price'and text()='15.99']")
	private WebElement product3price;

	@FindBy(xpath = "//div[@class='inventory_item_price'and text()='49.99']")
	private WebElement product4price;

	@FindBy(xpath = "//div[@class='inventory_item_price'and text()='7.99']")
	private WebElement product5price;

	@FindBy(xpath = "//div[@class='inventory_item_price'and text()='15.99']")
	private WebElement product6price;

	public WebElement getProduct1price() {
		return product1price;
	}

	public WebElement getProduct2price() {
		return product2price;
	}

	public WebElement getProduct3price() {
		return product3price;
	}

	public WebElement getProduct4price() {
		return product4price;
	}

	public WebElement getProduct5price() {
		return product5price;

	}

	public WebElement getProduct6price() {
		return product6price;
	}


	@FindBy(xpath="//div[@data-test='inventory-item-name']")
	private List<WebElement> products;

	
	public List<WebElement> getProducts() {
		return products;
	}
	@FindBy(xpath="//div[@data-test='inventory-item-price']")
	private List<WebElement> productprices;
	
	public List<WebElement> getProductprices() {
		return productprices;
	}
	
}
