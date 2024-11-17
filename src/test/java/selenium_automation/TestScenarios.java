package selenium_automation;

import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import test_scenarios.Scenario1;
import test_scenarios.Scenario2;
import test_scenarios.Scenario3;
import test_scenarios.Scenario4;
import test_scenarios.Scenario5;
import utils.WebDrivers;

public class TestScenarios {

	WebDriver driver = null;
	ExtentHtmlReporter reporter;
	ExtentReports reports;
	ExtentTest testScenario1;
	ExtentTest testScenario2;
	ExtentTest testScenario3;
	ExtentTest testScenario4;
	ExtentTest testScenario5;

	@BeforeSuite(enabled = true)
	public void beforeSuite() throws Exception {
		// Setting up the Extent Report for logging test results
		reporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test_report/testReport.html");
		reports = new ExtentReports();
		reports.attachReporter(reporter);

		// Setting up the theme and information layout of the test report
		reporter.config().setChartVisibilityOnOpen(true);
		reporter.config().setDocumentTitle("Selenium Test Report - Group 3");
		reporter.config().setReportName("Selenium Test Automation Report");
		reporter.config().setTestViewChartLocation(ChartLocation.TOP);
		reporter.config().setTheme(Theme.STANDARD);
		reporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
		if (driver == null) {
			// Initializing the WebDriver
			driver = WebDrivers.initializeBrowser(driver);
		}
	}

	// Executed before each test method and test class
	@BeforeMethod(enabled = true)
	@BeforeTest(enabled = true)
	public void beforeTest() throws Exception {
	}

	// Tests for 5 scenarios

	
	//Executing test scenario 1
	@Test(priority = 0)
	public void Scenario1Test() throws IOException {
		
		try {
			//Generate Scenario 1 test report
			testScenario1 = reports.createTest("Scenario 1: Download the latest transcript");

			Scenario1 scenario1 = new Scenario1();
			scenario1.runScenario(driver, reports, testScenario1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Executing test scenario 2
	@Test(priority = 1)
	public void Scenario2Test() throws IOException {
		try {
			//Generate Scenario 2 test report
			testScenario2 = reports.createTest("Scenario 2: Add two To-Do tasks for yourself");
			Scenario2 scenario2 = new Scenario2();
			scenario2.runScenario(driver, reports, testScenario2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Executing test scenario 3
	@Test(priority = 2)
	public void Scenario3Test() throws IOException {
		try {
			//Generate Scenario 3 test report
			testScenario3 = reports.createTest("Scenario 3: Download a classroom guide");
			Scenario3 scenario3 = new Scenario3();
			scenario3.runScenario(driver, reports, testScenario3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Executing test scenario 4
	@Test(priority = 3)
	public void Scenario4Test() throws IOException {
		try {
			//Generate Scenario 4 test report
			testScenario4 = reports.createTest("Scenario 4: Download a DATASET");
			Scenario4 scenario4 = new Scenario4();
			scenario4.runScenario(driver, reports, testScenario4);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//Executing test scenario 5
	@Test(priority = 4)
	public void Scenario5Test() throws IOException {
		try {
			//Generate Scenario 5 test report
			testScenario5 = reports.createTest("Scenario 5: Update the Academic calendar");
			Scenario5 scenario5 = new Scenario5();
			scenario5.runScenario(driver, reports, testScenario5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterMethod(enabled = true)
	    public void afterTest() throws IOException {
	        if (driver != null) {
	            driver.quit(); // Close the current WebDriver window
	            // Re-initialize the WebDriver to open a new window for the next test
	            driver = WebDrivers.initializeBrowser(driver);
	        }
	}
	
	@AfterSuite(enabled = true)
	public void afterSuite() {
	    // Closing the WebDriver and releasing resources
	    if (driver != null) {
	        driver.quit();
	    }
	    driver = null;

	    // Finalizing the test report
	    reports.flush();
	}


}
