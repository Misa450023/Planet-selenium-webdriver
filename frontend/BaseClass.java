package frontend;

import java.text.Normalizer;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseClass {

	public WebDriver driver;
	public WebDriverWait wait;

	public BaseClass(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}

	public void selectDropdown(By element, int index) {

		Select select = new Select(driver.findElement(element));
		select.selectByIndex(index);

	}

	public Boolean isPresent(By element) {

		Boolean status = false;

		try {
			waiting(element);
			if (driver.findElement(element).isDisplayed()) {
				status = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public void closeIframe(By element, By element2) {

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
		clickOn(element2);
		driver.switchTo().defaultContent();

	}

	public void hoverOn(String dir) {

		String xpath = "//*[contains(text(), '" + dir + "')][@style='color: ']";
		waiting(By.xpath(xpath));
		By element = By.xpath(xpath);
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(element)).build().perform();
	}

	public void waiting(By element) {

		wait.until(ExpectedConditions.visibilityOfElementLocated(element));
	}

	public void writeText(By element, String text) {

		waiting(element);
		driver.findElement(element).sendKeys(text);
	}

	public void clickOn(By element) {

		waiting(element);
		driver.findElement(element).click();
		;
	}

}
