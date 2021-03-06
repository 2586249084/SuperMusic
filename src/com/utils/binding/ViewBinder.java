package com.utils.binding;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import java.lang.reflect.Field;

public class ViewBinder {

    public static void bind(Activity activity){
        bind(activity, activity.getWindow().getDecorView());
    }

    public static void bind(Object target, View source){
        Field[] fields = target.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    if (field.get(target) != null) {
                        continue;
                    }

                    com.utils.binding.Bind bind = field.getAnnotation(com.utils.binding.Bind.class);
                    if (bind != null) {
                        int viewId = bind.value();
                        field.set(target, source.findViewById(viewId));
                    }
                } catch (Exception error) {
                    Log.e("Binding Error:", error.getLocalizedMessage());
                }
            }
        }
    }
}
