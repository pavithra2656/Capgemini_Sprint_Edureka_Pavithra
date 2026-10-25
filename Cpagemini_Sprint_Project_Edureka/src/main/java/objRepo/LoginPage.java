
package objRepo;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.edureka.utilities.CommonData;

public class LoginPage {

	private WebDriver driver;
	private WebDriverWait wait;
	private CommonData prop = new CommonData();

	@FindBy(id = "loginFormEmail")
	private WebElement emailField;

	@FindBy(id = "loginPassword")
	private WebElement passwordField;

	@FindBy(xpath = "//button[@type='submit' and normalize-space()='LOG IN']")
	private WebElement loginButton;

	@FindBy(xpath = "//span[contains(@class,'form_forgot_password')]")
	private WebElement forgotPasswordLink;

	@FindBy(xpath = "//div[contains(@class,'form_change_form')]//span[normalize-space()='Sign Up']")
	private WebElement signUpLink;

	@FindBy(xpath = "//*[contains(@class,'loginsignup_close_btn')]")
	private WebElement closeButton;

	@FindBy(xpath = "//*[contains(@class,'loginsignup_modal_content')]")
	private WebElement loginPopup;

	@FindBy(xpath = "//*[contains(@class,'resetpassword_heading')]")
	private WebElement resetPasswordHeading;

	@FindBy(xpath = "//button[contains(.,'SEND VERIFICATION CODE')]")
	private WebElement sendVerificationCodeButton;

	@FindBy(xpath = "//span[normalize-space()='Back to Login']")
	private WebElement backToLoginLink;

	@FindBy(xpath = "//div[contains(@class,'loginsignup_heading')]")
	private WebElement signUpHeading;

	@FindBy(id = "signupFormEmail")
	private WebElement signUpEmailField;

	@FindBy(id = "signupFormPhone")
	private WebElement signUpPhoneField;

	@FindBy(xpath = "//button[@type='submit' and normalize-space()='SIGN UP']")
	private WebElement signUpSubmitButton;

	public LoginPage(WebDriver driver) {
		this.driver = driver;

		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		PageFactory.initElements(driver, this);
	}

	public void openLoginPage() throws IOException {
		driver.get(prop.getData("url"));

		By loginLocator = By.xpath("//button[normalize-space()='Log In' or " + "normalize-space()='Log in']");

		wait.until(ExpectedConditions.elementToBeClickable(loginLocator)).click();

		wait.until(ExpectedConditions.visibilityOf(loginPopup));

		wait.until(ExpectedConditions.visibilityOf(emailField));

	}

	public boolean isLoginPopupDisplayed() {
		return wait.until(ExpectedConditions.visibilityOf(loginPopup)).isDisplayed();
	}

	public boolean isEmailFieldDisplayed() {
		return wait.until(ExpectedConditions.visibilityOf(emailField)).isDisplayed();
	}

	public boolean isPasswordFieldDisplayed() {
		return wait.until(ExpectedConditions.visibilityOf(passwordField)).isDisplayed();
	}

	public boolean isLoginButtonDisplayed() {
		return wait.until(ExpectedConditions.visibilityOf(loginButton)).isDisplayed();
	}

	public boolean isForgotPasswordLinkDisplayed() {
		return wait.until(ExpectedConditions.visibilityOf(forgotPasswordLink)).isDisplayed();
	}

	public boolean isSignUpLinkDisplayed() {
		try {
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//div[contains(@class,'form_change_form')]" + "//span[normalize-space()='Sign Up']")));

			return element.isDisplayed();

		} catch (TimeoutException e) {
			return false;
		}
	}

	public void enterEmail(String email) {
		WebElement element = wait.until(ExpectedConditions.visibilityOf(emailField));

		element.clear();
		element.sendKeys(email);
	}

	public void enterPassword(String password) {
		WebElement element = wait.until(ExpectedConditions.visibilityOf(passwordField));

		element.clear();
		element.sendKeys(password);
	}

	public void clickLogin() {
		wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
	}

	public void clickForgotPassword() {
		wait.until(ExpectedConditions.elementToBeClickable(forgotPasswordLink)).click();
	}

	public void clickSignUp() {
		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[contains(@class,'form_change_form')]" + "//span[normalize-space()='Sign Up']")))
				.click();
	}

	public void closeLoginPopup() {
		wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
	}

	public String getEmailValue() {
		return emailField.getAttribute("value");
	}

	public String getPasswordValue() {
		return passwordField.getAttribute("value");
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public String getPasswordInputType() {
		return passwordField.getAttribute("type");
	}

	public void submitPasswordUsingEnter() {
		wait.until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(Keys.ENTER);
	}

	public boolean isLoginPopupDisplayedSafely() {
		try {
			return loginPopup.isDisplayed();
		} catch (NoSuchElementException | StaleElementReferenceException e) {
			return false;
		}
	}

	// Diagnostic check for controls following the password field
	public void inspectPasswordVisibilityControls() {
		List<WebElement> controls = driver.findElements(
				By.cssSelector("#loginPassword ~ button, " + "#loginPassword ~ span, " + "#loginPassword ~ i"));

		for (WebElement control : controls) {
			System.out.println("Tag: " + control.getTagName() + " | Type: " + control.getAttribute("type")
					+ " | Class: " + control.getAttribute("class") + " | Text: " + control.getText());
		}
	}

	public boolean isResetPasswordHeadingDisplayed() {
		return wait.until(ExpectedConditions.visibilityOf(resetPasswordHeading)).isDisplayed();
	}

	public boolean isSendVerificationCodeButtonDisplayed() {
		return wait.until(ExpectedConditions.visibilityOf(sendVerificationCodeButton)).isDisplayed();
	}

	public boolean isBackToLoginDisplayed() {
		return wait.until(ExpectedConditions.visibilityOf(backToLoginLink)).isDisplayed();
	}

	public void clickBackToLogin() {
		wait.until(ExpectedConditions.elementToBeClickable(backToLoginLink)).click();
	}

	public boolean isSignUpHeadingDisplayed() {
		return wait.until(ExpectedConditions.visibilityOf(signUpHeading)).isDisplayed();
	}

	public boolean isSignUpEmailFieldDisplayed() {
		return wait.until(ExpectedConditions.visibilityOf(signUpEmailField)).isDisplayed();
	}

	public boolean isSignUpPhoneFieldDisplayed() {
		return wait.until(ExpectedConditions.visibilityOf(signUpPhoneField)).isDisplayed();
	}

	public boolean isSignUpSubmitButtonDisplayed() {
		return wait.until(ExpectedConditions.visibilityOf(signUpSubmitButton)).isDisplayed();
	}
}
