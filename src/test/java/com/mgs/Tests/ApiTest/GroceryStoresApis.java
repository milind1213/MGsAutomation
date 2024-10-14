package com.mgs.Tests.ApiTest;

import com.mgs.CommonConstants;
import com.mgs.CommonUtils.CommonRestAssured;
import com.mgs.Pages.RestPage.Endpoints;
import com.mgs.Pages.RestPage.GroceryStorePayloads;
import com.mgs.Utils.TestListeners;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

import static com.mgs.Utils.FileUtil.getProperty;

@Listeners(TestListeners.class)
public class GroceryStoresApis extends GroceryStorePayloads implements Endpoints {
    String baseUrl = getProperty(CommonConstants.MGS, CommonConstants.REST_URL1);

    @Test
    public void getStatus() {
        Response res = CommonRestAssured.Get(baseUrl, status, new HashMap<>());
        String status = res.jsonPath().getString("status");
        Assert.assertEquals(status, "UP", "Server is Not running and up");
    }

    @Test
    public void getProductById() {
        int id = 4643;
        Response res = CommonRestAssured.Get(baseUrl,products+id, new HashMap<>());
        int actualId = res.jsonPath().getInt("id");
        System.out.println("ID "+ actualId);
    }

    @Test
    public void getAllProducts() {
        Response res = CommonRestAssured.Get(baseUrl, products, new HashMap<>());
        String id = res.jsonPath().getString("[0].id");
        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(id, "4643");

        List<String> allIds = res.jsonPath().getList("id");
        List<String> categories = res.jsonPath().getList("category");
        List<String> products = res.jsonPath().getList("name");
        List<Boolean> isAvailableStocks = res.jsonPath().getList("inStock");

        Assert.assertEquals(allIds.size(), categories.size());
        Assert.assertEquals(allIds.size(), products.size());
        Assert.assertEquals(allIds.size(), isAvailableStocks.size());
        // Schema validation
        for (int i = 0; i < allIds.size(); i++) {
            Assert.assertNotNull(res.jsonPath().getString("[" + i + "].id"));
            Assert.assertNotNull(res.jsonPath().getString("[" + i + "].category"));
            Assert.assertNotNull(res.jsonPath().getString("[" + i + "].name"));
            Boolean inStock = res.jsonPath().getBoolean("[" + i + "].inStock");
            Assert.assertNotNull(inStock, "inStock should not be null"); // Ensure the field exists
        }
    }




}
