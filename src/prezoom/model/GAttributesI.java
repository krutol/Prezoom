package prezoom.model;

import java.awt.*;

/**
 * The interface of the Grapgical Attibutes
 *
 * @author Zhijie Lan<p>
 * create date: 2020/11/17<p>
 **/
public interface GAttributesI extends Cloneable
{
    Object clone() throws CloneNotSupportedException;

    String getLabel();

    void setLabel(String label);

    double getX();

    void setX(double x);

    double getY();

    void setY(double y);

    Color getCol();

    void setCol(Color col);

    Boolean getFilled();

    void setFilled(Boolean filled);

    int getLineWidth();

    void setLineWidth(int lineWidth);

    int getWidth();

    void setWidth(int width);

    int getHeight();

    void setHeight(int height);

    double getX2();

    void setX2(double x2);

    double getY2();

    void setY2(double y2);

    BasicStroke getStroke();

    void setStroke(BasicStroke stroke);

    Boolean getVisible();

    void setVisible(Boolean visible);
}
