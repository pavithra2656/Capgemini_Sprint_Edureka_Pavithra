
package objRepo;

import java.util.List;
import java.util.Properties;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ArtificialIntelligencePage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Properties properties;

    private final By aiNavigationLink = By.xpath("//*[self::a or self::div]"+ "[@data-category='Artificial Intelligence']"+ "[self::a or .//a]");

    private final By aiHeading = By.xpath("//*[self::h1 or self::h2]"+ "[contains(normalize-space(.), 'Artificial Intelligence')]");

    private final By exploreAIButton = By.xpath("//a[contains(normalize-space(.)," +"'Explore Artificial Intelligence Courses')]");

    private final By aiCourseLinks = By.cssSelector("a[data-category='Artificial Intelligence'][data-title][href]");
    
    private final By viewProgramButtons = By.xpath("//button[contains(@class,'popularcourses_view_program_btn') " +"and normalize-space()='View Program']");
    
    private final By courseSummaryCandidates = By.xpath(
    	    "//*[self::p or self::span or self::div or self::h2 or self::h3]" +
    	    "[not(*) and " +"contains(translate(normalize-space(.)," +
    	    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'course')]" );

    public ArtificialIntelligencePage(
            WebDriver driver,
            Properties properties,
            WebDriverWait wait) {

        this.driver = driver;
        this.properties = properties;
        this.wait = wait;
    }


    public void openAIListing() {

        driver.get(properties.getProperty("url"));

        By aiLink = By.cssSelector("div[data-category='Artificial Intelligence'] a");

        wait.until(ExpectedConditions.elementToBeClickable(aiLink)).click();

        wait.until(ExpectedConditions.urlContains("/artificial-intelligence-certification-courses"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(aiHeading));
    }



	public void navigateToAIFromHome() {
	    driver.get(properties.getProperty("url"));
	
	    By aiLink = By.cssSelector("[data-category='Artificial Intelligence'] a");
	
	    wait.until(ExpectedConditions.elementToBeClickable(aiLink)).click();
	
	    wait.until(ExpectedConditions.urlContains("/artificial-intelligence-certification-courses"));
	
	    wait.until(ExpectedConditions.visibilityOfElementLocated(aiHeading));
	}


    public boolean isAIListingDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(aiHeading)).isDisplayed();
    }

    public void exploreAICourses() {
        wait.until(ExpectedConditions.elementToBeClickable(exploreAIButton)).click();
    }

    private WebElement getFirstAICourseLink() {
        List<WebElement> links = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(aiCourseLinks));

        for (WebElement link : links) {
            if (link.isDisplayed()&& link.getAttribute("href") != null&& !link.getAttribute("href").isBlank()) {
                return link;
            }
        }

        throw new AssertionError("No visible AI course link was found.");
    }

    public String getFirstAICourseTitle() {
        return getFirstAICourseLink().getAttribute("data-title");
    }



    public void openFirstAICourse() {

        By courseLinks = By.cssSelector("a[data-category='Artificial Intelligence']"+ "[data-title][href]");

        WebElement courseLink = wait.until( ExpectedConditions.elementToBeClickable(courseLinks));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});",courseLink);

        String originalWindow = driver.getWindowHandle();

        courseLink.click();

        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        wait.until(d ->((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));

        System.out.println("Opened AI course URL: "+ driver.getCurrentUrl());
    }



    public boolean refreshCourseDetailPage() {
        String originalUrl = driver.getCurrentUrl();

        driver.navigate().refresh();

        wait.until(ExpectedConditions.urlToBe(originalUrl));

        wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));

        return driver.getCurrentUrl().equals(originalUrl)&& !driver.findElement(By.tagName("body")).getText().trim().isEmpty();
    }

    public boolean navigateBackToAIListing() {

        String courseWindow = driver.getWindowHandle();

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
	
	    Number scrollPosition = (Number) js.executeScript("return window.scrollY;" );
	
	    if (scrollPosition == null || scrollPosition.doubleValue() <= 0) {
	        return false;
	    }
	
	    List<WebElement> links = driver.findElements(aiCourseLinks);
	
	    for (WebElement link : links) {
	        try {
	            String href = link.getAttribute("href");
	
	            if (link.isDisplayed()&& link.isEnabled()&& href != null&& !href.isBlank()) {
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

        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(viewProgramButtons));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", button);

        button.click();

        try {
            wait.until(d ->!d.getCurrentUrl().equals(listingUrl)|| d.getWindowHandles().size() > 1);
        } catch (TimeoutException e) {
            return false;
        }

        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalWindow)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        wait.until(d ->((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));

        return !driver.getCurrentUrl().equals(listingUrl)
            && !driver.getCurrentUrl().isBlank();
    }
    
 
}
