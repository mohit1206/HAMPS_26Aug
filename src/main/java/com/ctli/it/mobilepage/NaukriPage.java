package com.ctli.it.mobilepage;

import java.util.Set;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ctli.it.lib.DataTable;
import com.ctli.it.lib.MobileBaseClass;
import com.gargoylesoftware.htmlunit.WebWindowNotFoundException;
import com.relevantcodes.extentreports.ExtentTest;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class NaukriPage  extends MobileBaseClass{

	public NaukriPage(AndroidDriver androiddriver, ExtentTest testReport) {
		super(androiddriver, testReport);
		PageFactory.initElements(new AppiumFieldDecorator(androiddriver), this);
	}
	@FindBy(id="com.android.chrome:id/button_primary")
	public WebElement popupCancel;
	
	@FindBy(xpath="//div[@id=\"searchJob\"]")
	public WebElement tbx_SearchJob;
	@FindBy(xpath="//label[text()='Keyskills, Designations, Companies']")
	public WebElement tbx_keySkills;
	@FindBy(xpath="//input[@id=\"location\"]")
	public WebElement   btn_Location;
	@FindBy(xpath="//input[@placeholder=\"Work Experience\"]")
	public WebElement btn_WorkExperience;
	
	//input[@placeholder="Min. Salary (INR)"]
	
	
	@FindBy(xpath="//input[@placeholder=\"Min. Salary (INR)\"]")
	public WebElement btn_MinSalary;
	@FindBy(xpath="//button[text()='SEARCH JOBS']")
	public WebElement btn_searcjobs;
//	@FindBy(xpath="//input[@placeholder=\"Work Experience\"]")
//	public WebElement tbx_WorkExperience;
	@FindBy(xpath="//input[@name=\"suggestor\"]")
	public WebElement tbx_searchBy;
	
	@FindBy(xpath="//div[text()='Done']")
	public WebElement btn_Done;
	
	//input[@name="suggestor"]
	
	@FindBy(xpath="//input[@name=\"suggestor\"]")
	public WebElement tbx_Location;
	
	@FindBy(xpath="//a[text()='5 Years']")
	public WebElement btn_yearsExp;
	
	@FindBy(xpath="//a[text()='8 Lacs']")
	public WebElement Btn_MinmSalary;
	
	
	public void searchJobs() throws InterruptedException
	{
		
		Set<String> nativeView = androiddriver.getContextHandles();
		for (String view : nativeView) {
		if (view.startsWith("NATIVE")) {
			System.out.println(view);
		androiddriver.context(view);
		Thread.sleep(5000);
		break;
		}}
		click(popupCancel);
		switchWebView();
		
		tbx_SearchJob.click();
		System.out.println("seach jobs");
		checkPageIsReady();
		DataTable dt=new DataTable("C:\\Users\\ab73168\\Documents\\GitTestNg\\HAMPS_26Aug\\Resources\\FeatureInput.xlsx");
		String s1 = dt.getCellData("sheet1", 2, 2);
		System.out.println(dt.getCellData("sheet1", 3, 2)+"get location");
		System.out.println(s1);
		click(tbx_keySkills);
		type(tbx_searchBy, s1);
		click(btn_Done);
		
		click(btn_Location);
		type(tbx_Location, dt.getCellData("sheet1", 3, 2));
		click(btn_Done);
	
		
		click(btn_WorkExperience);
		click(btn_yearsExp);
		click(btn_MinSalary);
				click(Btn_MinmSalary);
		click(btn_searcjobs);
		
		
		
		
		
		
	}
	
	
	
	
	
	
	

}
