package prezoom.model.GObject;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/** The oval objects
 * @author Zhijie Lan<p>
 * create date: 2020/11/1
 **/
public class GOval extends GObject
{
    public GOval(double x, double y, double w, double h, Color col, Boolean filled, int lineWidth)
    {
        super(x, y, col, filled, lineWidth, w, h,
                null,null,true, null, null, null, null);
        this.drawShape = new Ellipse2D.Double(x, y, w, h);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Graphics2D g)
    {
        double x = getCurrentAttributes().getX(), y = getCurrentAttributes().getY();
        double w = getCurrentAttributes().getWidth(), h = getCurrentAttributes().getHeight();

        this.drawShape = new Ellipse2D.Double(x, y, w, h);

        drawing(g);
    }

}
