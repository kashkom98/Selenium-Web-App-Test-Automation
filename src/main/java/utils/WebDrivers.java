package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDrivers {

	public static WebDriver initializeBrowser(WebDriver driver) throws IOException {
		
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
		XSSFCell browserNameCell = row.getCell(0);
		String browserName = browserNameCell.toString();

		System.out.println("Browser from excel: " + browserName);

		try {
			// Initialize ChromeDriver if 'chrome' is specified
			if (browserName.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				ChromeOptions options = new ChromeOptions();
				// Disable session storage and SSO-related features using Chrome DevTools Protocol
		        options.setExperimentalOption("excludeSwitches", new String[]{"enable-logging"});
		        options.setExperimentalOption("useAutomationExtension", false);
		        options.setExperimentalOption("prefs", new String[]{
		                "profile.default_content_settings.cookies", "1",
		                "profile.cookie_manager.block_third_party_cookies", "true",
		                "profile.managed_default_content_settings.cookies", "1"
		        });

		        // Disable cache
		        options.addArguments("--disable-cache");

		        // Disable cookies
		        options.addArguments("--disable-cookies");
		        
		        
				String projectPath = System.getProperty("user.dir");

				//Configuring preferences for Chrome browser
				HashMap<String, Object> chromePreferences = new HashMap<String, Object>();
				chromePreferences.put("download.default_directory", projectPath);
				chromePreferences.put("plugins.always_open_pdf_externally", true);

				options.setExperimentalOption("prefs", chromePreferences);

				driver = new ChromeDriver(options);
				System.out.println("Chrome Driver initialized");
			}

			// Initialize FirefoxDriver if 'firefox' is specified
			else if (browserName.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				System.out.println("Firefox Driver initialized");
			}

			// Initialize InternetExplorerDriver if 'ie' is specified
			else if (browserName.equalsIgnoreCase("ie")) {
				WebDriverManager.iedriver().setup();
				driver = new InternetExplorerDriver();
				System.out.println("IE Driver initialized");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Configure WebDriver options
		if (driver != null) {
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} else {
			System.out.println("Driver was not initialized");
		}

		return driver;
	}

}
