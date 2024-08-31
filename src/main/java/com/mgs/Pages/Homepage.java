package com.mgs.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mgs.CommonUtils.CommonSelenium;
import org.openqa.selenium.WebElement;

public class Homepage extends CommonSelenium {
    public WebDriver driver;

    public Homepage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    protected By signInButton = By.xpath("//button[.='Sign In']");
	protected By signUpButton = By.xpath("//button[.='Sign Up']");
	protected By emailField = By.xpath("//input[@id='email']");
	protected By passwordField= By.xpath("//input[@id='password']");
	protected By loginButton = By.xpath("//button[@type='submit']");


	public WebDashboard webLogin(String email, String password) {
		if (isElementPresent(driver, signInButton)) {
			click(signInButton);
			log("Clicking on Sign In button");
		} else {
			waitForElementClickable(driver.findElement(signInButton), 10);
			log("Clicking on Sign In button");
			click(signInButton);
		}
		log("Entering email address");
		sendKeys(emailField, email);

		log("Entering password");
		sendKeys(passwordField, password);

		log("Clicking on Login button");
		click(loginButton);
		return new WebDashboard(driver);
	}

	public String getTitle() {
		return driver.getTitle();
	}


}
