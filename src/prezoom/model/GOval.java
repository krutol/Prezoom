package prezoom.model;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/** The oval objects
 * @author Zhijie Lan<p>
 * create date: 2020/11/1
 **/
public class GOval extends GObject
{
    public GOval(int x, int y, int w, int h, Color col, Boolean filled, int lineWidth)
    {
        super(x, y, col, filled, lineWidth, w, h, 0,0,true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Graphics2D g)
    {
        double x = getCurrentAttributes().getX(), y = getCurrentAttributes().getY();
        int w = getCurrentAttributes().getWidth(), h = getCurrentAttributes().getHeight();

        this.drawShape = new Ellipse2D.Double(x, y, w, h);

        drawing(g);
    }

}
