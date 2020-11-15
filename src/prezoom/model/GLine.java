package prezoom.model;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

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
        super(x, y, col, true, lineWidth, 0, 0, x2, y2, true);
    }

    /**
     * draw this object.
     * use {@link GAttributes#col} as color.
     * use {@link GAttributes#filled} to depend whether calling {@link Graphics2D#fillOval(int, int, int, int)} or {@link Graphics2D#draw(Shape)}.
     * use {@link GAttributes#stroke} to set the line style
     * @param g the Graphics to paint
     */
    @Override
    public void draw(Graphics2D g)
    {
        g.setColor(getCurrentAttributes().getCol());
        double x = getCurrentAttributes().getX(), y = getCurrentAttributes().getY();
        double x2 = getCurrentAttributes().getX2(), y2 = getCurrentAttributes().getY2();

        g.setStroke(new BasicStroke(getCurrentAttributes().getLineWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(new Line2D.Double(x,y,x2,y2));


    }
}
