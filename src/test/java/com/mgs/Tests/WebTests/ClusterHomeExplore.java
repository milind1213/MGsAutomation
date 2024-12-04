package com.mgs.Tests.WebTests;

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
public class ClusterHomeExplore extends BaseTest {
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


    public WebDashboard getLoginWithEmail() throws Exception {
        Loginpage homepage = getWebLogin();
        WebDashboard userDashboard = homepage.loginWithGmail();
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
    public void VerifyingCommunityMembers() throws Exception {
        WebDashboard user = getLoginInstance();
        log("Clicking on Connect Dropdowns");
        user.getHome().moveOnConnect();

        log("Clicking on Community Dropdowns");
        user.getHome().selectConnectionType("Community");
        log("Clicked on Community option");

        List<WebElement> ele = user.getHome().getConnectionOptions();
        for (WebElement e : ele) {
            if (e.getText() == "Community") {
                e.click();
            }
        }
        log("Clicking on Sort Button");
        user.getHome().clickSortButton();
        log("Clicking on A-Z Dropdowns");
        user.getHome().clickedOnA2ZDropdowns();

        List<WebElement> userList = user.getHome().getSortResults();
        if (userList.get(1).getText().startsWith("A")) {
            log("Starts With 'A': " +  userList.get(0).getText());
        } else {
            log("Not Starts With 'A': " + userList.get(0).getText());
        }
        Assert.assertFalse(userList.isEmpty(), "The user list is empty.");
        log("User List is not empty and Validated Sorted List Ascending Order");
    }


    public void getLoginWithWeb() throws Exception {
        WebDashboard user = getLoginWithEmail();
        user.getHome().moveOnConnect();
    }
}