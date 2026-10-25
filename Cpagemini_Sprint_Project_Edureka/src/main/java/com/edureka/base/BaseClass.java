package com.edureka.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.edureka.utilities.CommonData;
import com.edureka.utilities.ExcelUtilities;
import com.edureka.utilities.ScreenShot;

import objRepo.AllCoursesPage;
import objRepo.ArtificialIntelligencePage;
import objRepo.LoginPage;

public class BaseClass {

	protected WebDriver driver;
	protected Properties properties;
	protected WebDriverWait wait;
	protected ExcelUtilities excel;
	protected CommonData prop;

	public LoginPage loginPage;
	public AllCoursesPage page;
	public ArtificialIntelligencePage aiPage;

	private static final int IMPLICIT_WAIT_SECONDS = 10;
	private static final int EXPLICIT_WAIT_SECONDS = 20;
	private static final int PAGE_LOAD_TIMEOUT_SECONDS = 60;

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() {
		Reporter.log("Test suite started.", true);
	}

	@BeforeTest(alwaysRun = true)
	public void beforeTest() {
		Reporter.log("Test started.", true);
	}

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		prop = new CommonData();
		excel = new ExcelUtilities();

		properties = new Properties();

		try (FileInputStream fis = new FileInputStream("./src/main/resources/CommonData.properties")) {
			properties.load(fis);
		}

		Reporter.log("Before class", true);
	}

	@Parameters("browser")
	@BeforeMethod(alwaysRun = true)
	public void setUp(@Optional("chrome") String browser) throws IOException {

		String selectedBrowser = browser;

		if (selectedBrowser == null || selectedBrowser.isBlank()) {
			selectedBrowser = properties.getProperty("browser", "chrome");
		}

		switch (selectedBrowser.trim().toLowerCase()) {
		case "chrome":
			ChromeOptions chromeOptions = new ChromeOptions();
			driver = new ChromeDriver(chromeOptions);
			break;

		case "edge":
			EdgeOptions edgeOptions = new EdgeOptions();
			driver = new EdgeDriver(edgeOptions);
			break;

		case "firefox":
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			driver = new FirefoxDriver(firefoxOptions);
			break;

		default:
			throw new IllegalArgumentException("Unsupported browser: " + selectedBrowser);
		}

		driver.manage().window().maximize();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_SECONDS));

		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIMEOUT_SECONDS));

		loginPage = new LoginPage(driver);
		page = new AllCoursesPage(driver);
		aiPage = new ArtificialIntelligencePage(driver);

		wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_SECONDS));

		Reporter.log("Launching browser: " + selectedBrowser, true);

		driver.get(prop.getData("url"));
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result) {
		try {
			if (result.getStatus() == ITestResult.FAILURE) {
				Reporter.log("FAILED: " + result.getMethod().getMethodName(), true);

				if (driver != null) {
					try {
						String screenshotPath = ScreenShot.captureScreenshot(driver, result.getMethod().getMethodName(),
								properties);

						Reporter.log("Screenshot saved: " + screenshotPath, true);

					} catch (Exception e) {
						Reporter.log("Screenshot capture failed: " + e.getMessage(), true);
					}
				}

			} else if (result.getStatus() == ITestResult.SUCCESS) {
				Reporter.log("PASSED: " + result.getMethod().getMethodName(), true);

			} else if (result.getStatus() == ITestResult.SKIP) {
				Reporter.log("SKIPPED: " + result.getMethod().getMethodName(), true);
			}

		} finally {
			if (driver != null) {
				try {
					driver.quit();
					Reporter.log("Browser closed successfully.", true);

				} catch (Exception e) {
					Reporter.log("Browser cleanup message: " + e.getMessage(), true);

				} finally {
					driver = null;
					wait = null;
				}
			}
		}
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		Reporter.log("Test class completed.", true);
	}
}
