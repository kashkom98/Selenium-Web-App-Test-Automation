package test_scenarios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import utils.CaptureScreenshot;
import utils.LoginOperation;

public class Scenario3 {


	public void runScenario(WebDriver driver, ExtentReports reports, ExtentTest test) throws Exception {
		
		
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
		XSSFCell classRoomGuideUrlCell = row.getCell(5);

		String classRoomGuideUrl = classRoomGuideUrlCell.toString();

		// Log and navigate to the specified URL
		test.info("Navigating to the Classroom Guide URL");
		driver.get(classRoomGuideUrl);

		// Initialize WebDriverWait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));

		try {
			
			driver.findElement(By.linkText("Log in")).click();
			
			LoginOperation performLogin = new LoginOperation();
			performLogin.loginClassroomGuide(driver, reports, test, "Scenario3");
			
			
			String expectedTitle = "Classroom Status - Northeastern Tech Service Portal";
			String actualTitle = driver.getTitle();
			try {
				Assert.assertEquals(actualTitle, expectedTitle, "Title does not match");
				test.pass("Title matched. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
			} catch (AssertionError e) {
				test.fail("Title mismatch. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
				throw e;
			}
			
			

			// Wait for a specific element to be clickable and then click it
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0, 4000)", "");
			Thread.sleep(5000);
			test.info("Waiting for Classroom Details link and clicking");
			wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//a[@href='/tech?id=classroom_details&classroom=9ac92fb397291d905a68bd8c1253afd0']")));
			CaptureScreenshot.takeScreenshot(driver, "Scenario3", "BeforeClickingBehrakisClass");
			Thread.sleep(2000);
			driver.findElement(
					By.xpath("//a[@href='/tech?id=classroom_details&classroom=9ac92fb397291d905a68bd8c1253afd0']"))
					.click();
			test.pass("Clicked on Classroom Details link");
			Thread.sleep(5000);

			// Wait for another element (PDF link) and click it
			test.info("Waiting for PDF link and clicking");
			wait.until(
					ExpectedConditions.elementToBeClickable(By.cssSelector("a[href*='Hybrid_Nuflex_Classroom.pdf']")));
			CaptureScreenshot.takeScreenshot(driver, "Scenario3", "AfterClickingBehrakisClass");
			Thread.sleep(2000);
			driver.findElement(By.cssSelector("a[href*='Hybrid_Nuflex_Classroom.pdf']")).click();
			test.pass("Clicked on PDF link");
			Thread.sleep(5000);
			
			/*
			// Switch to the new window opened after clicking the PDF link
			test.info("Switching to new window");
			String originalWindow = driver.getWindowHandle();
			for (String windowHandle : driver.getWindowHandles()) {
				if (!originalWindow.contentEquals(windowHandle)) {
					driver.switchTo().window(windowHandle);
					test.pass("Switched to new window");
					break;
				}
			}
			*/
			Thread.sleep(10000);

			// Mark the test as passed in the report
			test.pass("Passed Test Scenario 3 Successfully");
			System.out.println("Scenario: 3 Completed Successfully");
		} catch (Exception e) {
			// Mark the scenario as failed in the Extent Report
			test.fail("Test failed with exception: " + e.getMessage());
			throw e;
		}
	}

}
