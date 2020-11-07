package prezoom.model;

import prezoom.controller.GAttributeManager;

import java.awt.*;

/** The base class for all graphical objects
 * @author Zhijie Lan<p>
 * create date: 2020/11/1
 **/
public abstract class GObject
{
    //protected HashMap<Integer,prezoom.model.GAttributes> state_Attributes_map = new HashMap<>(); //Attributes of each state
//    protected ArrayList<GAttributes> state_Attributes_list = new ArrayList<>();
//    //int current_State = 0;
//    GAttributes cur_Attributes;

    /**
     * the manager to manage the state of this object
     */
    public GAttributeManager gAttributeManager;

    /**
     * the constructor will call the {@link GAttributeManager} to manage all the attributes
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
//        for (int i = 0; i< StateManager.total_State+1; i++)
//        {
//            if (i<getCurrent_State())
//                state_Attributes_list.add(null);
//            else
//            {
//                GAttributes attributes = new GAttributes(x,y,col,filled,lineWidth, width, height, x2, y2, visible);
//                state_Attributes_list.add(i,attributes);
//            }
//        }
//        updateCur_Attributes();
        gAttributeManager = new GAttributeManager(x, y, col, filled, lineWidth, width, height, x2, y2, visible);

    }

    public Color getCol()
    {
        return gAttributeManager.cur_Attributes.col;
    }

    public void setCol(Color col)
    {
        gAttributeManager.cur_Attributes.col = col;
    }

    public double getX()
    {
        return gAttributeManager.cur_Attributes.x;
    }

    public void setX(double x)
    {
        gAttributeManager.cur_Attributes.x = x;
    }

    public double getY()
    {
        return gAttributeManager.cur_Attributes.y;
    }

    public void setY(double y)
    {
        gAttributeManager.cur_Attributes.y = y;
    }

    public Boolean getFilled()
    {
        return gAttributeManager.cur_Attributes.filled;
    }

    public void setFilled(Boolean filled)
    {
        gAttributeManager.cur_Attributes.filled = filled;
    }

    public Boolean getVisible()
    {
        return gAttributeManager.cur_Attributes.visible;
    }

    public void setVisible(Boolean visible)
    {
        gAttributeManager.cur_Attributes.visible = visible;
    }

    public int getLineWidth()
    {
        return gAttributeManager.cur_Attributes.lineWidth;
    }

    public void setLineWidth(int lineWidth)
    {
        gAttributeManager.cur_Attributes.lineWidth = lineWidth;
    }

    /**
     * draw this object
     * @param g the Graphics to paint
     */
    public void draw(Graphics2D g)
    {
    }

    /**
     * Whether the given position is in this object
     * @param x x
     * @param y y
     * @return True if in the object
     */
    public boolean inShape(double x, double y)
    {
        return false;
    }
}

