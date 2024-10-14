package com.mgs.Tests.ApiTest;

import com.mgs.CommonConstants;
import com.mgs.CommonUtils.RestConfig;
import com.mgs.Pages.RestPage.Endpoints;
import com.mgs.Pages.RestPage.GroceriesPayloads;
import com.mgs.Utils.TestListeners;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mgs.Utils.FileUtil.getProperty;

@Listeners(TestListeners.class)
public class GroceryApis extends GroceriesPayloads implements Endpoints {
    List<String> allIds, categories, productsList;
    String cartId, oldCartId = "bx0-ycNjqIm5IvufuuZ09", itemId;
    String baseUrl = getProperty(CommonConstants.MGS, CommonConstants.REST_URL1);

    @Test(priority = 1)
    public void getServerStatus() {
        RestConfig reqConfig = new RestConfig();
        Response res = RestConfig.Get(baseUrl, status, reqConfig);
        String serverStatus = res.jsonPath().getString("status");
        Assert.assertEquals(serverStatus, "UP", "Server is not running and up");
    }

    @Test(priority = 2)
    public void getProductById(){
        RestConfig reqConfig = new RestConfig();
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("id", 4643);
        reqConfig.setPathParameters(pathParams);

        Response res = RestConfig.Get(baseUrl, getProduct, reqConfig);
        int actualId = res.jsonPath().getInt("id");
        Assert.assertEquals(actualId, 4643, "Product id Not found");
    }

    @Test(priority = 3)
    public void getAllProducts()  {
        RestConfig reqConfig = new RestConfig();
        Response res = RestConfig.Get(baseUrl, products, reqConfig);
        allIds = res.jsonPath().getList("id");
        categories = res.jsonPath().getList("category");
        productsList = res.jsonPath().getList("name");
        List<Boolean> isAvailableStocks = res.jsonPath().getList("inStock");

        Assert.assertEquals(allIds.size(), categories.size());
        Assert.assertEquals(allIds.size(), productsList.size());
        Assert.assertEquals(allIds.size(), isAvailableStocks.size());
        // Schema validation
        for (int i = 0; i < allIds.size(); i++) {
            Assert.assertNotNull(res.jsonPath().getString("[" + i + "].id"));
            Assert.assertNotNull(res.jsonPath().getString("[" + i + "].category"));
            Assert.assertNotNull(res.jsonPath().getString("[" + i + "].name"));
            Boolean inStock = res.jsonPath().getBoolean("[" + i + "].inStock");
            Assert.assertNotNull(inStock, "inStock should not be null");
        }
    }

    @Test(priority=4)
    public void createCarts(){
        RestConfig reqConfig = new RestConfig();
        Response res = RestConfig.Post(baseUrl,carts,reqConfig,createCart(oldCartId, true));
        Assert.assertEquals(res.getStatusCode(), 201);
        cartId = res.jsonPath().getString("cartId");
        Assert.assertNotNull(cartId, "Not Found Cart Id");
    }

    @Test(priority=5,dependsOnMethods = "createCarts")
    public void getCartIds(){
        RestConfig reqConfig = new RestConfig();
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("cartId", cartId);

        reqConfig.setPathParameters(pathParams);
        Response res = RestConfig.Get(baseUrl,getCarts,reqConfig);
        Assert.assertEquals(res.getStatusCode(), 200);
    }


    @Test (priority = 6, dependsOnMethods = "createCarts")
    public void addItemToCart() {
        RestConfig reqConfig = new RestConfig();
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("cartId",cartId);
        reqConfig.setPathParameters(pathParams);

        Response res = RestConfig.Post(baseUrl,addItemToCart,reqConfig, addItemsInCart("4643"));
        Assert.assertEquals(res.getStatusCode(), 201);

        itemId = res.jsonPath().getString("itemId");
        Assert.assertNotNull(itemId, "Not Found Item Id");
    }

    @Test (priority=7, dependsOnMethods = {"createCarts", "addItemToCart"})
    public void modifyCartItems(){
        RestConfig reqConfig = new RestConfig();
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("cartId",cartId);
        pathParams.put("itemId",itemId);
        reqConfig.setPathParameters(pathParams);

        Response res = RestConfig.Patch(baseUrl,updateCarts, reqConfig,updateCart("5"));
        Assert.assertEquals(res.getStatusCode(), 204);
    }

}
