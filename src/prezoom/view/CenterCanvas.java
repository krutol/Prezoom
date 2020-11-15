package prezoom.view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

import org.pushingpixels.trident.api.Timeline;
import org.pushingpixels.trident.api.swing.SwingRepaintTimeline;
import prezoom.controller.GAttributeManager;
import prezoom.model.*;
import prezoom.controller.CameraManager;
import prezoom.Main;

/** The center canvas where you can edit the presentation, move the camera, etc.
 * @author Zhijie Lan<p>
 * create date: 2020/11/1
 **/
public class CenterCanvas extends JPanel
{
//    private int mxstart, mystart;
//    private double zoomFactor = 1;
//    private double prevZoomFactor = 1;
    private boolean isZooming;
    private boolean isDragging;
    private boolean isReleased;
//    private double xOffset = 0;
//    private double yOffset = 0;
    private double xDiff;
    private double yDiff;
    private final Point dragCanvasStartPoint = new Point(), dragObjStartPoint = new Point();
    private GObject selectedObj;

    /**
     * the camera state manager
     */
    public CameraManager cameraManager = new CameraManager();

    private CameraInfo getCurCamInfo()
    {
        return cameraManager.cur_CamInfo;
    }

    // for test purpose
    public ArrayList<GObject> objects = new ArrayList<>();

    {
        objects.add(new GRectangle(50, 100, 30, 40, Color.red, false, 1));
        objects.add(new GRectangle(350, 500, 30, 40, Color.GREEN, true, 10));
        objects.add(new GOval(150, 200, 50, 30, Color.BLUE, true, 3));
        objects.add(new GLine(500,500,672, 789, Color.magenta, 5));
    }

    /**
     * add Mouse Listener, Mouse Wheel Listener, and Mouse Motion Listener to this panel
     */
    public CenterCanvas()
    {
//        addMouseWheelListener(this);
//        addMouseMotionListener(this);
//        addMouseListener(this);
        addMouseWheelListener(new MouseActionHandler());
        addMouseMotionListener(new MouseActionHandler());
        addMouseListener(new MouseActionHandler());

        SwingRepaintTimeline repaintTimeline = SwingRepaintTimeline.repaintBuilder(this)
                .setAutoRepaintMode(true).build();
        repaintTimeline.playLoop(Timeline.RepeatBehavior.LOOP);
    }

    /*
      move the camera to the given location

      @param xOffset        x offset
     * @param yOffset        y offset
     * @param zoomFactor     zoom index
     * @param prevZoomFactor previous zoom index that is used to get better effect when zooming
     */
//    public void setCanvasCamera(double xOffset, double yOffset, double zoomFactor, double prevZoomFactor)
//    {
//        this.xOffset = xOffset;
//        this.yOffset = yOffset;
//        this.zoomFactor = zoomFactor;
//        this.prevZoomFactor = prevZoomFactor;
//    }

    /**
     * override the default paint method to deal with dragging, zooming by {@link CameraManager#moveCamera(Graphics2D, double, double, double, double)}.
     * check objects' attributes for the current state from {@link GAttributeManager#getCur_Attributes()},
     * skip objects whose attributes for the current state do not exist
     *
     * @param g the graphics to paint
     */
    @Override
    protected void paintComponent(Graphics g)
    {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        //AffineTransform at = new AffineTransform();
        CameraInfo cam = getCurCamInfo();

        if (isZooming)
        {

            double xRel = MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX();
            double yRel = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY();

            //double zoomDiv = zoomFactor / prevZoomFactor;

//            xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * xRel;
//            yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * yRel;
//
//            prevZoomFactor = zoomFactor;
//            at.translate(xOffset, yOffset);
//            at.scale(zoomFactor, zoomFactor);
//            g2.transform(at);

            //cameraManager.setCamInfo(xOffset,yOffset,zoomFactor);
            //cameraManager.moveCamera(g2, xOffset, yOffset, zoomFactor, prevZoomFactor);

            double zoomDiv = cam.getZoomFactor() / cam.getPreZoomFactor();

            cam.setOffsetX((zoomDiv) * (cam.getOffsetX()) + (1 - zoomDiv) * xRel);
            cam.setOffsetY((zoomDiv) * (cam.getOffsetY()) + (1 - zoomDiv) * yRel);

            cam.setPreZoomFactor(cam.getZoomFactor());

            cameraManager.moveCamera(g2);

            //System.out.println(zoomFactor+" "+cur_off_x+" "+cur_off_y);

            isZooming = false;
        } else if (isDragging)
        {
            //System.out.println(xOffset + xDiff);
//            at.translate(xOffset + xDiff, yOffset + yDiff);
//            at.scale(zoomFactor, zoomFactor);
//            g2.transform(at);
            //cameraManager.setCamInfo(xOffset + xDiff,yOffset + yDiff,zoomFactor);
            //cameraManager.moveCamera(g2, xOffset + xDiff, yOffset + yDiff, zoomFactor, prevZoomFactor);
            cameraManager.moveCamera(g2,cam.getOffsetX()+xDiff,cam.getOffsetY()+yDiff,
                    cam.getZoomFactor(),cam.getPreZoomFactor());
            if (isReleased)
            {
//                xOffset += xDiff;
//                yOffset += yDiff;
                cam.setOffsetX(cam.getOffsetX()+xDiff);
                cam.setOffsetY(cam.getOffsetY()+yDiff);
                isDragging = false;
            }
        } else
        {
//            at.translate(xOffset, yOffset);
//            at.scale(zoomFactor, zoomFactor);
//            g2.transform(at);
            //cameraManager.setCamInfo(xOffset,yOffset,zoomFactor);
            //cameraManager.moveCamera(g2,xOffset,yOffset,zoomFactor);
            cameraManager.moveCamera(g2);
        }

        //g2.setColor(Color.white);
        //g2.fillRect(0,0,2000,1000);
        for (GObject go : objects)
        {
            if (go.getAttributeManager().getCur_Attributes() != null)
                go.draw(g2);
        }

        g2.dispose();


    }

