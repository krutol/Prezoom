import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import javax.swing.*;

public class CenterCanvas extends JPanel implements MouseWheelListener, MouseListener, MouseMotionListener{
    int mxstart, mystart;
    private double zoomFactor = 1;
    private double prevZoomFactor = 1;
    private boolean zoomer;
    private boolean dragger;
    private boolean released;
    private double xOffset = 0;
    private double yOffset = 0;
    private int xDiff;
    private int yDiff;
    private Point startPoint;
    private double cur_off_x = 0;
    private double cur_off_y = 0;
    GObject selectedObj;
    CameraManager cameraManager = new CameraManager();

    ArrayList<GObject> objects = new ArrayList<>();
    {
        objects.add(new Rectangle(50, 100, Color.red, false, 1,30, 40));
        objects.add(new Rectangle(350, 500, Color.GREEN, true, 10,30, 40));
        objects.add(new Oval(150, 200, Color.BLUE, true,3,50,30));
    }

    public CenterCanvas() {
        addMouseWheelListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        AffineTransform at = new AffineTransform();

        if (zoomer) {

            double xRel = MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX();
            double yRel = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY();

            double zoomDiv = zoomFactor / prevZoomFactor;

            xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * xRel;
            yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * yRel;

            prevZoomFactor = zoomFactor;
//            at.translate(xOffset, yOffset);
//            at.scale(zoomFactor, zoomFactor);
//            g2.transform(at);

            //cameraManager.setCamInfo(xOffset,yOffset,zoomFactor);
            cameraManager.moveCamera(g2,xOffset,yOffset,zoomFactor);

            cur_off_x = xOffset;
            cur_off_y = yOffset;

            //System.out.println(zoomFactor+" "+cur_off_x+" "+cur_off_y);

            zoomer = false;
        }
        else if (dragger) {
            //System.out.println(xOffset + xDiff);
//            at.translate(xOffset + xDiff, yOffset + yDiff);
//            at.scale(zoomFactor, zoomFactor);
//            g2.transform(at);
            //cameraManager.setCamInfo(xOffset + xDiff,yOffset + yDiff,zoomFactor);
            cameraManager.moveCamera(g2,xOffset + xDiff,yOffset + yDiff,zoomFactor);

            cur_off_x = xOffset + xDiff;
            cur_off_y = yOffset + yDiff;

            if (released) {
                xOffset += xDiff;
                yOffset += yDiff;
                dragger = false;
            }
        }
        else {
//            at.translate(xOffset, yOffset);
//            at.scale(zoomFactor, zoomFactor);
//            g2.transform(at);
            //cameraManager.setCamInfo(xOffset,yOffset,zoomFactor);
            cameraManager.moveCamera(g2,xOffset,yOffset,zoomFactor);
        }

        //g2.setColor(Color.white);
        //g2.fillRect(0,0,2000,1000);
        for (GObject go : objects) {
            go.draw(g2);
        }


    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        zoomer = true;

        //Zoom in
        if (e.getWheelRotation() < 0) {
            zoomFactor *= 1.1;
            repaint();
        }
        //Zoom out
        if (e.getWheelRotation() > 0) {
            zoomFactor /= 1.1;
            repaint();
        }

        Main.app.statusBar.setZoomText(String.format("Zoom: %3.2f %%",zoomFactor*100));

    }

    @Override
    public void mouseDragged(MouseEvent e) {

        if(SwingUtilities.isRightMouseButton(e))
        {
            Point curPoint = e.getLocationOnScreen();
            xDiff = curPoint.x - startPoint.x;
            yDiff = curPoint.y - startPoint.y;

            dragger = true;
            repaint();
        }
        else if(selectedObj != null){
            int mx=e.getX();
            int my=e.getY();
            //System.out.println(mx-mxstart);

//            double z = 1;
//            if (zoomFactor<1)
//                z = zoomFactor;

            selectedObj.setX(selectedObj.getX()+ /*(int)*/((mx-mxstart)/zoomFactor));
            selectedObj.setY(selectedObj.getY()+ /*(int)*/((my-mystart)/zoomFactor));
            //selectedObj.setX(mx);
            //selectedObj.setY(my);
            mxstart=mx;
            mystart=my;
            repaint();

        }


    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Main.app.statusBar.setStatusText(String.format("Moving at [%d,%d]",e.getX(),e.getY()));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mxstart = e.getX();
        mystart = e.getY();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        released = false;
        startPoint = MouseInfo.getPointerInfo().getLocation();

        mxstart = e.getX();
        mystart = e.getY();
        //System.out.println(mxstart);

        //mxstart -= o_X;
        //mystart -= o_Y;
        int mx=(int)((e.getX()-cur_off_x)/zoomFactor);
        int my=(int)((e.getY()-cur_off_y)/zoomFactor);
//        int mx=e.getX();
//        int my=e.getY();
        for (GObject go : objects)  {
            if (go.inShape(mx, my)) {
                selectedObj = go;
            }
        }
        //repaint();
        //mxstart=mx;
        //mystart=my;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        released = true;
        selectedObj = null;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}
