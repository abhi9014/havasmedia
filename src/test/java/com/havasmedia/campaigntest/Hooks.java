package com.havasmedia.campaigntest;

import java.io.File;
import java.io.IOException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;

import com.google.common.io.Files;
import com.havasmedia.basetest.BaseConfigurationSetup;
import com.havasmedia.pageObjects.CampaignPage;
import com.havasmedia.pageObjects.HwInit;
import com.cucumber.listener.Reporter;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.AfterStep;
import cucumber.api.java.Before;
import cucumber.api.java.BeforeStep;

public class Hooks extends BaseConfigurationSetup {
	public WebDriver driver;
	public static HwInit pageInit;
	public static CampaignPage campaignPageObj;
	
	@Before("@test")
	public void setUpInitObjects() throws Exception {
		
		driver = initWebBrowserDriver(configFileLoc.getProperty("CHROME"));
		navigateToPage(configFileLoc.getProperty("HM_URL"));
		pageInit = new HwInit(driver);
		campaignPageObj = pageInit.initCampainPage(driver);
	}

	@BeforeStep()
	public void navigateToPageURL() throws Exception {
		
	}

	@AfterStep
	public void relaseBrowserInstance(Scenario scenario) {

		if (scenario.isFailed()) {
			
			/*File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

			try {
			FileUtils.copyFile(scrFile, new File(USER_DIRECTORY+"//ScreenShot//Screenshot_"+
					 getCurrentDate()+"_"+getCurrentTimeInSS().trim().replace("/", "_")+".png"));
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}*/
		String screenshotName = scenario.getName().replaceAll(" ", "_");
		 try {
		 //This takes a screenshot from the driver at save it to the specified location
		 File sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		 
		 //Building up the destination path for the screenshot to save
		 //Also make sure to create a folder 'screenshots' with in the cucumber-report folder
		 File destinationPath = new File(USER_DIRECTORY + "ScreenShots/" + screenshotName +getCurrentTimeInSS().trim()+ ".png");
		 
		 //Copy taken screenshot from source location to destination location
		 Files.copy(sourcePath, destinationPath);   
		 
		 } catch (IOException e) {
		 } }
	//	Reporter.loadXMLConfig("src/extent-config.xml");
		
	}
	@After
	public void releaseAllInstances() {
		driver.quit();
	}
}
