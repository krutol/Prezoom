package prezoom.controller;

import prezoom.model.*;
import prezoom.view.MainWindow;

import java.awt.*;
import java.awt.geom.Point2D;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * The manager that manages all the graphical objects on the canvas.
 *
 * @author Zhijie Lan<p>
 * create date: 2020/11/15<p>
 **/
public class GObjectManager
{
    /**
     * the object that is being dragged on the canvas
     */
    public static GObject draggedObj;

    /**
     * the selected object that is showed on the inspector panel
     */
    public static GObject inspectedObj;

    /**
     * the new object that is created by drawing
     */
    public static GObject drawingObj;

    public static String drawingType = "";

    public static Color drawingColor = Color.black;

    public static int drawingLineWidth = 5;

    public static boolean drawingFilled = false;

    private static final ArrayList<GObject> gObjectList = new ArrayList<>();

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

    public static void updateDrawingObj(Point2D start, Point2D current)
    {
        if (drawingType.isEmpty())
            return;

        double pointX1 = start.getX(), pointY1 = start.getY();
        double pointX2 = current.getX(), pointY2 = current.getY();

        if (drawingType.equals("Line"))
        {
            if (drawingObj == null)
                drawingObj = new GLine(pointX1, pointY1, pointX2, pointY2, drawingColor, 5);
            else {
                drawingObj.getCurrentAttributes().setX(pointX1);
                drawingObj.getCurrentAttributes().setY(pointY1);
                drawingObj.getCurrentAttributes().setX2(pointX2);
                drawingObj.getCurrentAttributes().setY2(pointY2);
            }

        }
        else
        {
            double pX, pY;   // Top left corner of rectangle that contains the figure.
            double width, height;         // Width and height of rectangle that contains the figure.
            if (pointX1 >= pointX2)
            {  // pointX2 is left edge
                pX = pointX2;
                width = pointX1 - pointX2;
            }
            else
            {   // pointX1 is left edge
                pX = pointX1;
                width = pointX2 - pointX1;
            }
            if (pointY1 >= pointY2)
            {  // pointY2 is top edge
                pY = pointY2;
                height = pointY1 - pointY2;
            }
            else
            {   // pointY1 is top edge
                pY = pointY1;
                height = pointY2 - pointY1;
            }

            if (drawingObj == null)
            {
                switch (drawingType)
                {
                    case "Rectangle":
                        drawingObj = new GRectangle(pX, pY, width, height, drawingColor, drawingFilled, drawingLineWidth);
                        break;
                    case "Oval":
                        drawingObj = new GOval(pX, pY, width, height, drawingColor, drawingFilled, drawingLineWidth);
                        break;
                    case "Circle":
                        drawingObj = new GOval(pX, pY, width, width, drawingColor, drawingFilled, drawingLineWidth);
                        break;
                }
            }else
            {
                drawingObj.getCurrentAttributes().setX(pX);
                drawingObj.getCurrentAttributes().setY(pY);
                drawingObj.getCurrentAttributes().setWidth(width);
                if (drawingType.equals("Circle"))
                    drawingObj.getCurrentAttributes().setHeight(width);
                else
                    drawingObj.getCurrentAttributes().setHeight(height);
            }

        }

        MainWindow.centerCanvas.repaint();

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

    public static GObject findSelected(Point2D point2D)
    {
        return findSelected(point2D.getX(), point2D.getY());
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
     */
    public static void insertStateToGObjects()
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
