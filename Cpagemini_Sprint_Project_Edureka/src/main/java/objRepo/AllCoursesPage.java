
package objRepo;

import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AllCoursesPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Properties properties;

    private final By allCoursesLink = By.cssSelector("a[href*='/all-courses']");

    private final By pageHeading = By.xpath("//*[normalize-space()='Choose a category to find your course']");

    private final By introductoryText = By.xpath("//*[contains(normalize-space(.), " +"'100+ Live online courses chosen by')]");

    private final By categoryButtons = By.cssSelector("li.category_slide_detail");

    private final By othersCategory = By.cssSelector("li.other_cat");

    private final By rpaCategory = By.xpath("//*[normalize-space(.)='Robotic Process Automation']");

    private final By viewAllCourses = By.xpath("//*[normalize-space(.)='View All Courses']");

    public AllCoursesPage(WebDriver driver,Properties properties,WebDriverWait wait) {
        this.driver = driver;
        this.properties = properties;
        this.wait = wait;
    }

    public void openAllCoursesPage() {

        driver.get(properties.getProperty("url"));

        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(allCoursesLink));

        link.click();

        wait.until(ExpectedConditions.urlContains("/all-courses"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeading));
    }

    public boolean isAllCoursesPageLoaded() {
        try {
            return driver.getCurrentUrl().contains("/all-courses")&& wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeading)).isDisplayed();

        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isPageHeadingDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeading)).isDisplayed();

        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getPageHeading() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeading)).getText();
    }

    public boolean isIntroductoryTextDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(introductoryText)).isDisplayed();

        } catch (TimeoutException e) {
            return false;
        }
    }

    public List<WebElement> getCategoryButtons() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(categoryButtons));
    }

    private By getCategoryLocator(String categoryName) {
        return By.xpath("//*[@data-category=" +xpathLiteral(categoryName) + "]");
    }

    // Safely represent text as an XPath literal
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

            wait.until(ExpectedConditions.visibilityOfElementLocated(rpaCategory));

        } else {

            By category = getCategoryLocator(categoryName);

            wait.until(ExpectedConditions.elementToBeClickable(category)).click();
        }
    }


	public boolean isCategoryVisible(String categoryName) {
	
	    try {
	        By categoryLocator = By.xpath("//*[self::li or self::a or self::button or self::div]" +"[normalize-space(.)=" +xpathLiteral(categoryName) + "]");
	
	        List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(categoryLocator));
	
	        for (WebElement element : elements) {
	            if (element.isDisplayed()&& element.getText().trim().equalsIgnoreCase(categoryName)) {
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

            boolean rpaDisplayed = wait.until(ExpectedConditions.visibilityOfElementLocated(rpaCategory)).isDisplayed();

            boolean viewAllDisplayed = wait.until(ExpectedConditions.visibilityOfElementLocated(viewAllCourses)).isDisplayed();

            return rpaDisplayed && viewAllDisplayed;

        } catch (TimeoutException e) {
            return false;
        }
    }

    // Get current URL
    public String getPageUrl() {
        return driver.getCurrentUrl();
    }
}
