//package com.project.ecommerce.metadata;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import org.json.JSONException;
//import org.json.simple.JSONObject;
//
//import javax.persistence.AttributeConverter;
//import java.io.IOException;
//import java.util.Map;
//
//public class HashMapConverter implements AttributeConverter<JSONObject, String> {
//
//    @Override
//    public String convertToDatabaseColumn(JSONObject jsonData) {
//        String json;
//        try{
//            json = jsonData.toString();
//        }
//        catch (NullPointerException ex)
//        {
//            //extend error handling here if you want
//            json = "";
//        }
//        return json;
//    }
//
//    @Override
//    public JSONObject convertToEntityAttribute(String jsonDataAsJson) {
//        JSONObject jsonData;
//        try {
//            jsonData = new JSONObject(jsonDataAsJson);
//        } catch (JSONException ex) {
//            //extend error handling here if you want
//            jsonData = null;
//        }
//        return jsonData;
//    }
//}
