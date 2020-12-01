package prezoom.model;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * The abstract class for getting the getter and setter map
 * @author Zhijie Lan<p>
 * create date: 2020/11/18
 **/
public abstract class AttributeMap implements AttributeMapI
{
    /**
     * the map of getters and setters. transient, cuz The Method cannot be serialized to save
     */
    private transient Map<String, Method> getters, setters;

    /**
     * {@inheritDoc}
     */
    public Map<String, Method> validSetterMap()
    {
        if (setters == null)
            setters = MethodFactory.getNonNullSetters(this);

        return setters;
    }

    /**
     * {@inheritDoc}
     */
    public Map<String, Method> validGetterMap()
    {
        if (getters == null)
            getters = MethodFactory.getNonNullGetters(this);

        return getters;
    }

    /**
     * {@inheritDoc}
     */
    public Map<String, Object> validAttributeMap()
    {
        return MethodFactory.getNonNullProperties(this);
    }
}
