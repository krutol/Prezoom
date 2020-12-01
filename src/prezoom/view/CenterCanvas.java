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
    private final Point dragCanvasStartPoint = new Point(),
            dragObjStartPoint = new Point(),
            drawObjStartPoint = new Point();
    private final JLabel pageLabel = new JLabel("", JLabel.CENTER);

    private CameraInfoI getCurCamInfo()
    {
        return CameraManager.getCorrectCamera();
    }


    /**
     * add Mouse Listener, Mouse Wheel Listener, and Mouse Motion Listener to this panel
     * if in the presentation mode, set another set of listener
     * @param isPresenting whether the canvas is using for presentation
     */
    public CenterCanvas(boolean isPresenting)
    {
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

        SwingRepaintTimeline repaintTimeline = SwingRepaintTimeline.repaintBuilder(this)
                .setAutoRepaintMode(true).build();
        repaintTimeline.playLoop(Timeline.RepeatBehavior.LOOP);

        setLayout(null);
    }

    /**
     * to get the current screen image
     * @return the screen shot image
     */
    public BufferedImage getScreenShot()
    {
        if (this.getWidth() == 0 || this.getHeight() == 0)
            return null;
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        this.paint(image.getGraphics());   // paints into image's Graphics
        return image;
    }

    /**
     * override the default paint method to deal with dragging, zooming by {@link CameraManager#moveCamera(Graphics2D)}.
     * drawing all objects by {@link GObjectManager#drawAll(Graphics2D)},
     *
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

//        if (GObjectManager.drawingObj != null
//                || GObjectManager.draggedObj != null
//                || GObjectManager.resizedObj != null)
        if (StateManager.getTotal_State_Number() > 0)
            MainWindow.attributePanel.rearrangeValues();

        //if camera manage updatedCameraInfo is updated
        if(CameraManager.updatedCameraInfo) {
            //update the cameraInspectorPanel
            MainWindow.cameraPanel.rearrangeValues();

            //set the updatedCameraInfo to false after camera inspector panel update
            CameraManager.updatedCameraInfo = false;
        }
    }

    /**
     * move the camera location
     * @param e mouse event
     */
    private void dragCanvas(MouseEvent e)
    {
        CameraInfoI cam = getCurCamInfo();
        Point curPoint = e.getLocationOnScreen();
        double xDiff = curPoint.getX() - dragCanvasStartPoint.getX();
        double yDiff = curPoint.getY() - dragCanvasStartPoint.getY();

        cam.setOffsetX(cam.getOffsetX() + xDiff);
        cam.setOffsetY(cam.getOffsetY() + yDiff);

        dragCanvasStartPoint.setLocation(curPoint);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        CameraManager.updatedCameraInfo = true;
    }

    /**
     * scale the camera
     * @param e mouse event
     */
    private void zoomCanvas(MouseWheelEvent e)
    {
        CameraInfoI cam = getCurCamInfo();

        //Zoom in
        if (e.getWheelRotation() < 0)
        {
            cam.setZoomFactor(cam.getZoomFactor() * 1.1);
            repaint();
        }
        //Zoom out
        if (e.getWheelRotation() > 0)
        {
            cam.setZoomFactor(cam.getZoomFactor() / 1.1);
            repaint();
        }

        double xRel = MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX();
        double yRel = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY();

        double zoomDiv = cam.getZoomFactor() / cam.getPreZoomFactor();

        cam.setOffsetX((zoomDiv) * (cam.getOffsetX()) + (1 - zoomDiv) * xRel);
        cam.setOffsetY((zoomDiv) * (cam.getOffsetY()) + (1 - zoomDiv) * yRel);

        cam.setPreZoomFactor(cam.getZoomFactor());
        CameraManager.updatedCameraInfo = true;
    }

    /**
     * the listener for the edit mode
     */
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
            zoomCanvas(e);

            MainWindow.statusBar.setZoomText(String.format("Zoom: %3.2f %%", getCurCamInfo().getZoomFactor() * 100));
        }

        /**
         * deal with dragging
         * if mouse right clicked, drag the canvas.
         * if {@link GObjectManager#drawingType} != null, drawing shapes.
         * if {@link GObjectManager#draggedObj} != null, dragging the object
         * if {@link GObjectManager#resizedObj} != null, resizing the object
         *
         * @param e mouse action
         */
        @Override
        public void mouseDragged(MouseEvent e)
        {

            if (SwingUtilities.isRightMouseButton(e))
            {
                dragCanvas(e);
            }
            else if (!GObjectManager.drawingType.isEmpty())
            {
                GObjectManager.drawingObj = GObjectManager.updateResizing(drawObjStartPoint, CameraManager.toWorldCoordinates(e.getPoint()),
                        GObjectManager.drawingObj, GObjectManager.drawingType);
                GObjectManager.inspectedObj = GObjectManager.drawingObj;

            }else if (GObjectManager.draggedObj != null)
            {
                Point point = e.getPoint();

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

                dragObjStartPoint.setLocation(point);

            } else if (GObjectManager.resizedObj != null)
            {
                Point2D cursor = CameraManager.toWorldCoordinates(e.getPoint());

                String type = GObjectManager.resizedObj.getClass().getSimpleName().substring(1);

                Point2D point1 = GObjectManager.unselectedResizePoint;
                Point2D point2 = GObjectManager.selectedResizePoint;
                point2.setLocation(cursor);

                GObjectManager.updateResizing(point1,point2,GObjectManager.resizedObj, type);

            } else if (SwingUtilities.isLeftMouseButton(e))
            {
                dragCanvas(e);
            }
            repaint();

        }

        /**
         * when mouse moving, keep updating the cursor type
         * @param e mouse event
         */
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

        /**
         * right click to pop up delete menu
         * @param e mouse event
         */
        @Override
        public void mouseClicked(MouseEvent e)
        {
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
        }

        /**
         * when pressed check whether an object or a resize point is selected
         *
         * @param e mouse action
         */
        @Override
        public void mousePressed(MouseEvent e)
        {
            CenterCanvas.this.requestFocus();

            dragCanvasStartPoint.setLocation(MouseInfo.getPointerInfo().getLocation());


            dragObjStartPoint.setLocation(e.getPoint());

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
                GObjectManager.resizedObj = GObjectManager.resizePointObj;
            }

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

    /**
     * the listener for the presentation mode.
     */
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
            zoomCanvas(e);

        }

        /**
         * deal with dragging
         *
         * @param e mouse action
         */
        @Override
        public void mouseDragged(MouseEvent e)
        {
            dragCanvas(e);
            repaint();

        }

        @Override
        public void mouseMoved(MouseEvent e)
        {
        }

        /**
         * deal with presentation mouse trigger
         * @param e mouse event
         */
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



