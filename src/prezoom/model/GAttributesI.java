package prezoom.model;

import java.awt.*;
import java.io.Serializable;

/**
 * The interface of the Graphical Attributes
 *
 * @author Zhijie Lan<p>
 * create date: 2020/11/17
 **/
public interface GAttributesI extends Cloneable, AttributeMapI, Serializable
{
    GAttributes clone() throws CloneNotSupportedException;

    String getTextString();

    void setTextString(String textString);

    Double getX();

    void setX(Double x);

    Double getY();

    void setY(Double y);

    Color getColor();

    void setColor(Color color);

    Boolean getFilled();

    void setFilled(Boolean filled);

    Integer getLineWidth();

    void setLineWidth(Integer lineWidth);

    Double getWidth();

    void setWidth(Double width);

    Double getHeight();

    void setHeight(Double height);

    Double getX2();

    void setX2(Double x2);

    Double getY2();

    void setY2(Double y2);

    BasicStroke getStroke();

    void setStroke(BasicStroke stroke);

    Boolean getVisible();

    void setVisible(Boolean visible);

    String getFontName();

    void setFontName(String fontName);

    Integer getFontStyle();

    void setFontStyle(Integer fontStyle);

    Double getFontSize();

    void setFontSize(Double fontSize);
}
