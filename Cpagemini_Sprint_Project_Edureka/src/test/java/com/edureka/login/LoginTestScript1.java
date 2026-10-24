package com.edureka.login;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.edureka.base.BaseClass;
import com.edureka.utilities.ExcelUtilities;

import objRepo.LoginPage;

public class LoginTestScript1 extends BaseClass {

    private String excelData(String testId, String column) throws Exception {
        return ExcelUtilities.getDataByKey( "Test Cases", "TC_ID", testId, column);
    }

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

  

    @Test(groups = {"negative", "functional"})
    public void verifyIncorrectPassword() throws Exception {
        String testId = "TC_LOGIN_03";
        String expected = excelData(testId, "Expected Result");

        String email = properties.getProperty("validEmail");
        String password = properties.getProperty("invalidPassword");

        Assert.assertNotNull(email,"Configure validEmail in CommonData.properties.");
        Assert.assertFalse(email.trim().isEmpty(),"Configure validEmail in CommonData.properties.");
        Assert.assertNotNull(password,"Configure invalidPassword in CommonData.properties.");

        LoginPage login = new LoginPage(driver, properties);
        login.openLoginPage();
        login.enterEmail(email);
        login.enterPassword(password);
        login.clickLogin();

        Assert.assertTrue(login.isLoginPopupDisplayed(), expected);
        Reporter.log(testId + " completed: " + expected, true);
    }

    // TC_LOGIN_05: Verify blank credentials
    @Test(groups = {"negative", "functional"})
    public void verifyBlankCredentials() throws Exception {
        String testId = "TC_LOGIN_05";
        String expected = excelData(testId, "Expected Result");

        LoginPage login = new LoginPage(driver, properties);
        login.openLoginPage();
        login.enterEmail("");
        login.enterPassword("");
        login.clickLogin();

        Assert.assertTrue(login.isLoginPopupDisplayed(), expected);
        Reporter.log(testId + " completed: " + expected, true);
    }

    @Test(groups = {"negative", "functional"})
    public void verifyInvalidEmailFormat() throws Exception {
        String testId = "TC_LOGIN_06";
        String testData = excelData(testId, "Test Data");
        String expected = excelData(testId, "Expected Result");

        Assert.assertNotNull(testData,"Test Data is missing for " + testId + " in Edureka_TestCases.xlsx.");

        String[] data = testData.split(";", -1);
        String email = data[0].trim();
        String password = data.length > 1 ? data[1].trim() : "";

        LoginPage login = new LoginPage(driver, properties);
        login.openLoginPage();
        login.enterEmail(email);
        login.enterPassword(password);
        login.clickLogin();

        Assert.assertTrue(login.isLoginPopupDisplayed(), expected);
        Reporter.log(testId + " completed: " + expected, true);
    }
}
