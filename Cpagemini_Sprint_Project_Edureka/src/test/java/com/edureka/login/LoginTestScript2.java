package com.edureka.login;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.edureka.base.BaseClass;
import com.edureka.utilities.ExcelUtilities;

import objRepo.LoginPage;

public class LoginTestScript2 extends BaseClass {

    private String excelData(String testId, String column)
            throws Exception {

        return ExcelUtilities.getDataByKey(
                "Test Cases", "TC_ID", testId, column);
    }

    @Test(groups = {"negative", "functional"})
    public void verifyIncorrectPassword() throws Exception {

        String testId = "TC_LOGIN_03";
        String expected = excelData(testId, "Expected Result");

        String email = properties.getProperty("validEmail");
        String password = properties.getProperty("invalidPassword");

        Assert.assertNotNull(email,"Configure validEmail in CommonData.properties.");
        Assert.assertFalse(email.trim().isEmpty(),"Configure validEmail in CommonData.properties.");

        LoginPage login = new LoginPage(driver, properties);
        login.openLoginPage();
        login.enterEmail(email);
        login.enterPassword(password);
        login.clickLogin();

        Assert.assertTrue(login.isLoginPopupDisplayed(), expected);
        Reporter.log(testId + " completed: " + expected, true);
    }

    @Test(groups = {"negative", "functional"})
    public void verifyUnregisteredEmail() throws Exception {

        String testId = "TC_LOGIN_04";
        String testData = excelData(testId, "Test Data");
        String expected = excelData(testId, "Expected Result");

        String[] data = testData.split(";");
        String email = data[0].trim();
        String password = data.length > 1? data[1].trim() : "";

        LoginPage login = new LoginPage(driver, properties);
        login.openLoginPage();
        login.enterEmail(email);
        login.enterPassword(password);
        login.clickLogin();

        Assert.assertTrue(login.isLoginPopupDisplayed(), expected);
        Reporter.log(testId + " completed: " + expected, true);
    }

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

        String[] data = testData.split(";");
        String email = data[0].trim();
        String password = data.length > 1
                ? data[1].trim() : "";

        LoginPage login = new LoginPage(driver, properties);
        login.openLoginPage();
        login.enterEmail(email);
        login.enterPassword(password);
        login.clickLogin();

        Assert.assertTrue(login.isLoginPopupDisplayed(), expected);
        Reporter.log(testId + " completed: " + expected, true);
    }
}