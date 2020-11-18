package prezoom.model;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * The line objects
 *
 * @author Zhijie Lan<p>
 * create date: 2020/11/14<p>
 **/
public class GLine extends GObject
{
    public GLine(double x, double y, double x2, double y2, Color col, int lineWidth)
    {
        super(x, y, col, null, lineWidth, null, null, x2, y2, true);
        this.drawShape = new Line2D.Double(x,y,x2,y2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Graphics2D g)
    {
        double x = getCurrentAttributes().getX(), y = getCurrentAttributes().getY();
        double x2 = getCurrentAttributes().getX2(), y2 = getCurrentAttributes().getY2();

        this.drawShape = new Line2D.Double(x,y,x2,y2);

        drawing(g);
    }

    /**
     * override the function since {@link Line2D#contains(double, double)} always return false
     * {@inheritDoc}
     */
    @Override
    public boolean inShape(double mx, double my)
    {
        return drawShape.getBounds().contains(mx,my);
    }
}
