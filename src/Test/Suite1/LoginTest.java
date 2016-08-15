package Test.Suite1;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;

import Test.TestBase;
import TestUtils.utils;

//step 1
@RunWith(Parameterized.class)
public class LoginTest extends TestBase{

	// step 2
	String email;
	String password;
	String firstname;
	String positive;

	
	// step 3
	public LoginTest(String email,String password,String firstname,String positive) {
		this.email=email;
		this.password=password;
		this.firstname=firstname;
		this.positive=positive;
	}
	
	// step 4
	@Parameters
	public static Collection<Object[]> getData() {
		Object[][] data=utils.getData("Login");
		return Arrays.asList(data);
	}
	
	@BeforeClass
	public static void Inti() throws IOException {
		InitializeResources();
		System.out.println("Executing BeforeClass .....");		
		dr = new FirefoxDriver();
		driver = new EventFiringWebDriver(dr);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	}
	
	
	@Before
	public void setBrowser() {
		System.out.println("Executing Before .....");
		driver.get("http://cart.interiorsinn.com");
	}
	
	@AfterClass
	public static void closeBrowser() {
		// close browser
		driver.quit();

	}
	
	@Test
	public void TestLoginForm() throws IOException {
		
		if(!utils.IsSkipped("Login")) {
			// report test case skipped
			Assume.assumeTrue(false);
		}

		String result = utils.isLoggin(email,password,firstname,positive);
		String FileName="login_"+email;
		if(!result.equals("Pass")) {
			// take screenshot
			utils.takeScreenshot(FileName);

			// report error
			Assert.assertTrue(false, result);
			
			
		}
		
		
	}

}
