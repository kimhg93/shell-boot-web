package com.boot.shell.common.object;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {

    public Map<String, Object> jsonToMap(String json) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> resultMap = objectMapper.readValue(json, HashMap.class);
            return resultMap;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String mapToJson(Map<String, Object> map) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(map);
            return json;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
