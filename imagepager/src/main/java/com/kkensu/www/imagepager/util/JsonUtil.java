package com.kkensu.www.imagepager.util;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by johyunchol on 2018. 4. 12..
 */

public class JsonUtil {
    public static <T> JSONObject getJsonObjectFromMap(Map<String, T> map) {
        try {
            JSONObject json = new JSONObject();
            for (Map.Entry<String, T> entry : map.entrySet()) {
                String key = entry.getKey();
                T value = entry.getValue();
                json.put(key, value);
            }
            return json;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static JSONObject getJsonObjectFromObject(Object object) {
        Gson gson = new Gson();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(gson.toJson(object));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static <T> T convertClass(JSONObject jsonObject, Class<T> clazz) {
        return new Gson().fromJson(jsonObject.toString(), clazz);
    }
}
