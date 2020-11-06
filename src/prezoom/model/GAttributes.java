package prezoom.model;

import java.awt.*;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/2
 **/

public class GAttributes implements Cloneable
{
    protected double x, y;
    protected Color col;
    protected Boolean filled = false;
    protected int lineWidth = 3;
    protected int width, height;
    protected double x2,y2;
    protected BasicStroke stroke;
    protected Boolean visible = true;

    public GAttributes() {}

    public GAttributes(double x, double y, Color col, Boolean filled, int lineWidth, int width, int height, double x2, double y2, Boolean visible)
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
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
