package com.havasmedia.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CampaignPage extends HwInit {
	WebDriver driver;
	public CampaignPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
	}

	
	@FindBy(how=How.XPATH,using="//div[@class='makeFlex column flexOne whiteText latoBold']")
	private WebElement wbToLoginBtn;
	@FindBy(how=How.ID, using="username")
	private WebElement wbUsernameTxt;
	
	@FindBy(how=How.XPATH,using="//span[text()='Continue']/..")
	private WebElement wbContinueBtn;
		
	@FindBy(how=How.ID, using="password")
	private WebElement wbPasswordTxt;
	
	@FindBy(how=How.XPATH, using="//span[text()='Login']/..")
	private WebElement wbLoginBtn;
	
	@FindBy(how =How.XPATH,using="//a[@class='mmtLogo makeFlex']")
	private WebElement wbLoggedInUserTxt;
	
	
	public boolean getTextFromApplication() {
		return wbLoggedInUserTxt.isDisplayed();
		
	}
	public void enterCredentials(String userName,String password) {
		try {
			waitUntilElementVisible(wbToLoginBtn);
			wbToLoginBtn.click();
			waitUntilElementVisible(wbUsernameTxt);
			wbUsernameTxt.sendKeys(userName);
			waitUntilElementVisible(wbContinueBtn);
			wbContinueBtn.click();
			waitUntilElementVisible(wbPasswordTxt);
			wbPasswordTxt.sendKeys(password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}		
		public void clickLoginButton(){
			try {
				waitTillElementVisible(wbLoggedInUserTxt);
				wbLoginBtn.click();
			} catch (Exception e) {
			e.printStackTrace();
			
			}
	}
}
