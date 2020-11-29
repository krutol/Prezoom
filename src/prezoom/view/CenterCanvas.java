package prezoom.view;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.swing.*;

import org.pushingpixels.trident.api.Timeline;
import org.pushingpixels.trident.api.swing.SwingRepaintTimeline;
import prezoom.controller.*;
import prezoom.model.CameraInfoI;
import prezoom.model.GAttributesI;

/** The center canvas where you can edit the presentation, move the camera, etc.
 * @author Zhijie Lan<p>
 * create date: 2020/11/1
 **/
public class CenterCanvas extends JPanel
{
//    private int mxstart, mystart;
//    private double zoomFactor = 1;
//    private double prevZoomFactor = 1;
//    private double xOffset = 0;
//    private double yOffset = 0;
    private final Point dragCanvasStartPoint = new Point(),
            dragObjStartPoint = new Point(),
            drawObjStartPoint = new Point();
    private final JLabel pageLabel = new JLabel("", JLabel.CENTER);

    //self reference
    private final JPanel self;

//    private GObject selectedObj;
//    public GObject inspectedObj;

//    /**
//     * the camera state manager
//     */
//    public static CameraManager cameraManager = new CameraManager();
//
//    /**
//     * the object manager
//     */
//    public static GObjectManager gObjectManager = new GObjectManager();

    private CameraInfoI getCurCamInfo()
    {
        return CameraManager.getCorrectCamera();
    }


    // for test purpose
//    public ArrayList<GObject> objects = new ArrayList<>();
//
//    {
//        objects.add(new GRectangle(50, 100, 30, 40, Color.red, false, 1));
//        objects.add(new GRectangle(350, 500, 30, 40, Color.GREEN, true, 10));
//        objects.add(new GOval(150, 200, 50, 30, Color.BLUE, true, 3));
//        objects.add(new GLine(500,500,672, 789, Color.magenta, 5));
//    }
    /**
     * add Mouse Listener, Mouse Wheel Listener, and Mouse Motion Listener to this panel
     */
    public CenterCanvas(boolean isPresenting)
    {
//        addMouseWheelListener(this);
//        addMouseMotionListener(this);
//        addMouseListener(this);

        if (isPresenting)
        {
            addMouseWheelListener(new PresentModeActionHandler());
            addMouseMotionListener(new PresentModeActionHandler());
            addMouseListener(new PresentModeActionHandler());
            pageLabel.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2-50,
                    (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()-30,
                    100,30);
            pageLabel.setFont(pageLabel.getFont().deriveFont(Font.BOLD, 16));
            add(pageLabel);
        }
        else {
            addMouseWheelListener(new EditModeActionHandler());
            addMouseMotionListener(new EditModeActionHandler());
            addMouseListener(new EditModeActionHandler());
            setPreferredSize(new Dimension(1280,720));
        }

        //store tis in self
        this.self = this;

        //add focus listener to canvas
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent arg0) {
                //remove focus listener
                //self.removeFocusListener(this);

                //set focus on the last row of inspector panel to remove focus from
                //the currently edited cell, if the opposite component of the event
                //is currently edited cell
                MainWindow.inspectorPanel.jTableInspector.setFocusOnLastRowFirstColumn(arg0.getOppositeComponent());

                //set focus on the last row of camera inspector panel
                MainWindow.cameraInspectorPanel.jTableInspector.setFocusOnLastRowFirstColumn(arg0.getOppositeComponent());
                //self.addFocusListener(this);
            }

