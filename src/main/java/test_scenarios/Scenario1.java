package test_scenarios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Pdf;
import org.openqa.selenium.PrintsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import utils.LoginOperation;
import utils.CaptureScreenshot;

public class Scenario1 {


	public void runScenario(WebDriver driver, ExtentReports reports, ExtentTest test) throws Exception {
		
		
		String excelPath = System.getProperty("user.dir") + "/data.xlsx";

		test.info("Importing and reading data from Excel file");
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
		// Importing data from the excel sheet
		XSSFSheet sheet = myworkbook.getSheetAt(0);
		XSSFRow row = sheet.getRow(1);
		XSSFCell neuUserCell = row.getCell(4);
		String neuUser = neuUserCell.toString();
		XSSFCell encodedPasswordCell = row.getCell(3);
		String password = encodedPasswordCell.toString();
		test.pass("Data read from Excel file successfully");

		byte[] decodedPassword = Base64.decodeBase64(password);
		String neuPassword = new String(decodedPassword);
		Thread.sleep(2000);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		try {
			
	        
			
			LoginOperation performLogin = new LoginOperation();
			performLogin.login(driver, reports, test, "Scenario1");

			CaptureScreenshot.takeScreenshot(driver, "Scenario1", "Before_Switching_to_Resources_tab");
			Thread.sleep(2000);
			test.info("Heading to 'Resources' tab");
			driver.findElement(By.linkText("Resources")).click();
			test.pass("Now we are in 'Resources' tab section");

			test.info("Clicking on Academics, Classes & Registration category icon");
			CaptureScreenshot.takeScreenshot(driver, "Scenario1", "Before_Selecting_Academics_Classes_Registration_category");
			Thread.sleep(2000);
			driver.findElement(By.cssSelector("button[data-category='academics,-classes-&-registration']")).click();

			Thread.sleep(2000);
			CaptureScreenshot.takeScreenshot(driver, "Scenario1", "After_Selecting_Academics_Classes_Registration");
			Thread.sleep(2000);
			test.pass("Successfully selected Academics, Classes & Registration category");

			test.info("Selecting 'My Transcript'");
			CaptureScreenshot.takeScreenshot(driver, "Scenario1", "Before_Selecting_MyTranscript");
			Thread.sleep(2000);
			driver.findElement(By.linkText("My Transcript")).click();
			test.pass("'My Transcript' selected");

			// Switching window and logging
			test.info("Opening a new window");
			String originalWindow = driver.getWindowHandle();
			for (String windowHandle : driver.getWindowHandles()) {
				if (!originalWindow.contentEquals(windowHandle)) {
					driver.switchTo().window(windowHandle);
					test.pass("Successfully opened and switched to new window");
					 driver.switchTo().window(originalWindow).close();
					 driver.switchTo().window(windowHandle);
					break;
				}
			}

			test.info("Entering NEU credentials to login again!");
			Thread.sleep(5000);

			Thread.sleep(2000);
			CaptureScreenshot.takeScreenshot(driver, "Scenario1", "Before_Login");
			Thread.sleep(2000);
			WebElement user = driver.findElement(By.name("j_username"));
			user.sendKeys(neuUser);
			WebElement pass = driver.findElement(By.name("j_password"));
			pass.sendKeys(neuPassword);
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

			wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='submit']")));
			String expectedTitle = "Academic Transcript Options";
			String actualTitle = driver.getTitle();
			try {
				Assert.assertEquals(actualTitle, expectedTitle, "Title does not match");
				test.pass("Successfully matched heading!. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
			} catch (AssertionError e) {
				test.fail("Title mismatch. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
				throw e;
			}
			CaptureScreenshot.takeScreenshot(driver, "Scenario1", "Before_Choosing_Graduate_Option");
			Thread.sleep(2000);
			// Selecting options from dropdown
			test.info("Selecting 'Graduate' from dropdown");
			WebElement dropdownElement1 = driver.findElement(By.id("levl_id"));
			Select dropdown1 = new Select(dropdownElement1);
			dropdown1.selectByVisibleText("Graduate");
			Thread.sleep(3000);
			CaptureScreenshot.takeScreenshot(driver, "Scenario1", "After_Choosing_Graduate_Option");
			Thread.sleep(2000);
			driver.findElement(By.cssSelector("input[type='submit'][value='Submit']")).click();
			test.pass("Selected Transcript level as 'Graduate' and Transcript type as 'Audit Transcript'");
			expectedTitle = "Academic Transcript";
			actualTitle = driver.getTitle();
			try {
				Assert.assertEquals(actualTitle, expectedTitle, "Title does not match");
				test.pass("Title matched. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
			} catch (AssertionError e) {
				test.fail("Title mismatch. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
				throw e;
			}
			// Printing to PDF
			test.info("Academic Audit Transcript to be saved as a PDF");
			Pdf pdf = ((PrintsPage) driver).print(new PrintOptions());
			Files.write(Paths.get("./transcript.pdf"), OutputType.BYTES.convertFromBase64Png(pdf.getContent()));
			test.pass("Transcript downloaded as PDF");
			Thread.sleep(3000);
			// Mark the test as passed in the report
			test.pass("Passed Test Scenario 1 Successfully");
			System.out.println("Scenario: 1 Completed Successfully");
			
			driver.manage().deleteAllCookies();
			
			
			
		} catch (Exception e) {
			// Mark the scenario as failed in the Extent Report
			test.fail("Test failed with exception: " + e.getMessage());
			throw e;
		}
	}
}
