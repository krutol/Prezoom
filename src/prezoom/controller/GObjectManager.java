package prezoom.controller;

import prezoom.model.*;
import prezoom.view.CenterCanvas;
import prezoom.view.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
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
     * the object that is being resized
     */
    public static GObject resizedObj;

    /**
     * the object that shows the resize point
     */
    public static GObject resizePointObj;

    /**
     * the small rectangles on the diagonal of the shapes,
     * used to resize the shapes
     */
    private static Rectangle2D[] resizeRecs;

    /**
     * the selected resize rectangle
     */
    public static Point2D selectedResizePoint;

    /**
     * another resize rectangle, fixed when resizing
     */
    public static Point2D unselectedResizePoint;

    /**
     * the new object that is created by drawing
     */
    public static GObject drawingObj;

    /**
     * the type of the new shape, empty when not drawing
     */
    public static String drawingType = "";

    /**
     * the color of the new shape
     */
    public static Color drawingColor = Color.black;

    /**
     * the line width of the new shape
     */
    public static int drawingLineWidth = 3;

    /**
     * whether the new shape is filled
     */
    public static boolean drawingFilled = false;

    //protected static ArrayList<JTextArea> jTextAreaList = new ArrayList<>();
    protected static ArrayList<GObject> gObjectList = new ArrayList<>();

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
     * when dragging mouse to resize shapes,
     * use this function to update the shape to have dynamic resizing effect
     * @param start the start point of new shapes
     * @param current the current point of the cursor
     * @param obj the object to be resized
     * @param type the type of resized object
     * @return the resized GObject
     */
    public static GObject updateResizing(Point2D start, Point2D current, GObject obj, String type)
    {
        if (type.isEmpty())
            return null;

        double pointX1 = start.getX(), pointY1 = start.getY();
        double pointX2 = current.getX(), pointY2 = current.getY();

        if (type.equals("Line"))
        {
            if (obj == null)
                obj = new GLine(pointX1, pointY1, pointX2, pointY2, drawingColor, 5);
            else {
                obj.getCurrentAttributes().setX(pointX1);
                obj.getCurrentAttributes().setY(pointY1);
                obj.getCurrentAttributes().setX2(pointX2);
                obj.getCurrentAttributes().setY2(pointY2);
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

            if (obj == null)
            {
                switch (type)
                {
                    case "Rectangle":
                        obj = new GRectangle(pX, pY, width, height, drawingColor, drawingFilled, drawingLineWidth);
                        break;
                    case "Oval":
                        obj = new GOval(pX, pY, width, height, drawingColor, drawingFilled, drawingLineWidth);
                        break;
                    case "Circle":
                        obj = new GCircle(pX, pY, width, drawingColor, drawingFilled, drawingLineWidth);
                        break;
                    case "Text":
                        obj = new GText("Text Area", pX, pY, Color.BLACK, width, height, "", 0, 20.0);
                        break;
                }
            }else
            {
                obj.getCurrentAttributes().setX(pX);
                obj.getCurrentAttributes().setY(pY);
                obj.getCurrentAttributes().setWidth(width);
                if (type.equals("Circle"))
                    obj.getCurrentAttributes().setHeight(width);
                else
                    obj.getCurrentAttributes().setHeight(height);
            }

        }

        return obj;

    }

    /**
     * finishing drawing the new objects
     */
    public static void finishDrawingNew()
    {
        if (drawingObj != null && !drawingType.isEmpty())
        {
            drawingObj.getAttributeManager().finishDrawingNew();
            addGObject(drawingObj);
            drawingObj = null;
            drawingType = "";

        }
    }

    public static void addImageObject(BufferedImage image)
    {
        Point2D p = CameraManager.toWorldCoordinates(new Point(100,100));

        drawingObj = new GImage(image, p.getX(),p.getY(),
                (double) image.getWidth(), (double) image.getHeight());
        drawingType = "Image";
        finishDrawingNew();
    }

    /**
     * to draw two small rectangle on the diagonal of shapes,
     * let the shapes resizable by dragging those points.
     */
    public static void drawResizePoints(Graphics2D g2)
    {
        Point2D[] points = resizePointObj.getResizePoints();
        if (points == null) return;
        double SIZE = 11;

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        if (resizeRecs == null)
            resizeRecs = new Rectangle2D[points.length];
        for (int i = 0; i < points.length; i++)
        {
            double x = points[i].getX() - SIZE / 2;
            double y = points[i].getY() - SIZE / 2;
            if (resizeRecs[i] == null)
                resizeRecs[i] = new Rectangle2D.Double(x, y, SIZE, SIZE);
            else
                resizeRecs[i].setFrame(x,y,SIZE,SIZE);
            g2.draw(resizeRecs[i]);
        }

    }

    /**
     * find if the given x,y select a resize point
     * @param x x
     * @param y y
     * @return the selected rec, null if no rec is selected
     */
    public static Rectangle2D getResizableRec(double x, double y)
    {
        if (resizeRecs == null)
            return null;
        for (Rectangle2D rec:resizeRecs)
        {
            if (rec.contains(x,y))
                return rec;
        }

        return null;
    }

    /**
     * find if the given x,y select a resize point
     * @param point point
     * @return the selected rec, null if no rec is selected
     */
    public static Rectangle2D getResizableRec(Point2D point)
    {
        return getResizableRec(point.getX(),point.getY());
    }

    public static void updateResizablePoint(double x, double y)
    {
        if (resizeRecs == null)
            return;
        selectedResizePoint = null;
        unselectedResizePoint = null;

        for (Rectangle2D rec:resizeRecs)
        {
            if (rec.contains(x,y))
            {
                selectedResizePoint = new Point2D.Double(rec.getCenterX(), rec.getCenterY());
                unselectedResizePoint = rec == resizeRecs[0] ?
                        new Point2D.Double(resizeRecs[1].getCenterX(), resizeRecs[1].getCenterY()) :
                        new Point2D.Double(resizeRecs[0].getCenterX(), resizeRecs[0].getCenterY());
            }
        }

    }

    public static void updateResizablePoint(Point2D point)
    {
        updateResizablePoint(point.getX(), point.getY());
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

    /**
     * find if the given x,y select an object
     * @param point2D the point
     * @return the selected object, null if no selected
     */
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
            if (obj instanceof GText)
                ((GText) obj).updateTextString();
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
        if (StateManager.getTotal_State_Number() == 0)
        {
            deleteAllTextArea();
            gObjectList = new ArrayList<>();
        }
        else
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

    /**
     * add a JTextArea component to the list and center canvas
     * @param textObj the GText object that holds the JTextArea
     */
    public static void addTextArea(GText textObj)
    {
        JTextArea textArea = textObj.textArea;
        textArea.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                textObj.getCurrentAttributes().setTextString(textArea.getText());
            }
        });
        textArea.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                GObjectManager.inspectedObj = textObj;
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                //super.focusLost(e);
                GObjectManager.resizePointObj = null;
            }

        });
        textArea.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                super.mouseEntered(e);
                GObjectManager.resizePointObj = textObj;
            }
        });
        MainWindow.centerCanvas.add(textArea);
        textArea.requestFocus();
    }

    public static void deleteTextArea(GText textObj)
    {
        MainWindow.centerCanvas.remove(textObj.textArea);
    }

    /**
     * delete an object. If the object is a text box, delete the text area as well
     * @param obj the object to be deleted
     */
    public static void deleteGObject(GObject obj)
    {
        if (obj instanceof GText)
        {
            deleteTextArea((GText) obj);
        }
        gObjectList.remove(obj);

        resizePointObj = null;
        inspectedObj = null;
    }

    public static void reloadAllTextArea()
    {
        for (GObject obj: gObjectList)
            if (obj instanceof GText)
                addTextArea((GText) obj);
    }

    public static void deleteAllTextArea()
    {
        for(GObject obj: gObjectList)
            if (obj instanceof GText)
                deleteTextArea((GText) obj);
    }

    public static void addTextComponentToPresenter(CenterCanvas presentCanvas)
    {
        for(GObject obj: GObjectManager.gObjectList)
        {
            if (obj instanceof GText)
            {
                GText gText = (GText) obj;
                gText.textArea.setEditable(false);
                gText.textArea.setFocusable(false);
                presentCanvas.add(gText.textArea);
            }
        }
    }

    public static void resetTextComponentToCanvas()
    {
        for(GObject obj: GObjectManager.gObjectList)
        {
            if (obj instanceof GText)
            {
                GText gText = (GText) obj;
                gText.textArea.setEditable(true);
                gText.textArea.setFocusable(true);
                MainWindow.centerCanvas.add(gText.textArea);
            }
        }
    }
}
