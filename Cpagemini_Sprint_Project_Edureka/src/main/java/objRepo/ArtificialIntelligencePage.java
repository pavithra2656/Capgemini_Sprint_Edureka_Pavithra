
package objRepo;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.edureka.utilities.CommonData;

public class ArtificialIntelligencePage {

	private WebDriver driver;
	private WebDriverWait wait;
	private CommonData prop = new CommonData();

	@FindBy(xpath = "//*[self::h1 or self::h2][contains(normalize-space(.), 'Artificial Intelligence')]")
	private WebElement aiHeading;

	@FindBy(xpath = "//a[contains(normalize-space(.), 'Explore Artificial Intelligence Courses')]")
	private WebElement exploreAIButton;

	@FindBy(css = "a[data-category='Artificial Intelligence'][data-title][href]")
	private List<WebElement> aiCourseLinks;

	@FindBy(css = "[data-category='Artificial Intelligence'] a")
	private List<WebElement> aiNavigationLinks;

	@FindBy(xpath = "//button[contains(@class,'popularcourses_view_program_btn') and normalize-space()='View Program']")
	private List<WebElement> viewProgramButtons;

	@FindBy(tagName = "body")
	private WebElement body;

	public ArtificialIntelligencePage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		PageFactory.initElements(driver, this);
	}

	public void openAIListing() throws IOException {
		driver.get(prop.getData("url"));

		WebElement aiLink = wait.until(ExpectedConditions.elementToBeClickable(aiNavigationLinks.get(0)));

		aiLink.click();

		wait.until(ExpectedConditions.urlContains("/artificial-intelligence-certification-courses"));

		wait.until(ExpectedConditions.visibilityOf(aiHeading));
	}

	public void navigateToAIFromHome() throws IOException {
		driver.get(prop.getData("url"));

		WebElement aiLink = wait.until(ExpectedConditions.elementToBeClickable(aiNavigationLinks.get(0)));

		aiLink.click();

		wait.until(ExpectedConditions.urlContains("/artificial-intelligence-certification-courses"));

		wait.until(ExpectedConditions.visibilityOf(aiHeading));
	}

	public boolean isAIListingDisplayed() {
		return wait.until(ExpectedConditions.visibilityOf(aiHeading)).isDisplayed();
	}

	public void exploreAICourses() {
		wait.until(ExpectedConditions.elementToBeClickable(exploreAIButton)).click();
	}

	private WebElement getFirstAICourseLink() {
		List<WebElement> links = wait.until(ExpectedConditions.visibilityOfAllElements(aiCourseLinks));

		for (WebElement link : links) {
			String href = link.getAttribute("href");

			if (link.isDisplayed() && href != null && !href.isBlank()) {
				return link;
			}
		}

		throw new AssertionError("No visible AI course link was found.");
	}

	public String getFirstAICourseTitle() {
		return getFirstAICourseLink().getAttribute("data-title");
	}

	public void openFirstAICourse() {
		WebElement courseLink = wait.until(ExpectedConditions.elementToBeClickable(getFirstAICourseLink()));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", courseLink);

		String originalWindow = driver.getWindowHandle();

		courseLink.click();

		wait.until(ExpectedConditions.numberOfWindowsToBe(2));

		for (String windowHandle : driver.getWindowHandles()) {
			if (!windowHandle.equals(originalWindow)) {
				driver.switchTo().window(windowHandle);
				break;
			}
		}

		wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));

		System.out.println("Opened AI course URL: " + driver.getCurrentUrl());
	}

	public boolean refreshCourseDetailPage() {
		String originalUrl = driver.getCurrentUrl();

		driver.navigate().refresh();

		wait.until(ExpectedConditions.urlToBe(originalUrl));

		wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));

		return driver.getCurrentUrl().equals(originalUrl)
				&& !wait.until(ExpectedConditions.visibilityOf(body)).getText().trim().isEmpty();
	}

	public boolean navigateBackToAIListing() {
		driver.close();

		for (String windowHandle : driver.getWindowHandles()) {
			driver.switchTo().window(windowHandle);
			break;
		}

		wait.until(ExpectedConditions.urlContains("/artificial-intelligence-certification-courses"));

		return isAIListingDisplayed();
	}

	public boolean scrollToLowerSections() {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		long initialHeight = ((Number) js.executeScript("return document.documentElement.scrollHeight")).longValue();

		js.executeScript("window.scrollTo(0, document.documentElement.scrollHeight);");

		wait.until(d -> ((Number) ((JavascriptExecutor) d).executeScript("return window.scrollY")).longValue() > 0);

		long scrollPosition = ((Number) js.executeScript("return window.scrollY")).longValue();

		return scrollPosition > 0 && initialHeight > 0;
	}

	public boolean verifyCourseLinksAfterScrolling() {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollTo(0, document.documentElement.scrollHeight);");

		wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));

		Number scrollPosition = (Number) js.executeScript("return window.scrollY;");

		if (scrollPosition == null || scrollPosition.doubleValue() <= 0) {
			return false;
		}

		List<WebElement> links = driver
				.findElements(By.cssSelector("a[data-category='Artificial Intelligence'][data-title][href]"));

		for (WebElement link : links) {
			try {
				String href = link.getAttribute("href");

				if (link.isDisplayed() && link.isEnabled() && href != null && !href.isBlank()) {
					return true;
				}
			} catch (org.openqa.selenium.StaleElementReferenceException e) {
			}
		}

		return false;
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public boolean clickPrimaryCourseCallToAction() {
		String listingUrl = driver.getCurrentUrl();
		String originalWindow = driver.getWindowHandle();

		WebElement button = wait.until(ExpectedConditions.elementToBeClickable(viewProgramButtons.get(0)));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", button);

		button.click();

		try {
			wait.until(d -> !d.getCurrentUrl().equals(listingUrl) || d.getWindowHandles().size() > 1);
		} catch (TimeoutException e) {
			return false;
		}

		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(originalWindow)) {
				driver.switchTo().window(handle);
				break;
			}
		}

		wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));

		return !driver.getCurrentUrl().equals(listingUrl) && !driver.getCurrentUrl().isBlank();
	}
}
