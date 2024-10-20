package com.mgs.CommonUtils;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Random;
import java.util.Set;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.mgs.Utils.TestListeners;
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

	public void sendKeys(By locator, String text) {
		try {
			waitFor(1);
			WebElement element = driver.findElement(locator);
			drawBorder(driver, element);
			element.sendKeys(text);
			waitFor(1);
		} catch (Exception e) {
			e.getMessage();
			Assert.fail("Failed to send keys to element [" + locator + "]");
		}

	}

	public void sendKeys(WebElement element, String text) {
		try {
			waitFor(2);
			drawBorder(driver, element);
			element.sendKeys(text);
			waitFor(1);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to send keys to element");
		}
	}

	public void click(By locator) {
		try {
			waitFor(2);
			WebElement element = driver.findElement(locator);
			drawBorder(driver, element);
			element.click();
			waitFor(1);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to click on element [" + locator + "]");
		}
	}

	public void scrollToElement(WebDriver driver, By locator, int yOffset) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, 0);");
		js.executeScript("window.scrollBy(0, arguments[0]);", yOffset);
	}

	public void handleEleClickInterException(WebDriver driver, WebElement element) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			Actions actions = new Actions(driver);
			actions.moveToElement(element).click().perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static WebElement waitForElementToBeClickable(WebDriver driver, By locator, int seconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public static WebElement waitForElementToBeClickable(WebDriver driver, WebElement element, int seconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void waitForElementToAppear(WebElement ele, int seconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
		wait.until(ExpectedConditions.visibilityOf(ele));
	}

	public static void selectDropdownOptionByText(WebDriver driver, By dropdownLocator, String optionText) {
		WebElement dropdown = driver.findElement(dropdownLocator);
		Select select = new Select(dropdown);
		select.selectByVisibleText(optionText);
	}

	protected void waitForElementDisplay(WebElement ele, int seconds) {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(seconds)).until(ExpectedConditions.visibilityOf(ele));
		} catch (Exception e) {
			System.err.println("Waited for element [" + ele + "] for " + seconds + " seconds");
		}
	}

	public void waitForElementPresence(WebDriver driver, By by, int seconds) {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(seconds))
					.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (Exception e) {
			System.err.println("Waited for element [" + by.toString() + "] presence for " + seconds + " seconds");
		}
	}

	public void waitForElementVisibility(WebDriver driver, By by, int seconds) {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(seconds))
					.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			System.err.println("Waited for element [" + by.toString() + "] visibility for " + seconds + " seconds");
		}
	}

	public void waitForElementToBeClickable(By by, int seconds) {
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

	public void waitFor(int i) {
		try {
			Thread.sleep(1000 * i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getText(By locator) {
		try {
			waitFor(2);
			return driver.findElement(locator).getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean isTextInPage(String text) {
		try {
			return driver.getPageSource().contains(text);
		} catch (Exception e) {
			return false;
		}
	}

	public void waitForNBLoad(int seconds) {
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

	public boolean isElementPresent(WebDriver driver, By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	public boolean isWebElementPresent(By locator, int duration) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return true;
		} catch (NoSuchElementException | TimeoutException e) {
			return false;
		}
	}

	public void clickElementWithText(String textVal) {
		String xpath = String.format("//*[text()='%s' or contains(text(),'%s')]", textVal, textVal);
//	        waitFor(2);
		click(By.xpath(xpath));
	}

	public void clearElement(WebDriver driver, By locator) {
		WebElement element = driver.findElement(locator);
		element.clear();
	}

	public void javascriptClick(By locators) {
		try {
			waitFor(1);
			WebElement element = driver.findElement(locators);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);

			waitFor(1);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to click on element [" + locators + "] ");
		}
	}

	public void moveToElement(By locator) {
		try {
			waitFor(2); // Optional: wait for 2 seconds (adjust as necessary)

			// Find the element
			WebElement element = driver.findElement(locator);
			drawBorder(driver, element); // Optional: highlight the element with a border

			Actions actions = new Actions(driver);

			// Move to the element
			actions.moveToElement(element).build().perform();

			waitFor(1); // Optional: additional wait after moving to the element
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to move to element [" + locator + "]");
		}
	}

	public void doubleClick(By locator) {
		try {
			waitFor(2);
			WebElement element = driver.findElement(locator);
			drawBorder(driver, element);

			Actions actions = new Actions(driver);
			actions.doubleClick(element).build().perform();

			waitFor(1);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to double click on element [" + locator + "]");
		}
	}

	public void click(WebElement element) {
		try {
			waitFor(2);
			drawBorder(driver, element);
			element.click();
			waitFor(1);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to click on element");
		}
	}

	public void sendKeysWithWait(By locator, String text, int seconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds)); // Adjust timeout as needed
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		element.sendKeys(text);
	}

	public static void drawBorder(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}

	public static void scrollIntoView(WebDriver driver, By locator) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;

			js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(locator));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void scrollIntoView(WebElement element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center' });", element);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("Failed to scroll to element.");
		}
	}

	public void switchToChildWindow() {
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

	public void scrollUpto(By elementLocator) {
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

	protected WebElement waitForElementClickable(WebElement webElement, int timeoutSeconds) {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
					.until(ExpectedConditions.elementToBeClickable(webElement));
		} catch (Exception e) {
			System.err.println(
					"Waited for element [" + webElement + "] to be clickable for " + timeoutSeconds + " seconds");
		}
		return webElement;
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

	public static int getRandomNumberInRange(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}


	public void log(String message) {
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
