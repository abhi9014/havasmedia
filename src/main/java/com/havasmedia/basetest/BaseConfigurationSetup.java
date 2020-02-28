package com.havasmedia.basetest;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import com.havasmedia.Utils.WebEventListener;


public class BaseConfigurationSetup{
	public  WebDriver driver;
	public static Properties configFileLoc;
	public static final String USER_DIRECTORY = System.getProperty("user.dir");
	public static final String CONFIG_PROPERTIES = USER_DIRECTORY + "/src/main/resources/ConfigProperties/config.properties";
	
	public static final String IE_DRIVER_PATH = USER_DIRECTORY + "/src/main/resources/drivers/IEDriverServer_Win32_3.14.0/IEDriverServer.exe";
	public static final String CHROME_DRIVER_PATH = USER_DIRECTORY + "/src/main/resources/drivers/chromeDriver/chromedriver.exe";
	public static final String FIREFOX_DRIVER_PATH = USER_DIRECTORY + "/src/main/resources/drivers/geckodriver-v0.23.0-win64/geckodriver32.exe";
	public JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
	public   EventFiringWebDriver e_driver;
	public  WebEventListener eventListener;
	//public Logger logger;
	
	//Constructor: to initialize and load properties in prop.
	public BaseConfigurationSetup()  {
		try {
		//	logger=LoggerFactory.getLogger(getClass());
			configFileLoc = new Properties();
			FileInputStream fInputStream = new FileInputStream(CONFIG_PROPERTIES);
			configFileLoc.load(fInputStream);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	//Method: Initialization of given browser and setting prerequisites of the test browser.
	public  WebDriver initWebBrowserDriver(String browserName)  {
		try {
			if(browserName.equals("chrome")) {
				System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
				driver = new ChromeDriver();
			}else if(browserName.equals("FF")) {
				System.setProperty("webdriver.gecko.driver", FIREFOX_DRIVER_PATH);
				driver = new FirefoxDriver();
			}else if(browserName.equals("IE")) {
				System.setProperty("webdriver.ie.driver", IE_DRIVER_PATH);
				driver = new InternetExplorerDriver();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return driver;
	}

	

	//Method: to maximize, set timeouts, delete cookies from browser and launch url;
	public  void navigateToPage(String url){
			//Listener initialization 
			e_driver = new EventFiringWebDriver(driver);
			eventListener = new WebEventListener(); 
			e_driver.register(eventListener);
			driver = e_driver;
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.get(url);
	}
	
	//Method: to click on main menu and sub menu by using mouse actions
	public void clickOnSubMenu(WebDriver driver, String mainMenuXpath, String subMenuXpath) {
		WebElement tu_mainMenu = driver.findElement(By.xpath(mainMenuXpath));
		WebElement tu_subMenu = driver.findElement(By.xpath(subMenuXpath));

		Actions act = new Actions(driver);
		act.moveToElement(tu_mainMenu).click().build().perform();
		act.click(tu_subMenu).build().perform();
	}

	//Method: select list item as per given list item text
	public void selectSingleDropDownItem(WebDriver driver, WebElement attSelectCustListBox, String custListOption) {
		Select dropDownElement = new Select(attSelectCustListBox);
		dropDownElement.selectByVisibleText(custListOption);
	}
	
	// Methods to get dropdown values
	public List getDropdownValue(WebElement element) {
		Select res = new Select(element);
		List<WebElement> l= res.getOptions();
		return l;
	}

	//Method: to get today's date in MMM dd, yyyy format.
	public String getTodaysDate() {
		Date myDate = new Date();
		SimpleDateFormat sm = new SimpleDateFormat("dd MMM,yyyy");
		String strDate = sm.format(myDate);
		return strDate;
	}

	//Method: to get future date in dd/mm/yyyy format based on giving bookingDate inputs.
	//MMDD
	public String getFutureDateInDDMMYYYY(int bookingDate) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, bookingDate);
		Date fDate = cal.getTime();
		SimpleDateFormat sm = new SimpleDateFormat("MM/dd/yyyy");
		return sm.format(fDate);
	}

	//Method: to get future date in MM/dd/yyyy format based on giving bookingDate inputs.
	//This methods used in Store App to enter date in MMDDYYYY format.
	public  String getFutureDateInMMDDYYYY(int bookingDate) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, bookingDate);
		Date fDate = cal.getTime();
		SimpleDateFormat sm = new SimpleDateFormat("MM/dd/yyyy");
		return sm.format(fDate);
	}
	
