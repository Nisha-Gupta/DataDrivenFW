package Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import Data.Xls_Reader;

public class TestBase {

	public static WebDriver dr;
	public static EventFiringWebDriver driver;
	public static Properties OR;
	public static Properties appText;
	public static Xls_Reader reader = new Xls_Reader(System.getProperty("user.dir")+"//src//Data//Data.xlsx");
	public static boolean LoginFlag=false;
	public static void InitializeResources() throws IOException {

		OR = new Properties();
		File f = new File(System.getProperty("user.dir")+"//src//Config//OR.properties");
		//File f = new File("D://WS3//DataDrivenFW//src//Config//OR.properties");
		FileInputStream FI = new FileInputStream(f);
		OR.load(FI);

	
		appText = new Properties();
		f = new File(System.getProperty("user.dir")+"//src//Config//appText.properties");
		FI = new FileInputStream(f);
		appText.load(FI);

		
	}
	
		
	public static WebElement getWebElement(String locator) {
		
		String[] Type=locator.split("_");
		try {
			if(Type[0].equals("linktext")) {	
				return driver.findElement(By.linkText(OR.getProperty(locator)));
			}else if(Type[0].equals("partiallinktext")) {
				return driver.findElement(By.partialLinkText(OR.getProperty(locator)));
			}else if(Type[0].equals("name")) {
				return driver.findElement(By.name(OR.getProperty(locator)));
			}else if(Type[0].equals("xpath")) {
				return driver.findElement(By.xpath(OR.getProperty(locator)));
			}else if(Type[0].equals("css")) {
				return driver.findElement(By.cssSelector(OR.getProperty(locator)));
			}else if(Type[0].equals("id")) {
				return driver.findElement(By.id(OR.getProperty(locator)));
			}else if(Type[0].equals("classname")) {
				return driver.findElement(By.className(OR.getProperty(locator)));
			}	
		}catch(Exception e) {
				// take screenshot and send to report
				System.out.println(e.getMessage());
		}
		return driver.findElement(By.xpath("html"));
	}
	
}
