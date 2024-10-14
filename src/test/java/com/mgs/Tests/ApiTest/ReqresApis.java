package com.mgs.Tests.ApiTest;

import com.mgs.CommonConstants;
import com.mgs.CommonUtils.CommonRestAssured;
import com.mgs.Pages.RestPage.Endpoints;
import com.mgs.Pages.RestPage.ReqresPayloads;
import com.mgs.Utils.TestListeners;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.HashMap;

import static com.mgs.CommonConstants.generateRandomText;
import static com.mgs.Utils.FileUtil.getProperty;

@Listeners(TestListeners.class)
public class ReqresApis extends ReqresPayloads implements Endpoints {
    String baseUrl = getProperty(CommonConstants.MGS, CommonConstants.REST_URL);

    @Test(priority = 1)
    public void getUserDetails() {
        HashMap<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", 1);
        Response res = CommonRestAssured.Get(baseUrl, users, new HashMap<>(), queryParams);
        Assert.assertEquals(res.getStatusCode(), 200);
    }

    @Test(priority = 2)
    public void createUser() {
        String name = "John" + generateRandomText(3).toLowerCase();
        Response res = CommonRestAssured.Post(baseUrl, users, createUser(name, "leader"), new HashMap<>());
        Assert.assertEquals(res.getStatusCode(), 201);
        String actualName = res.jsonPath().getString("name");
        Assert.assertEquals(actualName, name);
    }

    @Test(priority = 3)
    public void UpdateUserDetails() {
        String name = "John" + generateRandomText(3).toLowerCase();
        int id = 3;
        Response res = CommonRestAssured.Put(baseUrl, users + id, updateUsers(name, "Manager"), new HashMap<>());
        Assert.assertEquals(res.getStatusCode(), 200);
        String updatedAt = res.jsonPath().getString("updatedAt");
        Assert.assertNotNull(updatedAt, "Updated Time is Not Updated");
    }

    @Test(priority = 4)
    public void registerExistingUsers() {
        String email ="eve.holt@reqres.in",password ="pistol";
        Response res = CommonRestAssured.Post(baseUrl,registers, registerUser(email, password), new HashMap<>());
        Assert.assertEquals(res.getStatusCode(), 200);
        String token = res.jsonPath().getString("token");
        System.out.println(token);
    }

}