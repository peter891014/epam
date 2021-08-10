package com.epam.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Iterator;

public class JsonUtils {

    public static String getKey(JSONObject jsonObject, String value) {
        String keyValue = "";
        Iterator<String> keys = jsonObject.keySet().iterator();// jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            if (jsonObject.get(key) instanceof String) {
                if (((String) jsonObject.get(key)).equals(value)) {
                    keyValue = key.toString();
                    break;
                }
            }else if (jsonObject.get(key) instanceof JSONObject) {
                JSONObject innerObject = (JSONObject) jsonObject.get(key);
                keyValue = getKey(innerObject, value);
                if (!keyValue.equals("")) {
                    break;
                }
            } else if (jsonObject.get(key) instanceof JSONArray) {
                JSONArray innerObject = (JSONArray) jsonObject.get(key);
                keyValue = getKey(innerObject, key, value);
                if (!keyValue.equals("")) {
                    break;
                }
            } else if (jsonObject.get(key) instanceof Integer) {
                if ((jsonObject.get(key).toString()).equals(value)) {
                    keyValue = key.toString();
                    break;
                }
            }
        }
        return keyValue;
    }

    public static String getKey(JSONArray json1, String key, String value) {
        String keyValue = "";
        if (json1 != null) {
            Iterator<Object> i1 = json1.iterator();
            while (i1.hasNext()) {
                Object ele = i1.next();
                if (ele instanceof JSONObject) {
                    JSONObject innerObject = (JSONObject) ele;
                    keyValue = getKey(innerObject, value);
                    if (!keyValue.equals("")) {
                        break;
                    }
                } else if (ele instanceof JSONArray) {
                    JSONArray innerObject = (JSONArray) ele;
                    keyValue = getKey(innerObject, key, value);
                    if (!keyValue.equals("")) {
                        break;
                    }
                } else if (ele instanceof String) {
                    String innerObject = (String) ele;
                    if (innerObject.equals(value)) {
                        keyValue = key;
                        break;
                    }
                } else if (ele instanceof Integer) {
                    Integer innerObject = (Integer) ele;
                    if (innerObject.toString().equals(value)) {
                        keyValue = key;
                        break;
                    }
                }
            }
        }
        return keyValue;
    }
}
