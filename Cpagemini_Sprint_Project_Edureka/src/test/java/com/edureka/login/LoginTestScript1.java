
package com.edureka.login;

import java.io.IOException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.edureka.base.BaseClass;

public class LoginTestScript1 extends BaseClass {

	@Test(groups = { "smoke", "functional" })
	public void verifyEmailFieldDisplayed() throws IOException {

		Reporter.log("Starting test: Verify email field visibility", true);

		loginPage.openLoginPage();

		Assert.assertTrue(loginPage.isEmailFieldDisplayed(), "Email field is not displayed.");

		Reporter.log("Passed: Email field is displayed", true);
	}

	@Test(groups = { "smoke", "functional" })
	public void verifyPasswordFieldDisplayed() throws IOException {

		Reporter.log("Starting test: Verify password field visibility", true);

		loginPage.openLoginPage();

		Assert.assertTrue(loginPage.isPasswordFieldDisplayed(), "Password field is not displayed.");

		Reporter.log("Passed: Password field is displayed", true);
	}

	@Test(groups = { "smoke", "functional" })
	public void verifyLoginButtonDisplayed() throws IOException {

		Reporter.log("Starting test: Verify login button visibility", true);

		loginPage.openLoginPage();

		Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button is not displayed.");

		Reporter.log("Passed: Login button is displayed", true);
	}

	@Test(groups = { "negative", "functional" })
	public void verifyIncorrectPassword() throws Exception {

		String email = prop.getData("validEmail");
		String password = excel.getData("login", 1, 1);

		Assert.assertNotNull(email, "Configure validEmail in CommonData.properties.");

		Assert.assertFalse(email.trim().isEmpty(), "Configure validEmail in CommonData.properties.");

		Assert.assertFalse(password.trim().isEmpty(), "Invalid password is missing in Excel.");

		loginPage.openLoginPage();

		loginPage.enterEmail(email);
		loginPage.enterPassword(password);
		loginPage.clickLogin();

		Assert.assertTrue(loginPage.isLoginPopupDisplayed(), "Login popup is not visible.");

		Reporter.log("TC_LOGIN_03 completed", true);
	}

	@Test(groups = { "negative", "functional" })
	public void verifyBlankCredentials() throws Exception {

		loginPage.openLoginPage();

		loginPage.enterEmail("");
		loginPage.enterPassword("");
		loginPage.clickLogin();

		Assert.assertTrue(loginPage.isLoginPopupDisplayed(), "Login popup is not visible.");

		Reporter.log("TC_LOGIN_05 completed", true);
	}

	@Test(groups = { "negative", "functional" })
	public void verifyInvalidEmailFormat() throws Exception {

		String email = excel.getData("login", 0, 1);
		String password = excel.getData("login", 1, 1);

		Assert.assertFalse(email.trim().isEmpty(), "Invalid email is missing in Excel.");

		loginPage.openLoginPage();

		loginPage.enterEmail(email);
		loginPage.enterPassword(password);
		loginPage.clickLogin();

		Assert.assertTrue(loginPage.isLoginPopupDisplayed(), "Login popup is not visible.");

		Reporter.log("TC_LOGIN_06 completed", true);
	}
}
