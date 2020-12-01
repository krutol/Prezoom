package prezoom.model;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * to generate maps that contain all non null filed names, values, getter methods, or setter method of a class.
 * ordered as declared
 * @author Zhijie Lan<p>
 * create date: 2020/11/18<p>
 **/
class MethodFactory
{
    private static Map<String, Object> att_map;
    private static Map<String, Method> getter_map;
    private static Map<String, Method> setter_map;
    private static Object currentObject;

    /**
     * to generate the map that has all the none null fields name and corresponding values
     * @param bean the target object
     * @return setter map, key: field names, value: values of fields
     */
    public static Map<String, Object> getNonNullProperties(Object bean)
    {
        generateMaps(bean);
        return att_map;
    }

    /**
     * to generate the map that has all the none null fields name and corresponding getter methods
     * @param bean the target object
     * @return setter map, key: field names, value: getter methods
     */
    public static Map<String, Method> getNonNullGetters(Object bean)
    {
        if (!bean.equals(currentObject))
        {
            generateMaps(bean);
        }
        return getter_map;
    }

    /**
     * to generate the map that has all the none null fields name and corresponding setter methods
     * @param bean the target object
     * @return setter map, key: field names, value: setter methods
     */
    public static Map<String, Method> getNonNullSetters(Object bean)
    {
        if (!bean.equals(currentObject))
        {
            generateMaps(bean);
        }
        return setter_map;
    }

    /**
     * generate all three maps at ones
     * @param bean the target objects
     */
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

    /**
     * sorted the field names of the method maps as the declared order of the object class
     * @param unordered_mp the unordered method map
     * @param obj the target object
     * @return the ordered method map
     */
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

    /**
     * sorted the field names of the value maps as the declared order of the object class
     * @param unordered_mp the unordered value map
     * @param obj the target object
     * @return the ordered value map
     */
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