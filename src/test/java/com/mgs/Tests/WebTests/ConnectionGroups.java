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
public class ConnectionGroups extends BaseTest {
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
    public void VerifyingConnectionsGroups()throws Exception{
        WebDashboard user = getLoginInstance();
        log("Clicking on Connect Dropdowns");
        user.getHome().moveOnConnect();

        log("Clicking on Groups Dropdowns");
        user.getHome().selectConnectionType("Groups");
        log("Clicked on Groups option");

        List<WebElement> ele = user.getHome().getConnectionOptions();
        for (WebElement e : ele) {
            if (e.getText() == "Groups") {
                e.click();
                log("Clicked on Groups option");
            }
        }
        List<WebElement> groupList =user.getHome().getGroupNames();
        for(WebElement group : groupList){
            log("Group Members : "+group.getText());
        }
        int members = user.getHome().getMembersCounts();
        Assert.assertEquals(members, 30);
        log("Successfully validated members : "+ members);
    }
}
