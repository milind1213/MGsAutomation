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
    public void verifyServerStatusIsUp() {
        RestConfig reqConfig = new RestConfig();
        Response res = RestConfig.Get(baseUrl, status, reqConfig);
        String serverStatus = res.jsonPath().getString("status");
        Assert.assertEquals(serverStatus, "UP", "Expected server status to be 'UP', but it is not.");
    }

    @Test(priority = 2)
    public void generateAccessToken() {
        String email = generateRandomEmail(5);
        String name = "Client " + generateRandomText(3);
        RestConfig reqConfig = new RestConfig();
        Response res = RestConfig.Post(baseUrl, apiClients, reqConfig, generateToken(name, email));
        accessToken = res.jsonPath().getString("accessToken");
        Assert.assertEquals(res.getStatusCode(), 201, "Failed to generate access token.");
    }

    @Test(priority = 3)
    public void fetchProductById() {
        RestConfig reqConfig = new RestConfig();
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("id", 4643);
        reqConfig.setPathParameters(pathParams);

        Response res = RestConfig.Get(baseUrl, getProduct, reqConfig);
        int actualId = res.jsonPath().getInt("id");
        Assert.assertEquals(actualId, 4643, "Product ID mismatch. Expected 4643.");
    }

    @Test(priority = 4)
    public void fetchAllProducts() {
        RestConfig reqConfig = new RestConfig();
        Response res = RestConfig.Get(baseUrl, products, reqConfig);
        allIds = res.jsonPath().getList("id");
        categories = res.jsonPath().getList("category");
        productsList = res.jsonPath().getList("name");
        List<Boolean> isAvailableStocks = res.jsonPath().getList("inStock");

        Assert.assertEquals(allIds.size(), categories.size(), "Mismatch in products and categories count.");
        Assert.assertEquals(allIds.size(), productsList.size(), "Mismatch in products and names count.");
        Assert.assertEquals(allIds.size(), isAvailableStocks.size(), "Mismatch in products and stock availability count.");

        for (int i = 0; i < allIds.size(); i++) {
            Assert.assertNotNull(res.jsonPath().getString("[" + i + "].id"), "Product ID should not be null.");
            Assert.assertNotNull(res.jsonPath().getString("[" + i + "].category"), "Product category should not be null.");
            Assert.assertNotNull(res.jsonPath().getString("[" + i + "].name"), "Product name should not be null.");
            Boolean inStock = res.jsonPath().getBoolean("[" + i + "].inStock");
            Assert.assertNotNull(inStock, "Stock availability should not be null.");
        }
    }

    @Test(priority = 5)
    public void validateProductsUsingPOJO() {
        RestConfig reqConfig = new RestConfig();
        Response res = RestConfig.Get(baseUrl, products, reqConfig);
        List<Products> products = res.jsonPath().getList("", Products.class);
        assertTrue(products.size() > 0, "Product list should not be empty.");

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
        System.out.println("Coffee Product Names: " + coffeeProductList);
    }

    @Test(priority = 6)
    public void createNewCart() {
        RestConfig reqConfig = new RestConfig();
        Response res = RestConfig.Post(baseUrl, carts, reqConfig, createCart(oldCartId, true));
        Assert.assertEquals(res.getStatusCode(), 201, "Failed to create cart.");
        cartId = res.jsonPath().getString("cartId");
        Assert.assertNotNull(cartId, "Cart ID should not be null.");
    }

    @Test(priority = 7, dependsOnMethods = "createNewCart")
    public void fetchCartById() {
        RestConfig reqConfig = new RestConfig();
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("cartId", cartId);

        reqConfig.setPathParameters(pathParams);
        Response res = RestConfig.Get(baseUrl, getCarts, reqConfig);
        Assert.assertEquals(res.getStatusCode(), 200, "Failed to retrieve cart details.");
    }

    @Test(priority = 8, dependsOnMethods = "createNewCart")
    public void addItemToCart() {
        RestConfig reqConfig = new RestConfig();
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("cartId", cartId);
        reqConfig.setPathParameters(pathParams);

        Response res = RestConfig.Post(baseUrl, addItemToCart, reqConfig, addItemsInCart("4643"));
        Assert.assertEquals(res.getStatusCode(), 201, "Failed to add item to cart.");

        itemId = res.jsonPath().getString("itemId");
        Assert.assertNotNull(itemId, "Item ID should not be null.");
    }

    @Test(priority = 9, dependsOnMethods = {"createNewCart", "addItemToCart"})
    public void updateCartItemQuantity() {
        RestConfig reqConfig = new RestConfig();
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("cartId", cartId);
        pathParams.put("itemId", itemId);
        reqConfig.setPathParameters(pathParams);

        Response res = RestConfig.Patch(baseUrl, updateCarts, reqConfig, updateCart("5"));
        Assert.assertEquals(res.getStatusCode(), 204, "Failed to update cart item.");
    }

    @Test(priority = 10, dependsOnMethods = {"generateAccessToken", "createNewCart"})
    public void createOrder() {
        String name = "MG No" + generateRandomText(3);
        RestConfig reqConfig = new RestConfig();
        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);
        reqConfig.setHeaders(headers);

        CreateOrder payload = new CreateOrder();
        payload.setCartId(cartId); // Assuming 'cartId' is set from 'createNewCart' method
        payload.setCustomerName(name);

        Response res = RestConfig.Post(baseUrl, orders, reqConfig, payload);

        int statusCode = res.getStatusCode();
        Assert.assertEquals(statusCode, 201, "Order creation failed. Expected status code 201.");

        boolean isOrderCreated = res.jsonPath().getBoolean("created");
        Assert.assertTrue(isOrderCreated, "Order was not created successfully.");

        orderId = res.jsonPath().getString("orderId");
        Assert.assertNotNull(orderId, "Order ID should not be null.");
    }

    @Test(priority = 11, dependsOnMethods = {"generateAccessToken"})
    public void fetchAllOrders() {
        RestConfig reqConfig = new RestConfig();

        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);
        reqConfig.setHeaders(headers);

        Response res = RestConfig.Get(baseUrl, orders, reqConfig);
        Assert.assertEquals(res.getStatusCode(), 200, "Failed to retrieve orders.");
    }

    @Test(priority = 12)
    public void updateOrderCustomerName() {
        String name = "Mady " + generateRandomText(3);
        RestConfig reqConfig = new RestConfig();
        String payload = "{\n" + "\"customerName\": \"" + name + "\"\n" + "}";

        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);
        reqConfig.setHeaders(headers);

        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("orderId", orderId);
        reqConfig.setPathParameters(pathParams);

        Response res = RestConfig.Patch(baseUrl, updateOrder, reqConfig, payload);
        Assert.assertEquals(res.getStatusCode(), 204, "Failed to update order customer name.");
    }

    @Test(priority = 13)
    public void deleteOrderById() {
        RestConfig reqConfig = new RestConfig();

        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);
        reqConfig.setHeaders(headers);

        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("orderId", orderId);
        reqConfig.setPathParameters(pathParams);

        Response res = RestConfig.Delete(baseUrl, deleteOrder, reqConfig);
        Assert.assertEquals(res.getStatusCode(), 204, "Failed to delete order.");
    }


}
