package prezoom.model.GObject;

import prezoom.controller.GAttributeManager;
import prezoom.controller.PresentManager;
import prezoom.model.GAttributes;
import prezoom.model.GAttributesI;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;

/** The base class for all graphical objects
 * @author Zhijie Lan<p>
 * create date: 2020/11/1
 **/
public abstract class GObject implements Serializable
{
    /**
     * the manager to manage the state of this object
     */
    private final GAttributeManager gAttributeManager;

    /**
     * the actual shape object that shows on the canvas
     */
    protected Shape drawShape;

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
    protected GObject(Double x, Double y, Color col, Boolean filled,
                      Integer lineWidth, Double width, Double height,
                      Double x2, Double y2, Boolean visible,
                      String fontName, Integer fontStyle, Double fontSize, String textString)
    {
        gAttributeManager = new GAttributeManager(x, y, col, filled, lineWidth, width, height,
                x2, y2, visible, fontName, fontStyle, fontSize, textString);
    }

    /**
     * generate the shape to draw
     * @param g the Graphics to paint
     */
    public abstract void draw(Graphics2D g);

    /**
    * General function to draw all kinds of shapes
    * use {@link GAttributes#getColor()} as color.
    * use {@link GAttributes#getFilled()} to depend whether calling {@link Graphics2D#fill(Shape)} or {@link Graphics2D#draw(Shape)}.
    * use {@link GAttributes#getWidth()} to set the line style
    * @param g the Graphics to paint
    */
    protected void drawing(Graphics2D g)
    {
        if (!getCurrentAttributes().getVisible()
            && PresentManager.isPresenting)
            return;

        g.setColor(getCurrentAttributes().getColor());
        if (getCurrentAttributes().getFilled() != null && getCurrentAttributes().getFilled()) g.fill(drawShape);
        else
        {
            g.setStroke(new BasicStroke(getCurrentAttributes().getLineWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.draw(drawShape);
        }

        g.setColor(Color.BLACK);
        if (!getCurrentAttributes().getVisible())
            if (getCurrentAttributes().getX2() != null && getCurrentAttributes().getY2() != null)
                g.drawString("Invisible " + this.getClass().getSimpleName().substring(1),
                    (float) (getCurrentAttributes().getX()+getCurrentAttributes().getX2())/2-30,
                    (float) (getCurrentAttributes().getY()+getCurrentAttributes().getY2())/2);
            else
                g.drawString("Invisible " + this.getClass().getSimpleName().substring(1),
                        (float) (getCurrentAttributes().getX()+getCurrentAttributes().getWidth()/2) -50,
                        (float) (getCurrentAttributes().getY()+getCurrentAttributes().getHeight()/2));
    }

    /**
     * Whether the given position is in this object
     * @param mx x
     * @param my y
     * @return True if in the object
     */
    public boolean inShape(double mx, double my)
    {
        return drawShape.contains(mx,my);
    }

    /**
     * Get the attribute manager of this object
     * @return the attribute manager
     */
    public GAttributeManager getAttributeManager()
    {
        return gAttributeManager;
    }

    /**
     * Get the attributes for the current state
     * @return current attributes
     */
    public GAttributesI getCurrentAttributes()
    {
        return getAttributeManager().getCur_Attributes();
    }

    /**
     * get the position of the diagonal points to draw the resize points
     * @return diagonal points
     */
    public Point2D[] getResizePoints()
    {
        if (getCurrentAttributes() == null)
            return null;
        Point2D[] points = new Point2D[2];
        points[0]= new Point2D.Double(getCurrentAttributes().getX(), getCurrentAttributes().getY());
        if (this instanceof GLine)
        {
            points[1] = new Point2D.Double(getCurrentAttributes().getX2(), getCurrentAttributes().getY2());
        }
        else
            points[1] = new Point2D.Double(getCurrentAttributes().getX()+getCurrentAttributes().getWidth(),
                    getCurrentAttributes().getY()+getCurrentAttributes().getHeight());

        return points;
    }

}

