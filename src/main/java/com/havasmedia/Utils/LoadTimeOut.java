package com.havasmedia.Utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.havasmedia.basetest.BaseConfigurationSetup;

public class LoadTimeOut extends BaseConfigurationSetup {

	WebDriver driver;
	WebDriverWait wait;
	final int IMPLICIT_TIMEOUT_INSECONDS = 30;
	final int EXPLICIT_TIMEOUT_INSECONDS = 60;

	public LoadTimeOut(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, EXPLICIT_TIMEOUT_INSECONDS);
	}

	public void waitTillPageLoads() {
		driver.manage().timeouts().implicitlyWait(IMPLICIT_TIMEOUT_INSECONDS, TimeUnit.SECONDS);
	}

	public void waitTillElementVisible(WebElement element) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void waitTillElementClickable(WebElement element) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void waitTillElementClickableAndClick(WebElement element) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element)).click();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void waitTillElementClickableAndJsClick(WebElement element) {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void waitUntilElementVisible(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public String waitAndGetText(WebElement vWebElement) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.elementToBeClickable(vWebElement));
		return vWebElement.getText();
	}

	public String waitTillVisibleAndGetText(WebElement vWebElement, String eLocator) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(eLocator)));
		return vWebElement.getText();
	}
}
