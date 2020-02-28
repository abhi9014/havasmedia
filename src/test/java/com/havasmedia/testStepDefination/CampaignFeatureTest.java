package com.havasmedia.testStepDefination;


import org.testng.Assert;

import com.havasmedia.basetest.BaseConfigurationSetup;
import com.havasmedia.campaigntest.Hooks;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class CampaignFeatureTest extends BaseConfigurationSetup{
	
	@Given("user enters valid {string} and {string}")
	public void user_enters_valid_and(String userName, String passWord) {
		Hooks.campaignPageObj.enterCredentials(userName, passWord);
	}
	@And("^user clicks on LoginButton$")
	public void clickOnLogin() throws Throwable {
		Hooks.campaignPageObj.clickLoginButton();
		
	}
	@Then("^Verify the UserName in Homepage$")
	public void Verify_the_UserName_in_Homepage() throws Throwable {
		String struserName=Hooks.campaignPageObj.getTextFromApplication().getText();
		Assert.assertEquals(struserName, "Abhishek Chatterjee");
	}

}