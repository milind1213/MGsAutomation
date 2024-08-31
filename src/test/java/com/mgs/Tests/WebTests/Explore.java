package com.mgs.Tests.WebTests;

import com.mgs.CommonConstants;
import com.mgs.Pages.Homepage   ;
import com.mgs.Pages.WebDashboard;
import com.mgs.TestBase.BaseTest;
import com.mgs.Utils.TestListeners;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static com.mgs.Utils.FileUtil.getProperty;

@Listeners(TestListeners.class)
public class Explore extends BaseTest {
    WebDriver driver;
    public WebDashboard getLoginInstance() throws Exception {
        Homepage homepage = getWebLogin();
        WebDashboard userDashboard = homepage.webLogin(
                getProperty(CommonConstants.MGS, CommonConstants.MGS_USERNAME),
                getProperty(CommonConstants.MGS, CommonConstants.MGS_PASSWORD)
        );
        String title = userDashboard.homepage.getTitle();
        Assert.assertEquals(title, "PeopleGrove for Career & Alumni");
        log("Successfully Validated title of the Homepage");
        return userDashboard;
    }

    public Homepage getWebLogin() {
        driver = getWeDriver();
        driver.get(getProperty(CommonConstants.MGS, CommonConstants.MGS_WEBURL));
        log("Launching the URL :["+getProperty(CommonConstants.MGS, CommonConstants.MGS_WEBURL)+"]");
        return new Homepage(driver);
    }

    @Test(priority = 1)
    public void testLoginFunctionality() throws Exception {
        WebDashboard user = getLoginInstance();
        user.getConnections().clickOnConnect();
        log("Clicked on the Connect button");

    }

}

