package prezoom.model;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class GRectangle extends GObject
{
    public GRectangle(double x, double y, Color col, Boolean filled, int lineWidth, int w, int h)
    {
        super(x, y, col, filled, lineWidth,w,h,0,0,true);
    }

    public int getH()
    {
        return cur_Attributes.height;
    }

    public void setH(int h)
    {
        cur_Attributes.height = h;
    }

    public int getW()
    {
        return cur_Attributes.width;
    }

    public void setW(int w)
    {
        cur_Attributes.width = w;
    }

    public boolean inShape(double mx, double my)
    {
        double x = getX(), y = getY();
        int w = getW(), h = getH();
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

    public void draw(Graphics2D g)
    {
        g.setColor(cur_Attributes.col);
        double x = getX(), y = getY();
        int w = getW(), h = getH();

        if (getFilled()) g.fillRect((int)x, (int)y, w, h);
        else
        {
            g.setStroke(new BasicStroke(getLineWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.draw(new Rectangle2D.Double(x, y, w, h));
        }
    }

}
