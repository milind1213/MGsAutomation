package com.mgs.Tests.ApiTest;

import com.mgs.CommonConstants;
import com.mgs.CommonUtils.RestConfig;
import com.mgs.Pages.RestPage.Endpoints;
import com.mgs.Pages.RestPage.GroceriesPayloads;
import com.mgs.Pages.RestPage.POJO.CreateOrder;
import com.mgs.Pages.RestPage.POJO.Products;
import com.mgs.Utils.TestListeners;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mgs.CommonConstants.generateRandomEmail;
import static com.mgs.CommonConstants.generateRandomText;
import static com.mgs.Utils.FileUtil.getProperty;
import static org.testng.Assert.assertTrue;

@Listeners(TestListeners.class)
public class GroceriesApis extends GroceriesPayloads implements Endpoints {
    private static String accessToken;
    List<String> allIds, categories, productsList;
    String cartId, oldCartId = "bx0-ycNjqIm5IvufuuZ09", itemId , orderId ;
    String baseUrl = getProperty(CommonConstants.MGS, CommonConstants.REST_URL1);

    @Test(priority = 1)
    public void getServerStatus() {
        RestConfig reqConfig = new RestConfig();
        Response res = RestConfig.Get(baseUrl, status, reqConfig);
        String serverStatus = res.jsonPath().getString("status");
        Assert.assertEquals(serverStatus, "UP", "Server is not running and up");
    }

    @Test(priority = 2)
    public void getToken(){
        String email = generateRandomEmail(5);
        String name = "Client "+generateRandomText(3);
        RestConfig reqConfig = new RestConfig();
        Response res = RestConfig.Post(baseUrl,apiClients,reqConfig,generateToken(name,email));
        accessToken = res.jsonPath().getString("accessToken");
        Assert.assertEquals(res.getStatusCode(), 201);
    }

    @Test(priority = 3)
    public void getProductById(){
        RestConfig reqConfig = new RestConfig();
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("id", 4643);
        reqConfig.setPathParameters(pathParams);

        Response res = RestConfig.Get(baseUrl, getProduct, reqConfig);
        int actualId = res.jsonPath().getInt("id");
        Assert.assertEquals(actualId, 4643, "Product id Not found");
    }

    @Test(priority = 4)
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

    @Test(priority = 5)
    public void validateProductsWithPOJO() {
        RestConfig reqConfig = new RestConfig();
        Response res = RestConfig.Get(baseUrl, products, reqConfig);
        List<Products> products = res.jsonPath().getList("", Products.class);
        assertTrue(products.size() > 0, "Product List Should Not be Empty");

        List<String> productNamesInStock = new ArrayList<>();
        for (Products product : products) {
            if (product.isInStock()) {
                productNamesInStock.add(product.getName());
            }
        }
        System.out.println("Number of Products In Stock: " + productNamesInStock.size());

        List<String> coffeeProductList = new ArrayList<>();
        List<Integer> coffeeIDs = new ArrayList<>();
        for (Products product : products) {
            if (product.getCategory().equalsIgnoreCase("coffee")) {
                coffeeProductList.add(product.getName());
                coffeeIDs.add(product.getId());
            }
        }
        System.out.println("Coffee Products: " + coffeeIDs);
        System.out.println("Coffee Products: " + coffeeProductList);
    }


    @Test(priority=6)
    public void createCarts(){
        RestConfig reqConfig = new RestConfig();
        Response res = RestConfig.Post(baseUrl,carts,reqConfig,createCart(oldCartId, true));
        Assert.assertEquals(res.getStatusCode(), 201);
        cartId = res.jsonPath().getString("cartId");
        Assert.assertNotNull(cartId, "Not Found Cart Id");
    }

    @Test(priority=7,dependsOnMethods = "createCarts")
    public void getCartIds(){
        RestConfig reqConfig = new RestConfig();
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("cartId", cartId);

        reqConfig.setPathParameters(pathParams);
        Response res = RestConfig.Get(baseUrl,getCarts,reqConfig);
        Assert.assertEquals(res.getStatusCode(), 200);
    }


    @Test (priority =8, dependsOnMethods = "createCarts")
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

    @Test (priority=9, dependsOnMethods = {"createCarts", "addItemToCart"})
    public void modifyCartItems(){
        RestConfig reqConfig = new RestConfig();
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("cartId",cartId);
        pathParams.put("itemId",itemId);
        reqConfig.setPathParameters(pathParams);
        Response res = RestConfig.Patch(baseUrl,updateCarts, reqConfig,updateCart("5"));
        Assert.assertEquals(res.getStatusCode(), 204);
    }


    @Test(priority = 10, dependsOnMethods = {"getToken", "createCarts"})
    public void createNewOrder() {
        RestConfig reqConfig = new RestConfig();

        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);
        reqConfig.setHeaders(headers);

        CreateOrder payload = new CreateOrder();
        payload.setCartId(cartId); // Assuming 'cartId' is set from 'createCarts' method
        payload.setCustomerName("John Doe");

        Response res = RestConfig.Post(baseUrl, orders, reqConfig, payload);

        int statusCode = res.getStatusCode();
        Assert.assertEquals(statusCode, 201, "Expected status code 201 but got " + statusCode);

        boolean isOrderCreated = res.jsonPath().getBoolean("created");
        Assert.assertTrue(isOrderCreated, "Order was not created successfully.");

        orderId = res.jsonPath().getString("orderId");
        Assert.assertNotNull(orderId, "Order ID was not generated in the response.");
    }

    @Test (priority=11, dependsOnMethods = {"getToken"})
    public void getAllOrder(){
        RestConfig reqConfig = new RestConfig();

        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);
        reqConfig.setHeaders(headers);

        Response res= RestConfig.Get(baseUrl, orders,reqConfig);
        Assert.assertEquals(res.getStatusCode(),200);
    }

    @Test(priority = 12)
    public void updateOrder(){
        String name = "Mady "+generateRandomText(3);
        RestConfig reqConfig = new RestConfig();
        String payload = "{\n"+"\"customerName\": \""+name+"\"\n"+"}";

        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);
        reqConfig.setHeaders(headers);

        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("orderId",orderId);
        reqConfig.setPathParameters(pathParams);

        Response res= RestConfig.Patch(baseUrl, updateOrder,reqConfig,payload);
        Assert.assertEquals(res.getStatusCode(),204);
    }


    @Test(priority = 13)
    public void deleteOrder(){
        RestConfig reqConfig = new RestConfig();

        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);
        reqConfig.setHeaders(headers);

        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("orderId",orderId);
        reqConfig.setPathParameters(pathParams);

        Response res= RestConfig.Delete(baseUrl, deleteOrder,reqConfig);
        Assert.assertEquals(res.getStatusCode(),204);
    }

}
