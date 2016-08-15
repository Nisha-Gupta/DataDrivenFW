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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;

import Test.TestBase;
import TestUtils.utils;
//step 1
@RunWith(Parameterized.class)
public class RegistrationTest extends TestBase{
	
	// step 2
	String firstname;
	String lastname;
	String email;
	String telephone;
	String addr1;
	String city;
	String postcode;
	String country;
	String state;
	String password;
	String confpassword;
	String positive;
	
	// step 3
	public RegistrationTest(String firstname,String lastname,String email,String telephone,String addr1,String city,String postcode,String country,String state,String password,String confpassword,String positive) {
		this.firstname=firstname;
		this.lastname=lastname;
		this.email=email;
		this.telephone=telephone;
		this.addr1=addr1;
		this.city=city;
		this.postcode=postcode;
		this.country=country;
		this.state=state;
		this.password=password;
		this.confpassword=confpassword;
		this.positive=positive;
	}
	
	// step 4
	@Parameters
	public static Collection<Object[]> getData() {
		Object[][] data=utils.getData("Registration");
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
	public void RegistrationFormTest() throws IOException {
		String FileName = "reg_"+email;
		
		if(!utils.IsSkipped("Registration")) {
			// report test case skipped
			Assume.assumeTrue(false);
		}
		
		getWebElement("linktext_reg_createAccount").click();
		getWebElement("name_reg_firstname").sendKeys(firstname);
		getWebElement("name_reg_lastname").sendKeys(lastname);
		getWebElement("xpath_reg_email").sendKeys(email);

		getWebElement("css_reg_telephone").sendKeys(telephone);
		getWebElement("xpath_reg_area").sendKeys(addr1);
		getWebElement("name_reg_city").sendKeys(city);
		getWebElement("css_reg_postcode").sendKeys(postcode);
		getWebElement("xpath_reg_country").sendKeys(country);
		getWebElement("name_reg_zone").sendKeys(state);
		getWebElement("xpath_reg_password").sendKeys(password);
		getWebElement("name_reg_confirm").sendKeys(confpassword);
		getWebElement("xpath_reg_agree").click();
		getWebElement("xpath_reg_button").click();
		
		try {
			String actualText = driver.findElement(By.xpath(OR.getProperty("xpath_reg_verify_reg_text"))).getText();
			String expectedText = appText.getProperty("xpath_reg_verify_reg_text");
						
			if(!actualText.equals(expectedText)) {
				
				// take screenshot
				utils.takeScreenshot(FileName);
				
				// report error
				Assert.assertTrue(false, "Actual text "+actualText+" is not same as expected text "+expectedText);
			}
			
			if(positive.equals("N")) {
				// take screenshot
				utils.takeScreenshot(FileName);

				// report error
				Assert.assertTrue(false, "Test passed for negative data");
			}			
			
			
		}catch (Exception e) {
			// report error
			if(positive.equals("Y")) {
				// take screenshot
				utils.takeScreenshot(FileName);

				// report error
				Assert.assertTrue(false, "Test failed for positive data");
			}			
		}
		
	}	
	

}
