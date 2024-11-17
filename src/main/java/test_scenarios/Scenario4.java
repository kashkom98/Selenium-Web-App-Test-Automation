package test_scenarios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import utils.CaptureScreenshot;
import utils.LoginOperation;

public class Scenario4 {


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
		XSSFCell libraryUrlCell = row.getCell(6);
		String libraryUrl = libraryUrlCell.toString();
		XSSFCell dataSetCell = row.getCell(12);
		String dataSet = dataSetCell.toString();
		test.pass("Data read from Excel file successfully");


		try {
			 
			
			
			// Navigate to the One Search Library URL specified in the properties file
			test.info("Navigating to the One Search Library URL");
			driver.get(libraryUrl);
			test.pass("Successfully navigated to the One Search Library URL");
			Thread.sleep(5000);
			
			driver.findElement(By.cssSelector("span[ng-if='!$ctrl.isSignedIn()'][translate='eshelf.signin.title']")).click();;
			 
			 LoginOperation performLogin = new LoginOperation();
				performLogin.loginLibrary(driver, reports, test, "Scenario4");
			
			
			
			String expectedTitle = "SOS";
			String actualTitle = driver.getTitle();
			try {
				Assert.assertEquals(actualTitle, expectedTitle, "Title does not match");
				test.pass("Title matched. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
			} catch (AssertionError e) {
				test.fail("Title mismatch. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
				throw e;
			}

			// Initialize WebDriverWait to handle dynamic element interactions
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));

			// Click on the link to access the digital repository service
			test.info("Clicking on the Digital Repository Service link");
			wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//a[@aria-label='digital repository service, opens in a new window']")));
			CaptureScreenshot.takeScreenshot(driver, "Scenario4", "BeforeDigitalRepositoryService");
			Thread.sleep(2000);
			driver.findElement(By.xpath("//a[@aria-label='digital repository service, opens in a new window']"))
					.click();
			test.pass("Clicked on the Digital Repository Service link");

			// Switch to the new window that opens after clicking the link
			test.info("Switching to the new window");
			String originalWindow = driver.getWindowHandle();
			for (String windowHandle : driver.getWindowHandles()) {
				if (!originalWindow.contentEquals(windowHandle)) {
					driver.switchTo().window(windowHandle);
					test.pass("Switched to the new window");
					driver.switchTo().window(originalWindow).close();
					driver.switchTo().window(windowHandle);
					break;
				}
			}
			Thread.sleep(2000);

			// Navigate to the Datasets section
			test.info("Navigating to the Datasets section");
			wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Datasets")));
			expectedTitle = "DRS";
			actualTitle = driver.getTitle();
			try {
				Assert.assertEquals(actualTitle, expectedTitle, "Title does not match");
				test.pass("Title matched. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
			} catch (AssertionError e) {
				test.fail("Title mismatch. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
				throw e;
			}

			CaptureScreenshot.takeScreenshot(driver, "Scenario4", "AfterDigitalRepositoryService");
			Thread.sleep(2000);
			driver.findElement(By.linkText("Datasets")).click();
			test.pass("Navigated to the Datasets section");
			Thread.sleep(2000);

			// Perform a search for specific datasets as per the property file
			test.info("Performing a search for datasets");
			WebElement search = driver.findElement(By.cssSelector("input[placeholder='Search this featured content']"));
			CaptureScreenshot.takeScreenshot(driver, "Scenario4", "AfterClickingDatasets");
			Thread.sleep(2000);
			search.sendKeys(dataSet);
			test.pass("Search performed for datasets");
			Thread.sleep(5000);

			// Click on the search submit button
			test.info("Clicking on the search submit button");
			wait.until(ExpectedConditions
					.elementToBeClickable(By.cssSelector("button.btn.btn-info#search-submit-header")));
			List<WebElement> buttons = driver.findElements(By.cssSelector("button.btn.btn-info#search-submit-header"));
			if (!buttons.isEmpty()) {
				CaptureScreenshot.takeScreenshot(driver, "Scenario4", "BeforeClickingSearchforCSV");
				Thread.sleep(2000);
				buttons.get(1).click();
				test.pass("Clicked on the search submit button");
			} else {
				System.out.println("No buttons found");
				test.fail("No search submit button found");
			}
			Thread.sleep(2000);

			// Download the dataset by clicking on the Zip File link
			test.info("Downloading the dataset");
			wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Zip File")));
			CaptureScreenshot.takeScreenshot(driver, "Scenario4", "AfterClickingSearchforCSV");
			Thread.sleep(2000);
			driver.findElement(By.linkText("Zip File")).click();
			test.pass("Dataset downloaded successfully");
			Thread.sleep(15000);

			// Mark the scenario as passed in the Extent Report
			test.pass("Passed Test Scenario 4 Successfully");
			System.out.println("Scenario: 4 Completed Successfully");
		} catch (Exception e) {
			// Mark the scenario as failed in the Extent Report
			test.fail("Test failed with exception: " + e.getMessage());
			throw e;
		}
	}

}
