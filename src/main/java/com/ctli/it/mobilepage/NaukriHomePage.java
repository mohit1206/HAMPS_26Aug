package com.ctli.it.mobilepage;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ctli.it.lib.MobileBaseClass;
import com.relevantcodes.extentreports.ExtentTest;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class NaukriHomePage extends MobileBaseClass {

	public NaukriHomePage(AndroidDriver androiddriver, ExtentTest testReport) {
		super(androiddriver, testReport);
		// TODO Auto-generated constructor stub
		PageFactory.initElements(new AppiumFieldDecorator(androiddriver), this);

	}
	
	@FindBy(xpath="//i[text()='Refine']")
	public WebElement btn_Filter;
	
	@FindBy(xpath="//div[text()='Industry']")
	public WebElement btn_Industry;

	@FindBy(xpath="(//input[contains(@id,\"industry\")][@type='checkbox']/..)[1]")
	public WebElement cbx_itsoftware;

	@FindBy(xpath="//button[text()='Apply FIlters']")
	public WebElement btn_ApplyFilters;

	@FindBy(xpath="//div[text()='Education']")
	public WebElement btn_Education;
	
	@FindBy(xpath="//span[text()='B.Tech/B.E.']/../../span[1]")
	public WebElement cbx_BEBtech;
	
	@FindBy(xpath="//i[text()='FilterFilled']")
	public WebElement btn_secondFilter;
	@FindBy(xpath="//span[text()='Later']")
	public WebElement popupLater;
	
	
	
	
	public void filter()
	{
		
//		click(btn_Filter);
		
		
		try
		{
			
		click(popupLater);
		}
		catch(Exception e)
		{
			
		}
		
		click(btn_Filter);
		
		click(btn_Industry);
		click(cbx_itsoftware);
		click(btn_ApplyFilters);
		click(btn_secondFilter);
		click(btn_Education);
		click(cbx_BEBtech);
		click(btn_ApplyFilters);
		
		List<WebElement> list = androiddriver.findElements(By.xpath("//span[text()='Global Recruiters']"));
		list.get(list.size()-1).click();
		
		
		
	}


}
