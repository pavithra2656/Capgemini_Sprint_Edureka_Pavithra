package com.edureka.login;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.edureka.base.BaseClass;
import com.edureka.utilities.ExcelUtilities;

import objRepo.LoginPage;

public class LoginTestScript1 extends BaseClass {

    @Test(groups = {"smoke", "functional"})
    public void verifyEmailFieldDisplayed() {

        Reporter.log("Starting test: Verify email field visibility", true);

        LoginPage loginPage = new LoginPage(driver, properties);
        loginPage.openLoginPage();

        Assert.assertTrue(loginPage.isEmailFieldDisplayed(),"Email field is not displayed.");

        Reporter.log("Passed: Email field is displayed", true);
    }

    @Test(groups = {"smoke", "functional"})
    public void verifyPasswordFieldDisplayed() {

        Reporter.log("Starting test: Verify password field visibility", true);

        LoginPage loginPage = new LoginPage(driver, properties);
        loginPage.openLoginPage();

        Assert.assertTrue(loginPage.isPasswordFieldDisplayed(),"Password field is not displayed.");

        Reporter.log("Passed: Password field is displayed", true);
    }

    @Test(groups = {"smoke", "functional"})
    public void verifyLoginButtonDisplayed() {

        Reporter.log("Starting test: Verify login button visibility", true);

        LoginPage loginPage = new LoginPage(driver, properties);
        loginPage.openLoginPage();

        Assert.assertTrue(loginPage.isLoginButtonDisplayed(),"Login button is not displayed.");

        Reporter.log("Passed: Login button is displayed", true);
    }

    
    @Test(groups = {"functional"})
	public void verifyForgotPasswordNavigation() throws Exception {
	
	    String testId = "TC_LOGIN_09";
	
	    String expected = ExcelUtilities.getDataByKey("Test Cases", "TC_ID", testId, "Expected Result");
	
	    LoginPage login = new LoginPage(driver, properties);
	
	    login.openLoginPage();
	    login.clickForgotPassword();
	
	    Assert.assertTrue(login.isResetPasswordHeadingDisplayed(),
	        "Reset your password heading was not displayed.");
	
	    Assert.assertTrue(login.isSendVerificationCodeButtonDisplayed(),
	        "Send Verification Code button was not displayed.");
	
	    Assert.assertTrue(login.isBackToLoginDisplayed(),
	        "Back to Login link was not displayed.");
	
	    Reporter.log(testId + " passed: " + expected,true);
	}
    

	@Test(groups = {"functional"})
	public void verifySignUpNavigation() throws Exception {
	
	    String testId = "TC_LOGIN_10";
	
	    String expected = ExcelUtilities.getDataByKey("Test Cases", "TC_ID", testId, "Expected Result");
	
	    LoginPage login = new LoginPage(driver, properties);
	
	    login.openLoginPage();
	    login.clickSignUp();
	
	    Assert.assertTrue(login.isSignUpHeadingDisplayed(),
	        "Registration heading was not displayed.");
	
	    Assert.assertTrue(login.isSignUpEmailFieldDisplayed(),
	        "Registration email field was not displayed.");
	
	    Assert.assertTrue( login.isSignUpPhoneFieldDisplayed(),
	        "Registration phone field was not displayed.");
	
	    Assert.assertTrue(login.isSignUpSubmitButtonDisplayed(),
	        "Sign Up button was not displayed.");
	
	    Reporter.log(testId + " passed: " + expected,true);
	}
}
