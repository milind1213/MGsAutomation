package com.mgs.Utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class UtilsJson {
  private static Gson gson = new Gson();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Using Jackson to read And Write
    public static <T> List<T> readsDataFromJson(String filePath, Class<T> clazz) throws IOException {
        return objectMapper.readValue(new File(filePath),
                objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }

    public static <T> void writesDataToJson(List<T> dataList, String filePath) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), dataList);
    }

    // Using Gson to read And Write
    public static <T> List<T> readDataFromJson(String filePath, Class<T> clazz) throws IOException {
        FileReader reader = new FileReader(filePath);
        Type typeOfT = TypeToken.getParameterized(List.class, clazz).getType();
        List<T> dataList = gson.fromJson(reader, typeOfT);
        reader.close();
        return dataList;
    }
    public static <T> void writeDataToJson(List<T> dataList, String filePath) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        gson.toJson(dataList, writer);
        writer.flush();
        writer.close();
    }


}
