package com.mgs.CommonUtils;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Random;
import java.util.Set;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.mgs.Utils.Reporting.TestListeners;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class CommonSelenium {
	public WebDriver driver;
	public CommonSelenium(WebDriver driver) {
		this.driver = driver;
	}

	public void sendKeys(By locator, String text)
	{
		try {
			waitFor(1);
			WebElement element = driver.findElement(locator);
			highlight(element);
			element.sendKeys(text);
			waitFor(1);
		} catch (Exception e) {
			e.getMessage();
			Assert.fail("Failed to send keys to element [" + locator + "]");
		}
	}

	public void sendKeys(WebElement element, String text)
	{
		try {
			waitFor(2);
			highlight(element);
			element.sendKeys(text);
			waitFor(1);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to send keys to element");
		}
	}

	public void enterValueUsingJS(By locator, String text)
	{
		try {

			WebElement element = driver.findElement(locator);
			highlight(element);
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript("arguments[0].setAttribute('text', arguments[1]);", element, text);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to Enter textValue to element");
		}
	}

	public void enterValueUsingJS(WebElement element, String text)
	{
		try {
			highlight(element);
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript("arguments[0].setAttribute('text', arguments[1]);", element, text);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to Enter textValue to element");
		}
	}

	public void click(By locator)
	{
		try {
			waitFor(2);
			WebElement element = driver.findElement(locator);
			highlight(element);
			element.click();
			waitFor(1);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to click on element [" + locator + "]");
		}
	}

	public void handleEleClickInterException(WebDriver driver, WebElement element) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			Actions actions = new Actions(driver);
			highlight(element);
			actions.moveToElement(element).click().perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void waitForElementClickable(WebElement webElement, int timeoutSeconds)
	{
		try {
			new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
					.until(ExpectedConditions.elementToBeClickable(webElement));
		} catch (Exception e) {
			System.err.println(
					"Waited for element [" + webElement + "] to be clickable for " + timeoutSeconds + " seconds");
		}
	}


	public void waitForElementClickable(By locator, int timeoutSeconds)
	{
		try {
			new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
					.until(ExpectedConditions.elementToBeClickable(driver.findElement(locator)));
		} catch (Exception e) {
			System.err.println(
					"Waited for Locator [" + locator + "] to be clickable for " + timeoutSeconds + " seconds");
		}
	}

	public void waitForElementToAppear(WebElement ele, int seconds) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
			wait.until(ExpectedConditions.visibilityOf(ele));
		} catch (TimeoutException e) {
			e.printStackTrace();
			Assert.fail("Element did not appear within " + seconds + " seconds: " + ele);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("An unexpected error occurred while waiting for the element: " + ele);
		}
	}

	public void selectDropdownOptionByText(WebDriver driver, By dropdownLocator, String optionText) {
		try {
			WebElement dropdown = driver.findElement(dropdownLocator);
			highlight(dropdownLocator);
			Select select = new Select(dropdown);
			select.selectByVisibleText(optionText);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to select dropdown option: [" + optionText + "] from dropdown: " + dropdownLocator);
		}
	}


	protected void waitForElementDisplay(WebElement ele, int seconds)
	{
		try {
			new WebDriverWait(driver, Duration.ofSeconds(seconds)).until(ExpectedConditions.visibilityOf(ele));
		} catch (Exception e) {
			System.err.println("Waited for element [" + ele + "] for " + seconds + " seconds");
		}
	}

	public void waitForElementPresence(WebDriver driver, By by, int seconds)
	{
		try {
			new WebDriverWait(driver, Duration.ofSeconds(seconds))
					.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (Exception e) {
			System.err.println("Waited for element [" + by.toString() + "] presence for " + seconds + " seconds");
		}
	}

	public void waitForElementVisibility(WebDriver driver, By by, int seconds)
	{
		try {
			new WebDriverWait(driver, Duration.ofSeconds(seconds))
					.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			System.err.println("Waited for element [" + by.toString() + "] visibility for " + seconds + " seconds");
		}
	}

	public void waitForElementToBeClickable(By by, int seconds)
	{
		try {
			new WebDriverWait(driver, Duration.ofSeconds(seconds)).until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			System.err
					.println("Waited for element [" + by.toString() + "] to be clickable for " + seconds + " seconds");
		}
	}

	protected void waitForElementDisplay(WebDriver driver, By locator, int seconds) {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(seconds))
					.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception e) {
			System.err.println("Waited for element located by [" + locator + "] for " + seconds + " seconds");
		}
	}

	public void waitFor(int i)
	{
		try {
			Thread.sleep(1000 * i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getText(By locator)
	{
		try {
			waitFor(2);
			highlight(locator);
			return driver.findElement(locator).getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void scrollToElement(WebDriver driver, By locator, int yOffset)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, 0);");
		js.executeScript("window.scrollBy(0, arguments[0]);", yOffset);
	}

	public boolean isTextInPage(String text)
	{
		try {
			return driver.getPageSource().contains(text);
		} catch (Exception e) {
			return false;
		}
	}

	public void waitForNBLoad(int seconds)
	{
		try {
			for (int i = 0; i < seconds / 2; i++) {
				waitFor(1);
				if (!driver.getPageSource().contains("Loading..."))
					break;
				else
					System.out.println("Loading...");
			}

		} catch (Exception e) {
			System.err.println("Loading error");
		}

	}

	public boolean isTextPresent(WebDriver driver, String actualText) {
		return actualText.contains(actualText);
	}

	public boolean isElementPresent(WebDriver driver, By by)
	{
		try {
			driver.findElement(by);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	public boolean isWebElementPresent(By locator, int duration)
	{
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return true;
		} catch (NoSuchElementException | TimeoutException e) {
			return false;
		}
	}

	public void clickElementWithText(String textVal) {
		try {
			String xpath = String.format("//*[text()='%s' or contains(text(),'%s')]", textVal, textVal);
			waitFor(1);
			highlight(By.xpath(xpath));
			click(By.xpath(xpath));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("An error occurred while clicking element with text: " + textVal + ". Error: " + e.getMessage());
		}
	}


	public void clearElement(WebDriver driver, By locator)
	{
		WebElement element = driver.findElement(locator);
		highlight(element);
		element.clear();
	}

	public void javascriptClick(By locators)
	{
		try {
			waitFor(1);
			WebElement ele = driver.findElement(locators);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			highlight(ele);
			executor.executeScript("arguments[0].click();", ele);

			waitFor(1);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to click on element [" + locators + "] ");
		}
	}

	public void moveToElement(By locator)
	{
		try {
			waitFor(2); // Optional: wait for 2 seconds (adjust as necessary)
			WebElement element = driver.findElement(locator);
			Actions actions = new Actions(driver);
			actions.moveToElement(element).build().perform();
			highlight(element);
			waitFor(1); // Optional: additional wait after moving to the element
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to move to element [" + locator + "]");
		}
	}

	public void doubleClick(By locator)
	{
		try {
			waitFor(2);
			WebElement element = driver.findElement(locator);
			highlight(element);

			Actions actions = new Actions(driver);
			actions.doubleClick(element).build().perform();

			waitFor(1);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to double click on element [" + locator + "]");
		}
	}

	public void click(WebElement element)
	{
		try {
			waitFor(2);
			highlight(element);
			element.click();
			waitFor(1);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to click on element");
		}
	}

	public void highlight(By element)
	{
		try
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style','background: ; border: 2px solid red;');", driver.findElement(element));
		} catch (Exception e)
		{
			e.printStackTrace();
			junit.framework.Assert.fail("Failed to highlight [" + element + "] element.");
		}
	}

	public void highlight(WebElement element)
	{
		try
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style','background: ; border: 2px solid red;');", element);
		} catch (Exception e)
		{
			e.printStackTrace();
			junit.framework.Assert.fail("Failed to highlight [" + element + "] element.");
		}
	}


	public void sendKeysWithWait(By locator, String text, int seconds)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds)); // Adjust timeout as needed
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		element.sendKeys(text);
	}
