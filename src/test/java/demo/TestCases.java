package demo;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import demo.wrappers.Wrappers;

import java.time.Duration;
import java.util.logging.Level;

public class TestCases {
    ChromeDriver driver;
    String url = "https://www.scrapethissite.com/";
    JavascriptExecutor js;
    WebDriverWait wait;

    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // Initialize after driver is assigned
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(priority = 1, description = "TestCase01", enabled = true)
    public void testCase01() throws Exception {
        System.out.println("Start TestCase01");
        // Navigating to the website
        Wrappers.navigateToUrl(driver, url);
        // Selecting the page
        Wrappers.selectPage(driver, "Sandbox");
        // Clicking on the desired heading
        Wrappers.headingSelection(driver, "Hockey");
        // Printing the Teamname, year, win %
        Wrappers.teamData(driver);
        System.out.println("End TestCase01");
    }

    @Test(priority = 2, description = "TestCase02", enabled = true)
    public void testCase02() throws Exception {
        System.out.println("Start TestCase02");
        // Navigating to the website
        Wrappers.navigateToUrl(driver, url);
        // Selecting the page
        Wrappers.selectPage(driver, "Sandbox");
        // Clicking on the desired heading
        Wrappers.headingSelection(driver, "Oscar");
        Wrappers.scrapeOscarData(driver);
        Wrappers.verifyFile("src/test/resources/oscar-winner-data.json");
        System.out.println("End TestCase02");
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();
    }
}
