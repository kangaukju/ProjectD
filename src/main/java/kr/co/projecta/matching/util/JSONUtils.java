package kr.co.projecta.matching.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class JSONUtils {
    static public Map<String, Object> getJSONMap(Class clazz, Object object) throws RuntimeException {
        String name;
        String methodName;
        Map<String, Object> map = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isPrivate(field.getModifiers())) {
                try {
                    name = field.getName();
                    methodName = "get"+name.substring(0, 1).toUpperCase()+name.substring(1, name.length());
                    Method method = clazz.getMethod(methodName, null);
                    map.put(field.getName(), method.invoke(object, null));

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return map;
    }
}
