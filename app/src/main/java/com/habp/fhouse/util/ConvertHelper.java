package com.habp.fhouse.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
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
                if(value != null)
                    map.put(field.getName(), value);
            }
        } catch (Exception e) {
            Log.d("ConvertHelper", e.getMessage());
        }
        return map;
    }

    public static byte[] convertImageViewToByte(ImageView imageView) {
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        return baos.toByteArray();
    }

}
