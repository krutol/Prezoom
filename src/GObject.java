import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/2
 **/
class GObject
{
    protected double x, y;
    protected Color col;
    protected Boolean filled = Boolean.FALSE;
    protected Boolean visible = Boolean.TRUE;
    protected int lineWidth = 3;
    private BasicStroke stroke;

    protected GObject(int x, int y, Color col, Boolean filled, int lineWidth)
    {
        this.x = x;
        this.y = y;
        this.col = col;
        this.filled = filled;
        this.lineWidth = lineWidth;
    }

    public Color getCol()
    {
        return col;
    }

    public void setCol(Color col)
    {
        this.col = col;
    }

    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public Boolean getFilled()
    {
        return filled;
    }

    public void setFilled(Boolean filled)
    {
        this.filled = filled;
    }

    public Boolean getVisible()
    {
        return visible;
    }

    public void setVisible(Boolean visible)
    {
        this.visible = visible;
    }

    public int getLineWidth()
    {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth)
    {
        this.lineWidth = lineWidth;
    }

    protected void draw(Graphics2D g)
    {
    }

    protected boolean inShape(int x, int y)
    {
        return false;
    }
}

class Rectangle extends GObject
{
    private int w, h;

    public Rectangle(int x, int y, Color col, Boolean filled, int lineWidth, int w, int h)
    {
        super(x, y, col, filled, lineWidth);
        this.w = w;
        this.h = h;
    }

    public int getH()
    {
        return h;
    }

    public void setH(int h)
    {
        this.h = h;
    }

    public int getW()
    {
        return w;
    }

    public void setW(int w)
    {
        this.w = w;
    }

    public boolean inShape(int mx, int my)
    {
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

    public void draw(Graphics2D g)
    {
        g.setColor(col);

        if (getFilled()) g.fillRect((int)x, (int)y, w, h);
        else
        {
            g.setStroke(new BasicStroke(getLineWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.draw(new Rectangle2D.Double(x, y, w, h));
        }
    }

}

class Oval extends GObject
{
    private int w, h;

    public Oval(int x, int y, Color col, Boolean filled, int lineWidth, int w, int h)
    {
        super(x, y, col, filled, lineWidth);
        this.w = w;
        this.h = h;
    }

    public int getW()
    {
        return w;
    }

    public void setW(int w)
    {
        this.w = w;
    }

    public int getH()
    {
        return h;
    }

    public void setH(int h)
    {
        this.h = h;
    }

    public boolean inShape(int mx, int my)
    {
        //return Math.sqrt((mx - x) * (mx - x) + (my - y) * (my - y)) < r;
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

    public void draw(Graphics2D g)
    {
        g.setColor(col);

        if (getFilled()) g.fillOval((int)x, (int)y, w, h);
        else
        {
            g.setStroke(new BasicStroke(getLineWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.draw(new Ellipse2D.Double(x, y, w, h));
        }
    }

}
