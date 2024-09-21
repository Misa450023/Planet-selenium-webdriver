 
package frontend;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utility.UtilClass;

public class MainPage extends BaseClass {

	
public MainPage(WebDriver driver) {
	super(driver);
}
	
	
public void openUrl(String url) {
	
driver.get(url);

}

public void closeFrame() {
	
closeIframe(By.xpath("//iframe[@title='**********']"),By.id("close"));	
clickOn(By.xpath("//button[@id='btn-cookie-allow']"));
}

public List<String> inputSearch(String text) {

By search=By.xpath("//input[@id='search']");
writeText(search, text);
driver.findElement(search).submit();
wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//a[@class='product-item-link']"), 1));

return driver.findElements(By.xpath("//a[@class='product-item-link']"))
.stream().map(el->el.getText()).collect(Collectors.toList());
}




public void openDirectory(String dir,String section) {

hoverOn(dir);
clickOn(By.linkText(section));

}

public void clearOfferPopup() {
	
String xpath="//button[@id='zsde5555555ter-close-btn']";


try {
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).click();
} catch (Exception e) {	
	e.printStackTrace();
}
}



public void navigateToMain() {
	
driver.navigate().to("<<**********>>");
wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='menu.desktop']")));
}

public List<String>getLinks(){
	
return driver.findElements(By.tagName("a")).stream()
.map(el->el.getAttribute("href")).collect(Collectors.toList());	
}


public void filterProducts() {
	
clickOn(By.xpath("//li[@data-label='PATIKE ZA TRČANJE']"));
clickOn(By.xpath("//li[@data-label='NIKE']"));
}

public void checkPriceFilter(String from,String to) {
	
List<WebElement>list=
driver.findElements(By.xpath("//input[@type='number']"));
list.get(0).sendKeys(from);
list.get(1).sendKeys(to);
list.get(1).submit();

}

public List chechPriceDropdownFilter(int index) throws InterruptedException {

Thread.sleep(4000);
selectDropdown(By.xpath("//select[@id='sorter']"), index);
Thread.sleep(3999);
List<String>prices=
driver.findElements(By.xpath("//span[@class='normal-price special-price zsdev-special-price']"))
.stream().map(el->el.getText()).collect(Collectors.toList());

return prices;
}

public List<String> setSizeofShooes(String size) {
	
String xpath="//div[@data-am-js='swatch-item'][@data-option-label='"+size+"']";
driver.findElement(By.xpath(xpath)).click();
wait.until(ExpectedConditions.numberOfElementsToBeMoreThan
		(By.xpath("//a[@class='product-item-link']"), 1));
driver.findElements(By.xpath("//a[@class='product-item-link']")).stream()
.findAny().get().click();
wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[@role='listbox']/div"), 1));          

return driver.findElements(By.xpath("//div[@role='listbox']/div")).stream()
.map(el->el.getAttribute("data-option-tooltip-value")).collect(Collectors.toList());
	
}



public List getFilteredData() { 

List<String>data=	
driver.findElements(By.xpath("//a[@class='product-item-link']"))
.stream().map(el->el.getText()).collect(Collectors.toList());


try {
	while(driver.findElement(By.xpath("//a[@class='action  next']")).isDisplayed()) {
	driver.findElement(By.xpath("//a[@class='action  next']")).click();
	List<String>tempData=driver.findElements(By.xpath("//a[@class='product-item-link']")).stream()
	.map(el->el.getText()).collect(Collectors.toList());
	data = Stream.concat(data.stream(), tempData.stream())
	.collect(Collectors.toList());
	}
} catch (Exception e) {
	
	e.printStackTrace();
}
return data;
}


public HashMap<String,Boolean> getRandomProduct(String dir,String section) {
	
hoverOn(dir);
clickOn(By.linkText(section));
WebElement item=driver.findElements(By.xpath("//a[@class='product-item-link']"))
.stream().findAny().get();
item.click();
return checkProductElements(section);
	
}

public HashMap<String,Boolean>checkProductElements(String section){
	
HashMap<String,Boolean>data=new HashMap<String,Boolean>();
data.put("URL_contains",driver.getCurrentUrl().contains(UtilClass.normalizeTxt(section)));
data.put("title_contains",UtilClass.normalizeTxt(driver.getTitle()).contains(UtilClass.normalizeTxt(section)));
data.put("image", isPresent(By.xpath("//div[@class='product media']")));
data.put("prices",driver.findElements(By.xpath("//span[@class='price tooltip-toggle']")).stream().allMatch(el-> el!=null));             
	


return data;	
}





}
