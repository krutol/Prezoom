package prezoom.model;

import java.awt.*;

/** The base attribute class that holds all the attribute an object has
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

    /**
     * the default value constructor
     */
    public GAttributes() {}

    /**
     * the constructor with parameters
     * @param x location, x
     * @param y location, y
     * @param col paint color
     * @param filled whether filled
     * @param lineWidth width of lines
     * @param width width of the object, if applicable
     * @param height height of the object, if applicable
     * @param x2 x2 of the object, if applicable
     * @param y2 y2 of the object, if applicable
     * @param visible whether visible
     */
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

    /**
     * The attributes can be clone
     * @return the cloned attributes
     * @throws CloneNotSupportedException nothing
     */
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
