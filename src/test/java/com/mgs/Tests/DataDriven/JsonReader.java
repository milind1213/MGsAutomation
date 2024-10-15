package com.mgs.Tests.DataDriven;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;

public class JsonReader {
   @Test
    public static void checkCode() throws IOException, ParseException {
       String filePath = System.getProperty("user.dir") + "/TestData/FixedDeposits.xlsx";
       JSONParser jsonParser = new JSONParser();
       FileReader reader = new FileReader(filePath);
       Object obj = jsonParser.parse(reader);
       JSONObject jsonObject =  (JSONObject)obj;
       jsonObject.get("");
    }
}
