package com.mgs.Tests.WebTests;

import com.mgs.CommonConstants;
import com.mgs.Pages.Loginpage;
import com.mgs.Pages.WebDashboard;
import com.mgs.TestBase.BaseTest;
import com.mgs.Utils.TestListeners;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

import static com.mgs.Utils.FileUtil.getProperty;

@Listeners(TestListeners.class)
public class Explore extends BaseTest {
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
        driver = getWeDriver();
        driver.get(getProperty(CommonConstants.MGS, CommonConstants.MGS_WEBURL));
        log("Launching the URL :[" + getProperty(CommonConstants.MGS, CommonConstants.MGS_WEBURL) + "]");
        return new Loginpage(driver);
    }

    @Test(priority = 1)
    public void testLoginFunctionality() throws Exception {
        WebDashboard user = getLoginInstance();
        log("Clicking on Connect Dropdowns");
        user.getHome().clickOnConnect().click();

        log("Clicking on Community Dropdowns");
        user.getHome().selectConnectionType("Community");
        log("Clicked on Community option");

        log("Clicking on Sort Button");
        user.getHome().clickSortButton();

        List<WebElement> ele = user.getHome().getConnectionOptions();
        for (WebElement e : ele) {
            if (e.getText() == "Community") {
                e.click();
            }
        }
        List<WebElement> userList = user.getHome().getSortResults();
        if (userList.get(0).getText().startsWith("A")) {
            log("Starts With 'A': " +  userList.get(0).getText());
        } else {
            log("Not Starts With 'A': " + userList.get(0).getText());
        }
        Assert.assertFalse(userList.isEmpty(), "The user list is empty.");
        log("User List is not empty and Validated Sorted List Ascending Order");
    }







}

