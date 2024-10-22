package com.mgs.Tests.ApiTest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mgs.CommonUtils.RestConfig;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static com.mgs.Pages.RestPage.Endpoints.users;
import static io.restassured.RestAssured.given;

public class SchemaValidation {
    String baseUrl = "https://reqres.in/";

    @Test(priority = 1)
    public void userSchemaValidations() {
        RestConfig reqConfig = new RestConfig();
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("page", 1);
        reqConfig.setQueryParameters(queryParam);
        Response res = RestConfig.Get(baseUrl, users, reqConfig);
        int page = res.jsonPath().getInt("page");
        Assert.assertEquals(page, 1, "Page Number Not found");
        res.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("user-chema.json"));
    }

    @Test(priority = 2)
    public void userCreateSemaValidations() {
        RestConfig reqConfig = new RestConfig();
        Response res = RestConfig.Post(baseUrl, users, reqConfig, createPayload("Milind", "Manager"));
        String name = res.jsonPath().getString("name");
        Assert.assertEquals(res.getStatusCode(), 201);
        Assert.assertEquals(name, "Milind", "Page Number Not found");
        res.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("create-user.json"));
    }

    @Test(priority = 3)
    public void userCreateSchemaValidationNegative() {
        RestConfig reqConfig = new RestConfig();
        Response res = RestConfig.Post(baseUrl, users, reqConfig, createPayload("Milind", null));
        Assert.assertEquals(res.getStatusCode(), 201);
        try {
            res.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("create-user.json"));
            Assert.fail("Expected schema validation to fail, but it passed.");
        } catch (AssertionError e) {
            System.out.println("Schema validation failed as expected: " + e.getMessage());
        }
    }

    public static String createPayload(String name, String job) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", name);
        payload.put("job", job);
        return new Gson().toJson(payload);
    }

    public void dataValidationWithDB() {
        // Step 1: Fetch API response
        RestAssured.baseURI = "https://fake-json-api.mock.beeceptor.com";
        Response res = given().
                when().get("/users").then().extract().response();
        String responseString = res.asString();
        JsonPath js = new JsonPath(responseString);

        // Extract specific data from the API response
        String id = js.getString("[2].id");
        String name = js.getString("[2].name");

        System.out.println("API Response:");
        System.out.println(res.prettyPrint());
        System.out.println("ID: " + id + " Name: " + name);

        // Step 2: Validate JSON Schema (optional if schema validation needed)
        res.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Json-Schema.json"));

        // Step 3: Fetch data from MySQL
        String dbUrl = "jdbc:mysql://localhost:3306/yourDatabase";
        String dbUser = "yourUsername";
        String dbPassword = "yourPassword";

        try {
            // Establish the connection to the database
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            Statement statement = connection.createStatement();

            // Query to get the specific record from the database
            String query = "SELECT id, name FROM users WHERE id = " + id;
            ResultSet resultSet = statement.executeQuery(query);

            // Compare the API response with the data from the database
            if (resultSet.next()) {
                String dbId = resultSet.getString("id");
                String dbName = resultSet.getString("name");

                // Validate if data matches
                if (id.equals(dbId) && name.equals(dbName)) {
                    System.out.println("Data matches with the database.");
                } else {
                    System.out.println("Data mismatch! API data does not match the database.");
                }
            } else {
                System.out.println("No matching data found in the database for ID: " + id);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        RestAssured.baseURI = "https://fake-json-api.mock.beeceptor.com";
        Response res = given().
                when().get("/users").then().extract().response();
        String responseString = res.asString();
        JsonPath js = new JsonPath(responseString);
        String id = js.getString("[2].id");
        String name = js.getString("[2].name");
        System.out.println(res.prettyPrint());
        System.out.println("ID: " + id + " Name: " + name);
        res.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Json-Schema.json"));
    }

    @Test
    public void postSchemaValidation() {
        RestAssured.baseURI = "https://reqres.in/";
        String ans = getPayload();
        Response res = given()
                .body(ans)
                .contentType("application/json")
                .when().put("/api/users/2")
                .then()
                .assertThat()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("JsonPut-schema.json"))
                .extract().response();
        System.out.println(res.prettyPrint());
    }

    static String getUsersPayload(){
        Map<String,Object> payload = new HashMap<>();
        payload.put("name","morpheusss");
        payload.put("job","Manager");
        return new Gson().toJson(payload);
    }

    static String getPayload() {
        JsonObject obj = new JsonObject();
        obj.addProperty("name", "morpheusss");
        obj.addProperty("job", "Manager");
        return obj.toString();
    }
}

