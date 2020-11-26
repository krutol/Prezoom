package prezoom.controller;

import prezoom.model.CameraInfo;
import prezoom.model.CameraInfoI;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * This class is the manager to manage all the camera related functions, including movement, info, states.
 *
 * @author Zhijie Lan<p>
 * create date: 2020/11/4
 **/
public class CameraManager
{
    /**
     * the array that stores info of each state
     */
    static ArrayList<CameraInfoI> state_CamInfo_list = new ArrayList<>();

    /**
     * the info for the current state
     */
    public static CameraInfoI cur_CamInfo;

    /**
     * the camera location used to navigate in the present canvas during presentation,
     * but not affect the stored state camera
     */
    public static CameraInfoI presentationCamera = new CameraInfo();

    static
    {
        CameraInfoI cameraInfo = new CameraInfo();
        state_CamInfo_list.add(getCurrent_State(), cameraInfo);
        updateCur_CamInfo();
    }

    public CameraManager()
    {
        CameraInfoI cameraInfo = new CameraInfo();
        state_CamInfo_list.add(getCurrent_State(), cameraInfo);
        updateCur_CamInfo();
    }

//    public void setCamInfo(double cam_x_offset, double cam_y_offset, double zoomFactor)
//    {
//        cur_CamInfo.cam_x_offset = cam_x_offset;
//        cur_CamInfo.cam_y_offset = cam_y_offset;
//        cur_CamInfo.am_zoomFactor = zoomFactor;
//    }

    /**
     * move the camera to the given location
     *
     * @param g2             the Graphics to paint
     * @param cam_x_offset   x offset
     * @param cam_y_offset   y offset
     * @param zoomFactor     zoom index
     * @param prevZoomFactor previous zoom index that is used to get better effect when zooming
     */
    public static void moveCamera(Graphics2D g2, double cam_x_offset, double cam_y_offset, double zoomFactor, double prevZoomFactor)
    {
//        cur_CamInfo.setOffsetX(cam_x_offset);
//        cur_CamInfo.setOffsetY(cam_y_offset);
//        cur_CamInfo.setZoomFactor(zoomFactor);
//        cur_CamInfo.setPreZoomFactor(prevZoomFactor);
//
//        AffineTransform at = new AffineTransform();
//        at.translate(cur_CamInfo.getOffsetX(), cur_CamInfo.getOffsetY());
//        at.scale(cur_CamInfo.getZoomFactor(), cur_CamInfo.getZoomFactor());
//        g2.transform(at);

        AffineTransform at = new AffineTransform();
        at.translate(cam_x_offset, cam_y_offset);
        at.scale(zoomFactor, zoomFactor);
        g2.transform(at);
    }

    /**
     * move the camera to the stored location
     *
     * @param g2 the Graphics to paint
     */
    public static void moveCamera(Graphics2D g2)
    {
        AffineTransform at = new AffineTransform();
        at.translate(getCorrectCamera().getOffsetX(), getCorrectCamera().getOffsetY());
        at.scale(getCorrectCamera().getZoomFactor(), getCorrectCamera().getZoomFactor());
        g2.transform(at);
    }


    /**
     * from view coordinate to world coordinate according to current camera location
     * @param point view coordinate
     * @return world coordinate
     */
    public static Point2D toWorldCoordinates(Point point)
    {
        double wX = (point.getX() - getCorrectCamera().getOffsetX()) / getCorrectCamera().getPreZoomFactor();
        double wY = (point.getY() - getCorrectCamera().getOffsetY()) / getCorrectCamera().getPreZoomFactor();

        return new Point2D.Double(wX, wY);
    }

    /**
     * from world coordinate to view coordinate according to current camera location
     * @param wX world coordinate X
     * @param wY world coordinate Y
     * @return view coordinate
     */
    public static Point2D toViewCoordinates(double wX, double wY)
    {
        double vX = wX*getCorrectCamera().getPreZoomFactor() + getCorrectCamera().getOffsetX();
        double vY = wY*getCorrectCamera().getPreZoomFactor() + getCorrectCamera().getOffsetY();

        return new Point2D.Double(vX, vY);
    }

    /**
     * set the location of presentation camera
     */
    public static void initializePresentationCamera()
    {
        updateCur_CamInfo();
    }

    /**
     * get the current state index
     *
     * @return current state index
     */
    private static int getCurrent_State()
    {
        return StateManager.getCurrent_State();
    }

    /**
     * get the camera info for the current state
     *
     * @return the current camera info
     */
    private static CameraInfoI getCur_CamInfoFromList()
    {
        return state_CamInfo_list.get(getCurrent_State());
    }

    /**
     * get the current camera info
     * @return {@link #cur_CamInfo} if not presenting, {@link #presentationCamera} if presenting
     */
    public static CameraInfoI getCorrectCamera()
    {
        return PresentManager.isPresenting ? presentationCamera : cur_CamInfo;
    }

    /**
     * update the {@link #cur_CamInfo}.
     * and add interpolation from previous value to the current value
     */
    public static void updateCur_CamInfo()
    {
        //this.cur_CamInfo = getCur_CamInfoFromList();
//        if (Main.app != null)
//        Main.app.centerCanvas.setCanvasCamera(cur_CamInfo.getOffsetX(),
//                cur_CamInfo.getOffsetY(), cur_CamInfo.getZoomFactor(), cur_CamInfo.getPreZoomFactor());
        if (cur_CamInfo != null)
        {
            CameraInfoI preCam = cur_CamInfo, curCam;
            cur_CamInfo = getCur_CamInfoFromList();
            if (PresentManager.isPresenting)
            {
                try
                {
                    preCam = presentationCamera.clone();
                    presentationCamera = cur_CamInfo.clone();

                    presentationCamera.setOffsetX(
                            PresentManager.presentZoomFactor *
                                    presentationCamera.getOffsetX());
                    presentationCamera.setOffsetY(
                            PresentManager.presentZoomFactor *
                                    presentationCamera.getOffsetY());

                    presentationCamera.setZoomFactor(
                            presentationCamera.getZoomFactor()*
                            PresentManager.presentZoomFactor);
                    presentationCamera.setPreZoomFactor(
                            presentationCamera.getPreZoomFactor()*
                                    PresentManager.presentZoomFactor);


                } catch (CloneNotSupportedException e)
                {
                    e.printStackTrace();
                }
                curCam = presentationCamera;

            }
            else
            {
                curCam = cur_CamInfo;
            }

            InterpolationFactory.buildInterpolation(preCam, curCam);

        } else
        {
            cur_CamInfo = getCur_CamInfoFromList();
        }


    }

    /**
     * insert a new camera info of a new state into {@link #state_CamInfo_list} at the current state index.
     * The new camera info will clone the previous info of the current state
     *
     */
    public static void insertCamState()
    {
        CameraInfoI cameraInfo = new CameraInfo();

        if (!state_CamInfo_list.isEmpty())
        {
            try
            {
                cameraInfo = state_CamInfo_list.get(getCurrent_State() - 1).clone();
            } catch (CloneNotSupportedException e)
            {
                e.printStackTrace();
            }
        }

        state_CamInfo_list.add(getCurrent_State(), cameraInfo);

        // comment the update,  set it be triggered by the state manager to avoid double update
        //updateCur_CamInfo();
    }

    /**
     * delete a given state form {@link #state_CamInfo_list}
     *
     * @param state the state to be deleted
     */
    public static void deleteCamState(int state)
    {
        state_CamInfo_list.remove(state);
        // comment the update,  set it be triggered by the state manager to avoid double update
        //updateCur_CamInfo();
    }
}
