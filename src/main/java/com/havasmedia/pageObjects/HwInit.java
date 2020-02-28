package com.havasmedia.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.havasmedia.Utils.LoadTimeOut;
import com.havasmedia.basetest.BaseConfigurationSetup;

public class HwInit extends LoadTimeOut{
	WebDriver driver;
	BaseConfigurationSetup baseSetup;
	public HwInit(WebDriver driver) {
		super(driver);
		this.driver=driver;
		baseSetup=new BaseConfigurationSetup();
		PageFactory.initElements(driver, this);
	}
	
	public CampaignPage initCampainPage(WebDriver driver) {
		return PageFactory.initElements(driver, CampaignPage.class);
		
	}
}
