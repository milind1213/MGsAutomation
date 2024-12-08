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
    String designation = "QA Manager";
    String colName = "Job Requirements";
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

    @Test(priority = 1)
    public void TC_01_JobOpeningValidation() throws Exception {
        WebDashboard user = getLoginInstance();
        user.getCareer().selectCareerOption("Career","Jobs");

        List<String> jobs = user.getCareer().getJobList();
        boolean isJobFound = jobs.contains(designation);

        Assert.assertTrue(isJobFound, designation +"position is not listed in job openings.");
        log("Successfully Validated the ["+ designation +"] Open position");
    }

    @Test(priority = 1)
    public void TC_02_JobApplication() throws Exception {
        String filePath = "/home/milind/MG-TECH/TestData/testDocs.pdf";  // File path for the resume
        WebDashboard user = getLoginInstance();  // Getting user instance after login

        log("Navigating to ['Career' > 'Jobs'] page.");
        user.getCareer().selectCareerOption("Career", "Jobs");

        log(String.format("Selecting job application for designation: [%s].", designation));
        user.getCareer().JobApplication(designation);

        log(String.format("Selecting profile column: [%s].", colName));
        user.getCareer().selectProfileColumn(colName);

        log("Clicking on the [Apply Now] button.");
        user.getCareer().clickOnApplyButton();

        log(String.format("Uploading resume from file path: [%s].", filePath));
        user.getCareer().clickUploadResumeBtn(filePath);

        String successMsg = user.getCareer().getUploadSuccessMessage();
        Assert.assertEquals(successMsg, "Resume uploaded successfully","Resume upload failed.");
        log("Successfully validated the 'Resume uploaded successfully' message.");
    }

    @Test(priority = 3)
    public void TC_03_JobPosting() throws Exception {
        String filePath = "/home/milind/MG-TECH/TestData/testDocs.pdf";  // File path for the resume
        WebDashboard user = getLoginInstance();  // Getting user instance after login

        log("Navigating to ['Career' > 'Jobs'] page.");
        user.getCareer().selectCareerOption("Career", "Jobs");

        user.getCareer().clickJobPostBtn();
        user.getCareer().clickShareOrHireBtn("Share");
        user.getCareer().selectCompanyName("Test","Qualitest");

    }





}
