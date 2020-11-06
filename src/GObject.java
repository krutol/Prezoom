import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/2
 **/

class GAttributes implements Cloneable
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
    protected Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}

class GObject
{
    //protected HashMap<Integer,GAttributes> state_Attributes_map = new HashMap<>(); //Attributes of each state
    protected ArrayList<GAttributes> state_Attributes_list = new ArrayList<>();
    //int current_State = 0;
    GAttributes cur_Attributes;

    protected GObject(double x, double y, Color col, Boolean filled, int lineWidth, int width, int height, double x2, double y2, Boolean visible)
    {
        //this.id = id;
//        this.x = x;
//        this.y = y;
//        this.col = col;
//        this.filled = filled;
//        this.lineWidth = lineWidth;
        //state_Attributes_map.put(getCurrent_State(), attributes);
        //state_Attributes_list = new ArrayList<>((Collections.nCopies(getCurrent_State(), null)));
        for (int i = 0; i< StateManager.total_State+1; i++)
        {
            if (i<getCurrent_State())
                state_Attributes_list.add(null);
            else
            {
                GAttributes attributes = new GAttributes(x,y,col,filled,lineWidth, width, height, x2, y2, visible);
                state_Attributes_list.add(i,attributes);
            }
        }
        updateCur_Attributes();
    }

    public int getCurrent_State()
    {
        return StateManager.current_State;
    }

    public GAttributes getCur_Attributes()
    {
        //return state_Attributes_map.get(getCurrent_State());
        return state_Attributes_list.get(getCurrent_State());
    }

    public void updateCur_Attributes()
    {
        if (getCur_Attributes() != null)
        this.cur_Attributes = getCur_Attributes();
    }

    public void insertState() throws CloneNotSupportedException
    {
        GAttributes attributes = new GAttributes();
        if (!state_Attributes_list.isEmpty())
        {
            if(state_Attributes_list.get(getCurrent_State()-1) == null)
                attributes = null;
            else
                attributes = (GAttributes) state_Attributes_list.get(getCurrent_State()-1).clone();
        }

        state_Attributes_list.add(getCurrent_State(), attributes);
        updateCur_Attributes();
    }


    public Color getCol()
    {
        return cur_Attributes.col;
    }

    public void setCol(Color col)
    {
        cur_Attributes.col = col;
    }

    public double getX()
    {
        return cur_Attributes.x;
    }

    public void setX(double x)
    {
        cur_Attributes.x = x;
    }

    public double getY()
    {
        return cur_Attributes.y;
    }

    public void setY(double y)
    {
        cur_Attributes.y = y;
    }

    public Boolean getFilled()
    {
        return cur_Attributes.filled;
    }

    public void setFilled(Boolean filled)
    {
        cur_Attributes.filled = filled;
    }

    public Boolean getVisible()
    {
        return cur_Attributes.visible;
    }

    public void setVisible(Boolean visible)
    {
        cur_Attributes.visible = visible;
    }

    public int getLineWidth()
    {
        return cur_Attributes.lineWidth;
    }

    public void setLineWidth(int lineWidth)
    {
        cur_Attributes.lineWidth = lineWidth;
    }

    protected void draw(Graphics2D g)
    {
    }

    protected boolean inShape(double x, double y)
    {
        return false;
    }
}

class Rectangle extends GObject
{
    public Rectangle(double x, double y, Color col, Boolean filled, int lineWidth, int w, int h)
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

class Oval extends GObject
{
    public Oval(int x, int y, Color col, Boolean filled, int lineWidth, int w, int h)
    {
        super(x, y, col, filled, lineWidth, w, h, 0,0,true);
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
        //return Math.sqrt((mx - x) * (mx - x) + (my - y) * (my - y)) < r;
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

    public void draw(Graphics2D g)
    {
        g.setColor(cur_Attributes.col);
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
