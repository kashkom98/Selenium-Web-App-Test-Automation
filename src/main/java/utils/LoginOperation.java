package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class LoginOperation extends WebDrivers {

	public void login(WebDriver driver, ExtentReports reports, ExtentTest test, String scenarioName)
			throws IOException, Exception {

		test.info("Logging into Student Hub!!!");

		// Navigate to the Northeastern University login URL from properties file
		test.info("Navigating to the NEU login page");
		String excelPath = System.getProperty("user.dir") + "/data.xlsx";
		FileInputStream inputstream = null;
		try {
			inputstream = new FileInputStream(new File(excelPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		XSSFWorkbook myworkbook = null;
		try {
			myworkbook = new XSSFWorkbook(inputstream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFSheet sheet = myworkbook.getSheetAt(0);
		XSSFRow row = sheet.getRow(1);
		XSSFCell neuUrlCell = row.getCell(1);

		// Getting data from the excel sheet
		String neu_url = neuUrlCell.toString();
		XSSFCell neuUsernameCell = row.getCell(2);
		String neuUsername = neuUsernameCell.toString();
		XSSFCell neuPasswordCell = row.getCell(3);
		String encodedPassword = neuPasswordCell.toString();
		byte[] neuPassword = Base64.decodeBase64(encodedPassword);
		String password = new String(neuPassword);
		
		driver.get(neu_url);
		test.pass("Successfully navigated to the NEU login page");
		Thread.sleep(2000);

		// Initialize WebDriverWait to handle dynamic elements on the page
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		CaptureScreenshot.takeScreenshot(driver, scenarioName, "BeforeIdPassword");
		Thread.sleep(2000);
		test.info("Let's wait for login button to be clickable");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
		// Assert and report the outcome
		String expectedTitle = "Sign in to your account";
		String actualTitle = driver.getTitle();
		try {
			Assert.assertEquals(actualTitle, expectedTitle, "Title does not match");
			test.pass("Title matched. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
		} catch (AssertionError e) {
			test.fail("Title mismatch. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
			throw e;
		}
		test.pass("Login button is clickable");

		test.info("NEU_USERNAME field being entered");
		WebElement email = driver.findElement(By.id("i0116"));
		email.sendKeys(neuUsername);
		driver.findElement(By.id("idSIButton9")).click();
		test.pass("The NEU_USERNAME is submitted");
		Thread.sleep(2000);

		// Wait until the password field is ready, decode the password and enter it
		test.info("Password field is loading up next");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));

		// Decode and enter the password
		test.info("Entering NEU_PASSWORD");
		WebElement pass = driver.findElement(By.id("i0118"));
		Thread.sleep(2000);
		CaptureScreenshot.takeScreenshot(driver, scenarioName, "AfterIdPassword");
		Thread.sleep(2000);

		// Enter the decoded password and click the submit button
		pass.sendKeys(password);
		driver.findElement(By.id("idSIButton9")).click();
		test.pass("NEU_PASSWORD is submitted");
		Thread.sleep(10000);

		// Handle 'trust browser' prompt if it appears
		test.info("Working on handling 'trust browser' prompt");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("trust-browser-button")));
		CaptureScreenshot.takeScreenshot(driver, scenarioName, "BeforeDuoAuthentication");
		Thread.sleep(2000);
		driver.findElement(By.id("trust-browser-button")).click();
		test.pass("Successfully handled 'trust browser' prompt");
		Thread.sleep(5000);
		CaptureScreenshot.takeScreenshot(driver, scenarioName, "AfterDuoAuthentication");
		Thread.sleep(2000);

		// Click the 'back' button if it appears
		test.info("Clicking the 'back' button");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idBtn_Back")));
		driver.findElement(By.id("idBtn_Back")).click();
		test.pass("Clicked the 'back' button");
		Thread.sleep(10000);

		expectedTitle = "Student Hub - Home";
		actualTitle = driver.getTitle();
		try {
			Assert.assertEquals(actualTitle, expectedTitle, "Title does not match");
			test.pass("Page Title is same! Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
		} catch (AssertionError e) {
			test.fail("Page title is different! Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
			throw e;
		}

		test.pass("Successfully logged in!!");
		Thread.sleep(5000);
	}
	
	
	public void canvasLogin(WebDriver driver, ExtentReports reports, ExtentTest test, String scenarioName)
			throws IOException, Exception {

		test.info("Logging in to Canvas!!!");

		// Navigate to the Northeastern University login URL from properties file
		test.info("Navigating to the NEU login page");
		String excelPath = System.getProperty("user.dir") + "/data.xlsx";
		FileInputStream inputstream = null;
		try {
			inputstream = new FileInputStream(new File(excelPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		XSSFWorkbook myworkbook = null;
		try {
			myworkbook = new XSSFWorkbook(inputstream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFSheet sheet = myworkbook.getSheetAt(0);
		XSSFRow row = sheet.getRow(1);

		// Getting data from the excel sheet
		XSSFCell neuUsernameCell = row.getCell(2);
		String neuUsername = neuUsernameCell.toString();
		XSSFCell neuPasswordCell = row.getCell(3);
		String encodedPassword = neuPasswordCell.toString();
		byte[] neuPassword = Base64.decodeBase64(encodedPassword);
		String password = new String(neuPassword);
        
		
		// Navigate to the specified URL
		test.info("Navigating to the Canvas URL");
		XSSFCell canvasUrlCell = row.getCell(7);
		String canvasUrl = canvasUrlCell.toString();
		driver.get(canvasUrl);
		test.pass("Successfully navigated to the Canvas URL");
		Thread.sleep(2000);
		

		// Initialize WebDriverWait to handle dynamic elements on the page
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		CaptureScreenshot.takeScreenshot(driver, scenarioName, "BeforeIdPassword");
		Thread.sleep(2000);
		test.info("Let's wait for login button to be clickable");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
		// Assert and report the outcome
		String expectedTitle = "Sign in to your account";
		String actualTitle = driver.getTitle();
		try {
			Assert.assertEquals(actualTitle, expectedTitle, "Title does not match");
			test.pass("Title matched. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
		} catch (AssertionError e) {
			test.fail("Title mismatch. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
			throw e;
		}
		test.pass("Login button is clickable");

		test.info("NEU_USERNAME field being entered");
		WebElement email = driver.findElement(By.id("i0116"));
		email.sendKeys(neuUsername);
		driver.findElement(By.id("idSIButton9")).click();
		test.pass("The NEU_USERNAME is submitted");
		Thread.sleep(2000);

		// Wait until the password field is ready, decode the password and enter it
		test.info("Password field is loading up next");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));

		// Decode and enter the password
		test.info("Entering NEU_PASSWORD");
		WebElement pass = driver.findElement(By.id("i0118"));
		Thread.sleep(2000);
		CaptureScreenshot.takeScreenshot(driver, scenarioName, "AfterIdPassword");
		Thread.sleep(2000);

		// Enter the decoded password and click the submit button
		pass.sendKeys(password);
		driver.findElement(By.id("idSIButton9")).click();
		test.pass("NEU_PASSWORD is submitted");
		Thread.sleep(10000);

		// Handle 'trust browser' prompt if it appears
		test.info("Working on handling 'trust browser' prompt");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("trust-browser-button")));
		CaptureScreenshot.takeScreenshot(driver, scenarioName, "BeforeDuoAuthentication");
		Thread.sleep(2000);
		driver.findElement(By.id("trust-browser-button")).click();
		test.pass("Successfully handled 'trust browser' prompt");
		Thread.sleep(5000);
		CaptureScreenshot.takeScreenshot(driver, scenarioName, "AfterDuoAuthentication");
		Thread.sleep(2000);

		//Click the 'back' button if it appears
		test.info("Clicking the 'back' button");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idBtn_Back")));
		driver.findElement(By.id("idBtn_Back")).click();
		test.pass("Clicked the 'back' button");
		Thread.sleep(10000);

		test.pass("Successfully logged in!!");
		Thread.sleep(5000);
	}
	
	public void loginClassroomGuide(WebDriver driver, ExtentReports reports, ExtentTest test, String scenarioName)
			throws IOException, Exception {

		test.info("Logging into Classroom Guide!!!");

		// Navigate to the Northeastern University login URL from properties file
		test.info("Navigating to the NEU login page");
		String excelPath = System.getProperty("user.dir") + "/data.xlsx";
		FileInputStream inputstream = null;
		try {
			inputstream = new FileInputStream(new File(excelPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		XSSFWorkbook myworkbook = null;
		try {
			myworkbook = new XSSFWorkbook(inputstream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFSheet sheet = myworkbook.getSheetAt(0);
		XSSFRow row = sheet.getRow(1);
	
		XSSFCell neuUsernameCell = row.getCell(2);
		String neuUsername = neuUsernameCell.toString();
		XSSFCell neuPasswordCell = row.getCell(3);
		String encodedPassword = neuPasswordCell.toString();
		byte[] neuPassword = Base64.decodeBase64(encodedPassword);
		String password = new String(neuPassword);
		
		test.pass("Successfully navigated to the NEU login page");
		Thread.sleep(2000);

		// Initialize WebDriverWait to handle dynamic elements on the page
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		CaptureScreenshot.takeScreenshot(driver, scenarioName, "BeforeIdPassword");
		Thread.sleep(2000);
		test.info("Let's wait for login button to be clickable");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
		// Assert and report the outcome
		String expectedTitle = "Sign in to your account";
		String actualTitle = driver.getTitle();
		try {
			Assert.assertEquals(actualTitle, expectedTitle, "Title does not match");
			test.pass("Title matched. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
		} catch (AssertionError e) {
			test.fail("Title mismatch. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
			throw e;
		}
		test.pass("Login button is clickable");

		test.info("NEU_USERNAME field being entered");
		WebElement email = driver.findElement(By.id("i0116"));
		email.sendKeys(neuUsername);
		driver.findElement(By.id("idSIButton9")).click();
		test.pass("The NEU_USERNAME is submitted");
		Thread.sleep(2000);

		// Wait until the password field is ready, decode the password and enter it
		test.info("Password field is loading up next");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));

		// Decode and enter the password
		test.info("Entering NEU_PASSWORD");
		WebElement pass = driver.findElement(By.id("i0118"));
		Thread.sleep(2000);
		CaptureScreenshot.takeScreenshot(driver, scenarioName, "AfterIdPassword");
		Thread.sleep(2000);

		// Enter the decoded password and click the submit button
		pass.sendKeys(password);
		driver.findElement(By.id("idSIButton9")).click();
		test.pass("NEU_PASSWORD is submitted");
		Thread.sleep(10000);

		// Handle 'trust browser' prompt if it appears
		test.info("Working on handling 'trust browser' prompt");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("trust-browser-button")));
		CaptureScreenshot.takeScreenshot(driver, scenarioName, "BeforeDuoAuthentication");
		Thread.sleep(2000);
		driver.findElement(By.id("trust-browser-button")).click();
		test.pass("Successfully handled 'trust browser' prompt");
		Thread.sleep(5000);
		CaptureScreenshot.takeScreenshot(driver, scenarioName, "AfterDuoAuthentication");
		Thread.sleep(2000);

		// Click the 'back' button if it appears
		test.info("Clicking the 'back' button");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idBtn_Back")));
		driver.findElement(By.id("idBtn_Back")).click();
		test.pass("Clicked the 'back' button");
		Thread.sleep(5000);

		test.pass("Successfully logged in!!");
		Thread.sleep(2000);
	}
	
	
	public void loginLibrary(WebDriver driver, ExtentReports reports, ExtentTest test, String scenarioName)
			throws IOException, Exception {

		test.info("Logging into Library!!!");

		// Navigate to the Northeastern University login URL from properties file
		test.info("Navigating to the NEU login page");
		String excelPath = System.getProperty("user.dir") + "/data.xlsx";
		FileInputStream inputstream = null;
		try {
			inputstream = new FileInputStream(new File(excelPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		XSSFWorkbook myworkbook = null;
		try {
			myworkbook = new XSSFWorkbook(inputstream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFSheet sheet = myworkbook.getSheetAt(0);
		XSSFRow row = sheet.getRow(1);
		
		XSSFCell neuUserCell = row.getCell(4);
		String neuUser = neuUserCell.toString();
		XSSFCell neuPasswordCell = row.getCell(3);
		String encodedPassword = neuPasswordCell.toString();
		byte[] neuPassword = Base64.decodeBase64(encodedPassword);
		String password = new String(neuPassword);
	
		
		test.pass("Successfully navigated to the NEU login page");
		Thread.sleep(2000);

		test.info("Entering NEU credentials to login again!");

		Thread.sleep(2000);
		CaptureScreenshot.takeScreenshot(driver, "Scenario1", "Before_Login");
		Thread.sleep(2000);
		WebElement user = driver.findElement(By.name("j_username"));
		user.sendKeys(neuUser);
		WebElement pass = driver.findElement(By.name("j_password"));
		pass.sendKeys(password);
		Thread.sleep(2000);
		CaptureScreenshot.takeScreenshot(driver, "Scenario1", "After_Login");
		Thread.sleep(2000);
		driver.findElement(By.name("_eventId_proceed")).click();

		driver.switchTo().frame("duo_iframe");
		CaptureScreenshot.takeScreenshot(driver, "Scenario1", "Before_Duo_Authentication");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[normalize-space(text())='Send Me a Push']")).click();
		Thread.sleep(10000);
		test.pass("Logged in successfully");

		test.pass("Successfully logged in!!");
		Thread.sleep(5000);
	}


}
