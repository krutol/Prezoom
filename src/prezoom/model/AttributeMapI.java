package prezoom.model;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * The interface for getting the getter and setter map
 *
 * @author Zhijie Lan<p>
 * create date: 2020/11/18<p>
 **/
public interface AttributeMapI
{
    Map<String, Method> validSetterMap();

    Map<String, Method> validGetterMap();
}
