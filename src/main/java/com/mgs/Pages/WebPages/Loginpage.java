package com.mgs.Pages.WebPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mgs.CommonUtils.CommonSelenium;

public class Loginpage extends CommonSelenium {
    public WebDriver driver;

    public Loginpage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }
    protected By signInButton = By.xpath("//button[.='Sign In']");
	protected By signUpButton = By.xpath("//button[.='Sign Up']");
	protected By emailField = By.xpath("//input[@id='email']");
	protected By passwordField= By.xpath("//input[@id='password']");
	protected By loginButton = By.xpath("//button[@type='submit']");
    By gmailIcon = By.xpath("//button[@aria-label='Continue with Google']//div[@class='svg-icon']//div//*[name()='svg']");
	By selectEmail = By.tagName("(//*[@class='lPxAeb'])[3]");
	By continueBtn = By.xpath("//span[contains(text(),'Continue')]");
	By googleInputField = By.xpath("//div[contains(text(),'Email or phone')]");

	public WebDashboard webLogin(String email, String password)
	{
		if (isElementPresent(driver, signInButton))
		{
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

	public WebDashboard loginWithGmail()
	{
		if (isElementPresent(driver, signInButton))
		{
			click(signInButton);
			log("Clicking on Sign In button");
		} else {
			waitForElementClickable(driver.findElement(signInButton), 10);
			log("Clicking on Sign In button");
			click(signInButton);
		}
		waitForElementClickable(driver.findElement(gmailIcon),5);
		click(gmailIcon);
		switchToChildWindow();
		if(isElementPresent(driver, googleInputField)){
		sendKeys(By.xpath("//input[@id='identifierId']"),"mgs.milind@gmail.com");
		click(By.xpath("//span[normalize-space()='Next']"));
		sendKeys(By.xpath("//input[@name='Passwd']"),"Nbts@1213");
		click(By.xpath("//span[normalize-space()='Next']"));
		click(By.xpath("//span[normalize-space()='Continue']"));
	}
		click(selectEmail);
		click(continueBtn);
		return new WebDashboard(driver);
	}

	public String getTitle() {
		return driver.getTitle();
	}


}
