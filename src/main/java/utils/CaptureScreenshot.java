package utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class CaptureScreenshot {

	public static void takeScreenshot(WebDriver driver, String scenarioName, String stepName) throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String filePath = System.getProperty("user.dir") + "/screenshots/" + scenarioName + "/" + stepName + ".png";
		FileUtils.copyFile(scrFile, new File(filePath));
	}
}
