package com.mgs.Tests.WebTests;

import com.mgs.CommonConstants;
import com.mgs.Pages.WebPages.Loginpage;
import com.mgs.Pages.WebPages.WebDashboard;
import com.mgs.TestBase.BaseTest;
import com.mgs.Utils.Reporting.TestListeners;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

import static com.mgs.Utils.FileUtil.getProperty;

@Listeners(TestListeners.class)
public class CareerDetails extends BaseTest {
    WebDriver driver;
    public WebDashboard getLoginInstance() throws Exception {
        Loginpage homepage = getWebLogin();
        WebDashboard userDashboard = homepage.webLogin(
                getProperty(CommonConstants.MGS, CommonConstants.MGS_USERNAME),
                getProperty(CommonConstants.MGS, CommonConstants.MGS_PASSWORD)
        );
        String title = userDashboard.loginpage.getTitle();
        Assert.assertEquals(title, "PeopleGrove for Career & Alumni");
        log("Successfully Validated title of the Homepage");
        return userDashboard;
    }

    public Loginpage getWebLogin() {
        driver = getWebDriver();
        driver.get(getProperty(CommonConstants.MGS, CommonConstants.MGS_WEBURL));
        log("Launching the URL :[" + getProperty(CommonConstants.MGS, CommonConstants.MGS_WEBURL) + "]");
        return new Loginpage(driver);
    }

    @Test
    public void TC_01_JobOpeningValidation() throws Exception {
        WebDashboard user = getLoginInstance();
        user.getCareer().selectCareerOption("Career","Jobs");

        List<String> jobs = user.getCareer().getJobList();
        boolean isJobFound = jobs.contains("QA Manager");

        Assert.assertTrue(isJobFound, "QA Manager position is not listed in job openings.");


    }

    @Test
    public void TC_02_JobApplication() throws Exception {
        WebDashboard user = getLoginInstance();
        user.getCareer().selectCareerOption("Career","Jobs");


    }


}