/*
	public static void drawBorder(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}*/

	public static void scrollIntoView(WebDriver driver, By locator)
	{
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;

			js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(locator));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void scrollIntoView(WebElement element)
	{
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center' });", element);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("Failed to scroll to element.");
		}
	}

	public void scrollByPixels(int x, int y)
	{
		try {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript("window.scrollBy(arguments[0], arguments[1]);", x, y);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Failed to scroll by pixels y [" + y + "] and [" + y + "]");
		}
	}

	public int getVerticalScrollOffset()
	{
		try {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			return ((Long) jsExecutor.executeScript("return window.pageYOffset;")).intValue();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("An error occurred while getting the vertical scroll offset.");
			return 0;
		}
	}

	public void switchToChildWindow()
	{
		String parentWindow = driver.getWindowHandle();
		Set<String> handles = driver.getWindowHandles();
		for (String windowHandle : handles) {
			if (!windowHandle.equals(parentWindow)) {
				driver.switchTo().window(windowHandle);
				driver.switchTo().window(parentWindow);
				driver.close();
				driver.switchTo().window(windowHandle);
				System.out.println("closing Parent window and control on the new open window");
			}
		}
	}

	public void scrollUp() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, -1750)");
	}

	public void scrollDown() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,750)");
	}

	public void scrollToBottomOfPage() {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}

	public void scrollToTopOfPage () {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollTo(0, 0);");
	}

	public void setPageZoom(String zoomLevel) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("document.body.style.zoom = arguments[0];", zoomLevel);
	}


	public void scrollUpTo(By elementLocator)
	{
		try {
			WebElement element = driver.findElement(elementLocator);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center' });", element);
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			Assert.fail("Element not found: " + elementLocator);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Failed to scroll to element: " + elementLocator);
		}
	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	protected void scrollClick(By locator) {
		WebElement element = driver.findElement(locator);
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].scrollIntoView(true);", element);
		waitFor(1);
		js.executeScript("arguments[0].click();", element);
	}


	public void waitForOverlayToDisappear(By overlayElement, int timeoutSeconds) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(overlayElement));
		} catch (Exception e) {
			System.err.println("Overlay did not disappear within " + timeoutSeconds + " seconds.");
		}
	}

	public static String generateRandomText(int length) {
		return RandomStringUtils.randomAlphanumeric(length);
	}

	public static int getRandomNumberInRange(int min, int max)
	{
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}


	public void log(String message)
	{
		try {
			String timestamp = new SimpleDateFormat("h:mm:ss a").format(new Date());
			ExtentTest extentTest = TestListeners.extentTest.get();
			if (extentTest != null) {
				extentTest.log(Status.INFO, message);
			}
			System.out.println("[" + timestamp + "] " + "INFO: " + message);
		} catch (Exception e) {
			System.err.println("Failed to log message: " + e.getMessage());
		}
	}

}
