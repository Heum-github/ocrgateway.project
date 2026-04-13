
package com.cjh.common.shared.utils;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class JsonUtils {
    
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            logger.error("JSON 변환 중 오류 발생", e);
            return null;
        }
    }

    public static String toPrettyJson(Object object) {
        try {
            String jsonData = toJson(object);
            JsonNode jsonDataNode = mapper.readTree(jsonData);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonDataNode);
        } catch (Exception e) {
            logger.error("JSON 변환 중 오류 발생", e);
            return null;            
        }
    }
    
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            logger.error("JSON 변환 중 오류 발생", e);
            return null;
        }
    }

    public static String parseJsonToMapValue(String jsonStr, String key) {
        try{
            Map<String, String> map = parseJsonToMap(jsonStr);
            return map.get(key);
        }catch(Exception e){
            return null;
        }
    }

    public static Map<String, String> parseJsonToMap(String jsonStr){
        try{
            return mapper.readValue(jsonStr, Map.class);
        }catch(IOException e){
            logger.error("JSON 변환 중 오류 발생", e);
            return null;
        }
    }

    public static String parseJsonToXml(String json) {
        try{
            ObjectMapper jsonMapper = new ObjectMapper();
            JsonNode jsonNode = jsonMapper.readTree(json);
            XmlMapper xmlMapper = new XmlMapper();
            return xmlMapper.writeValueAsString(jsonNode);
        }catch(IOException e){
            logger.error("JSON을 XML로 변환 중 오류 발생", e);
            return null;
        }
    }

    public static <T> T convertValue(Object source, Class<T> clazz) {
        try {
            return mapper.convertValue(source, clazz);
        } catch (IllegalArgumentException e) {
            logger.error("Map → Object 변환 중 오류 발생", e);
            return null;
        }
    }
}
