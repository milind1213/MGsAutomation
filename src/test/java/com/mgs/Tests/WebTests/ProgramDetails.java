package com.mgs.Tests.WebTests;

import com.aventstack.extentreports.ExtentTest;
import com.mgs.CommonConstants;
import com.mgs.Pages.WebPages.Loginpage;
import com.mgs.Pages.WebPages.WebDashboard;
import com.mgs.TestBase.BaseTest;
import com.mgs.Utils.Reporting.TestListeners;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.util.List;
import static com.mgs.Utils.FileUtil.getProperty;

@Listeners(TestListeners.class)
public class ProgramDetails extends BaseTest {
    public ExtentTest extent;
    WebDriver driver;
    public WebDashboard getLoginInstance() throws Exception {
        Loginpage homepage = getWebLogin();
        WebDashboard userDashboard = homepage.webLogin(
                getProperty(CommonConstants.MGS, CommonConstants.MGS_USERNAME),
                getProperty(CommonConstants.MGS, CommonConstants.MGS_PASSWORD)
        );
        String title = userDashboard.loginpage.getTitle();
        Assert.assertEquals(title, "PeopleGrove for CareerPage & Alumni");
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
    public void VerifyingProgramDetails()throws Exception{
        WebDashboard user = getLoginInstance();
        log("Clicking on Connect Dropdowns");
        user.getHome().moveOnConnect();

        log("Clicking on Programs Dropdowns");
        user.getHome().selectConnectionType("Programs");
        log("Clicked on Programs option");

        List<WebElement> ele = user.getHome().getConnectionOptions();
        for (WebElement e : ele) {
            if (e.getText() == "Programs") {
                e.click();
                log("Clicked on Programs option");
            }
        }
        int programCount = user.getHome().getPrograms();
        System.out.println("Total number of programs: " + programCount);
    }

}
