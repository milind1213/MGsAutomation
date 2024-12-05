package com.mgs.CommonUtils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import lombok.Getter;
import lombok.Setter;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static com.mgs.Utils.Reporting.TestListeners.extentTest;

@Getter
@Setter
public class RestConfig {
    private Map<String, Object> headers;
    private Map<String, Object> cookies;
    private Map<String, Object> queryParameters;
    private Map<String, Object> pathParameters;

    public RestConfig() {
        this.headers = new HashMap<>();
        this.cookies = new HashMap<>();
        this.queryParameters = new HashMap<>();
        this.pathParameters = new HashMap<>();
    }

    public static RequestSpecification getRequestSpecification(String baseURL, String endPoint, RestConfig config) {
        RequestSpecification spec = RestAssured.given().log().all()
                .baseUri(baseURL)
                .basePath(endPoint)
                .headers(Optional.ofNullable(config.getHeaders()).orElse(Collections.emptyMap()))  // Add headers
                .cookies(Optional.ofNullable(config.getCookies()).orElse(Collections.emptyMap()))  // Add cookies
                .pathParams(Optional.ofNullable(config.getPathParameters()).orElse(Collections.emptyMap()));  // Add path parameters
                 Optional.ofNullable(config.getQueryParameters()).ifPresent(spec::queryParams);
        return spec;
    }

    public static Response Get(String baseURL, String endPoint, RestConfig config)
    {
        RequestSpecification requestSpecification = getRequestSpecification(baseURL, endPoint, config);
        requestLog(requestSpecification);
        Response response = requestSpecification.get();
        responseLog(response);
        return response;
    }

    public static Response Post(String baseURL, String endPoint, RestConfig config, Object body)
    {
        RequestSpecification requestSpecification = getRequestSpecification(baseURL, endPoint, config).contentType(ContentType.JSON)
                .body(body);
        requestLog(requestSpecification);  // LogRequest Details
        Response response = requestSpecification.post();
        responseLog(response);  // LogResponse Details
        return response;
    }

    public static Response Patch(String baseURL, String endPoint, RestConfig config, Object body)
    {
        RequestSpecification requestSpecification = getRequestSpecification(baseURL, endPoint, config).contentType(ContentType.JSON)
                .body(body);
        requestLog(requestSpecification);  // LogRequest Details
        Response response = requestSpecification.patch();
        responseLog(response);  // LogResponse Details
        return response;
    }


    public static Response Delete(String baseURL, String endPoint, RestConfig config)
    {
        RequestSpecification requestSpecification = getRequestSpecification(baseURL, endPoint, config);
        requestLog(requestSpecification);
        Response response = requestSpecification.delete();
        responseLog(response);
        return response;
    }


    public static void requestLog(RequestSpecification requestSpecification)
    {
        QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
        logInfo("Base URL : " + queryableRequestSpecification.getBaseUri());
        logInfo("EndPoint is : " + queryableRequestSpecification.getBasePath());
        if (!queryableRequestSpecification.getQueryParams().isEmpty())
        {
            logInfo("Query Parameters : ");
            queryableRequestSpecification.getQueryParams().forEach((key, value) -> logInfo(key + " : " + value));
        }
        if (!queryableRequestSpecification.getHeaders().asList().isEmpty())
        {
            logInfo("Request Headers : ");
            logHeaders(queryableRequestSpecification.getHeaders().asList());
        }
        String requestBody = queryableRequestSpecification.getBody();
        if (requestBody != null && !requestBody.trim().isEmpty())
        {
            logInfo("Request Body : ");
            logJson(requestBody);
        }
    }

    public static void responseLog(Response response)
    {
        logInfo("Status Code is :  " + response.getStatusCode());
        if (!response.getHeaders().asList().isEmpty())
        {
            // logInfo("Response Headers : ");
            // logHeaders(response.getHeaders().asList());*/
        }
        String responseBody = response.getBody().asString();
        if (responseBody != null && !responseBody.trim().isEmpty())
        {
            logInfo("Response Body : ");
            logJson(response.getBody().prettyPrint());
        }
    }

    protected static void logInfo(String log)
    {
        if (extentTest.get() != null)
        {
            try {
                extentTest.get().info(MarkupHelper.createLabel(log, ExtentColor.PINK));
            } catch (Exception e) {
                System.err.println("Error logging to Extent Report: " + e.getMessage());
            }
        } else {
            System.out.println(log);
        }
    }

    static void logJson(String json)
    {
        if (extentTest.get() != null)
        {
            extentTest.get().info(MarkupHelper.createCodeBlock(json, CodeLanguage.JSON));
        } else {
            System.out.println(json);
        }
    }

    private static void logHeaders(List<Header> headersList)
    {
        String[][] arrayHeaders = headersList.stream().map(header -> new String[]{header.getName(), header.getValue()})
                .toArray(String[][]::new);
        if (extentTest.get() != null)
        {
            extentTest.get().info(MarkupHelper.createTable(arrayHeaders));
        } else
        {
            for (String[] header : arrayHeaders) {
                System.out.println(header[0] + ": " + header[1]);
            }
        }
    }

    public String getDecryptedString(String inputText, String secret)
    {
        SecretKey key = new SecretKeySpec(secret.getBytes(), "AES");
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            Thread.sleep(1000);
            return new String(cipher.doFinal(org.apache.commons.codec.binary.Base64.decodeBase64(inputText)));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

