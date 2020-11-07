package prezoom.model;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/** The oval objects
 * @author Zhijie Lan<p>
 * create date: 2020/11/1
 **/
public class GOval extends GObject
{
    public GOval(int x, int y, Color col, Boolean filled, int lineWidth, int w, int h)
    {
        super(x, y, col, filled, lineWidth, w, h, 0,0,true);
    }

    public int getH()
    {
        return gAttributeManager.cur_Attributes.height;
    }

    public void setH(int h)
    {
        gAttributeManager.cur_Attributes.height = h;
    }

    public int getW()
    {
        return gAttributeManager.cur_Attributes.width;
    }

    public void setW(int w)
    {
        gAttributeManager.cur_Attributes.width = w;
    }

    /**
     * Whether the given position is in this object
     * @param mx x
     * @param my y
     * @return True if in the object
     */
    @Override
    public boolean inShape(double mx, double my)
    {
        double x = getX(), y = getY();
        int w = getW(), h = getH();
        //return Math.sqrt((mx - x) * (mx - x) + (my - y) * (my - y)) < r;
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
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
        g.setColor(gAttributeManager.cur_Attributes.col);
        double x = getX(), y = getY();
        int w = getW(), h = getH();

        if (getFilled()) g.fillOval((int)x, (int)y, w, h);
        else
        {
            g.setStroke(new BasicStroke(getLineWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.draw(new Ellipse2D.Double(x, y, w, h));
        }
    }

}
