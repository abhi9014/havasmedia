package com.havasmedia.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CampaignPage extends HwInit {
	WebDriver driver;
	public CampaignPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
	}

	@FindBy(id="username")
	private WebElement wbUsernameTxt;
	
	@FindBy(id="password")
	private WebElement wbPasswordTxt;
	
	@FindBy(xpath="//button[contains(@class,' btn-primary ')]")
	private WebElement wbSubmitBtn;
	
	@FindBy(xpath="//*[@id='feedsUserPhoto']/following-sibling::div")
	private WebElement wbLoggedInUserTxt;
	
	
	public WebElement getTextFromApplication() {
		return wbLoggedInUserTxt;
		
	}
	public void enterCredentials(String userName,String password) {
		try {
			waitUntilElementVisible(wbSubmitBtn);
			wbUsernameTxt.sendKeys(userName);
			wbPasswordTxt.sendKeys(password);
		} catch (Exception e) {
		//	baseSetup.logger.error("Not Able to Login To App due to : \n", e.getMessage());
		}
	}		
		public void clickLoginButton(){
			try {
				wbSubmitBtn.click();
				waitTillElementVisible(wbLoggedInUserTxt);
			} catch (Exception e) {
			e.printStackTrace();
			
			}
	}
}
