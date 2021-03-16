package com.habp.fhouse.util;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ConvertHelper {

    public static Map<String, Object> convertObjectToMap(Object object) {
        Map<String, Object> map = new HashMap<>();
        try {
            Field[] allFields = object.getClass().getDeclaredFields();
            for (Field field : allFields) {
                field.setAccessible(true);
                Object value = field.get(object);
                map.put(field.getName(), value);
            }
        } catch (Exception e) {
            Log.d("ConvertHelper", e.getMessage());
        }
        return map;
    }

}
