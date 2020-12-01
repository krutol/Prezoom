package prezoom.model;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * The interface for getting the getter and setter map
 *
 * @author Zhijie Lan<p>
 * create date: 2020/11/18
 **/
public interface AttributeMapI
{
    /**
     * the map that has all the none null fields name and corresponding setter methods
     * @return setter map, key: field names, value: setter methods
     */
    Map<String, Method> validSetterMap();

    /**
     * the map that has all the none null fields name and corresponding getter methods
     * @return setter map, key: field names, value: getter methods
     */
    Map<String, Method> validGetterMap();

    /**
     * the map that has all the none null fields name and corresponding values
     * @return setter map, key: field names, value: values of fields
     */
    Map<String, Object> validAttributeMap();
}
