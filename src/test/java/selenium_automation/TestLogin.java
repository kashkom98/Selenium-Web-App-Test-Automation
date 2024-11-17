package selenium_automation;

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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import utils.WebDrivers;

public class TestLogin extends WebDrivers {

	WebDriver driver;

	@Test
	public void login() throws IOException, Exception {

		
		
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

		// Retrieve username, password, and other required data from the excel sheet
		String neu_url = neuUrlCell.toString();
		XSSFCell neuUsernameCell = row.getCell(2);
		String neuUsername = neuUsernameCell.toString();
		XSSFCell neuPasswordCell = row.getCell(3);
		String encodedPassword = neuPasswordCell.toString();
		byte[] neuPassword = Base64.decodeBase64(encodedPassword);
		String password = new String(neuPassword);
		
		
		
		driver.get(neu_url);
		Thread.sleep(2000);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));

		WebElement email = driver.findElement(By.id("i0116"));
		email.sendKeys(neuUsername);
		driver.findElement(By.id("idSIButton9")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));

		WebElement pass = driver.findElement(By.id("i0118"));
		pass.sendKeys(password);
		driver.findElement(By.id("idSIButton9")).click();
		Thread.sleep(10000);

		wait.until(ExpectedConditions.elementToBeClickable(By.id("trust-browser-button")));
		driver.findElement(By.id("trust-browser-button")).click();
		Thread.sleep(5000);

		wait.until(ExpectedConditions.elementToBeClickable(By.id("idBtn_Back")));
		driver.findElement(By.id("idBtn_Back")).click();
		Thread.sleep(10000);

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Close popup modal']")));
		driver.findElement(By.xpath("//button[@aria-label='Close popup modal']")).click();
	}

}
