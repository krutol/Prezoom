package prezoom.model.GObject;


import prezoom.controller.PresentManager;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * The Image Object
 *
 * @author Zhijie Lan<p>
 * create date: 2020/11/27
 **/
public class GImage extends GObject
{
    /**
     * the Image to be shown
     */
    private final ImageIcon image;
    public GImage(BufferedImage image, Double x, Double y, Double width, Double height)
    {
        super(x, y, null, null, null,
                width, height, null, null, true,
                null, null, null, null);
        this.drawShape = new Rectangle2D.Double(x, y, width, height);
        this.image = new ImageIcon(image);
    }

    /**
     * draw the image to the canvas.
     * When the Image is invisible, draw a rectangle with dash lines to represent invisible images
     * @param g the Graphics to paint
     */
    @Override
    public void draw(Graphics2D g)
    {
        Double x = getCurrentAttributes().getX(), y = getCurrentAttributes().getY();
        Double w = getCurrentAttributes().getWidth(), h = getCurrentAttributes().getHeight();

        this.drawShape = new Rectangle2D.Double(x, y, w, h);

        if (!getCurrentAttributes().getVisible()
            &&!PresentManager.isPresenting)
        {
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                    50, new float[]{50}, 0));
            g.draw(drawShape);
            g.drawString("Invisible Image", (float) (x+w/2-50), (float) (y+h/2));
        } else if (getCurrentAttributes().getVisible())
            g.drawImage(image.getImage(),x.intValue(), y.intValue(),
                w.intValue(), h.intValue(),null);
    }
}