    private class MouseActionHandler implements MouseWheelListener, MouseMotionListener, MouseListener
    {
        /**
         * deal with zooming
         *
         * @param e mouse action
         */
        @Override
        public void mouseWheelMoved(MouseWheelEvent e)
        {

            isZooming = true;
            CameraInfo cam = getCurCamInfo();

            //Zoom in
            if (e.getWheelRotation() < 0)
            {
                cam.setZoomFactor(cam.getZoomFactor()*1.1);
                repaint();
            }
            //Zoom out
            if (e.getWheelRotation() > 0)
            {
                cam.setZoomFactor(cam.getZoomFactor()/1.1);
                repaint();
            }

            Main.app.statusBar.setZoomText(String.format("Zoom: %3.2f %%", cam.getZoomFactor() * 100));

        }

        /**
         * deal with dragging
         *
         * @param e mouse action
         */
        @Override
        public void mouseDragged(MouseEvent e)
        {

            if (SwingUtilities.isRightMouseButton(e))
            {
                Point curPoint = e.getLocationOnScreen();
                xDiff = curPoint.getX() - dragCanvasStartPoint.getX();
                yDiff = curPoint.getY() - dragCanvasStartPoint.getY();

                isDragging = true;
                repaint();
            } else if (selectedObj != null)
            {
                //int mx = e.getX();
                //int my = e.getY();
                Point point = e.getPoint();
                //System.out.println(mx-mxstart);

//            double z = 1;
//            if (zoomFactor<1)
//                z = zoomFactor;

                selectedObj.getCurrentAttributes()
                        .setX(selectedObj.getCurrentAttributes().getX() +
                                /*(int)*/((point.getX() - dragObjStartPoint.getX()) / getCurCamInfo().getPreZoomFactor()));

                selectedObj.getCurrentAttributes()
                        .setY(selectedObj.getCurrentAttributes().getY() +
                                /*(int)*/((point.getY() - dragObjStartPoint.getY()) / getCurCamInfo().getPreZoomFactor()));
                //selectedObj.setX(mx);
                //selectedObj.setY(my);
                //mxstart = mx;
                //mystart = my;
                dragObjStartPoint.setLocation(point);
                //dragObjStartPoint = point;
                repaint();

            }


        }

        @Override
        public void mouseMoved(MouseEvent e)
        {
            Main.app.statusBar.setStatusText(String.format("Moving at [%d,%d]", e.getX(), e.getY()));
        }

        @Override
        public void mouseClicked(MouseEvent e)
        {
            //mxstart = e.getX();
            //mystart = e.getY();
            dragObjStartPoint.setLocation(e.getPoint());
            //dragObjStartPoint = e.getPoint();
        }

        /**
         * when pressed check whether an object is selected
         *
         * @param e mouse action
         */
        @Override
        public void mousePressed(MouseEvent e)
        {
            isReleased = false;
            //dragCanvasStartPoint = MouseInfo.getPointerInfo().getLocation();
            dragCanvasStartPoint.setLocation(MouseInfo.getPointerInfo().getLocation());

            //mxstart = e.getX();
            //mystart = e.getY();
            dragObjStartPoint.setLocation(e.getPoint());
            //dragObjStartPoint = e.getPoint();
            //System.out.println(mxstart);

            //mxstart -= o_X;
            //mystart -= o_Y;
            double mx = (e.getX() - getCurCamInfo().getOffsetX()) / getCurCamInfo().getPreZoomFactor();
            double my = (e.getY() - getCurCamInfo().getOffsetY()) / getCurCamInfo().getPreZoomFactor();
//        int mx=e.getX();
//        int my=e.getY();
            for (GObject go : objects)
            {
                if (go.inShape(mx, my))
                {
                    selectedObj = go;
                }
            }
            //repaint();
            //mxstart=mx;
            //mystart=my;
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            isReleased = true;
            selectedObj = null;
            repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {

        }

        @Override
        public void mouseExited(MouseEvent e)
        {

        }

    }

}

