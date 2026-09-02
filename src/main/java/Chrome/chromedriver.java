package Chrome;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Chrome driver setup using WebDriverManager to avoid manual binary management.
 */
public class chromedriver {
	public RemoteWebDriver setup() {
		ChromeOptions option = new ChromeOptions();
		option.addArguments("--disable-features=PasswordLeakDetection,PasswordManagerOnboarding");
		option.addArguments("--guest");
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		option.setExperimentalOption("prefs", prefs);

		// Ensure chromedriver binary is available
		WebDriverManager.chromedriver().setup();

		RemoteWebDriver driver = new ChromeDriver(option);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

		return driver;
	}

}
