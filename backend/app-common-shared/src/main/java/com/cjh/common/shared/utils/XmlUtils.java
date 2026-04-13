
package com.cjh.common.shared.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

public class XmlUtils {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final XmlMapper xmlMapper = new XmlMapper();
    private static final Logger logger = LoggerFactory.getLogger(XmlUtils.class);

    public static String parseXmlToJson(String xml) throws Exception {
        
        try{
            
            JsonNode jsonNode = xmlMapper.readTree(xml.getBytes());
            return mapper.writeValueAsString(jsonNode);

        }catch(IOException e){
            logger.error("XML을 JSON로 변환 중 오류 발생", e);
            return null;
        }
    }


    public static <T> T fromXml(String xml, Class<T> clazz) {
        try {
            
            xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            xmlMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            
            return xmlMapper.readValue(xml, clazz);

        } catch (Exception e) {
            logger.error("XML 변환 중 오류 발생", e);
            return null;
        }
    }
}
