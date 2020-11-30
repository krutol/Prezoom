package prezoom.model;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * The abstract class for getting the getter and setter map
 *
 * @author Zhijie Lan<p>
 * create date: 2020/11/18<p>
 **/
public abstract class AttributeMap implements AttributeMapI
{
    //transient, cuz The Method cannot be serialized to save
    private transient Map<String, Method> getters, setters;

    public Map<String, Method> validSetterMap()
    {
        if (setters == null)
            setters = MethodFactory.getNonNullSetters(this);

        return setters;
    }

    public Map<String, Method> validGetterMap()
    {
        if (getters == null)
            getters = MethodFactory.getNonNullGetters(this);

        return getters;
    }

    public Map<String, Object> validAttributeMap()
    {
        return MethodFactory.getNonNullProperties(this);
    }
}
