package com.mgs.Pages.RestPage;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ReqresPayloads {

    public String createUser(String name,String jobTitle){
        return "{\n" +
                "    \"name\": \""+name+"\",\n" +
                "    \"job\": \""+jobTitle+"\"\n" +
                "}";
    }

    public String updateUsers(String name,String jobTitle){
       Map<String,Object> map = new HashMap<>();
       map.put("name",name);
       map.put("job",jobTitle);
       return  new Gson().toJson(map);
    }

    public String registerUser(String username, String password) {
        JsonObject json = new JsonObject();
        json.addProperty("email", username);
        json.addProperty("password", password);
        return json.toString();
    }
}
