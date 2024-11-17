package test_scenarios;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import utils.LoginOperation;
import utils.CaptureScreenshot;

public class Scenario5 {


	public void runScenario(WebDriver driver, ExtentReports reports, ExtentTest test) throws Exception {

		// Set up WebDriverWait for dynamic element handling
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		try {
			
			LoginOperation performLogin = new LoginOperation();
			performLogin.login(driver, reports, test, "Scenario5");
			
			// Navigate to and click on 'Resources'
			test.info("Navigating to 'Resources' tab");
			wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Resources")));
			CaptureScreenshot.takeScreenshot(driver, "Scenario5", "BeforeClickingResources");
			Thread.sleep(2000);
			driver.findElement(By.linkText("Resources")).click();
			test.pass("Now we are in 'Resources' tab section");

			// Click on a specific category icon
			test.info("Clicking on category icon");
			//wait.until(ExpectedConditions.elementToBeClickable(By.className("categoryIcon_5ebd5061")));
			CaptureScreenshot.takeScreenshot(driver, "Scenario5", "BeforeClickingAcademicsClassesRegistration");
			//driver.findElement(By.className("categoryIcon_5ebd5061")).click();
			driver.findElement(By.cssSelector("button[data-category='academics,-classes-&-registration']")).click();
			Thread.sleep(2000);
			CaptureScreenshot.takeScreenshot(driver, "Scenario5", "AfterClickingAcademicsClassesRegistration");
			Thread.sleep(2000);
			test.pass("Clicked on category icon");

			// Navigate to the 'Academic Calendar' section
			test.info("Navigating to 'Academic Calendar'");
			//wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Academic Calendar")));
			CaptureScreenshot.takeScreenshot(driver, "Scenario5", "BeforeClickingAcademicCalendar");
			Thread.sleep(2000);

			driver.findElement(By.linkText("Academic Calendar")).click();
			test.pass("Navigated to 'Academic Calendar'");

			// Switch to the new window that opens after clicking the link
			test.info("Switching to the new window");
			String originalWindow = driver.getWindowHandle();
			for (String windowHandle : driver.getWindowHandles()) {
				if (!originalWindow.contentEquals(windowHandle)) {
					driver.switchTo().window(windowHandle);
					test.pass("Switched to the new window");
					break;
				}
			}

			// Click on a specific link within the academic calendar
			test.info("Clicking on a specific link within the Academic Calendar");
			//wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href*='academic-calendar/']")));
			CaptureScreenshot.takeScreenshot(driver, "Scenario5", "BeforeClickingAcademicCalendarLink");
			Thread.sleep(2000);
			String expectedTitle = "Calendar - Office of the University Registrar at Northeastern University";
			String actualTitle = driver.getTitle();
			try {
				Assert.assertEquals(actualTitle, expectedTitle, "Title does not match");
				test.pass("Title matched. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
			} catch (AssertionError e) {
				test.fail("Title mismatch. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
				throw e;
			}
			driver.findElement(By.cssSelector("a[href*='academic-calendar/']")).click();
			test.pass("Clicked on the specific link");
			Thread.sleep(2000);
			CaptureScreenshot.takeScreenshot(driver, "Scenario5", "AfterClickingAcademicCalendarLink");
			Thread.sleep(2000);

			// Scroll and interact with elements within an iframe
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0, 800)", "");
			driver.switchTo().frame("trumba.spud.6.iframe");
			WebElement checkbox0 = wait.until(ExpectedConditions.elementToBeClickable(By.id("mixItem0")));
			WebElement checkbox1 = wait.until(ExpectedConditions.elementToBeClickable(By.id("mixItem2")));
			WebElement checkbox2 = wait.until(ExpectedConditions.elementToBeClickable(By.id("mixItem3")));
			WebElement checkbox3 = wait.until(ExpectedConditions.elementToBeClickable(By.id("mixItem4")));
			expectedTitle = "Academic Calendar - Office of the University Registrar at Northeastern University";
			actualTitle = driver.getTitle();
			try {
				Assert.assertEquals(actualTitle, expectedTitle, "Title does not match");
				test.pass("Title matched. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
			} catch (AssertionError e) {
				test.fail("Title mismatch. Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
				throw e;
			}
			CaptureScreenshot.takeScreenshot(driver, "Scenario5", "BeforeCheckingUnderGrad");
			Thread.sleep(2000);
			checkbox0.click();
			checkbox1.click();
			checkbox2.click();
			checkbox3.click();
			Thread.sleep(3000);
			CaptureScreenshot.takeScreenshot(driver, "Scenario5", "AfterCheckingUnderGrad");
			Thread.sleep(2000);
			driver.switchTo().defaultContent();
			driver.switchTo().frame("trumba.spud.2.iframe");
			test.info("Interacting with elements in iframes");

			// Scroll to a specific element and ensure its visibility
			js.executeScript("window.scrollBy(0, 2000)", "");
			WebElement AddtoMyCalendarButton = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[onclick*='Trumba.EA2.eventActionsMulti']")));
					//.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl04_ctl53_ctl00_buttonAtmc")));
			js.executeScript("arguments[0].scrollIntoView(true);", AddtoMyCalendarButton);
			//wait.until(ExpectedConditions.visibilityOf(calendarButton));
			CaptureScreenshot.takeScreenshot(driver, "Scenario5", "VisibleAddToCalendar");
			Thread.sleep(2000);
			test.pass("Scrolled to and ensured visibility of the calendar button");
			Thread.sleep(3000);

			// Mark the scenario as passed in the Extent Report
			test.pass("Passed Test Scenario 5 Successfully");
			System.out.println("Scenario: 5 Completed Successfully");
		} catch (Exception e) {
			// Mark the scenario as failed in the Extent Report
			test.fail("Test failed with exception: " + e.getMessage());
			throw e;
		}
	}

}
