package com.ctli.it.lib;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.xml.XmlTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import io.appium.java_client.android.AndroidDriver;

public class TestNgInitialization implements AutomationConstants {
	public ExtentTest testReport;
	public WebDriver driver;
	public AndroidDriver mobiledriver;
	 public static ExtentReports extent;
	 private HashMap<String, String> runData;

	
	@BeforeSuite
	public void initialSetUp()
	{
		 String extentConfigFile=System.getProperty("user.dir")+"\\Resources\\Config.xml";
		  extent=new ExtentReports(reportFilePath, true);
		  extent.loadConfig(new File(extentConfigFile));  
	}

	@BeforeMethod
	public void launchApp(Method method, XmlTest test) throws Exception {
		
		testReport = extent.startTest((this.getClass().getSimpleName() + "::"  +method.getName()),method.getName()); 
		String browser = test.getParameter("browser");
		if (browser.equalsIgnoreCase("GC")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-extensions");
			options.addArguments("disable-infobars");
			String key = "webdriver.chrome.driver";
			String value = "./Resources/chromedriver.exe";
			System.setProperty(key, value);
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(ReadPropertyFile.getPropertyValue("URL"));

		} else if (browser.equalsIgnoreCase("IE")) {
			String Key = "webdriver.ie.driver";
			String value = "./Resources/IEDriverServer.exe";
			System.setProperty(Key, value);
			// Create the DesiredCapability object of InternetExplorer
			 DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			 // Settings to Accept the SSL Certificate in the Capability object
			 capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			 capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			 driver = new InternetExplorerDriver(capabilities);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(ReadPropertyFile.getPropertyValue("URL"));

		} else if (browser.equalsIgnoreCase("FF")) {
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(ReadPropertyFile.getPropertyValue("URL"));
		}

		else if (browser.equalsIgnoreCase("appium")) {
			testReport = eReport.startTest(browser+"_"+method.getName());
			if (ReadPropertyFile.getPropertyValue("APPTYPE").equals("WebApp")) {
				String filepath=System.getProperty("user.dir")+"\\Resources\\chromedriver.exe";
				AppiumServerStarting appiumserverStart = new AppiumServerStarting();
				appiumserverStart.appiumStart();
                 
				DesiredCapabilities ds = new DesiredCapabilities();
				ds.setCapability("deviceName", ReadPropertyFile.getPropertyValue("DEVICENAME"));
				//ds.setCapability(CapabilityType.BROWSER_NAME, "Android");
				ds.setCapability("browserName", "Chrome");
				ds.setCapability("chromedriverExecutable", filepath);
				ds.setCapability(CapabilityType.VERSION,  ReadPropertyFile.getPropertyValue("ANDROIDVERSION"));
				ds.setCapability("platformName", "Android");
				mobiledriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), ds);
				mobiledriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				mobiledriver.get(ReadPropertyFile.getPropertyValue("APPURL"));
			}

			else {
				AppiumServerStarting appiumserverStart = new AppiumServerStarting();
				appiumserverStart.appiumStart();
				DesiredCapabilities ds = new DesiredCapabilities();
				ds.setCapability("deviceName", ReadPropertyFile.getPropertyValue("DEVICENAME"));
				ds.setCapability(CapabilityType.BROWSER_NAME, "Android");
				ds.setCapability(CapabilityType.VERSION, ReadPropertyFile.getPropertyValue("ANDROIDVERSION"));
				ds.setCapability("platformName", "Android");
				ds.setCapability("appPackage", ReadPropertyFile.getPropertyValue("APPPACKAGE"));
				ds.setCapability("appActivity", ReadPropertyFile.getPropertyValue("APPACTIVITY"));
				mobiledriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), ds);
				mobiledriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			}
		}

	}
	@AfterMethod
	public void closeApp(Method method, XmlTest test, ITestResult result)
	{
		 String browser = test.getParameter("browser");
		if(result.isSuccess()){
			System.out.println("pass");  
		}else{
			 if(browser.equalsIgnoreCase("ff")||browser.equalsIgnoreCase("gc")||browser.equalsIgnoreCase("ie")){
				 BaseClass bClass = new BaseClass(driver, testReport);
					bClass.getPageScreenShot();
					System.out.println("Successfully captured a screenshot");
			 }else if(browser.equalsIgnoreCase("appium")) {
				 MobileBaseClass mbClass = new MobileBaseClass(mobiledriver, testReport);
				 mbClass.getPageScreenShot();
				 System.out.println("Successfully captured a screenshot");
			 }
			
		}
		  if(browser.equalsIgnoreCase("ff")||browser.equalsIgnoreCase("gc")||browser.equalsIgnoreCase("ie")){
			  driver.quit();
		  }else if (browser.equalsIgnoreCase("appium")) {
			//mobiledriver.close();
		}
		
	}
	
	
	  @AfterSuite
	  public void afterSuite( XmlTest test){
		
	  extent.flush();



	  }
	 

	}
