package com.ctli.it.lib;

import static org.testng.Assert.fail;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByName;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.ctl.cipherTools.CipherMe;
import com.relevantcodes.extentreports.ExtentTest;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

public class MobileBaseClass {
	public ExtentTest testReport;
	public AndroidDriver androiddriver;
	private CipherMe cipher;
	private byte[] encryptedvalue;
	private String decryptedtext;
	static final long TIME_OUT = 60;
	public String parentName = null; 
	public String childName = null; 

	public MobileBaseClass(AndroidDriver androiddriver, ExtentTest testReport) {
		this.androiddriver = androiddriver;
		this.testReport=testReport;
	}
	
	public void tap(WebElement element) {
		try {
			shouldBeVisible(element);
			TouchAction t = new TouchAction(androiddriver);
			t.tap(element).perform();
		} catch (Exception e) {
			e.printStackTrace();
			fail("unable to tap on mobile element");
		}
	}

	public void shouldBeVisible(WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(androiddriver, TIME_OUT);
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			e.printStackTrace();
			fail("element is not visible");
		}
	}

	public void verticalScroll(String text ,WebElement element) {
       for(int i=0;i<10;i++)
       {
    	   try
    	   {
    		   androiddriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    	   if(element.isDisplayed())
    	   {
    		   break;
    	   }}
    	   catch(Exception e)
    	   {
    		   Dimension size = androiddriver.manage().window().getSize();
   			int y_start = (int) (size.height * 0.60);
   			int y_end = (int) (size.height * 0.20);
   			int x = size.width / 2;
   			androiddriver.swipe(x, y_start, x, y_end, 500);
    		   e.printStackTrace();
    	   }
    	 }
    	 
       
		

	}

	public void horizontalScroll() {
		try {
			Dimension size = androiddriver.manage().window().getSize();
			int x_start = (int) (size.width * 0.60);
			int x_end = (int) (size.width * 0.30);
			int y = 130;
			androiddriver.swipe(x_start, y, x_end, y, 4000);
		} catch (Exception e) {
			e.printStackTrace();
			fail("");
		}

	}

	public void seekBar(WebElement element) {
		try {
			// element should be the locator of seekbar
			shouldBeVisible(element);
			// get start co-ordinate of seekbar
			int start = element.getLocation().getX();
			// Get width of seekbar
			int end = element.getSize().getWidth();
			// get location of seekbar vertically
			int y = element.getLocation().getY();

			// Select till which position you want to move the seekbar
			TouchAction action = new TouchAction(androiddriver);

			// Move it will the end
			action.press(start, y).moveTo(end, y).release().perform();

			// Move it 40%
			int moveTo = (int) (end * 0.4);
			action.press(start, y).moveTo(moveTo, y).release().perform();

		} catch (Exception e) {
			e.printStackTrace();
			fail("");

		}
	}

	// to handle app permission (allow this app to access ur conatct)
	//
	public void allowAppPermission(String xpath) {
		while (androiddriver.findElements(MobileBy.xpath(xpath)).size() > 0)

		{
			androiddriver.findElement(MobileBy.xpath(xpath)).click();
		}

	}

	public void type(WebElement element, String text) {
		try {
			shouldBeVisible(element);
			element.clear();
			element.sendKeys(text);
		} catch (Exception e) {
			e.printStackTrace();
			fail("unable to enter text");
		}

	}

	public Boolean isEnabled(WebElement element) {
		shouldBeVisible(element);
		return element.isEnabled();
	}

	public Boolean isDisplayed(WebElement element) {
		shouldBeVisible(element);
		return element.isDisplayed();
	}

	public void webScroll() {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) androiddriver;
			jse.executeScript("window.scrollBy(0,250)", "");
		} catch (Exception e) {
			e.printStackTrace();
			fail("unable to scroll the element");
		}
	}
	
	
	public void checkPageIsReady() {

		JavascriptExecutor js = (JavascriptExecutor)androiddriver;


		if (js.executeScript("return document.readyState").toString().equals("complete")){ 
			System.out.println("Page Is loaded.");
			return; 
		} 

		for (int i=0; i<25; i++){ 
			try {
				Thread.sleep(1000);
			}catch (InterruptedException e) {} 
			if (js.executeScript("return document.readyState").toString().equals("complete")){ 
				break; 
			}   
		}
	}
	
	public void scrollToWebElement(WebElement element){
		//WebElement element = androiddriver.findElement(locator);
		try {
			((JavascriptExecutor) androiddriver).executeScript("arguments[0].scrollIntoView();", element);
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean slider(WebElement elemnt, int x, int y) throws Throwable {
		boolean status = false;
		try {
			waitTillElementToBeClickble(elemnt);
			Actions moveElement = new Actions(androiddriver);
			new Actions(androiddriver).clickAndHold(elemnt).moveByOffset(x, y).release().perform();
			moveElement.build().perform();
			status = true;
			System.out.println("Slider moved");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return status;
	}
	
	public void avoidStaleElementClick(String xpath) throws InterruptedException
	{
		Thread.sleep(5000);
		int count=0;
		while (count < 4) {
			   try {
			    //If exception generated that means It Is not able to find element then catch block will handle It.
			    WebElement staledElement = androiddriver.findElement(By.xpath(xpath));
			    //If exception not generated that means element found and element text get cleared.
			    staledElement.click();    
			   } catch (StaleElementReferenceException e) {
			    e.toString();
			    System.out.println("Trying to recover from a stale element :" + e.getMessage());
			    count = count + 1;
			   }
			   count = count + 4;
		
		}
		}
	
	public String avoidStaleElementText(String xpath) throws InterruptedException
	{
		String text= null;
		Thread.sleep(5000);
		int count=0;
		while (count < 4) {
			   try {
			    //If exception generated that means It Is not able to find element then catch block will handle It.
			    WebElement staledElement = androiddriver.findElement(By.xpath(xpath));
			    //If exception not generated that means element found and element text get cleared.
			    text =staledElement.getText();  
			   } catch (StaleElementReferenceException e) {
			    System.out.println(e.toString());
			    System.out.println("Trying to recover from a stale element :" + e.getMessage());
			    count = count + 1;
			   }
			   count = count + 4;
		
		}
		return text;
		}

	public void mouseMoveClick(WebElement ele) {
		try {
			WebDriverWait dWait = new WebDriverWait(androiddriver, TIME_OUT);
			ele = dWait.until(ExpectedConditions.visibilityOf(ele));
			Actions action = new Actions(androiddriver);
			action.moveToElement(ele).click().build().perform();
		} catch (Exception e) {
			fail("Failed mouseMoveClick() with Exception: " + e.getMessage());
		}
	}

	public void mouseHover(WebElement ele) {
		try {
			WebDriverWait dWait = new WebDriverWait(androiddriver, TIME_OUT);
			ele = dWait.until(ExpectedConditions.visibilityOf(ele));
			Actions action = new Actions(androiddriver);
			action.moveToElement(ele).perform();
		} catch (Exception e) {
			fail("Failed mouseHover() with Exception: " + e.getMessage());
		}
	}

	public void mouseSelect(WebElement hoverElm, WebElement clickElm) {
		try {
			mouseHover(hoverElm);
			WebDriverWait dWait = new WebDriverWait(androiddriver, TIME_OUT);
			clickElm = dWait.until(ExpectedConditions.visibilityOf(clickElm));
			Actions action = new Actions(androiddriver);
			action.moveToElement(clickElm).click(clickElm).build().perform();
		} catch (Exception e) {
			fail("Failed mouseSelect() with Exception: " + e.getMessage());
		}
	}

	public boolean isMenuOptionAvailable(WebElement hoverElm, WebElement clickElm) {
		try {
			mouseHover(hoverElm);
			WebDriverWait dWait = new WebDriverWait(androiddriver, TIME_OUT);
			clickElm = dWait.until(ExpectedConditions.visibilityOf(clickElm));
			Thread.sleep(3000);
			return !(clickElm.getAttribute("class").contains("disabledLink"));
		} catch (Exception e) {
			fail("Failed mouseSelect() with Exception: " + e.getMessage());
			return false;
		}
	}

	public void mouseSelectWithRetry(WebElement hoverElm, WebElement clickElm) {
		int attempts = 0;
		boolean success = false;
		while (attempts < 3) {
			try {
				mouseHover(hoverElm);
				WebDriverWait dWait = new WebDriverWait(androiddriver, TIME_OUT);
				Actions action = new Actions(androiddriver);
				clickElm = dWait.until(ExpectedConditions.visibilityOf(clickElm));
				action.moveToElement(clickElm).click(clickElm).perform();
				success = true;
			} catch (StaleElementReferenceException sere) {
				System.out.print("StaleElementReferenceException\n");
			} catch (NoSuchElementException nsee) {
				System.out.print("NoSuchElementException\n");
			} catch (Exception e) {
				fail("Failed mouseSelectWithRetry() with Exception: " + e.getMessage());
			}
			if (success)
				break;
			attempts++;
		}
	}

	public void mouseSelectWithRetry(WebElement hoverElm1, WebElement hoverElm2, String clickElmName) {
		int attempts = 0;
		boolean success = false;
		while (attempts < 3) {
			try {
				// System.out.print("attempts = " + attempts + "\n");
				mouseHover(hoverElm1);
				WebDriverWait dWait = new WebDriverWait(androiddriver, TIME_OUT);
				Actions action = new Actions(androiddriver);
				hoverElm2 = dWait.until(ExpectedConditions.visibilityOf(hoverElm2));
				action.moveToElement(hoverElm2);
				mouseHover(hoverElm2);
				dWait = new WebDriverWait(androiddriver, TIME_OUT);
				WebElement clickElm = dWait
						.until(ExpectedConditions.visibilityOfElementLocated(ByName.name(clickElmName)));
				action.moveToElement(clickElm).click(clickElm).perform();
				success = true;
			} catch (StaleElementReferenceException sere) {
				System.out.print("StaleElementReferenceException\n");
			} catch (NoSuchElementException nsee) {
				System.out.print("NoSuchElementException\n");
			} catch (Exception e) {
				fail("Failed mouseSelectWithRetry() with Exception: " + e.getMessage());
			}
			if (success)
				break;
			attempts++;
		}
	}

	public void click(WebElement elm){
		try {
			WebDriverWait dWait = new WebDriverWait(androiddriver, TIME_OUT);
			elm = dWait.until(ExpectedConditions.elementToBeClickable(elm));
			elm.click();
			System.out.println("clicked Done");
		} catch (Exception e) {
			fail("Failed click " + elm.getTagName() + " with Exception: " + e.getMessage());
		}
	}
	
	public Boolean isElementvisibleornot(WebElement element) {
		try {
			WebDriverWait dWait = new WebDriverWait(androiddriver, TIME_OUT);
			dWait.until(ExpectedConditions.visibilityOf(element));
			//testReport.log(LogStatus.INFO,"element is visible");
			return true;
		} catch (NoSuchElementException  e) {
			return false;
		}
	} 
	
	public boolean isVisible(WebElement element){
		try {
			WebDriverWait dWait = new WebDriverWait(androiddriver, TIME_OUT);
			dWait.until(ExpectedConditions.visibilityOf(element));
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	public boolean isSelected(WebElement element) {
		try {
			WebDriverWait dWait = new WebDriverWait(androiddriver, TIME_OUT);
			element = dWait.until(ExpectedConditions.visibilityOf(element));
			
			return element.isSelected();
			
		} catch (Exception e) {
			return false;
		}
	}

	public void selectOptionByVisibleText(WebElement element, String value) {
		try {
			Select select = new Select(element);
			List<WebElement> options = select.getOptions();
			for (int i = 0; i < options.size(); i++) {
				String txt = options.get(i).getText();
				if (txt.contains(value) || txt.equalsIgnoreCase(value)) {
					select.selectByVisibleText(txt);
					return;
				}
			}
		} catch (Exception e) {
			fail("Failed setDropdown " + element.getTagName() + " with Exception: " + e.getMessage());
		}
		fail("Failed setDropdown " + element.getTagName() + " Option not found");
	}

	public boolean verifyElementInDropdown(WebElement element, String value) {
		try {
			Select select = new Select(element);
			List<WebElement> options = select.getOptions();
			for (int i = 0; i < options.size(); i++) {
				String txt = options.get(i).getText();
				if (txt.contains(value) || txt.equalsIgnoreCase(value))
					return true;

			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}


	public void mouseHoverWithJs(WebElement hoverElm, WebElement clickElm) {
		try {
			String javaScript = "var evObj = document.createEvent('MouseEvents');"
					+ "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
					+ "arguments[0].dispatchEvent(evObj);";
			JavascriptExecutor js = (JavascriptExecutor) androiddriver;
			js.executeScript(javaScript, hoverElm);
			click(clickElm);

		} catch (StaleElementReferenceException sere) {
			System.out.print("StaleElementReferenceException\n");
		} catch (NoSuchElementException nsee) {
			System.out.print("NoSuchElementException\n");
		} catch (Exception e) {
			fail("Failed java script executor with Exception: " + e.getMessage());
		}
	}

	public void mouseSelectWithRetry(WebElement hoverElm1, WebElement hoverElm2, WebElement hoverElm3,
			String clickElmName) {
		int attempts = 0;
		boolean success = false;
		while (attempts < 3) {
			try {
				// System.out.print("attempts = " + attempts + "\n");
				mouseHover(hoverElm1);
				WebDriverWait dWait = new WebDriverWait(androiddriver, TIME_OUT);
				Actions action = new Actions(androiddriver);
				hoverElm2 = dWait.until(ExpectedConditions.visibilityOf(hoverElm2));
				action.moveToElement(hoverElm2);

				mouseHover(hoverElm2);
				dWait = new WebDriverWait(androiddriver, TIME_OUT);
				hoverElm3 = dWait.until(ExpectedConditions.visibilityOf(hoverElm3));
				action.moveToElement(hoverElm3);

				mouseHover(hoverElm3);
				dWait = new WebDriverWait(androiddriver, TIME_OUT);
				WebElement clickElm = dWait.until(ExpectedConditions
						.visibilityOfElementLocated(ByXPath.xpath("(//a[contains(@name,'" + clickElmName + "')])[1]")));
				action.moveToElement(clickElm).click(clickElm).perform();
				success = true;
			} catch (StaleElementReferenceException sere) {
				System.out.print("StaleElementReferenceException\n");
			} catch (NoSuchElementException nsee) {
				System.out.print("NoSuchElementException\n");
			} catch (Exception e) {
				fail("Failed mouseSelectWithRetry() with Exception: " + e.getMessage());
			}
			if (success)
				break;
			attempts++;
		}
	}

	public void jsClick(WebElement element) {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) androiddriver;
			executor.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			e.printStackTrace();
			fail("unable to click by java script");

		}

	}
	
	public  void highlightElement(WebElement element)
	{
		String presentColor=element.getCssValue("backgroundColor");
		String newCoclor="rgb(255,255,0)";
		
		for(int i=1;i<=3;i++)
		{
			((JavascriptExecutor)androiddriver).executeScript("arguments[0].style.backgroundColor='"+newCoclor+"'",element);
			((JavascriptExecutor)androiddriver).executeScript("arguments[0].style.backgroundColor='"+presentColor+"'",element);
			
		}
	}
	
	
	
	public void jsType(WebElement element, String xp)
	{
		String xp1="\"arguments[0].value='"+xp+"'\"";
		((JavascriptExecutor)androiddriver).executeAsyncScript(xp1,element);
	}
	
//..............................colour verification.......................
	public  void verifyElementColor(WebElement element,String eHexColor)
	{
		
		String strRGB=element.getCssValue("color");
		System.out.println(strRGB);
		String hex=convertRGBtoHex(strRGB);	
		String msg1="<span style='color:"+eHexColor+";'>Expected color</span>";
		String msg2="<span style='color:"+hex+";'>Actual color</span>";
		System.out.println(hex);
		if(hex.equals(eHexColor))
		{
			System.out.println("successfully verified");
		}
		else
		{
			System.out.println("not verified");
		}
	}
	
	
	public  String convertRGBtoHex(String strRGB)
	{
		String hex="";
		List<Integer> rgb=new ArrayList<Integer>();
		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(strRGB);
		 while(m.find())
		 {
			 int v=Integer.parseInt(m.group());
			 rgb.add(v);
		 }
		 		
		 int red=rgb.get(0);
		 int green=rgb.get(1);
		 int blue=rgb.get(2);
		 hex = String.format("#%02x%02x%02x",red, green,blue);
		 return hex; 
	}
	
	
	public  String getFormatedDateTime()
	{
		SimpleDateFormat sd = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		return sd.format(new Date());
	}
	

public void sentAnEmail()
{
       
       final String fromMail =ReadPropertyFile.getPropertyValue("FromEmail");
       final String tomail=ReadPropertyFile.getPropertyValue("TOEMAIL");
       String pwd=ReadPropertyFile.getPropertyValue("PASSWORD");
       String emailText=ReadPropertyFile.getPropertyValue("EMAILTEXT");
       String cc=ReadPropertyFile.getPropertyValue("CCEMAIL");
       String subject=ReadPropertyFile.getPropertyValue("SUBJECT");
       
       Properties props = new Properties();
       props.put("mail.smtp.host", "mailgate.uswc.uswest.com");
       props.put("mail.smtp.socketFactory.port", "25");
       //props.put("mail.smtp.socketFactory.class",
       //"javax.net.ssl.SSLSocketFactory");
       props.put("mail.smtp.auth", "true");
       props.put("mail.smtp.port", "25");
       Session session = Session.getDefaultInstance(props,
       new javax.mail.Authenticator() {
       protected PasswordAuthentication getPasswordAuthentication() {
       return new PasswordAuthentication(fromMail,tomail);
       }
       });
       try {
       MimeMessage message = new MimeMessage(session);
       message.setFrom(new InternetAddress(fromMail));
       
       String[] recipientList = tomail.split(",");
       InternetAddress[] recipientAddress = new InternetAddress[recipientList.length];
       int counter = 0;
       for (String recipient : recipientList) {
           recipientAddress[counter] = new InternetAddress(recipient.trim());
           counter++;
       }
       message.setRecipients(Message.RecipientType.TO, recipientAddress);

       message.setRecipient(Message.RecipientType.CC, new InternetAddress(cc));
       
        
       
       
       
       message.setSubject(subject);
       MimeBodyPart messageBodyPart = new MimeBodyPart();
       messageBodyPart.setText(emailText);

       Multipart multipart = new MimeMultipart();
       multipart.addBodyPart(messageBodyPart);

       messageBodyPart = new MimeBodyPart();


    String file= System.getProperty("user.dir")+"\\Report\\results.html";
    String fileName = "reults.html";
       DataSource source = new FileDataSource(file);
       messageBodyPart.setDataHandler(new DataHandler(source));
       messageBodyPart.setFileName(fileName);
       multipart.addBodyPart(messageBodyPart);

       message.setContent(multipart);

       Transport.send(message);
       } catch (MessagingException ex) {
       throw new RuntimeException(ex);
       }
}
	
	public Wait<WebDriver> Wait(int... waitTime) {
		int waitTimeInSeconds;
		if (waitTime.length > 0) {
			waitTimeInSeconds = waitTime[0];
		} else {
			waitTimeInSeconds = 5;
		}
		return new FluentWait<WebDriver>(androiddriver)
				.withTimeout(waitTimeInSeconds, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class)
				.ignoring(WebDriverException.class);
	}

	public void WaitForFrameToLoad(final String frameName, int... waitTime) {
		androiddriver.switchTo().defaultContent();
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver androiddriver) {
				return androiddriver.switchTo().frame(frameName) != null;
			}
		};
		if (waitTime.length > 0) {
			Wait(waitTime).until(expectation);
		} else {
			Wait(30).until(expectation);
		}
		androiddriver.switchTo().defaultContent();
	}

	public void storeRunTimeValue(String runtimeTestDataName, String runtimeTestDataValue){
		RuntimeData.storeRuntimeData(runtimeTestDataName, runtimeTestDataValue);
	}
	
	public String getRunTimeValue(String runtimeTestDataName){
		return RuntimeData.getRuntimeData(runtimeTestDataName);
	}
	
	private WebDriverWait getWaiter(){
		WebDriverWait waiter = new WebDriverWait(androiddriver,60);
		return waiter;		
	}

	private WebElement getElement(WebElement element){
		WebElement targetElement = null;
		try{
			targetElement = element;
			if(targetElement.isDisplayed()){
				System.out.println("INFO -- Element is displayed and Present on the Webpage. :: \""+element+"\"");
				return targetElement;
			}else{
				System.out.println("ERROR --Element is not enabled and not present on the WebPage. :: \""+element+"\" .");
			return null;
			}	
		}catch(Exception e){
			fail("Locator not valid Exception or syntax wrong. Entered locatorName is ::\""+element+"\" .");
			return null;
		}
	}
	
	public void switchToFrameById(String frameID){
		try{
			androiddriver.switchTo().frame(frameID);
			System.out.println("INFO -- Switched into the frame having frameid :: \""+frameID+"\" .");
		}catch(NoSuchFrameException e){
			fail("ERROR --Unable to switch to the frame. frameid :: \""+frameID+"\" .");
		}catch(StaleElementReferenceException e){
			fail("ERROR --Stale element reference exception. frame :: \""+frameID+"\" .");
		}catch(WebDriverException e){
			fail("ERROR --Unable to switch to the frame. frameid :: \""+frameID+"\" .");
		}		
	}

	public void switchToFrameByIndex(int index){
		try{
			androiddriver.switchTo().frame(index);
			System.out.println("INFO -- Switched into the frame having frame index :: \""+index+"\" .");
		}catch(NoSuchFrameException e){
			fail("ERROR --Unable to switch to the frame. frame index :: \""+index+"\" .");
		}catch(WebDriverException e){
			fail("ERROR --Unable to switch to the frame. frame index :: \""+index+"\" .");
		}
	}
	
	
	public void switchToFrameByElement(WebElement element){
		WebElement targetElement = getElement(element);
		try{
			androiddriver.switchTo().frame(targetElement);
			System.out.println("INFO -- Switched into the frame with webelement :: \""+element+"\" .");
		}catch(NoSuchFrameException e){
			fail("ERROR --Unable to switch to the frame. frame index :: \""+element+"\" .");
		}catch(StaleElementReferenceException e){
			fail("ERROR --Stale element reference exception. Object :: \""+element+"\" .");
		}catch(WebDriverException e){
			fail("ERROR --Unable to switch to the frame. frame index :: \""+element+"\" .");
		}
	}
	
	public void switchToParentFrame(){
		try{
			androiddriver.switchTo().parentFrame();
			System.out.println("INFO -- Switched into the parent frame");
		}catch(NoSuchFrameException e){
			System.out.println("ERROR --unable to switch to parent frame Exception.");
		}catch(WebDriverException e){
			System.out.println("ERROR --Unable to switch to the parent frame.");
		}
	}
	
	public void defaultContent(){
		try{
			androiddriver.switchTo().defaultContent();
			System.out.println("INFO -- Switched into the parent frame");
		}catch(NoSuchFrameException e){
			System.out.println("ERROR --unable to switch to parent frame Exception.");
		}catch(WebDriverException e){
			System.out.println("ERROR --Unable to switch to the parent frame.");
		}
	}

	public void navigateTo(String url) {
		try{
			androiddriver.navigate().to(url);
			System.out.println("INFO -- Navigated to \""+url+"\" WebPage.");
		}catch(WebDriverException e){
			fail("ERROR --Unable to Navigate to \""+url+"\" WebPage.");
		}
	}
	

	public String getText(WebElement element) {
		WebElement targetElement = getElement(element);
		String text = null;
		try{
			text = targetElement.getText();
			System.out.println("INFO -- Got the text from the Object :: \""+element+"\".");
		}catch(ElementNotVisibleException e){
			fail("ERROR --Unable to get the text. Element might be hidden. Object :: \""+element+"\".");
		}catch(WebDriverException e){
			fail("ERROR --Unable to get the text for the Object :: \""+element+"\".");
		}
		return text;		
	}
	
	public void closeCurrentWindow(){
		try{
			androiddriver.close();
			System.out.println("INFO -- Browser window is closed .");
			
		}catch(WebDriverException e){
			fail("ERROR --Unable to close the browser window.");
		}
	}

	public void navigateBack(){
		try{
			androiddriver.navigate().back();
			System.out.println("INFO -- Navigated back.");
			
		}catch(WebDriverException e){
			fail("ERROR --Unable to navigate back.");
		}
	}
	
	public void navigateForward(){
		try{
			androiddriver.navigate().forward();
			System.out.println("INFO -- Navigated forward.");
					
		}catch(WebDriverException e){
			fail("ERROR --Unable to navigate forward.");
		}
	}
	
	public void closeAllWindows(){
		try{
			Set<String> AllWindows = androiddriver.getWindowHandles();
			for (String window : AllWindows) {
				androiddriver.switchTo().window(window);
				androiddriver.close();
			}
			fail("INFO -- All windows are closed.");
		}catch(NoSuchWindowException e){
			fail("ERROR --Unable to switch into the window.");
		}catch(WebDriverException e){
			fail("ERROR --Unable to close all the windows.");
		}
	}
	
	public void reloadCurrentPage(){
		try{
			androiddriver.navigate().refresh();
			System.out.println("INFO -- Reloaded the current Page.");
		}catch(WebDriverException e){
			fail("Unable to reload the current page.");
		}
	}
	
	public String getTitle(){
		String title = null;
		try{
			title =  androiddriver.getTitle();
			System.out.println("INFO -- Current window title is retrieved. title :: \""+title+"\" .");
		}catch(WebDriverException e){
			fail("ERROR --Unable to get the current window title.");
		}	
		return title;
	}
	
	public void clear(WebElement element){
		WebElement targetElement = element;
		try{
			targetElement.clear();
			System.out.println("INFO -- cleared the text from the object ::\""+element+"\"");
			
		}catch(InvalidElementStateException e){
			fail("ERROR --Element is either hidden or disabled exception.Object :: \""+element+"\" . ");
		}catch(WebDriverException e){
			fail("ERROR --Element is either hidden or disabled exception.Object :: \""+element+"\" .");
		}
	}
	
	public void check(WebElement element){
		WebElement targetElement = getElement(element);
		if(!targetElement.isSelected()){
			try{
				targetElement.click();
				System.out.println("INFO -- checked the element. Object :: \""+element+"\" . ");
			}catch(StaleElementReferenceException e){
				fail("ERROR --Stale element reference exception. Object :: \""+element+"\" .");
			}catch(WebDriverException e){
				fail("ERROR --Stale Element Reference Exception.Object :: \""+element+"\" .");
			}
		}else{
			fail("INFO -- Already the element is checked. Object :: \""+element+"\" .");
		}		
	}
	
	public void unCheck(WebElement element){
		WebElement targetElement = getElement(element);
		if(targetElement.isSelected()){
			try{
				targetElement.click();
				System.out.println("INFO -- Uchecked the element . Object :: \""+element+"\" .");
				
			}catch(StaleElementReferenceException e){
				fail("ERROR --Stale element reference exception. Object :: \""+element+"\" .");
			}catch(WebDriverException e){
				fail("FATAL--MAX Exception :: DriverException.Related to uncheck the Object :: \""+element+"\" .");
			}
		}else{
			fail("INFO -- Already the element is unchecked. Object :: \""+element+"\" .");
		}
	}
	
	public String getAttribute(WebElement element, String attribute){
		WebElement targetElement = getElement(element);
		String attributeValue = null;
		try{
			attributeValue = targetElement.getAttribute(attribute);
			System.out.println("INFO -- \""+attribute+"\" attribute value :: \""+attributeValue+"\" is retrieved for the Object::\""+element+"\" .");
		}catch(WebDriverException e){
			fail("ERROR --Unable to get the \""+attribute+"\" attribute value for the Object::\""+element+"\" .");
		}
		return attributeValue;
	}
	
	public void verifyValue(WebElement element,String expectedValue) {
		String valueFromElement = getAttribute(getElement(element), "value");
		System.out.println("INFO -- The value from the element \"" + valueFromElement +"\" should be equal to expected value \"" +expectedValue+"\" .");
		Assert.assertEquals("The value from the element \"" + valueFromElement +"\" should be equal to expected value \"" +expectedValue+"\" .", expectedValue, valueFromElement);
		
	}
	
	public void verifyTitle(String title) {
		String actualTitle = androiddriver.getTitle();
		System.out.println("INFO -- The title of the Page \""+actualTitle+"\" should be equal to expected title \""+title+"\".");
		Assert.assertEquals("The title of the Page \""+actualTitle+"\" should be equal to expected title \""+title+"\"", title, actualTitle);
		 
	}
	
	private String getTagName(WebElement element){
		WebElement targetElement=getElement(element);
		return targetElement.getTagName();		
	}
	
	private boolean isSelectBox(WebElement element){
		return getTagName(getElement(element)).equalsIgnoreCase("SELECT");	
	}
	
	public void selectOptionByValue(WebElement element, String value){
		if(isSelectBox(getElement(element))){
			try{
				Select select = new Select(getElement(element));
				select.selectByValue(value);
				System.out.println("INFO -- \""+value+"\" Option is selected for the object :: \""+getElement(element)+"\"");
				 
			}catch(NoSuchElementException e){
				fail("ERROR --\""+value+"\" Option is not found in the list for the object :: \""+getElement(element)+"\"");
			}catch(WebDriverException e){
				fail("ERROR --\""+value+"\" Option is unable to select for the object :: \""+getElement(element)+"\"");
			}
		}else{
			fail("ERROR --The element is not of html tag - SELECT , Invalid type for selectOptionByValue");
		}
	}
	
	public void selectOptionByIndex(WebElement element, int index){
		if(isSelectBox(getElement(element))){
			try{
				Select select = new Select(getElement(element));
				select.selectByIndex(index);
				System.out.println("INFO -- \""+index+"\" Option is selected for the object :: \""+getElement(element)+"\"");
			}catch(NoSuchElementException e){
				fail("ERROR --\""+index+"\" Option is not found in the list for the object :: \""+getElement(element)+"\"");
			}catch(WebDriverException e){
				fail("ERROR --\""+index+"\" Option is unable to select from the object :: \""+getElement(element)+"\"");
			}
		}else{
			fail("ERROR --The element is not of html tag - SELECT , Invalid type for selectOptionByIndex");
		}
	}
	
	public void verifySelectedOption(WebElement element,  String Option){
		if(isSelectBox(getElement(element))){
			Select selectElement = new Select(getElement(element));
			try{
				String selectedOptionText = selectElement.getFirstSelectedOption().getText();
				System.out.println("INFO -- The selected option in the select element \"" + selectedOptionText +"\" should be equal to expected value \"" +Option+"\"");
				Assert.assertEquals("The selected option in the select element \"" + selectedOptionText +"\" should be equal to expected value \"" +Option+"\"",Option, selectedOptionText);
			}catch(NoSuchElementException e){
				fail("ERROR --\""+Option+"\" Option is not found in the list for the object :: \""+getElement(element)+"\"");
			}catch(WebDriverException e){
				fail("ERROR --\""+Option+"\" Option is unable to verify for the object :: \""+getElement(element)+"\"");
			}
		}else{
			fail("ERROR --The element is not of html tag - SELECT ");
		}		
	}


	public void verifyAlertPresent(){
		System.out.println("INFO -- Alert should be present.");
		Assert.assertTrue(isAlertPresent(), "Alert should be present.");
	}

	private boolean isAlertPresent() {
		try{
			Alert alert = getWaiter().until(ExpectedConditions.alertIsPresent());
			if(alert!=null){
				System.out.println("INFO -- Alert is present.");
				return true;
			}else{
				fail("ERROR --Alert is not found to be present in the current page");
				return false;
			}
		}catch(TimeoutException e){
			fail("ERROR --Timeout Exception.");
			return false;
		}catch(WebDriverException e){
			fail("ERROR --Exception in webDriver.");
			return false;
		}
	}
	
	public void verifyAlertAbsent(){
		System.out.println("INFO -- Alert should be absent.");
		Assert.assertFalse(isAlertPresent(), "Alert should be absent.");
	}
	
	private Alert getAlert(){
		try{
			Alert alert = getWaiter().until(ExpectedConditions.alertIsPresent());
			if(alert!=null){
				System.out.println("INFO -- Alert is present.");
				return alert;
			}else{
				fail("ERROR --Alert is not found to be present in the current page");
				return null;
			}
		}catch(TimeoutException e){
			fail("ERROR --Timeout Exception.");
			return null;
		}
		
	}
	
	public void acceptAlert(){		
		try{
			Alert alert = androiddriver.switchTo().alert();
			alert.accept();
			System.out.println("INFO -- Clicked on ok button in the Alert.");
		}catch(WebDriverException e){
			fail("ERROR --Exception in clicking ok button in alert.");
		}
	}
	
	public void dismissAlert(){
		try{
			Alert alert = androiddriver.switchTo().alert();
			alert.dismiss();
			System.out.println("INFO -- Clicked on ok button in the Alert.");
		}catch(WebDriverException e){
			fail("ERROR --Exception in clicking ok button in alert.");
		}
	}
	
	public void waitForAlertAndAccept(){
		Alert alert = getAlert();
		try{
			alert.accept();
			System.out.println("INFO -- Clicked on ok button in the Alert.");
		}catch(WebDriverException e){
			fail("ERROR --Exception in clicking ok button in alert.");
		}
	}
	
	
	
	public void waitForAlertAndDismiss(){
		Alert alert = getAlert();
		try{
			alert.dismiss();
			System.out.println("INFO -- Clicked on Cancel button in the Alert.");
		}catch(WebDriverException e){
			fail("ERROR --Exception in clicking Cancel button in the alert.");
		}
	}
	
	public String getTextFromAlert(){
		Alert alert = getAlert();
		String alertText;		
		try{
			alertText = alert.getText();
			System.out.println("INFO -- Text from the alert is retrieved . text :: \""+alertText+"\"");
			return alertText;
		}catch(WebDriverException e){
			fail("ERROR --Error in retrieving the alert text.");
			return null;
		}
				
	}
	
	public void verifyCurrentURL(String url) throws Exception{
		try{
			System.out.println("INFO -- The URL of the current window should be \""+url+ "\" and actual url :: \""+ androiddriver.getCurrentUrl() + "\"");
			Assert.assertEquals("The URL of the current window should be \""+ url+ "\"",url, androiddriver.getCurrentUrl());
		}catch(Exception e){
			fail("INFO -- The URL of the current window is not equal url: \""+url+ "\" and actual url :: \""+ androiddriver.getCurrentUrl() + "\"");
		}
		 
	}
	
	public void switchToAlert(){
		try{
			androiddriver.switchTo().alert();
			System.out.println("INFO -- Switched into the alert.");
		}catch(NoAlertPresentException e){
			fail("ERROR --Alert not Present in the current page. Switching to alert is not possible.");
		}catch(WebDriverException e){
			fail("ERROR --Problem in switching to the alert.");
		}
	}

	
	public void dragAndDrop(WebElement elementFrom, WebElement elementTo) throws Exception {
		   Actions builder = new Actions(androiddriver);
		   Action dragAndDrop = builder.clickAndHold(elementFrom)
		       .moveToElement(elementTo)
		       .release(elementTo)
		       .build();
		   dragAndDrop.perform();
	}
	
	
	
	public List<WebElement> getElements(By by){
		List<WebElement> targetElements = null;
		try{
			targetElements = androiddriver.findElements(by);
			return targetElements;
		}catch(InvalidSelectorException e){
			System.out.println("FATAL --Locator not valid Exception or syntax wrong. Entered element is ::\""+by+"\" .");
			return null;
		}catch(NoSuchElementException e){
			System.out.println("FATAL --Element not found Exception. :: \""+by+"\" .");
			return null;
		}catch(WebDriverException e){
			System.out.println("FATAL --Element not found Exception. :: \""+by+"\" .");
			return null;
		}
	}
	
	
	
	public void visibilityOfAllElements(By locator){
		try{
			getWaiter().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
			System.out.println("Info --all elements is visible for the object ::\""+locator+"\" .");
		}catch(Exception e){
			fail("Error :: all elements is not visible for the object ::\""+locator+"\" .");
		}
		
	}
	
	//#########################   Password Encryption & Decryption Method  ############################################//
	
			public byte[] encrpyttext(String text2encrpyt) {
				try {
					encryptedvalue= cipher.encryptText(text2encrpyt);
					
				} catch (Exception e) {
				
					e.printStackTrace();
				}
				
				return encryptedvalue;				
			}
			
			
			public String decrypttext(byte[] encryptedvalue) {
				try {
					decryptedtext= cipher.decryptText(encryptedvalue);
					
				} catch (Exception e) {
				
					e.printStackTrace();
				}			
				return decryptedtext;				
			}
			
			//#########################   date conversion Method  ############################################//
			
			public static void changeAnyDateFormat(String stringDate, String dateFormat, String preDateFormat) throws ParseException {
				//in preDateFormat need to pass the format of given Date(eg- dd/MM/yyyy)
				SimpleDateFormat formatter = new SimpleDateFormat(preDateFormat);
		            Date date = formatter.parse(stringDate);
		            System.out.println(date);
				String newDate = new SimpleDateFormat(dateFormat).format(date);
				System.out.println("newDate ="+newDate);
			}
			
			
		//############.......................################
			public void checkPageIsReady(int waitTimeInSec) {

				JavascriptExecutor js = (JavascriptExecutor)androiddriver;


				if (js.executeScript("return document.readyState").toString().equals("complete")){ 
					System.out.println("Page Is loaded.");
					return; 
				} 

				for (int i=0; i<waitTimeInSec; i++){ 
					try {
						Thread.sleep(1000);
					}catch (InterruptedException e) {} 
					if (js.executeScript("return document.readyState").toString().equals("complete")){ 
						break; 
					}   
				}
			}
				
			public void waitTillElementToBeClickble(WebElement ele) throws Throwable {
				WebDriverWait wait = new WebDriverWait(androiddriver, TIME_OUT);
				try {
					wait.until(ExpectedConditions.elementToBeClickable(ele));
					
				} catch (Exception e) {
					System.out.println("element is not clickable");
					e.printStackTrace();
				} 
				}
			
			public void waitForPageToLoad(){
				androiddriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			}
			
			public void windowHandle(){
				Set<String>  wids =  androiddriver.getWindowHandles();
				Iterator<String>  itr  =  wids.iterator();
				ArrayList<String> aList = new ArrayList<String>();
				while(itr.hasNext()){
					aList.add(itr.next());
				}
				System.out.println(aList.size());
				parentName = aList.get(0); 
				childName =aList.get(1); 
			}
			
			public void switchToChildWindow(){
				windowHandle();
				androiddriver.switchTo().window(childName);
			}
			
			public void switchToParentWindow(){
				closeCurrentWindow();
				androiddriver.switchTo().window(parentName);
			} 
			
		       public ArrayList<String> getstringarraylist(By by){                  // Return String arraylist text of the Webelements
		              List<WebElement> WebElements=androiddriver.findElements(by);
		              
		              ArrayList<String> Fieldtext=new ArrayList<String>();
		              
		              for(int j=0;j<WebElements.size();j++){
		                     Fieldtext.add(j, WebElements.get(j).getText().trim());
		                           //slf4jLogger.info("Fields Present:"+Devicecreate_attribute.get(j).getText());
		                     }
		              
		              return Fieldtext;
		       }
		       
		       public void getPageScreenShot()
		     	{
		   		String contextName = androiddriver.getContext();
		   		androiddriver.context("NATIVE_APP");
		   		String imagePath="./MobileScreenShots"+"/"+getFormatedDateTime()+".png";
		   		EventFiringWebDriver edriver=new EventFiringWebDriver(androiddriver);
		   		try{
		   			File screenShot = androiddriver.getScreenshotAs(OutputType.FILE);
		   			FileUtils.copyFile(screenShot, new File(imagePath));
		   			}
		   		catch(Exception e)
		   		{
		   			System.out.println("scrennshot couldn't capture");
		   		}
		   		
		   	}      
		       
		       public void switchNative() throws InterruptedException {
		    	   Set<String> nativeView = androiddriver.getContextHandles();
		    	   for (String view : nativeView) {
		    	   if (view.startsWith("NATIVE")) {
		    	   androiddriver.context(view);
		    	   Thread.sleep(5000);
		    	   break;
		    	   }}}		       
		       
		       public void switchWebView() throws InterruptedException {
		    	   Set<String> webview = androiddriver.getContextHandles();
		    	   for (String view : webview) {
		    	   if (view.startsWith("WEBVIEW")) {
		    	   androiddriver.context(view);
		    	   Thread.sleep(5000);
		    	   break;
		    	   }}}		       
		       
				
			

}











