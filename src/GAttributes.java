import java.awt.*;

public class GAttributes
{
    String label;
    protected double x, y;
    protected double x2,y2;
    protected int width, height;
    protected Color col;
    protected Boolean filled = Boolean.FALSE;
    protected Boolean visible = Boolean.TRUE;
    protected int lineWidth = 3;
    protected BasicStroke stroke;

    public GAttributes() {}

    public GAttributes(double x, double y, Color col, Boolean filled, int lineWidth)
    {
        this.x = x;
        this.y = y;
        this.col = col;
        this.filled = filled;
        this.lineWidth = lineWidth;
    }
}
