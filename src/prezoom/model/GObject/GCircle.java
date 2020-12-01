package prezoom.model.GObject;

import java.awt.*;

/**
 * The Circle class
 *
 * @author Zhijie Lan<p>
 * create date: 2020/11/18
 **/
public class GCircle extends GOval
{
    public GCircle(double x, double y, double w, Color col, Boolean filled, int lineWidth)
    {
        super(x, y, w, w, col, filled, lineWidth);
    }
}
