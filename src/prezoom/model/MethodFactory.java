package prezoom.model;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
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
    public static Map<String, Object> getNonNullProperties(Object bean)
    {
        try
        {
            Map<String, Object> map = new HashMap<>();
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
                                map.put(pd.getName(), value);
                            }
                        } catch (Exception e)
                        {
                            // add proper error handling here
                        }
                    });
            return map;
        } catch (IntrospectionException e)
        {
            // and here, too
            return Collections.emptyMap();
        }
    }

    public static Map<String, Method> getNonNullGetters(Object bean)
    {
        try
        {
            Map<String, Method> map = new HashMap<>();
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
                                map.put(pd.getName(), pd.getReadMethod());
                            }
                        } catch (Exception e)
                        {
                            // add proper error handling here
                        }
                    });
            return sortAsDeclaredOrder(map,bean);
        } catch (IntrospectionException e)
        {
            // and here, too
            return Collections.emptyMap();
        }
    }

    public static Map<String, Method> getNonNullSetters(Object bean)
    {
        try
        {
            Map<String, Method> map = new HashMap<>();
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
                                map.put(pd.getName(), pd.getWriteMethod());
                            }
                        } catch (Exception e)
                        {
                            // add proper error handling here
                        }
                    });
            return sortAsDeclaredOrder(map,bean);
        } catch (IntrospectionException e)
        {
            // and here, too
            return Collections.emptyMap();
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
}