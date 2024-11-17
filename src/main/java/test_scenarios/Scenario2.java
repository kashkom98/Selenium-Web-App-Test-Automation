package test_scenarios;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.io.File;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import utils.CaptureScreenshot;
import utils.LoginOperation;

public class Scenario2 {

	public void runScenario(WebDriver driver, ExtentReports reports, ExtentTest test) throws Exception {

		String excelPath = System.getProperty("user.dir") + "/data.xlsx";

		// Read data from the excel file
		test.info("Reading data from Excel file");
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
//		XSSFCell neuUserCell = row.getCell(4);
//		String neuUser = neuUserCell.toString();
//		XSSFCell encodedPasswordCell = row.getCell(1);
//		String password = encodedPasswordCell.toString();
//		test.pass("Data read from Excel file successfully");
//
//		byte[] decodedPassword = Base64.decodeBase64(password);
//		String neuPassword = new String(decodedPassword);
//		Thread.sleep(2000);
		
		

		// Initialize WebDriverWait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		try {
			
			LoginOperation logincanvas = new LoginOperation();
			logincanvas.canvasLogin(driver, reports, test, "Scenario2");
			
			// Navigate to the specified URL
//			test.info("Navigating to the Canvas URL");
//			XSSFCell canvasUrlCell = row.getCell(7);
//			String canvasUrl = canvasUrlCell.toString();
//			driver.get(canvasUrl);
//			test.pass("Successfully navigated to the Canvas URL");
//			Thread.sleep(2000);
			CaptureScreenshot.takeScreenshot(driver, "Scenario2", "Before_Choosing_Calendar");
			Thread.sleep(2000);
			wait.until(ExpectedConditions.elementToBeClickable(By.id("global_nav_calendar_link")));
			String expectedTitle = "Dashboard";
			String actualTitle = driver.getTitle();
			try {
				Assert.assertEquals(actualTitle, expectedTitle, "Title does not match");
				test.pass("Title matched. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
			} catch (AssertionError e) {
				test.fail("Title mismatch. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
				throw e;
			}

			WebElement calenderButton = driver.findElement(By.id("global_nav_calendar_link"));
			calenderButton.click();
			Thread.sleep(2000);

			wait.until(ExpectedConditions.elementToBeClickable(By.id("create_new_event_link")));
			expectedTitle = "Calendar";
			actualTitle = driver.getTitle();
			try {
				Assert.assertEquals(actualTitle, expectedTitle, "Title does not match");
				test.pass("Title matched. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
			} catch (AssertionError e) {
				test.fail("Title mismatch. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
				throw e;
			}

			CaptureScreenshot.takeScreenshot(driver, "Scenario2", "AfterClickingCalendar");
			Thread.sleep(2000);
			WebElement addButton = driver.findElement(By.id("create_new_event_link"));
			addButton.click();
			Thread.sleep(2000);

			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[a[text()='My To Do']]")));
			CaptureScreenshot.takeScreenshot(driver, "Scenario2", "AfterClickingToDo");
			Thread.sleep(2000);
			WebElement liElement = driver.findElement(By.xpath("//li[a[text()='My To Do']]"));
			liElement.click();
			Thread.sleep(2000);
			CaptureScreenshot.takeScreenshot(driver, "Scenario2", "BeforeTask1Details");
			Thread.sleep(2000);

			XSSFCell title = row.getCell(8);
			String Title = title.toString();
			WebElement titleField = driver.findElement(By.id("planner_note_title"));
			titleField.sendKeys(Title);
			Thread.sleep(2000);

			XSSFCell date = row.getCell(9);
			String Date = date.toString();
			WebElement DateField = driver.findElement(By.id("planner_note_date"));
			DateField.clear();
			Thread.sleep(2000);
			DateField.sendKeys(Date);
			Thread.sleep(2000);

			XSSFCell time = row.getCell(10);
			String Time = time.toString();
			WebElement TimeField = driver.findElement(By.id("planner_note_time"));
			TimeField.sendKeys(Time);
			Thread.sleep(2000);

			XSSFCell details = row.getCell(11);
			String Details = details.toString();
			WebElement DetailsField = driver.findElement(By.id("details_textarea"));
			DetailsField.sendKeys(Details);
			Thread.sleep(2000);
			CaptureScreenshot.takeScreenshot(driver, "Scenario2", "AfterTask1Details");
			Thread.sleep(2000);

			wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//*[@id=\"edit_planner_note_form_holder\"]/form/div[2]/button")))
					.click();
			Thread.sleep(2000);

			WebElement addButton2 = driver.findElement(By.id("create_new_event_link"));
			addButton2.click();
			Thread.sleep(2000);

			WebElement liElement2 = driver.findElement(By.xpath("//li[a[text()='My To Do']]"));
			liElement2.click();
			Thread.sleep(2000);
			CaptureScreenshot.takeScreenshot(driver, "Scenario2", "BeforeTask2Details");
			Thread.sleep(2000);

			XSSFRow row2 = sheet.getRow(2);
			XSSFCell title2 = row2.getCell(8);
			String Title2 = title2.toString();
			WebElement titleField2 = driver.findElement(By.id("planner_note_title"));
			titleField2.sendKeys(Title2);
			Thread.sleep(2000);

			XSSFCell date2 = row2.getCell(9);
			String Date2 = date2.toString();
			WebElement DateField2 = driver.findElement(By.id("planner_note_date"));
			DateField2.clear();
			Thread.sleep(2000);
			DateField2.sendKeys(Date2);
			Thread.sleep(2000);

			XSSFCell time2 = row2.getCell(10);
			String Time2 = time2.toString();
			WebElement TimeField2 = driver.findElement(By.id("planner_note_time"));
			TimeField2.sendKeys(Time2);
			Thread.sleep(2000);

			XSSFCell details2 = row2.getCell(11);
			String Details2 = details2.toString();
			Thread.sleep(2000);
			WebElement DetailsField2 = driver.findElement(By.id("details_textarea"));
			DetailsField2.sendKeys(Details2);
			Thread.sleep(2000);
			CaptureScreenshot.takeScreenshot(driver, "Scenario2", "AfterTask2Details");
			Thread.sleep(2000);

			wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//*[@id=\"edit_planner_note_form_holder\"]/form/div[2]/button")))
					.click();
			Thread.sleep(5000);

			// Close the Excel workbook to release resources
			test.info("Closing the Excel workbook");
			myworkbook.close();
			test.pass("Excel workbook closed successfully");

			// Mark the test as passed in the report
			test.pass("Passed Test Scenario 2 Successfully");
			System.out.println("Scenario: 2 Completed Successfully");
		} catch (Exception e) {
			// Mark the scenario as failed in the Extent Report
			test.fail("Test failed with exception: " + e.getMessage());
			throw e;
		}
	}

}
