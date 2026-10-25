
package objRepo;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.edureka.utilities.CommonData;

public class AllCoursesPage {

	private WebDriver driver;
	private WebDriverWait wait;
	private CommonData prop = new CommonData();

	@FindBy(css = "a[href*='/all-courses']")
	private WebElement allCoursesLink;

	@FindBy(xpath = "//*[normalize-space()='Choose a category to find your course']")
	private WebElement pageHeading;

	@FindBy(xpath = "//*[contains(normalize-space(.), '100+ Live online courses chosen by')]")
	private WebElement introductoryText;

	@FindBy(css = "li.category_slide_detail")
	private List<WebElement> categoryButtons;

	@FindBy(css = "li.other_cat")
	private WebElement othersCategory;

	@FindBy(xpath = "//*[normalize-space(.)='Robotic Process Automation']")
	private WebElement rpaCategory;

	@FindBy(xpath = "//*[normalize-space(.)='View All Courses']")
	private WebElement viewAllCourses;

	public AllCoursesPage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		PageFactory.initElements(driver, this);
	}

	public void openAllCoursesPage() throws IOException {
		driver.get(prop.getData("url"));

		wait.until(ExpectedConditions.elementToBeClickable(allCoursesLink)).click();

		wait.until(ExpectedConditions.urlContains("/all-courses"));
		wait.until(ExpectedConditions.visibilityOf(pageHeading));
	}

	public boolean isAllCoursesPageLoaded() {
		try {
			return driver.getCurrentUrl().contains("/all-courses")
					&& wait.until(ExpectedConditions.visibilityOf(pageHeading)).isDisplayed();
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean isPageHeadingDisplayed() {
		try {
			return wait.until(ExpectedConditions.visibilityOf(pageHeading)).isDisplayed();
		} catch (TimeoutException e) {
			return false;
		}
	}

	public String getPageHeading() {
		return wait.until(ExpectedConditions.visibilityOf(pageHeading)).getText();
	}

	public boolean isIntroductoryTextDisplayed() {
		try {
			return wait.until(ExpectedConditions.visibilityOf(introductoryText)).isDisplayed();
		} catch (TimeoutException e) {
			return false;
		}
	}

	public List<WebElement> getCategoryButtons() {
		return wait.until(ExpectedConditions.visibilityOfAllElements(categoryButtons));
	}

	private By getCategoryLocator(String categoryName) {
		return By.xpath("//*[@data-category=" + xpathLiteral(categoryName) + "]");
	}

	private String xpathLiteral(String value) {
		if (!value.contains("'")) {
			return "'" + value + "'";
		}

		if (!value.contains("\"")) {
			return "\"" + value + "\"";
		}

		String[] parts = value.split("'", -1);
		StringBuilder result = new StringBuilder("concat(");

		for (int i = 0; i < parts.length; i++) {
			if (i > 0) {
				result.append(", \"'\", ");
			}

			result.append("'").append(parts[i]).append("'");
		}

		result.append(")");
		return result.toString();
	}

	public void selectCategory(String categoryName) {
		if ("Others".equalsIgnoreCase(categoryName)) {
			wait.until(ExpectedConditions.elementToBeClickable(othersCategory)).click();
			wait.until(ExpectedConditions.visibilityOf(rpaCategory));
		} else {
			By category = getCategoryLocator(categoryName);
			wait.until(ExpectedConditions.elementToBeClickable(category)).click();
		}
	}

	public boolean isCategoryVisible(String categoryName) {
		try {
			By categoryLocator = By.xpath("//*[self::li or self::a or self::button or self::div]"
					+ "[normalize-space(.)=" + xpathLiteral(categoryName) + "]");

			List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(categoryLocator));

			for (WebElement element : elements) {
				if (element.isDisplayed() && element.getText().trim().equalsIgnoreCase(categoryName)) {
					return true;
				}
			}

			return false;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean isAdditionalCategoriesPageDisplayed() {
		try {
			boolean rpaDisplayed = wait.until(ExpectedConditions.visibilityOf(rpaCategory)).isDisplayed();

			boolean viewAllDisplayed = wait.until(ExpectedConditions.visibilityOf(viewAllCourses)).isDisplayed();

			return rpaDisplayed && viewAllDisplayed;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public String getPageUrl() {
		return driver.getCurrentUrl();
	}
}
