package com.sqa.lp;

import org.apache.log4j.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.safari.*;
import org.testng.annotations.*;

public class BasicAutoTest {

static WebDriver driver;
static Logger logger = Logger.getLogger(BasicAutoTest.class);

@BeforeClass(enabled = false, groups = "chrome")
public static void setUpChrome() throws InterruptedException {
System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver");
driver = new ChromeDriver();

driver.get("http://gmail.com");
Thread.sleep(1000);
}

@BeforeClass(enabled = true, groups = "firefox")
public static void setUpFirefox() throws InterruptedException {
driver = new FirefoxDriver();
driver.get("http://gmail.com");
Thread.sleep(1000);
}

@BeforeClass(enabled = false, groups = "safari")
public static void setUpSafari() throws InterruptedException {
driver = new SafariDriver();
driver.get("http://gmail.com");
Thread.sleep(1000);
}

@DataProvider(name = "UserAccountInfo")
public Object[][] getData() {
Object[][] data = { { "PratAutoTest1", "autotest1" }, { "PratAutoTest2", "autotest2" },
{ "PratAutoTest3", "autotest3" } };
return data;
}

@Test(dataProvider = "UserAccountInfo")
public void test(String userName, String password) {
System.out.println("Basic Test: U- " + userName + " P- " + password);
logger.info("My Informaion.. ");
for (int i = 0; i < 1000; i++) {
logger.info("Info Message: " + i);
if (i % 5 == 0) {
logger.fatal("Fatal Message: " + i);
}
logger.debug("Debug Message: " + i);
}
}

}
