package com.example.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {
    private static ObjectMapper objectMapper =  new ObjectMapper();

    /**
     * 对象转json字符串
     * @param obj
     * @return
     * @throws JsonProcessingException
     */
    public static String toJson(Object obj) throws JsonProcessingException {
      return objectMapper.writeValueAsString(obj);
    }

    public static Object toObj(String json) throws IOException {
        return objectMapper.readValue(json,Object.class);
    }
}
