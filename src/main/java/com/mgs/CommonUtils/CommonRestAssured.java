package com.mgs.CommonUtils;

import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.apache.commons.io.output.WriterOutputStream;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import static com.mgs.Utils.TestListeners.extentTest;
import static io.restassured.RestAssured.given;

public class CommonRestAssured {
    protected RequestSpecification req;
    protected Response res;
    StringWriter sw;
    PrintStream ps;

    private static RequestSpecification getRequestSpecification(String baseURL, String endPoint, Map<String, Object> headers) {
        return RestAssured.given().log().all()
                .baseUri(baseURL)
                .basePath(endPoint)
                .headers(headers);
    }

    public static Response Get(String baseURL, String endPoint, Map<String, Object> headers, Map<String, Object> queryParameters) {
        RequestSpecification requestSpecification = getRequestSpecification(baseURL, endPoint, headers);
        requestSpecification.queryParams(queryParameters);
        reqLogGet(requestSpecification);
        Response response = requestSpecification.get();
        responseLog(response);
        return response;
    }


    public static Response Post(String baseURL, String endPoint, String requestPayload, Map<String, Object> headers) {
        RequestSpecification requestSpecification = getRequestSpecification(baseURL, endPoint, headers);
        requestLog(requestSpecification.contentType(ContentType.JSON).body(requestPayload));
        Response response = requestSpecification.post();
        response.getBody().asString();
        responseLog(response);
        return response;
    }

    public static Response Put(String baseURL, String endPoint, String requestPayload, Map<String, Object> headers) {
        RequestSpecification requestSpecification = getRequestSpecification(baseURL, endPoint, headers);
        requestLog(requestSpecification.contentType(ContentType.JSON).body(requestPayload));
        Response response = requestSpecification.put();
        response.getBody().asString();
        responseLog(response);
        return response;
    }


    public static Response post1(String baseURL, String endPoint, String requestPayload, Map<String, Object> headers) {
        RequestSpecification requestSpecification = getRequestSpecification(baseURL, endPoint, headers);
        requestLog(requestSpecification.contentType(ContentType.JSON).body(requestPayload));
        Response response = requestSpecification.post();
        response.getBody().toString();
        return response;
    }

    public static Response Delete(String baseURL, String endPoint, String requestPayload, Map<String, Object> headers) {
        RequestSpecification requestSpecification = getRequestSpecification(baseURL, endPoint, headers);
        reqLogGet(requestSpecification.contentType(ContentType.JSON).body(requestPayload));
        Response response = requestSpecification.delete();
        response.getBody().asString();
        responseLog(response);
        return response;
    }

    public static Response Get(String baseURL, String endPoint, Map<String, Object> headers) {
        RequestSpecification requestSpecification = getRequestSpecification(baseURL, endPoint, headers);
        reqLogGet(requestSpecification);
        Response response = requestSpecification.get();
        responseLog(response);
        return response;
    }


    public static Response GetNew(String baseURL, String endPoint, Map<String, Object> headers, Map<String, Object> queryParameters) {
        RequestSpecification requestSpecification = getRequestSpecification(baseURL, endPoint, headers);
        requestSpecification.queryParams(queryParameters);
        Response response = requestSpecification.get();
        response.getBody().prettyPrint();
        return response;
    }

    public Response post(RequestSpecification request, String resourceURL) {
        res = request.post(resourceURL);
        return res;
    }

    protected Response get(RequestSpecification spec, String resourceURL) {
        sw = new StringWriter();
        ps = new PrintStream(new WriterOutputStream(sw), true);
        res = given().spec(spec).filter(new RequestLoggingFilter(ps)).log().all().when().get(resourceURL);
        sw.toString();
        res.prettyPrint();
        return res;
    }

    protected Response postNew(RequestSpecification spec, String resourceURL) {
        sw = new StringWriter();
        ps = new PrintStream(new WriterOutputStream(sw), true);
        res = given().spec(spec).filter(new RequestLoggingFilter(ps)).log().all().when().post(resourceURL);
        sw.toString();
        res.prettyPrint();
        return res;
    }

