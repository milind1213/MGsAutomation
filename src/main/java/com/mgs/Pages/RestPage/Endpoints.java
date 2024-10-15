package com.mgs.Pages.RestPage;

public interface Endpoints {
    String users  = "/api/users/";
    String registers = "/api/register/";

    // Simple Grocery API
    String status = "/status";
    String products = "/products/";
    String getProduct = "products/{id}";
    String carts = "/carts/";
    String getCarts = "/carts/{cartId}";
    String addItemToCart = "/carts/{cartId}/items";
    String updateCarts = "/carts/{cartId}/items/{itemId}";
    String orders ="/orders/";
    String apiClients ="api-clients";
    String updateOrder = "/orders/{orderId}";
    String deleteOrder = "/orders/{orderId}";



}
