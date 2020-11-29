package prezoom.model;

import prezoom.controller.GObjectManager;
import prezoom.controller.PresentManager;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * The Image Object
 *
 * @author Zhijie Lan<p>
 * create date: 2020/11/27<p>
 **/
public class GImage extends GObject
{
    private final Image image;
    public GImage(Image image, Double x, Double y, Double width, Double height)
    {
        super(x, y, null, null, null,
                width, height, null, null, true,
                null, null, null, null);
        this.drawShape = new Rectangle2D.Double(x, y, width, height);
        this.image = image;
    }

    @Override
    public void draw(Graphics2D g)
    {
        Double x = getCurrentAttributes().getX(), y = getCurrentAttributes().getY();
        Double w = getCurrentAttributes().getWidth(), h = getCurrentAttributes().getHeight();

        this.drawShape = new Rectangle2D.Double(x, y, w, h);

        if (!getCurrentAttributes().getVisible())
        {
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                    50, new float[]{50}, 0));
            g.draw(drawShape);
            g.drawString("Invisible Image", (float) (x+w/2-50), (float) (y+h/2));
        } else
            g.drawImage(image,x.intValue(), y.intValue(),
                w.intValue(), h.intValue(),null);
    }
}
