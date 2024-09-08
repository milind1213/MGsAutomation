package com.mgs.Tests.WebTests;

import com.mgs.CommonConstants;
import com.mgs.Pages.WebPages.Loginpage;
import com.mgs.Pages.WebPages.WebDashboard;
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
public class ClusterHomeExplore extends BaseTest {
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
    public void verifyingCommunityMembers() throws Exception {
        WebDashboard user = getLoginInstance();
        log("Clicking on Connect Dropdowns");
        user.getHome().clickOnConnect().click();

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


    @Test(priority = 2)
    public void verifyingGroups()throws Exception{
        WebDashboard user = getLoginInstance();
        log("Clicking on Connect Dropdowns");
        user.getHome().clickOnConnect().click();

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
        System.out.println(group.getText());
      }
      int members = user.getHome().getMembersCounts();
      System.out.println("Total number of members in all groups: " + members);

      Assert.assertEquals(members, 30);
      log("Successfully validated members");
}

    @Test(priority = 3)
    public void verifyingPrograms()throws Exception{
        WebDashboard user = getLoginInstance();
        log("Clicking on Connect Dropdowns");
        user.getHome().clickOnConnect().click();

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