    protected Response put(RequestSpecification spec, String resourceURL) {
        sw = new StringWriter();
        ps = new PrintStream(new WriterOutputStream(sw), true);
        res = given().spec(spec).filter(new RequestLoggingFilter(ps)).log().all().when().put(resourceURL);
        return res;
    }

    public int getStatusCode(Response response) {
        return response.getStatusCode();
    }

    public Response getCurrentResponse() {
        return res;
    }

    public void waitFor(int i) {
        try {
            Thread.sleep(1000 * i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object parseJson(Response response, String path) {
        return response.jsonPath().get(path);
    }

    public String getJsonPathValue(String res, String key) {
        JsonPath json = new JsonPath(res.toString());
        Object value = json.get(key);
        return String.valueOf(value);
    }

    public String getRedirectedURL(String link) throws IOException {
        HttpURLConnection con = (HttpURLConnection) (new URL(link).openConnection());
        con.setInstanceFollowRedirects(false);
        con.connect();
        int responseCode = con.getResponseCode();
        System.out.println(responseCode);
        String location = con.getHeaderField("Location");
        System.out.println(location);
        return location;
    }


    public String decrypt(int xdLength, String splitterString, String encryptedText) {
        String decoded = null;
        try {
            decoded = new String(org.apache.commons.codec.binary.Base64.decodeBase64(splitterString));
            System.out.println(decoded);
            String one = encryptedText.split(decoded)[0];
            String two = encryptedText.replace(one, "").replace(decoded, "");

            String encryptionString = new String(
                    org.apache.commons.codec.binary.Base64.decodeBase64(two.substring(0, xdLength)),
                    StandardCharsets.UTF_8).replace("new Function('return \"", "").replace("\"')", "");
            String third = two.substring(xdLength, two.length());
            return getDecryptedString(one + third, new String(
                    org.apache.commons.codec.binary.Base64.decodeBase64(encryptionString), StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDecryptedString(String inputText, String secret) {
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

    private static void requestLog(RequestSpecification requestSpecification) {
        QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
        logInfo("Base URL is  : " + queryableRequestSpecification.getBaseUri());
        logInfo("Base Path is  : " + queryableRequestSpecification.getBasePath());
        logInfo("Headers Are  : ");
        logHeaders(queryableRequestSpecification.getHeaders().asList());
        logInfo("Request Body is :  ");
        logJson(queryableRequestSpecification.getBody());
    }

    private static void responseLog(Response response) {
        logInfo("Response Status is  : " + response.getStatusCode());
        //logInfo("Response Headers are :  ");
        //logHeaders(response.getHeaders().asList());
        logInfo("Response Body is :  ");
        logJson(response.getBody().prettyPrint());
    }

    private static void reqLogGet(RequestSpecification requestSpecification) {
        QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
        logInfo("Base URL is  : " + queryableRequestSpecification.getBaseUri());
        logInfo("EndPoint is  : " + queryableRequestSpecification.getBasePath());
        /* logInfo("Headers Are  : ");
        logHeaders(queryableRequestSpecification.getHeaders().asList());
        logInfo("Query Parameters Are  : ");
        queryableRequestSpecification.getQueryParams().forEach((key, value) -> {
            logInfo(key + " : " + value);
         });
         */
    }

    protected static void logInfo(String log) {
        if (extentTest.get() != null) {
            extentTest.get().info(MarkupHelper.createLabel(log, ExtentColor.PINK));
        } else {
            System.out.println(log);
        }
    }

    static void logJson(String json) {
        if (extentTest.get() != null) {
            extentTest.get().info(MarkupHelper.createCodeBlock(json, CodeLanguage.JSON));
        } else {
            System.out.println(json);
        }
    }

    private static void logHeaders(List<Header> headersList) {
        String[][] arrayHeaders = headersList.stream().map(header -> new String[]{header.getName(), header.getValue()})
                .toArray(String[][]::new);
        if (extentTest.get() != null) {
            extentTest.get().info(MarkupHelper.createTable(arrayHeaders));
        } else {
            for (String[] header : arrayHeaders) {
                System.out.println(header[0] + ": " + header[1]);
            }
        }
    }


}
