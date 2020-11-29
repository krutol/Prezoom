package prezoom.model;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * The Image Object
 *
 * @author Zhijie Lan<p>
 * create date: 2020/11/27<p>
 **/
public class GImage extends GObject
{
    private Image image;
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

        g.drawImage(image,x.intValue(), y.intValue(),
                w.intValue(), h.intValue(),null);
    }

    public Image getImage()
    {
        return image;
    }

    public void setImage(Image image)
    {
        this.image = image;
    }
}
