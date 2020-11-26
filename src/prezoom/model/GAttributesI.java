package prezoom.model;

import java.awt.*;

/**
 * The interface of the Grapgical Attibutes
 *
 * @author Zhijie Lan<p>
 * create date: 2020/11/17<p>
 **/
public interface GAttributesI extends Cloneable, AttributeMapI
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

    Integer getFontSize();

    void setFontSize(Integer fontSize);
}
