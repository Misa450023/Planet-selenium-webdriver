package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest {

	public WebDriver driver;

	@BeforeTest
	public void setup() {

		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("dom.webnotifications.enabled", false);
		FirefoxOptions options = new FirefoxOptions();
		options.setProfile(profile);

		driver = new FirefoxDriver(options);
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\zi<<>>er.exe");
		driver.manage().window().maximize();
	}

	@AfterTest
	public void stop() {

		driver.quit();
	}

}
