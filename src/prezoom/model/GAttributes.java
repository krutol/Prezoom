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
    protected String textString;
    protected Double x, y;
    protected Double x2, y2;
    protected Double width, height;
    protected Color color;
    protected Boolean filled;
    protected Integer lineWidth;
    protected BasicStroke stroke;
    protected Boolean visible = true;
    protected String fontName;
    protected Integer fontStyle;
    protected Double fontSize;
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
     * @param color       paint color
     * @param filled    whether filled
     * @param lineWidth width of lines
     * @param width     width of the object, if applicable
     * @param height    height of the object, if applicable
     * @param x2        x2 of the object, if applicable
     * @param y2        y2 of the object, if applicable
     * @param visible   whether visible
     */
    public GAttributes(Double x, Double y, Color color, Boolean filled,
                       Integer lineWidth, Double width, Double height,
                       Double x2, Double y2, Boolean visible,
                       String fontName, Integer fontStyle, Double fontSize, String textString)
    {
        this.x = x;
        this.y = y;
        this.color = color;
        this.filled = filled;
        this.lineWidth = lineWidth;
        this.width = width;
        this.height = height;
        this.x2 = x2;
        this.y2 = y2;
        this.visible = visible;
        this.fontName = fontName;
        this.fontStyle = fontStyle;
        this.fontSize = fontSize;
        this.textString = textString;
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
    public GAttributes clone() throws CloneNotSupportedException
    {
        return (GAttributes) super.clone();
    }

    @Override
    public String getTextString()
    {
        return textString;
    }

    @Override
    public void setTextString(String textString)
    {
        this.textString = textString;
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
    public Color getColor()
    {
        return color;
    }

    @Override
    public void setColor(Color color)
    {
        this.color = color;
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

    public String getFontName()
    {
        return fontName;
    }

    public void setFontName(String fontName)
    {
        this.fontName = fontName;
    }

    public Integer getFontStyle()
    {
        return fontStyle;
    }

    public void setFontStyle(Integer fontStyle)
    {
        this.fontStyle = fontStyle;
    }

    public Double getFontSize()
    {
        return fontSize;
    }

    public void setFontSize(Double fontSize)
    {
        this.fontSize = fontSize;
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