            @Override
            public void focusLost(FocusEvent arg0) {
            }
        });

        SwingRepaintTimeline repaintTimeline = SwingRepaintTimeline.repaintBuilder(this)
                .setAutoRepaintMode(true).build();
        repaintTimeline.playLoop(Timeline.RepeatBehavior.LOOP);

        setLayout(null);
    }

    /**
     * used to get the current image drawn on the screen
     * @return the screen shot image
     */
    public BufferedImage getScreenShot()
    {
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        this.paint(image.getGraphics());   // paints into image's Graphics
        return image;
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

        Graphics2D g2 = (Graphics2D) g.create();

        CameraManager.moveCamera(g2);

        GObjectManager.drawAll(g2);

        if (GObjectManager.drawingObj != null)
            GObjectManager.drawingObj.draw(g2);

        if (GObjectManager.resizePointObj != null)
            GObjectManager.drawResizePoints(g2);

        if (PresentManager.isPresenting)
            pageLabel.setText(StateManager.getCurrent_State()+1+
                    "/"+StateManager.getTotal_State_Number());

        g2.dispose();

        if (GObjectManager.drawingObj != null
                || GObjectManager.draggedObj != null
                || GObjectManager.resizedObj != null)
            MainWindow.inspectorPanel.rearrangeValues();

        //if camera manage updatedCameraInfo is updated
        if(CameraManager.updatedCameraInfo) {
            //update the cameraInspectorPanel
            MainWindow.cameraInspectorPanel.rearrangeValues();

            //set the updatedCameraInfo to false after camera inspector panel update
            CameraManager.updatedCameraInfo = false;
        }
    }

    private class EditModeActionHandler implements MouseWheelListener, MouseMotionListener, MouseListener
    {
        /**
         * deal with zooming
         *
         * @param e mouse action
         */
        @Override
        public void mouseWheelMoved(MouseWheelEvent e)
        {
            CameraInfoI cam = getCurCamInfo();

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

            double xRel = MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX();
            double yRel = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY();

            double zoomDiv = cam.getZoomFactor() / cam.getPreZoomFactor();

            cam.setOffsetX((zoomDiv) * (cam.getOffsetX()) + (1 - zoomDiv) * xRel);
            cam.setOffsetY((zoomDiv) * (cam.getOffsetY()) + (1 - zoomDiv) * yRel);

            cam.setPreZoomFactor(cam.getZoomFactor());

            MainWindow.statusBar.setZoomText(String.format("Zoom: %3.2f %%", cam.getZoomFactor() * 100));

            //set the updatedCameraInfo to true after mouse wheel has updated
            //the camera info
            CameraManager.updatedCameraInfo = true;

            //on mouse wheel motion event focus back to center canvas
            CenterCanvas.this.requestFocus();

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
                CameraInfoI cam = getCurCamInfo();
                Point curPoint = e.getLocationOnScreen();
                double xDiff = curPoint.getX() - dragCanvasStartPoint.getX();
                double yDiff = curPoint.getY() - dragCanvasStartPoint.getY();

                cam.setOffsetX(cam.getOffsetX()+xDiff);
                cam.setOffsetY(cam.getOffsetY()+yDiff);

                dragCanvasStartPoint.setLocation(curPoint);

                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                CameraManager.updatedCameraInfo = true;
            }
            else if (!GObjectManager.drawingType.isEmpty())
            {
                GObjectManager.drawingObj = GObjectManager.updateResizing(drawObjStartPoint, CameraManager.toWorldCoordinates(e.getPoint()),
                        GObjectManager.drawingObj, GObjectManager.drawingType);
                GObjectManager.inspectedObj = GObjectManager.drawingObj;

            }else if (GObjectManager.draggedObj != null)
            {
                //int mx = e.getX();
                //int my = e.getY();
                Point point = e.getPoint();
                //System.out.println(mx-mxstart);

//            double z = 1;
//            if (zoomFactor<1)
//                z = zoomFactor;
                GAttributesI draggedObj_curAttr = GObjectManager.draggedObj.getCurrentAttributes();

                draggedObj_curAttr
                        .setX(draggedObj_curAttr.getX() +
                                /*(int)*/((point.getX() - dragObjStartPoint.getX()) / getCurCamInfo().getPreZoomFactor()));

                draggedObj_curAttr
                        .setY(draggedObj_curAttr.getY() +
                                /*(int)*/((point.getY() - dragObjStartPoint.getY()) / getCurCamInfo().getPreZoomFactor()));
                if (draggedObj_curAttr.getX2() != null
                        && draggedObj_curAttr.getY2() != null)
                {
                    draggedObj_curAttr
                            .setX2(draggedObj_curAttr.getX2() +
                                    /*(int)*/((point.getX() - dragObjStartPoint.getX()) / getCurCamInfo().getPreZoomFactor()));

                    draggedObj_curAttr
                            .setY2(draggedObj_curAttr.getY2() +
                                    /*(int)*/((point.getY() - dragObjStartPoint.getY()) / getCurCamInfo().getPreZoomFactor()));
                }
                //selectedObj.setX(mx);
                //selectedObj.setY(my);
                //mxstart = mx;
                //mystart = my;
                dragObjStartPoint.setLocation(point);

            } else if (GObjectManager.resizedObj != null)
            {
                Point2D cursor = CameraManager.toWorldCoordinates(e.getPoint());

                String type = GObjectManager.resizedObj.getClass().getSimpleName().substring(1);

                Point2D point1 = GObjectManager.unselectedResizePoint;
                Point2D point2 = GObjectManager.selectedResizePoint;
                point2.setLocation(cursor);

                GObjectManager.updateResizing(point1,point2,GObjectManager.resizedObj, type);

            }
            repaint();

        }

        @Override
        public void mouseMoved(MouseEvent e)
        {
            Point2D point2D = CameraManager.toWorldCoordinates(e.getPoint());
            MainWindow.statusBar.setStatusText(String.format("Moving at [%d,%d]", (int)point2D.getX(), (int)point2D.getY()));
            if (!GObjectManager.drawingType.isEmpty())
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            else if (GObjectManager.getResizableRec(point2D) != null)
                setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
            else if (GObjectManager.findSelected(point2D) != null)
                setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            else
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

        @Override
        public void mouseClicked(MouseEvent e)
        {
            //mxstart = e.getX();
            //mystart = e.getY();
            if (SwingUtilities.isRightMouseButton(e)
                && GObjectManager.findSelected(CameraManager.toWorldCoordinates(e.getPoint())) != null)
            {
                Point2D point2D = CameraManager.toWorldCoordinates(e.getPoint());

                JPopupMenu menu = new JPopupMenu();
                JMenuItem delete = new JMenuItem("Delete");
                delete.addActionListener(e1 -> GObjectManager.deleteGObject(GObjectManager.findSelected(point2D)));
                delete.setBackground(Color.lightGray);

                menu.add(delete);
                menu.show(CenterCanvas.this, e.getX(), e.getY());

            }


            dragObjStartPoint.setLocation(e.getPoint());
            CenterCanvas.this.requestFocus();
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
            //dragCanvasStartPoint = MouseInfo.getPointerInfo().getLocation();
            dragCanvasStartPoint.setLocation(MouseInfo.getPointerInfo().getLocation());


            //mxstart = e.getX();
            //mystart = e.getY();
            dragObjStartPoint.setLocation(e.getPoint());
            //dragObjStartPoint = e.getPoint();
            //System.out.println(mxstart);

            //mxstart -= o_X;
            //mystart -= o_Y;
            Point2D point2D = CameraManager.toWorldCoordinates(e.getPoint());
            drawObjStartPoint.setLocation(point2D);
            GObjectManager.updateResizablePoint(point2D);

            if (GObjectManager.selectedResizePoint == null)
            {
                GObjectManager.draggedObj = GObjectManager.findSelected(point2D);
                if (GObjectManager.draggedObj != null)
                {
                    GObjectManager.inspectedObj = GObjectManager.draggedObj;
                    GObjectManager.resizePointObj = GObjectManager.draggedObj;
                }else
                    GObjectManager.resizePointObj = null;

            } else
            {
                GObjectManager.resizedObj = GObjectManager.inspectedObj;
            }



            //repaint();
            //mxstart=mx;
            //mystart=my;
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            GObjectManager.draggedObj = null;
            GObjectManager.resizedObj = null;

            GObjectManager.finishDrawingNew();

            MainWindow.statePanel.updateBtnImage(getScreenShot());
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            repaint();

            //set focus on the last row first column
            MainWindow.inspectorPanel.jTableInspector.setFocusOnLastRowFirstColumn();

            //set focus on the last row first column
            MainWindow.cameraInspectorPanel.jTableInspector.setFocusOnLastRowFirstColumn();
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {
            MainWindow.statePanel.updateBtnImage(getScreenShot());
        }

        @Override
        public void mouseExited(MouseEvent e)
        {
            GObjectManager.resizePointObj = null;
            MainWindow.statePanel.updateBtnImage(getScreenShot());
        }

    }

    private class PresentModeActionHandler implements MouseWheelListener, MouseMotionListener, MouseListener
    {
        /**
         * deal with zooming
         *
         * @param e mouse action
         */
        @Override
        public void mouseWheelMoved(MouseWheelEvent e)
        {
            CameraInfoI cam = getCurCamInfo();

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

            double xRel = MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX();
            double yRel = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY();

            double zoomDiv = cam.getZoomFactor() / cam.getPreZoomFactor();

            cam.setOffsetX((zoomDiv) * (cam.getOffsetX()) + (1 - zoomDiv) * xRel);
            cam.setOffsetY((zoomDiv) * (cam.getOffsetY()) + (1 - zoomDiv) * yRel);

            cam.setPreZoomFactor(cam.getZoomFactor());

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
                CameraInfoI cam = getCurCamInfo();
                Point curPoint = e.getLocationOnScreen();
                double xDiff = curPoint.getX() - dragCanvasStartPoint.getX();
                double yDiff = curPoint.getY() - dragCanvasStartPoint.getY();

                cam.setOffsetX(cam.getOffsetX()+xDiff);
                cam.setOffsetY(cam.getOffsetY()+yDiff);

                dragCanvasStartPoint.setLocation(curPoint);

                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            repaint();

        }

        @Override
        public void mouseMoved(MouseEvent e)
        {
        }

        @Override
        public void mouseClicked(MouseEvent e)
        {
            if (PresentManager.isPresenting)
            {
                PresentationWindow pWindow = (PresentationWindow) CenterCanvas.
                        this.getParent().getParent().getParent().getParent();
                if (SwingUtilities.isLeftMouseButton(e))
                    pWindow.nextState();
                else if (SwingUtilities.isRightMouseButton(e))
                    pWindow.previousState();
                else if (SwingUtilities.isMiddleMouseButton(e))
                    pWindow.currentState();
            }
        }

        /**
         * when pressed check whether an object is selected
         *
         * @param e mouse action
         */
        @Override
        public void mousePressed(MouseEvent e)
        {
            dragCanvasStartPoint.setLocation(MouseInfo.getPointerInfo().getLocation());

        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
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



