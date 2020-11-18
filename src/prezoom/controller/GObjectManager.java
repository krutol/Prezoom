package prezoom.controller;

import prezoom.model.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The manager that manages all the graphical objects on the canvas.
 *
 * @author Zhijie Lan<p>
 * create date: 2020/11/15<p>
 **/
public class GObjectManager
{
    public static GObject draggedObj;
    public static GObject inspectedObj;

    private static final ArrayList<GObject> gObjectList = new ArrayList<>();
    static
    {
        // for test purpose
        gObjectList.add(new GRectangle(50, 100, 30, 40, Color.red, false, 1));
        gObjectList.add(new GRectangle(350, 500, 30, 40, Color.GREEN, true, 10));
        gObjectList.add(new GOval(150, 200, 50, 30, Color.BLUE, true, 3));
        gObjectList.add(new GLine(500,500,672, 789, Color.magenta, 5));
    }

    /**
     * draw all the objects on canvas
     * @param g2 graphics to paint
     */
    public static void drawAll(Graphics2D g2)
    {
        for (GObject obj : gObjectList)
        {
            if (obj.getAttributeManager().getCur_Attributes() != null)
                obj.draw(g2);
        }
    }

    /**
     * find if the given x,y select an object
     * @param x x
     * @param y y
     * @return the selected object, null if no selected
     */
    public static GObject findSelected(double x, double y)
    {
        for (GObject obj : gObjectList)
        {
            if (obj.inShape(x, y))
            {
                return obj;
            }
        }

        return null;
    }

    //public static void upD

    /**
     * call the objects' attribute manager to update all their attributes
     */
    public static void updateAllObject()
    {
        for (GObject obj: gObjectList)
        {
            obj.getAttributeManager().updateCur_Attributes();
        }
    }

    /**
     * call the objects' attribute manager to insert a state to all objects
     * @throws CloneNotSupportedException none
     */
    public static void insertStateToGObjects() throws CloneNotSupportedException
    {
        for (GObject obj: gObjectList)
        {
            obj.getAttributeManager().insertAttributeState();
        }
    }

    /**
     * call the objects' attribute manager to delete a given state
     * @param state the state to delete
     */
    public static void deleteStateToGObjects(int state)
    {
        for (GObject obj: gObjectList)
        {
            obj.getAttributeManager().deleteAttributeState(state);
        }
    }

    /**
     * add a graphical object to the canvas
     * @param obj the object to add
     */
    public static void addGObject(GObject obj)
    {
        gObjectList.add(obj);
    }



}
