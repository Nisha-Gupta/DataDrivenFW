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
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;

import Test.TestBase;
import TestUtils.utils;

//step 1
@RunWith(Parameterized.class)
public class AddWishList  extends TestBase{

	// step 2
	String email;
	String password;
	String firstname;
	String positive;
	String prod1;
	String prod2;
	
	// step 3
	public AddWishList(String email,String password,String firstname,String prod1,String prod2,String positive) {
		this.email=email;
		this.password=password;
		this.firstname=firstname;
		this.positive=positive;
		this.prod1=prod1;
		this.prod2=prod2;
	}
	
	// step 4
	@Parameters
	public static Collection<Object[]> getData() {
		Object[][] data=utils.getData("AddToWishList");
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
	public void AddProductsToWishList() throws IOException, InterruptedException {

		// skipping test
		if(!utils.IsSkipped("AddToWishList")) {
			// report test case skipped
			Assume.assumeTrue(false);
		}
		
		// logout application before logging
		utils.logOut();
		
		// login
		String result = utils.isLoggin(email,password,firstname,positive);
		String FileName="login_"+email;
		if(!result.equals("Pass")) {
			// take screenshot
			utils.takeScreenshot(FileName);
			// report error
			Assert.assertTrue(false, result);
		}
		
		
		// test case statements
		
		getWebElement("xpath_wishlist_product_link").click();
		JavascriptExecutor js = (JavascriptExecutor)driver;
		//js.executeScript("<script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js'></script>");
		js.executeScript("$('.s_actions').css('display','block');");
		
		driver.findElement(By.xpath("//*[@id='home']/div[1]/div[2]//a[contains(text(),'"+prod1+"')]/parent::h3/following-sibling::div/a[2]")).click();
		driver.findElement(By.xpath("//*[@id='home']/div[1]/div[2]//a[contains(text(),'"+prod2+"')]/parent::h3/following-sibling::div/a[2]")).click();
		
		Thread.sleep(5000L);
		
		getWebElement("xpath_wishlist_total").click();
		
		String product1 = driver.findElement(By.xpath("//*[@id='wishlist_form']/div/table/tbody/tr/td[3]/a[contains(text(),'"+prod1+"')]")).getText();
		String product2 = driver.findElement(By.xpath("//*[@id='wishlist_form']/div/table/tbody/tr/td[3]/a[contains(text(),'"+prod2+"')]")).getText();
		FileName = "wishlist_"+email;
		if(!product1.equals(prod1)) {
			// take screenshot
			utils.takeScreenshot(FileName);
			
			// report error
			Assert.assertTrue(false, "Actual text "+product1+" is not same as expected text "+prod1);
		}

		if(!product2.equals(prod2)) {
			// take screenshot
			utils.takeScreenshot(FileName);
			
			// report error
			Assert.assertTrue(false, "Actual text "+product2+" is not same as expected text "+prod2);
			
		}
		
		utils.logOut();
		
	}
	
}
