package com.mgs.Pages.RestPage;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class GroceriesPayloads {

    public String createCart(String id, boolean isCreated) {
        return "{\n" +" \"created\": " + isCreated + ",\n" +
                "\"cartId\": \"" + id + "\"\n" +"}";
    }

    public String addItemsInCart(String productId) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("productId",productId);
        return new Gson().toJson(requestBody);
    }

    public String updateCart(String Qty) {
        return "{\n"+"\"quantity\": "+Qty+"\n"+"}\n";
    }

    public String generateToken(String name,String email) {
        JsonObject json = new JsonObject();
        json.addProperty("clientName", name);
        json.addProperty("clientEmail", email);
        return json.toString();
    }





}
