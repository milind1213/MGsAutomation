package com.mgs.Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class UtilsJson {

    // Method to read data from JSON
    public static <T> List<T> readDataFromJson(String filePath, Class<T> clazz) throws IOException {
        Gson gson = new Gson();
        FileReader reader = new FileReader(filePath);

        // Use TypeToken to handle generic types
        Type typeOfT = TypeToken.getParameterized(List.class, clazz).getType();
        List<T> dataList = gson.fromJson(reader, typeOfT);
        reader.close();
        return dataList;
    }

    // Method to write data to JSON
    public static <T> void writeDataToJson(List<T> dataList, String filePath) throws IOException {
        Gson gson = new Gson();
        FileWriter writer = new FileWriter(filePath);

        gson.toJson(dataList, writer);
        writer.flush();
        writer.close();
    }
}
