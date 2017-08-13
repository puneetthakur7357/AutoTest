package Automation;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AppTest {
	WebDriver driver;
	
	@BeforeClass
	public void setupBrowser(){
		
		System.out.println("Running browser");
		//System.setProperty("webdriver.gecko.driver","C:\\Users\\PuneetPC\\Desktop\\Software Testing\\geckodriver.exe");
		driver = new FirefoxDriver();
		String urlname = "http://zero.webappsecurity.com/index.html";
		driver.get(urlname);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	}
	
	
	@Test(priority=1, description="This is test case for page title")
	public void pagetitle(){
		String actualTitle = driver.getTitle();
		String expectedTitle = "Zero - Personal Banking - Loans - Credit Cards";
		assertEquals(expectedTitle,actualTitle);
		System.out.println(actualTitle);
	}
	
	
	@Test(priority=2, description="This is test case for login")
	public void login() throws InterruptedException{
		System.out.println("Running TEST");
		
		driver.findElement(By.xpath("//button[@id='signin_button']")).click();
		driver.findElement(By.xpath("//input[@id='user_login']")).sendKeys("username");
		driver.findElement(By.xpath("//input[@id='user_password']")).sendKeys("password");
		driver.findElement(By.xpath("//input[@name='submit']")).click();

		
		Actions act=new Actions(driver);
		 WebElement ele=driver.findElement(By.xpath("//a[@href='/bank/redirect.html?url=pay-bills.html']"));
		 act.moveToElement(ele).build().perform();	  
		driver.findElement(By.xpath("//a[@href='/bank/redirect.html?url=pay-bills.html']")).click();
		 Thread.sleep(5000);
		 
		 Actions act1=new Actions(driver);
		 WebElement ele1=driver.findElement(By.xpath("//a[contains(.,'Add New Payee')]"));
		 act1.moveToElement(ele1).build().perform();	  
		 driver.findElement(By.xpath("//a[contains(.,'Add New Payee')]")).click();
		 Thread.sleep(5000);


		
		//Add payee details

	    driver.findElement(By.xpath("//input[@name='name']")).sendKeys("Hydro One");
	    driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys("123 Demo Ave, Toronto, ON x1x 01x");
	    driver.findElement(By.xpath("//input[@name='account']")).sendKeys("123456789");
	    driver.findElement(By.xpath("//input[@name='details']")).sendKeys("Natural Gas Bills");
		driver.findElement(By.xpath("//input[@id='add_new_payee']")).click();
		
		
		//check if user is added
		String expectedtext = "The new payee Hydro One was successfully created.";
		String actualtext = driver.findElement(By.xpath("//div[@id='alert_content']")).getText();
		
		System.out.println(actualtext);
		
		
		if(actualtext.contentEquals(expectedtext)){
			System.out.println("Test Passed!");
			
		}
		else{
			System.out.println("Test Failed!");
		}
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		Thread.sleep(2000);
	}
	
	public static String getRandomString(int length) {
		StringBuilder sb = new StringBuilder();
		String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		for (int i = 0; i < length; i++) {
			int index = (int) (Math.random() * characters.length());
			sb.append(characters.charAt(index));
		}
		return sb.toString();
	}
	
	
		@AfterClass
		public void tearDown() throws Exception {
			
			String fileName = getRandomString(10) + ".png";
			
			File sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(sourceFile, new File("C:\\project\\project\\Screenshot\\" + fileName));
		
	
			System.out.println("Running LOGOUT");
			
			driver.findElement(By.xpath(".//*[@id='settingsBox']/ul/li[3]/a")).click();
			driver.findElement(By.xpath(".//*[@id='logout_link']")).click();
			driver.quit();
		}

}
