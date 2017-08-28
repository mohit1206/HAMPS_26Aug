package com.ctli.it.mobiletestcase;

import org.testng.annotations.Test;

import com.ctli.it.lib.TestNgInitialization;
import com.ctli.it.mobilepage.NaukriHomePage;
import com.ctli.it.mobilepage.NaukriJobDescriptionPage;
import com.ctli.it.mobilepage.NaukriPage;

public class NaukriTestCase extends TestNgInitialization {
	@Test
	public void test() throws InterruptedException
	{
		NaukriPage np=new NaukriPage(mobiledriver, testReport);
		np.searchJobs();
		NaukriHomePage nhp=new NaukriHomePage(mobiledriver, testReport);
		nhp.filter();
		NaukriJobDescriptionPage ndesp=new NaukriJobDescriptionPage(mobiledriver, testReport);
		ndesp.storeDataIntoExcel();
	}

}
