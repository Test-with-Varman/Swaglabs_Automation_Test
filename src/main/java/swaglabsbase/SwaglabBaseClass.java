package swaglabsbase;

import org.openqa.selenium.WebDriver;

import Chrome.chromedriver;
import properties.readproperties;

public abstract class SwaglabBaseClass {

	protected WebDriver driver;

	public void execute() throws Throwable {

		chromedriver c = new chromedriver();
		driver = c.setup();

		readproperties rp = new readproperties();

		String url = rp.getUrl();
		driver.get(url);
		System.out.println(url);

	}

}