	public  String getFutureDateInMMMDDYYYY(int bookingDate) { 
		Calendar cal = Calendar.getInstance(); 
		cal.add(Calendar.DATE, bookingDate);
		Date fDate = cal.getTime();
		SimpleDateFormat sm = new SimpleDateFormat("MMM/dd/yyyy");
		return sm.format(fDate);
	}
	
	//Method: to get future date in yyyy-MM-dd format based on giving bookingDate inputs.
	//This methods used in RV Admin to enter date in yyyy-mm-dd format.
	public String getFutureDateInYYYYMMDD(int bookingDate) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, bookingDate);
		Date fDate = cal.getTime();
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
		return sm.format(fDate);
	}
	
	//Method: to get second SUNDAY after present date in dd-mm-yyyy format.
	public String getSecondFutureSundayDateInDDMMYYYY() {
		Calendar c=Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		DateFormat df=new SimpleDateFormat("dd/MM/yyyy");
		c.add(Calendar.DATE,14);
		return df.format(c.getTime());
	}

	//Method: to generate Random string with 6 chars.
	protected String getSaltString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 6) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
	}
	
	//Method: to generate Random number with 2 digits.
	protected String getSaltNumber2() {
		String SALTNUM = "123456789";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 2) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTNUM.length());
			salt.append(SALTNUM.charAt(index));
		}
		String saltNum = salt.toString();
		return saltNum;
	}

	//Method: to generate Random number with 10 digits.
	protected String getSaltNumber() {
		String SALTNUM = "123456789";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 10) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTNUM.length());
			salt.append(SALTNUM.charAt(index));
		}
		String saltNum = salt.toString();
		return saltNum;
	}

	//Method: to generate Random email address.
	protected String getSaltEmail() {
		String SALTEMAIL = "abcdefghijklmnopqrstuvwxyz";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 4) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTEMAIL.length());
			salt.append(SALTEMAIL.charAt(index));
		}
		String saltEMAIL = salt.toString();
		String fullEmail = saltEMAIL+"@"+saltEMAIL+".com";
		return fullEmail;
	}

	//Method: Click on WebElement in the list of WebElements.
	public  void selectElementByListByTextOfAnElement(List<WebElement> AttAllReportTabNameList, String tabName) {
		int listSize = AttAllReportTabNameList.size();
		for(int i=0;i<listSize;i++) {
			if(AttAllReportTabNameList.get(i).getText().contains(tabName)) {
				AttAllReportTabNameList.get(i).click();
			}
		}
	}

	//Method: to switchTo - Child Window, getText of given WebElement and close.
	public String getTextAndCloseChildWindow(WebElement webElement) {
		String x = null;
		String mainWindow = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();

		for(String childWindow: allWindows) {
			if(!childWindow.equals(mainWindow)) {
				driver.switchTo().window(childWindow);
				x = webElement.getText();
				driver.close();
				driver.switchTo().window(mainWindow);
				return x;
			}
		}
		return x;
	}

	//Method: to switchTo - Child Window and close.
	public void closeChildWindow() {
		String mainWindow = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();

		for(String childWindow: allWindows) {
			if(!childWindow.equals(mainWindow)) {
				driver.switchTo().window(childWindow);
				driver.close();
				driver.switchTo().window(mainWindow);
			}
		}
	}


	//Method: to switchTo - Child Window get Window title and close.
	public String getTitleAndCloseChildWindow() {
		String x = null;
		String mainWindow = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();

		for(String childWindow: allWindows) {
			if(!childWindow.equals(mainWindow)) {
				driver.switchTo().window(childWindow);
				x = driver.getTitle();
				driver.close();
				driver.switchTo().window(mainWindow);
				return x;
			}
		}
		return x;
	}


	//Method: to switchTo - Child Window.
	public void switchToChildWindow() {
		String mainWindow = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();
		
		for(String childWindow: allWindows) {
			if(!childWindow.equals(mainWindow)) {
				driver.switchTo().window(childWindow);
			}
		}
	}

	//Method: to switchTo - Main Window. [use after using switch to switchToChildWindow()]
	public void switchToMainWindow() {
		String childWindow = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();

		for(String mainWindow: allWindows) {
			if(!mainWindow.equals(childWindow)) {
				driver.switchTo().window(mainWindow);
			}
		}
	}

	//Method: to switchTo - ALert()
	public  void switchToAlert() {
		try {
			driver.switchTo().alert();
		}catch(NoAlertPresentException e) {
			e.printStackTrace();
		}
	}

	//Method: to switchTo - ALert() and Accept.
	public  void switchToAlertAndAccept() {
		try {
			Alert a = driver.switchTo().alert();
			a.accept();
		}catch(NoAlertPresentException e) {
			e.printStackTrace();
		}
	}

	//Method: to switchTo - ALert() and Dismiss.
	public  void switchToAlertAndDismiss() {
		try {
			Alert a = driver.switchTo().alert();
			a.dismiss();
		}catch(NoAlertPresentException e) {
			e.printStackTrace();
		}
	}

	//Method: to switchTo - ALert() and Dismiss.
	public String switchToAlertAndAcceptReturnTitle() {
		String alertMessage = null;

		try {
			Alert a = driver.switchTo().alert();
			alertMessage = a.getText();
			a.accept();
		}catch(NoAlertPresentException e) {
			e.printStackTrace();
		}
		return alertMessage;
	}

	//Method: to switchTo - Frame()
	public  void switchToFrame() {
		try {
			driver.switchTo().frame(0);
		}catch(NoSuchFrameException e) {
			e.printStackTrace();
		}
	}

	//Method: getClipboard string (it gives cntr+c data/string in return)
	public String getClipboardText(){
		String x = null;
		try {
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			Transferable contents = clipboard.getContents(null);
			x = (String) contents.getTransferData(DataFlavor.stringFlavor);
			return x;
		}catch(UnsupportedFlavorException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return x;
	}

	//Method:clearClipboard string (it set Clipboard as null)
	public void clearClipboardText() {
		StringSelection stringSelection = new StringSelection("");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}

	//Method:Enter date into Date Input box using Robot
	public void enterDateUsingRobot(int bookingDate) throws AWTException {
		String vdate = null;
		if(bookingDate==0){
			vdate = getSecondFutureSundayDateInDDMMYYYY();
		}else {
			vdate = getFutureDateInDDMMYYYY(bookingDate);
		}
		String vdatedigit = vdate.replace("/", "");
		Robot robot = new Robot(); 
		for (char c : vdatedigit.toCharArray()) {
			int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
			if (KeyEvent.CHAR_UNDEFINED == keyCode) {
				throw new RuntimeException(
						"Key code not found for character '" + c + "'");
			}
			robot.keyPress(keyCode);
			robot.delay(100);
			robot.keyRelease(keyCode);
			robot.delay(100);
		}
	}                              

	//Method:Enter data using Robot [Case sensitive robot method it used child methods needsShift() to check case]
	public void enterDataUsingRobot(String dataValue) throws AWTException {
		Robot robot = new Robot(); 
		for (char c : dataValue.toCharArray()) {
			int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
			if (KeyEvent.CHAR_UNDEFINED == keyCode) {
				throw new RuntimeException("Key code not found for character '" + c + "'");
			}
			if(needsShift(c)){
				robot.keyPress(KeyEvent.VK_SHIFT);
			}
			robot.keyPress(keyCode);
			robot.keyRelease(keyCode);
			if(needsShift(c)){
			    robot.keyRelease(KeyEvent.VK_SHIFT);
			} 
		}
	}
	
	//Child method to check case of the parent method: enterDataUsingRobot() chars.
	private boolean needsShift(Character c) {
	    return Character.isUpperCase(c);
	}
	
	//Method:Enter date into Date Input box using Robot for chrome
	public void enterDateUsingRobotForChrome(int bookingDate) throws AWTException {
		String vdate = null;
		if(bookingDate==0){
			vdate = getSecondFutureSundayDateInDDMMYYYY();
		}else {
			vdate = getFutureDateInDDMMYYYY(bookingDate);
		}
		String vdatedigit = vdate.replace("/", "");
		String vdateDDMM = vdatedigit.substring(0,4);
		Robot robot = new Robot(); 
		for (char c : vdateDDMM.toCharArray()) {
			int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
			if (KeyEvent.CHAR_UNDEFINED == keyCode) {
				throw new RuntimeException("Key code not found for character '" + c + "'");
			}
			robot.keyPress(keyCode);
			robot.delay(100);
			robot.keyRelease(keyCode);
			robot.delay(100);
		}
	}               

	//Method: get only Today's date like: 01/31/.. 
	public  String getTodaysDateInDD(){
		String vdate = null;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		Date fDate = cal.getTime();
		SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy");
		vdate = sm.format(fDate);
		String vdatedigit = vdate.replace("/", "");
		return vdatedigit.substring(0,2);
	}
	
	//Method: get only Today's day like: MON/TUE/... 
	public  String getTodaysDateInDay(){
		String vDate = null;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		Date fDate = cal.getTime();
		SimpleDateFormat sm = new SimpleDateFormat("EEE");
		vDate = sm.format(fDate);
		String vDateDay = vDate.replace("/", "");
		return vDateDay.substring(0,3).toUpperCase();
	}
	
	//Method: get only Given date in day like: MON/TUE/... 
	public  String getGivenDateInDay(int vdate){
		String vDate = null;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, vdate);
		Date fDate = cal.getTime();
		SimpleDateFormat sm = new SimpleDateFormat("EEE");
		vDate = sm.format(fDate);
		String vDateDay = vDate.replace("/", "");
		return vDateDay.substring(0,3).toUpperCase();
	}
	
	//Method: get only current month like: JAN/FEB/... 
	public  String getCurrentMonth(){
		String vDate = null;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		Date fDate = cal.getTime();
		SimpleDateFormat sm = new SimpleDateFormat("MMM");
		vDate = sm.format(fDate);
		String vMonth = vDate.replace("/", "");
		return vMonth.substring(0,3).toUpperCase();
	}
	
	//Method: get only current Time in Seconds 
	public  String getCurrentTimeInSS(){
		String vSeconds = null;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		Date fDate = cal.getTime();
		SimpleDateFormat sm = new SimpleDateFormat("ss");
		vSeconds = sm.format(fDate);
		return vSeconds;
	}
	
	//Method: get only current Date in dd 
	public  String getCurrentTimeInDD(){
		String vDate = null;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		Date fDate = cal.getTime();
		SimpleDateFormat sm = new SimpleDateFormat("dd");
		vDate = sm.format(fDate);
		return vDate;
	}
	
	//Method:Enter number into input box using Robot
	public void enterPhoneNumberUsingRobot(String bphone) throws AWTException {
		if(!bphone.equals("")){
			Robot robot = new Robot(); 
			String vnum = bphone;
			for (char c : vnum.toCharArray()) {
				int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
				if (KeyEvent.CHAR_UNDEFINED == keyCode) {
					throw new RuntimeException("Key code not found for character '" + c + "'");
				}
				robot.keyPress(keyCode);
				robot.delay(100);
				robot.keyRelease(keyCode);
				robot.delay(100);
			}	
		}else {
			Robot robot = new Robot(); 
			String vnum = getSaltNumber();
			for (char c : vnum.toCharArray()) {
				int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
				if (KeyEvent.CHAR_UNDEFINED == keyCode) {
					throw new RuntimeException("Key code not found for character '" + c + "'");
				}
				robot.keyPress(keyCode);
				robot.delay(100);
				robot.keyRelease(keyCode);
				robot.delay(100);
			}
		}
	}

	//Method:Enter number into input box using Robot
	public void enterDeleteUsingRobot() throws AWTException {
		Robot robot = new Robot(); 	
		robot.keyPress(KeyEvent.VK_DELETE);
		robot.delay(100);
		robot.keyRelease(KeyEvent.VK_DELETE); 
		robot.delay(100);
	}	

	//Method: Scroll Down the browser 
	public void scrollDown(WebDriver driver){
		jsDriver.executeScript("window.scrollBy(0,400)"); 
	}

	//Method: Scroll Up the browser
	public void scrollUp(WebDriver driver){
		jsDriver.executeScript("window.scrollBy(0,-400)"); 
	}
	
	public WebElement scrollIntoView(WebElement element) {
		jsDriver.executeScript("arguments[0].scrollIntoView();",element);
		return element;
	}

	//Method: to get current date in dd/mm/yyyy format based on giving bookingDate inputs.
	public String getCurrentDateInDDMMYYYY(int todaysDate) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, todaysDate);
		Date fDate = cal.getTime();
		SimpleDateFormat sm = new SimpleDateFormat("MM/dd/yyyy");
		return sm.format(fDate);
	}
	
	//Method: to get current date in mm/dd format based on giving bookingDate inputs.
	public String getCurrentDateInMMDD(int todaysDate) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, todaysDate);
		Date fDate = cal.getTime();
		SimpleDateFormat sm = new SimpleDateFormat("MM/dd");
		return sm.format(fDate);
	}
	
	//Method: to get current date and time.
	public String getCurrentDateAndTime_yyyy_MM_dd_HH_mm_ss() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		Date fDate = cal.getTime();
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sm.format(fDate);
	}
	
	public  String expectedText;
	
	//Selects elements from dropdown by visible text
	public  void 	selectDropDwnByText(WebElement element, String Value) {
		try {
			Select res = new Select(element);	
			res.selectByVisibleText(Value);
		} catch(Exception e) {
			e.printStackTrace(); }
	}
	
	//Selects elements and validate text
	public  String validateText(WebElement element) {
		try {
			String expectedText = element.getText();
			System.out.println("The ReservationType selected is: " + expectedText);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return expectedText;
	}
	
	//Selects elements from dropdown by index
	public  void 	selectDrpDwnByIndex(WebElement element, int Value) {
		try {
			Select res = new Select(element);	
			res.selectByIndex(Value);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Waiting until elemengt is visible
	public  void explicitWaitXpathTillVisible(WebDriver driver, String element) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)));
	}
	
	//Getting 1st Selected element option
	public  void getFirstSelectedOption(WebElement element) {
		try {
			Select res = new Select(element);	
			String str = res.getFirstSelectedOption().getText();
			System.out.println("the selected option is :-" + str);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Selecting Radio Button
	public  List<WebElement> selectRdoBtn(List<WebElement> webelement, int Value) {
		try {
			for (int i = 0; i < webelement.size(); i++) {
				webelement.get(Value).click();
				Thread.sleep(3000);
				break;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return webelement;
	}
	
	//Method to wait
	public  void waitInSeconds(int sec) {
			try {
				Thread.sleep(sec * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
	//scroll down
	public  void scrollDownCC(WebDriver driver) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver ;
			jse.executeScript("window.scrollBy(0,450)", "");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//scroll up
	public  void scrollUpCC(WebDriver driver) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(300,0)", "");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//gets the text of webelement
	public  String getText(WebElement element) {
		try {	
			expectedText = element.getText();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return expectedText;
	}
	
	//select the last element in a list 
	public  List<WebElement> selectLastElement(List<WebElement> webelement) {
		try {
			WebElement element = webelement.get(webelement.size()-1);
			element.click();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return webelement;	
	}
	
	//Selects elements from dropdown by value
	public  void selectDrpDwnByValue(WebElement element, String Value) {
		try {
			Select res = new Select(element);	
			res.selectByValue(Value);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Selects 1st Check box and sends the value
	public  void selectFirstChkBoxNSendValue(String value) {
		try {
			List<WebElement> wbListCheckBox = driver.findElements(By.xpath(value));
			for (WebElement webElement : wbListCheckBox) {
				webElement.click();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//checks the visibility of an webElement
	public  void checkVisibilityOfWebElement(WebElement element) {
		boolean bflag = false;
		try {
			if(element.isDisplayed()) {
				if(element.isEnabled()) {
					bflag = true;
					element.click();
				} else { 
					bflag = false; 
					System.out.println(element + "the button is not present");
				}
			} else { 
				bflag = false; 
				System.out.println(element + "the button is not present");
			}
			Thread.sleep(4000);
		} catch(Exception e) { 
			e.printStackTrace();
		}
	}
	
	//JavaScript click
	public  void jsClick(WebDriver driver, WebElement el) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", el);
	}
	
	/*/////////////////////////////////////////////////////////////////////////////////////////////////
	//PROCESS KILL METHODS///////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	//Process killer variables initialization
	private  final String TASKLIST = "tasklist";
	private  final String KILL = "taskkill /F /IM ";
	
	//Checks process availability by name
	public  boolean isProcessRunning(String serviceName) throws Exception {
		Process p = Runtime.getRuntime().exec(TASKLIST);
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
			if (line.contains(serviceName)) {
				return true;
			}
		}
		return false;
	}
	
	//kills Process by Name
	public  void killProcess(String serviceName) throws Exception {
		Runtime.getRuntime().exec(KILL + serviceName);
	}
	
	//Calling methods to kill process
	public  void killBrowserProcess() throws Exception{
		//Firefox Process
		String ffProcess = "firefox.exe";
		String ffDriverprocess = "geckodriver.exe";
		//Chrome Process
		String chProcess = "chrome.exe";
		String chDriverprocess = "chromedriver.exe";
		//IE Process
		String ieProcess = "iexplore.exe";
		String ieDriverprocess = "IEDriverServer.exe";
		//Conhost Process
		String conProcess = "conhost.exe";
		if(propv.getProperty("GUI").equals("FF")){
			if (isProcessRunning(ffProcess)) {
				killProcess(ffProcess);
			 }
			if (isProcessRunning(ffDriverprocess)) {
				killProcess(ffDriverprocess);
			 }
		}else if(propv.getProperty("GUI").equals("chrome")) {
			if (isProcessRunning(chProcess)) {
				killProcess(chProcess);
			}
			if (isProcessRunning(chDriverprocess)) {
				killProcess(chDriverprocess);
			}
		}else if(propv.getProperty("GUI").equals("IE")) {
			 if (isProcessRunning(ieProcess)) {
				 killProcess(ieProcess);
			 }
			 if (isProcessRunning(ieDriverprocess)) {
				 killProcess(ieDriverprocess);
			}
		}
		if (isProcessRunning(conProcess)) {
			killProcess(conProcess);
		}
	}*/
	

	//Choose NO THANKS in UpSell Pop-up.
	public void sayNoThanksToUpSell(WebElement upSellNoThanks,String upSellNoThanksXpath) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(upSellNoThanksXpath)));
		upSellNoThanks.click();
	}

	//Choose UPGRADE in UpSell Pop-up.
	public void chooseUpSellUpgrade(WebElement upSellUpgrade, String upSellUpgradeXpath) {
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(upSellUpgradeXpath)));
		upSellUpgrade.click();
	}

	//Remove or Add Child or Adult
	public void addORRemoveChildORAdult(WebElement addORRemoveElement, int cCount) {
		for(int i=1;i<=cCount;i++) {
			waitAndClick(addORRemoveElement);
		}
	}
	
	//To check if the element is clickable or not and returns true or false
	public  boolean isClickable(WebElement el) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 6);
			wait.until(ExpectedConditions.elementToBeClickable(el));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	//External wait till clickable and click
	public void waitAndClick(WebElement vWebElement) {
		WebDriverWait wait = new WebDriverWait(driver,15);
		wait.until(ExpectedConditions.elementToBeClickable(vWebElement));
		vWebElement.click();
	}
	
	//External wait till clickable 
	public void waitUntilElementClickable(WebElement vWebElement) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver,15);
		wait.until(ExpectedConditions.elementToBeClickable(vWebElement));
	}

	
	//External  waitUntilElementVisible	
	public void waitUntilElementVisible(WebElement element){
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		WebDriverWait wait=new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	//External wait: to wait and get text
	public String waitAndGetText(WebElement vWebElement) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver,15);
		wait.until(ExpectedConditions.elementToBeClickable(vWebElement));
		return vWebElement.getText();
	} 
		
	//External wait till visible and get text
	public  String waitTillVisibleAndGetText(WebElement vWebElement) {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.visibilityOf(vWebElement));
		return vWebElement.getText();
	}
	
	//Database query methods - Work in progress....
/*	public  void selectQuery() throws SQLException, ClassNotFoundException { 
		String dbURL = "jdbc:sqlserver://ipAddress:portNumber/dbName";
		String username = "dbusername";
		String password = "dbpassword";
		//Load MS SQL JDBC Driver
		Class.forName("net.sourceforge.jtds.jdbc.Driver");
		//Creating connection to the database
		Connection con = DriverManager.getConnection(dbURL,username,password);
		//Creating statement object
		Statement st = con.createStatement();
		String selectquery = "SELECT * FROM <tablename> WHERE <condition>";
		//Executing the SQL Query and store the results in ResultSet
		ResultSet rs = st.executeQuery(selectquery);
		//While loop to iterate through all data and print results
		while (rs.next()) {
			System.out.println(rs.getString("transaction_datetime"));
		}
		//Closing DB Connection
		con.close();
	}	*/


	//Find Available time slot for Birthday and click
	/*public  void selectAvailableTimeSlot(String BasePKG) throws InterruptedException { 
		Thread.sleep(5000);
		boolean isFound = false;
		int i=0;
		int j=0;
		int k=0;
		int l=0;
		int m=0;
		int n=0;
		if(BasePKG.equals("Star")){
			i=1;
			j=4;
			k=7;
			l=10;
			m=13;
			n=16;
		}else if(BasePKG.equals("SuperStar")){
			i=2;
			j=5;
			k=8;
			l=11;
			m=14;
			n=17;
		}else if(BasePKG.equals("MegaStar")){
			i=3;
			j=6;
			k=9;
			l=12;
			m=15;
			n=18;
		}
		try {
			String selectPackageStarXpath = "(//button[@title='Select Package'])["+i+"]";
			WebElement ele = driver.findElement(By.xpath(selectPackageStarXpath));
			if(ele.isEnabled()){
				ele.click();
				isFound=true;
			}
		}catch(Exception e) {
				//e.printStackTrace();
				System.out.println("!!!..........................FIRST TIME SLOT NOT AVAILABLE.....................!!!");
		}
		if(isFound!=true){
			try{
				String selectPackageStarXpath = "(//button[@title='Select Package'])["+j+"]";
				WebElement ele = driver.findElement(By.xpath(selectPackageStarXpath));
				if(ele.isEnabled()){
					ele.click();
					isFound=true;
				}
			}catch(Exception e) {
				//e.printStackTrace();
				System.out.println("!!!..........................SECOND TIME SLOT NOT AVAILABLE.....................!!!");
			}
		}
		if(isFound!=true){
			try{
				String selectPackageStarXpath = "(//button[@title='Select Package'])["+k+"]";
				WebElement ele = driver.findElement(By.xpath(selectPackageStarXpath));
				if(ele.isEnabled()) {
					ele.click();
				}
			}catch(Exception e){
				//e.printStackTrace();
				System.out.println("!!!..........................THIRD TIME SLOT NOT AVAILABLE.....................!!!");
			}	
		}
		if(isFound!=true){
			try{
				String selectPackageStarXpath = "(//button[@title='Select Package'])["+l+"]";
				WebElement ele = driver.findElement(By.xpath(selectPackageStarXpath));
				if(ele.isEnabled()){
					ele.click();
				}
			}catch(Exception e){
				//e.printStackTrace();
				System.out.println("!!!..........................FOURTH TIME SLOT NOT AVAILABLE.....................!!!");
			}
		}
		if(isFound!=true){
			try{
				String selectPackageStarXpath = "(//button[@title='Select Package'])["+m+"]";
				WebElement ele = driver.findElement(By.xpath(selectPackageStarXpath));
				if(ele.isEnabled()){
					ele.click();
				}
			}catch(Exception e){
				//e.printStackTrace();
				System.out.println("!!!..........................FIFTH TIME SLOT NOT AVAILABLE.....................!!!");
			}	
		}
		if(isFound!=true){
			try{
				String selectPackageStarXpath = "(//button[@title='Select Package'])["+n+"]";
				WebElement ele = driver.findElement(By.xpath(selectPackageStarXpath));
				if(ele.isEnabled()){
					ele.click();
				}
			}catch(Exception e){
				//e.printStackTrace();
				System.out.println("!!!..........................NO TIME SLOT NOT AVAILABLE.....................!!!");
			}	
		}
	}

	//Method: to get the Due date of a booking
	public String getDueDateMMDD(int todaysDate) throws AWTException {
		String vdate = getCurrentDateInDDMMYYYY(todaysDate+2);
		String vdatedigit = vdate.replace("/", "");
		String vdateDDMM = vdatedigit.substring(0,4); 
		String vdateDDMM1 = vdateDDMM.substring(0, 2);
		String vdateDDMM2 = vdateDDMM.substring(2, 4);
		vdateDDMM = vdateDDMM1+"/"+vdateDDMM2;
		return vdateDDMM;
	}
	
	//Find Available time slot for Event and click
	public  void selectEventAvailableTimeSlot(String BasePKG) throws InterruptedException { 
		Thread.sleep(5000);
		boolean isFound = false;
		int i=0;
		int j=0;
		int k=0;
		int l=0;
		int m=0;
		int n=0;
		if(BasePKG.equals("60min")){
			i=1;
			j=4;
			k=7;
			l=10;
			m=13;
			n=16;
		}else if(BasePKG.equals("90min")){
			i=2;
			j=5;
			k=8;
			l=11;
			m=14;
			n=17;
		}else if(BasePKG.equals("120min")){
			i=3;
			j=6;
			k=9;
			l=12;
			m=15;
			n=18;
		}
		try{
			String selectPackageStarXpath = "(//button[@title='Select Package'])["+i+"]";
			WebElement ele = driver.findElement(By.xpath(selectPackageStarXpath));
			if(ele.isEnabled()){
				ele.click();
				isFound=true;
			}
		}catch(Exception e){
			//e.printStackTrace();
			System.out.println("!!!..........................FIRST TIME SLOT NOT AVAILABLE.....................!!!");
		}
		if(isFound!=true){
			try{
				String selectPackageStarXpath = "(//button[@title='Select Package'])["+j+"]";
				WebElement ele = driver.findElement(By.xpath(selectPackageStarXpath));
				if(ele.isEnabled()){
					ele.click();
					isFound=true;
				}
			}catch(Exception e){
				//e.printStackTrace();
				System.out.println("!!!..........................SECOND TIME SLOT NOT AVAILABLE.....................!!!");
			}
		}
		if(isFound!=true){
			try{
				String selectPackageStarXpath = "(//button[@title='Select Package'])["+k+"]";
				WebElement ele = driver.findElement(By.xpath(selectPackageStarXpath));
				if(ele.isEnabled()){
					ele.click();
				}
			}catch(Exception e){
				//e.printStackTrace();
				System.out.println("!!!..........................THIRD TIME SLOT NOT AVAILABLE.....................!!!");
			}	
		}
		if(isFound!=true){
			try{
				String selectPackageStarXpath = "(//button[@title='Select Package'])["+l+"]";
				WebElement ele = driver.findElement(By.xpath(selectPackageStarXpath));
				if(ele.isEnabled()){
					ele.click();
				}
			}catch(Exception e){
				//e.printStackTrace();
				System.out.println("!!!..........................FOURTH TIME SLOT NOT AVAILABLE.....................!!!");
			}
		}
		if(isFound!=true){
			try{
				String selectPackageStarXpath = "(//button[@title='Select Package'])["+m+"]";
				WebElement ele = driver.findElement(By.xpath(selectPackageStarXpath));
				if(ele.isEnabled()){
					ele.click();
				}
			}catch(Exception e){
				//e.printStackTrace();
				System.out.println("!!!..........................FIFTH TIME SLOT NOT AVAILABLE.....................!!!");
			}	
		}
		if(isFound!=true){
			try{
				String selectPackageStarXpath = "(//button[@title='Select Package'])["+n+"]";
				WebElement ele = driver.findElement(By.xpath(selectPackageStarXpath));
				if(ele.isEnabled()){
					ele.click();
				}
			}catch(Exception e){
				//e.printStackTrace();
				System.out.println("!!!..........................NO TIME SLOT NOT AVAILABLE.....................!!!");
			}	
		}
	}*/
	
	//Get Curent Window Id Using get Window Handle
	public String getWindowID() {
		return driver.getWindowHandle();
	}
	
	//To get data from the excel by the colunm name
	public String getdataStringByColumnName(String excellocation, String sheetName, String columnName, int rowNum) {
		try {
			int colNum = -1;
			FileInputStream file=new FileInputStream(new File(excellocation));
			XSSFWorkbook wb = new XSSFWorkbook(file);
			XSSFSheet sheet = wb.getSheet(sheetName);
			XSSFRow row = sheet.getRow(0);
			XSSFCell cell=null;
			for(int i=0; i < row.getLastCellNum(); i++) {
				if(row.getCell(i).getStringCellValue().trim().equals(columnName))
					colNum = i;
			}
			row = sheet.getRow(rowNum);
			cell= row.getCell(colNum);
			if(cell.getCellTypeEnum()== CellType.STRING)
				return cell.getStringCellValue();
			else if(cell.getCellTypeEnum()==CellType.NUMERIC || cell.getCellTypeEnum()==CellType.FORMULA) {
				String cellValue= String.valueOf(cell.getNumericCellValue());
				return cellValue;
			} else if(cell.getCellTypeEnum()== CellType.BLANK) {
				return "";
			} else
				return String.valueOf(cell.getBooleanCellValue());
		} catch(Exception e) {
			e.printStackTrace();
			return "Data Not Found";
		}
	}
		
	//To check if element is in viewpoint using Java Script and returning true or  false
	public  boolean isVisibleInViewpoint(WebDriver driver, WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		return (Boolean) executor.executeScript(
				"var elem = arguments[0],                 " +
			     "  box = elem.getBoundingClientRect(),    " +
			     "  cx = box.left + box.width / 2,         " +
			     "  cy = box.top + box.height / 2,         " +
			     "  e = document.elementFromPoint(cx, cy); " +
			     "for (; e; e = e.parentElement) {         " +
			     "  if (e === elem)                        " +
			     "    return true;                         " +
			     "}                                        " +
			     "return false;                            "
			     , element);
	}
		
	
	//Get Month Name (January, August)
	public String getMonthName(int mon) {
		String monthName = Month.of(mon).name();
		return monthName;
	}
		
	//Get cuttent time and add hous
	public int addHoursToCurrentTime() {
		int hour;
		Calendar now = Calendar.getInstance();
		now.add(Calendar.HOUR,1);
		String var = now.get(Calendar.HOUR_OF_DAY)+ ":"+ now.get(Calendar.MINUTE)+ ":" + now.get(Calendar.SECOND);
		System.out.println("var  "+var);
		int h1 = (int)var.charAt(0) - '0'; 
		int h2 = (int)var.charAt(1)- '0'; 
		int hh = h1 * 10 + h2; 
		hh %= 12; 
		// Handle 00 and 12 case separately 
		if (hh == 0) {
			hour = hh;
		}else {
			hour=hh;
		}
		return hour;
	}
		
	//Get CurrentDate
	public String getCurrentDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		LocalDateTime now = LocalDateTime.now();  
		return dtf.format(now);
	}
public static void takeScreenshot(WebDriver driver) {
	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

	try {
	FileUtils.copyFile(scrFile, new File(USER_DIRECTORY+"//ScreenShot//Screenshot_"+System.currentTimeMillis()+".png"));
	} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	}
	
}

}
