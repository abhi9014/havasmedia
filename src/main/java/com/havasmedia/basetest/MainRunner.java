package com.havasmedia.basetest;




import cucumber.api.CucumberOptions;
import cucumber.api.Scenario;
import cucumber.api.testng.AbstractTestNGCucumberTests;
@CucumberOptions(
				features ="classpath:FeaturesFiles",
				glue= {"com.havasmedia.campaigntest","com.havasmedia.testStepDefination"},
				plugin = {"pretty",
							"junit:target/surefire-reports/TESTCucumber.xml"},
				monochrome = true, //display the console output in a proper readable format
				strict = true, //it will check if any step is not defined in step definition file
				dryRun = false,//to check the mapping is proper between feature file and step def file
				tags= {"@BDDTEST-TES-4"}//"~@Ignore"   "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:target/cucumber-reports/report.html
		)

public class MainRunner extends AbstractTestNGCucumberTests{
	public static Scenario scenario;
	/*
	 * @AfterClass public static void writeExtentReport() { String path
	 * =BaseConfigurationSetup.USER_DIRECTORY+
	 * "\\src\\main\\resources\\ConfigProperties\\extentReport.xml";
	 * System.out.println(path); Reporter.loadXMLConfig(path); //
	 * Reporter.loadXMLConfig(".
	 *src/main/resources/ConfigProperties/extent-config");Reporter.setSystemInfo("User Name",System.getProperty("user.name"));Reporter.setSystemInfo("Time Zone",System.getProperty("user.timezone"));Reporter.setSystemInfo("Machine","Windows 10"+"64 Bit");Reporter.setSystemInfo("Selenium","3.7.0");Reporter.setSystemInfo("Maven","3.5.2");Reporter.setSystemInfo("Java Version","1.8.0_151");
}*/
}
	