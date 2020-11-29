package prezoom.model;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * to generate maps that contain all non null filed names, values, getter methods, or setter method of a class
 *
 * @author Zhijie Lan<p>
 * create date: 2020/11/18<p>
 **/
class MethodFactory
{
    private static Map<String, Object> att_map;
    private static Map<String, Method> getter_map;
    private static Map<String, Method> setter_map;
    private static Object currentObject;

    public static Map<String, Object> getNonNullProperties(Object bean)
    {
        generateMaps(bean);
        return att_map;
    }

    public static Map<String, Method> getNonNullGetters(Object bean)
    {
        if (!bean.equals(currentObject))
        {
            generateMaps(bean);
        }
        return getter_map;
    }

    public static Map<String, Method> getNonNullSetters(Object bean)
    {
        if (!bean.equals(currentObject))
        {
            generateMaps(bean);
        }
        return setter_map;
    }

    private static void generateMaps(Object bean)
    {
        try
        {
            att_map = new HashMap<>();
            getter_map = new HashMap<>();
            setter_map = new HashMap<>();

            Arrays.stream(Introspector.getBeanInfo(bean.getClass(), Object.class)
                    .getPropertyDescriptors())
                    // filter out properties with setters only
                    .filter(pd -> Objects.nonNull(pd.getReadMethod()))
                    .forEach(pd ->
                    { // invoke method to get value
                        try
                        {
                            Object value = pd.getReadMethod().invoke(bean);
                            if (value != null)
                            {
                                att_map.put(pd.getName(), value);
                                getter_map.put(pd.getName(), pd.getReadMethod());
                                setter_map.put(pd.getName(), pd.getWriteMethod());
                            }
                        } catch (Exception e)
                        {
                            // add proper error handling here
                        }
                    });
            att_map = sortAsDeclaredOrder(bean,att_map);
            getter_map = sortAsDeclaredOrder(getter_map, bean);
            setter_map = sortAsDeclaredOrder(setter_map, bean);
            currentObject = bean;
        } catch (IntrospectionException e)
        {
            // and here, too
            att_map = null;
            getter_map = null;
            setter_map = null;
            currentObject = null;
        }
    }

    private static Map<String, Method> sortAsDeclaredOrder(Map<String, Method> unordered_mp, Object obj)
    {
        Map<String, Method> map = new LinkedHashMap<>();

        for (Field field:obj.getClass().getDeclaredFields())
        {
            Method m = unordered_mp.get(field.getName());
            if (m != null)
            {
                map.put(field.getName(), m);
            }
        }

        return map;
    }

    private static Map<String, Object> sortAsDeclaredOrder(Object obj, Map<String, Object> unordered_mp)
    {
        Map<String, Object> map = new LinkedHashMap<>();

        for (Field field:obj.getClass().getDeclaredFields())
        {
            Object m = unordered_mp.get(field.getName());
            if (m != null)
            {
                map.put(field.getName(), m);
            }
        }

        return map;
    }
}