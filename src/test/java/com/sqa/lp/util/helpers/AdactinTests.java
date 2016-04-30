package com.sqa.lp.util.helpers;

import java.util.*;

import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.support.ui.*;
import org.testng.*;
import org.testng.annotations.*;

public class AdactinTests {

	private static String devLocation = "src/main/resources/dev.properties";
	private static Properties devProperties;
	private static WebDriver driver;
	private static String searchHotelLocation = "src/main/resources/search-hotel.properties";
	private static Properties searchHotelProperties;
	private static String sharedMapUILocation = "src/main/resources/shared-map-ui.properties";
	private static Properties sharedMapUIProperties;

	@AfterClass
	public void afterClass() {
		driver.close();
		driver.quit();
	}

	@DataProvider
	public Object[][] invalidCheckOutDate() {
		Object[][] data = new Object[1][];
		Object[] data1 = { devProperties.getProperty("checkInDate"), devProperties.getProperty("checkOutDate") };
		data[0] = data1;

		System.out.println("In data provider----- checkInDate: " + devProperties.getProperty("checkInDate")
				+ " checkOutDate: " + devProperties.getProperty("checkOutDate"));
		return data;
	}

	@Test(dataProvider = "invalidCheckOutDate")
	public void invalidCheckOutDateTest(String checkInDate, String checkOutDate) {

		logIntoAccount("Prat1234", "sqa140sanpedro");
		searchHotel(checkInDate, checkOutDate);

		String expectedResult = "Check-In Date shall be before than Check-Out Date";
		WebElement checkInDateErrorMessage = driver
				.findElement(By.xpath(searchHotelProperties.getProperty("checkInDateErrorMessage")));
		Assert.assertEquals(checkInDateErrorMessage.getText(), expectedResult);

		logOut();

	}

	@BeforeClass
	public void loadProperties() {
		System.out.println("In load properties");
		devProperties = ConProperties.loadProperties(devLocation);
		sharedMapUIProperties = ConProperties.loadProperties(sharedMapUILocation);
		searchHotelProperties = ConProperties.loadProperties(searchHotelLocation);
	}

	@Test(dataProvider = "userCredentials")
	public void loginTest(String username, String password) {

		logIntoAccount(username, password);

		// WebElement welcomeMessage =
		// driver.findElement(By.xpath(sharedMapUIProperties.getProperty("welcomeMessage")));
		// String expectedResult = "Welcome to AdactIn Group of Hotels";
		// Assert.assertEquals(welcomeMessage.getText(), expectedResult);

		Assert.assertTrue(isElementPresent(By.id("username_show")));

		logOut();

	}

	@BeforeClass(dependsOnMethods = "loadProperties")
	public void setUpFirefox() {
		System.out.println("In set up firefox");
		driver = new FirefoxDriver();
		driver.get(devProperties.getProperty("baseURL"));
		driver.manage().window().maximize();
	}

	@DataProvider
	public Object[][] userCredentials() {
		Object[][] data = new Object[1][];
		Object[] data1 = { devProperties.getProperty("username"), devProperties.getProperty("password") };
		data[0] = data1;

		System.out.println("In data provider----- username: " + devProperties.getProperty("username") + " password: "
				+ devProperties.getProperty("password"));
		return data;
	}

	/**
	 * @param findElement
	 * @return
	 */
	private boolean isElementPresent(By by) {
		try {
			this.driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * @param username
	 * @param password
	 */
	private void logIntoAccount(String username, String password) {
		WebElement usernameField = driver.findElement(By.xpath(sharedMapUIProperties.getProperty("usernameField")));
		usernameField.clear();
		usernameField.sendKeys(username);
		WebElement passwordField = driver.findElement(By.xpath(sharedMapUIProperties.getProperty("passwordField")));
		passwordField.clear();
		passwordField.sendKeys(password);
		WebElement loginButton = driver.findElement(By.xpath(sharedMapUIProperties.getProperty("loginButton")));
		loginButton.click();
	}

	/**
	 *
	 */
	private void logOut() {
		WebElement logoutButton = driver.findElement(By.xpath(sharedMapUIProperties.getProperty("logoutButton")));
		logoutButton.click();
		driver.findElement(By.xpath(sharedMapUIProperties.getProperty("loginAgain"))).click();
	}

	/**
	 * @param checkInDate
	 * @param checkOutDate
	 */
	private void searchHotel(String checkInDate, String checkOutDate) {

		WebElement locationField = driver.findElement(By.xpath(searchHotelProperties.getProperty("locationField")));
		Select select = new Select(locationField);
		select.selectByIndex(1);

		WebElement hotelsField = driver.findElement(By.xpath(searchHotelProperties.getProperty("hotelsField")));
		Select select1 = new Select(hotelsField);
		select1.selectByIndex(1);

		WebElement roomTypeField = driver.findElement(By.xpath(searchHotelProperties.getProperty("roomTypeField")));
		Select select2 = new Select(roomTypeField);
		select2.selectByIndex(1);

		WebElement numberOfRoomsField = driver
				.findElement(By.xpath(searchHotelProperties.getProperty("numberOfRoomsField")));
		Select select3 = new Select(numberOfRoomsField);
		select3.selectByIndex(1);

		WebElement checkInDateField = driver
				.findElement(By.xpath(searchHotelProperties.getProperty("checkInDateField")));
		checkInDateField.clear();
		checkInDateField.sendKeys(checkInDate);

		WebElement checkOutDateField = driver
				.findElement(By.xpath(searchHotelProperties.getProperty("checkOutDateField")));
		checkOutDateField.clear();
		checkOutDateField.sendKeys(checkOutDate);

		WebElement adultsPerRoom = driver.findElement(By.xpath(searchHotelProperties.getProperty("adultsPerRoom")));
		Select select4 = new Select(adultsPerRoom);
		select4.selectByIndex(1);

		driver.findElement(By.xpath(searchHotelProperties.getProperty("searchButton"))).click();
	}

}
