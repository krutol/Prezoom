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
        super(x, y, col, null, lineWidth, null, null,
                x2, y2, true, null, null, null, null);
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
        if (!getCurrentAttributes().getVisible())
            return false;

        GAttributesI att = getCurrentAttributes();
        double x1 = Math.max(att.getX(), att.getX2());
        double x2 = Math.min(att.getX(), att.getX2());
        double y1 = Math.max(att.getY(), att.getY2());
        double y2 = Math.min(att.getY(), att.getY2());

        double dx = (x2 - x1) / 2;
        double dy = (y2 - y1) / 2;
        double cx = (x2 + x1) / 2;
        double cy = (y2 + y1) / 2;
        double m = Math.hypot(dx, dy);
        double ux = dx / m;
        double uy = dy / m;
        double vx = -uy;
        double vy = ux;
        double wx = mx - cx;
        double wy = my - cy;

        return (Math.abs(ux * wx + uy * wy) <= m
                && Math.abs(vx * wx + vy * wy) <= att.getLineWidth()/2.0+20);
    }
}
