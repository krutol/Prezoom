import java.awt.*;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/2
 **/
class GObject {
    protected int x,y;
    protected Color col;

    protected GObject(int x, int y, Color col) {
        this.x = x;
        this.y = y;
        this.col = col;
    }

    public Color getCol() {
        return col;
    }

    public void setCol(Color col) {
        this.col = col;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    protected void draw(Graphics g) {}

    protected boolean inShape(int x, int y) {return false;}
}

class Rectangle extends GObject {
    private int w,h;

    public Rectangle(int x, int y, Color col, int w, int h) {
        super(x, y, col);
        this.w = w;
        this.h = h;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public boolean inShape(int mx, int my) {
        if (mx>=x && mx<=x+w && my>=y && my<=y+h) {
            return true;
        } else {
            return false;
        }
    }

    public void draw(Graphics g) {
        g.setColor(col);
        g.drawRect(x, y, w, h);
    }

}

class Circle extends GObject {
    private int r;

    public Circle(int x, int y, Color col, int r) {
        super(x, y, col);
        this.r = r;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public boolean inShape(int mx, int my) {
        return Math.sqrt((mx-x)*(mx-x)+(my-y)*(my-y))<r;
    }

    public void draw(Graphics g) {
        g.setColor(col);
        g.drawOval(x, y, r, r);
    }

}
