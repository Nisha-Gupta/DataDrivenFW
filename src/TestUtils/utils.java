package TestUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.testng.Assert;

import Test.TestBase;

public class utils extends TestBase{

	
	public static boolean IsSkipped(String Testcase) {
		
		int rowNum = reader.getCellRowNum("Suite1", "TestCase", Testcase);
		String runmode = reader.getCellData("Suite1", "RunMode", rowNum);
		if(runmode.equals("Y")) {
			return true;
		}
		
		return false;
	}
	
	public static Object[][] getData(String Testcase) {
		
		int rowCount = reader.getRowCount(Testcase);
		int colCount = reader.getColumnCount(Testcase);
		Object[][] data = new Object[rowCount-1][colCount];
		
		for(int i=2;i<=rowCount;i++) {
			for(int j=0;j<colCount;j++) {
				data[i-2][j]=reader.getCellData(Testcase, j, i);
			}		
		}
		return data;
	}
	
	public static void takeScreenshot(String filename) throws IOException {
		File OutputFile = driver.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(OutputFile, new File(System.getProperty("user.dir")+"//src//Report//Screenshot//"+filename+".jpg"));
	}
	
	public static void logOut() {
		if(LoginFlag) {
			getWebElement("xpath_logout").click();
		}
	}
	
	
	
	public static String isLoggin(String email, String password,String firstname,String positive) {
		getWebElement("xpath_login_loginform").click();
		getWebElement("xpath_login_email").sendKeys(email);
		getWebElement("xpath_login_password").sendKeys(password);
		getWebElement("xpath_login_button").click();

		String fname = getWebElement("xpath_login_verify_linktext").getText();
		

		try {
			String actualText = driver.findElement(By.xpath(OR.getProperty("xpath_login_verify_linktext"))).getText();
			String expectedText = firstname;
						
			if(!actualText.equals(expectedText)) {
				return "Failed - "+actualText+" not same as expected text "+expectedText;
			}
			
			if(positive.equals("N")) {
				return "Failed - Allowed login with negative data";
			}			
			
			
		}catch (Exception e) {
			// report error
			if(positive.equals("Y")) {
				return "Failed - Pasitive data not logged in";
			}			
		}
		LoginFlag=true;
		return "Pass";
	}
	
	
}
