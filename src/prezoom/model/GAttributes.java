package prezoom.model;

import java.awt.*;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * The base attribute class that holds all the attribute an object has
 *
 * @author Zhijie Lan<p>
 * create date: 2020/11/2
 **/

public class GAttributes implements GAttributesI
{
    protected String label;
    protected Double x, y;
    protected Color col;
    protected Boolean filled;
    protected Integer lineWidth;
    protected Double width, height;
    protected Double x2, y2;
    protected BasicStroke stroke;
    protected Boolean visible = true;
    private final Map<String, Method> getters, setters;

    /**
     * the default value constructor
     */
    public GAttributes()
    {
        this.getters = MethodFactory.getNonNullGetters(this);
        this.setters = MethodFactory.getNonNullSetters(this);
    }

    /**
     * the constructor with parameters
     *
     * @param x         location, x
     * @param y         location, y
     * @param col       paint color
     * @param filled    whether filled
     * @param lineWidth width of lines
     * @param width     width of the object, if applicable
     * @param height    height of the object, if applicable
     * @param x2        x2 of the object, if applicable
     * @param y2        y2 of the object, if applicable
     * @param visible   whether visible
     */
    public GAttributes(Double x, Double y, Color col, Boolean filled, Integer lineWidth, Double width, Double height, Double x2, Double y2, Boolean visible)
    {
        this.x = x;
        this.y = y;
        this.col = col;
        this.filled = filled;
        this.lineWidth = lineWidth;
        this.width = width;
        this.height = height;
        this.x2 = x2;
        this.y2 = y2;
        this.visible = visible;
        this.getters = MethodFactory.getNonNullGetters(this);
        this.setters = MethodFactory.getNonNullSetters(this);
    }

    /**
     * The attributes can be clone
     *
     * @return the cloned attributes
     * @throws CloneNotSupportedException nothing
     */
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

    @Override
    public String getLabel()
    {
        return label;
    }

    @Override
    public void setLabel(String label)
    {
        this.label = label;
    }

    @Override
    public Double getX()
    {
        return x;
    }

    public void setX(Double x)
    {
        this.x = x;
    }

    @Override
    public Double getY()
    {
        return y;
    }

    public void setY(Double y)
    {
        this.y = y;
    }

    @Override
    public Color getCol()
    {
        return col;
    }

    @Override
    public void setCol(Color col)
    {
        this.col = col;
    }

    @Override
    public Boolean getFilled()
    {
        return filled;
    }

    @Override
    public void setFilled(Boolean filled)
    {
        this.filled = filled;
    }

    @Override
    public Integer getLineWidth()
    {
        return lineWidth;
    }

    public void setLineWidth(Integer lineWidth)
    {
        this.lineWidth = lineWidth;
    }

    @Override
    public Double getWidth()
    {
        return width;
    }

    public void setWidth(Double width)
    {
        this.width = width;
    }

    @Override
    public Double getHeight()
    {
        return height;
    }

    public void setHeight(Double height)
    {
        this.height = height;
    }

    @Override
    public Double getX2()
    {
        return x2;
    }

    public void setX2(Double x2)
    {
        this.x2 = x2;
    }

    @Override
    public Double getY2()
    {
        return y2;
    }

    public void setY2(Double y2)
    {
        this.y2 = y2;
    }

    @Override
    public BasicStroke getStroke()
    {
        return stroke;
    }

    @Override
    public void setStroke(BasicStroke stroke)
    {
        this.stroke = stroke;
    }

    @Override
    public Boolean getVisible()
    {
        return visible;
    }

    @Override
    public void setVisible(Boolean visible)
    {
        this.visible = visible;
    }

    @Override
    public Map<String, Method> validSetterMap()
    {
        return setters;
    }

    @Override
    public Map<String, Method> validGetterMap()
    {
        return getters;
    }
}
