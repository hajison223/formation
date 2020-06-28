package com.lifeistech.formation;

import com.google.gson.Gson;

public class ObjectStrage {
    public static void save(Object src, String key) {
        String json = new Gson().toJson(src);
        new CachePref().put(key, json);
    }

    public static <T> T get(String key, Class<T> klazz) {
        String jsonStr = new CachePref().get(key, "");
        if (jsonStr.equals("")) {
            return null;
        }
        return new Gson().fromJson(jsonStr, klazz);
    }

}
