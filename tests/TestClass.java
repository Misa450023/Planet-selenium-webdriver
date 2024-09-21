package tests;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import frontend.BaseClass;
import frontend.MainPage;
import utility.UtilClass;

public class TestClass extends BaseTest {

	public MainPage mainPage;

//description "give products from specific directory"
	@Test(priority = 0)
	public void test01() {

		mainPage = new MainPage(driver);

		mainPage.openUrl("<****************************>");
		mainPage.closeFrame();
		mainPage.openDirectory("MUŠKARCI", "NAOČARE");
		List<String> data = mainPage.getFilteredData();

		Assert.assertTrue(data.stream().allMatch(el -> el.contains("Naočare") || el.contains("Naocare")));
		Assert.assertTrue(data.size() > 1);

		mainPage.navigateToMain();

	}

//Get a single product and check al eelements
	@Test(priority = 1)
	public void test02() throws InterruptedException {

		Thread.sleep(2500);
		HashMap<String, Boolean> mymap = mainPage.getRandomProduct("MUŠKARCI", "NAOČARE");
		boolean match = mymap.values().stream().allMatch(el -> el == true);

		Assert.assertEquals(match, true);

		mainPage.navigateToMain();
	}

	@Test(priority = 2)
	public void test03() throws InterruptedException {

		Thread.sleep(2500);
		mainPage.clearOfferPopup();
		mainPage.openDirectory("MUŠKARCI", "PATIKE");
		List<String> list = mainPage.setSizeofShooes("50");

		Assert.assertTrue(list.contains("50"));

		mainPage.navigateToMain();
	}

//test drop down filter
	@Test(priority = 3)
	public void test04() throws InterruptedException {

		Thread.sleep(2500);
		mainPage.openDirectory("MUŠKARCI", "PATIKE");
		List<String> prices = mainPage.chechPriceDropdownFilter(1);
		List<Integer> pricesNumeric = prices.stream().map(el -> UtilClass.pricetoInt(el)).collect(Collectors.toList());

		Assert.assertTrue(UtilClass.isSortedReverse(pricesNumeric));

		mainPage.navigateToMain();
	}

//test input search field
	@Test(priority = 4)
	public void test05() {

		List<String> list = UtilClass.removeBlank(mainPage.inputSearch("downshifter"));

		Assert.assertTrue(list.size() > 1);
		Assert.assertTrue(list.stream().allMatch(el -> el.contains("downshifter")));

		mainPage.navigateToMain();
	}

//testing broken links if links are good returns true
	@Test(priority = 5)
	public void test06() {

		mainPage.openDirectory("MUŠKARCI", "PATIKE");
		List<String> twentyRandomlinks = UtilClass.getRandomElements(mainPage.getLinks(), 20);

		Assert.assertTrue(UtilClass.emptyLinkCheck(twentyRandomlinks));
		Assert.assertTrue(UtilClass.checkBrokenLinks(twentyRandomlinks));

	}

}
