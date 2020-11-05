import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/4
 **/
class CameraManager
{
    ArrayList<CameraInfo> state_CamInfo_list = new ArrayList<>();
    CameraInfo cur_CamInfo;

    public CameraManager()
    {
        CameraInfo cameraInfo = new CameraInfo();
        state_CamInfo_list.add(getCurrent_State(), cameraInfo);
        updateCur_CamInfo();
    }

//    public void setCamInfo(double cam_x_offset, double cam_y_offset, double zoomFactor)
//    {
//        cur_CamInfo.cam_x_offset = cam_x_offset;
//        cur_CamInfo.cam_y_offset = cam_y_offset;
//        cur_CamInfo.am_zoomFactor = zoomFactor;
//    }

    public void moveCamera(Graphics2D g2, double cam_x_offset, double cam_y_offset, double zoomFactor, double prevZoomFactor)
    {
        cur_CamInfo.cam_x_offset = cam_x_offset;
        cur_CamInfo.cam_y_offset = cam_y_offset;
        cur_CamInfo.cam_zoomFactor = zoomFactor;
        cur_CamInfo.cam_prevZoomFactor = prevZoomFactor;

        AffineTransform at = new AffineTransform();
        at.translate(cur_CamInfo.cam_x_offset, cur_CamInfo.cam_y_offset);
        at.scale(cur_CamInfo.cam_zoomFactor, cur_CamInfo.cam_zoomFactor);
        g2.transform(at);
    }

    public void moveCamera(Graphics2D g2)
    {
        AffineTransform at = new AffineTransform();
        at.translate(cur_CamInfo.cam_x_offset, cur_CamInfo.cam_y_offset);
        at.scale(cur_CamInfo.cam_zoomFactor, cur_CamInfo.cam_zoomFactor);
        g2.transform(at);
    }

    public int getCurrent_State()
    {
        return StateManager.current_State;
    }

    public CameraInfo getCur_CamInfo()
    {
        return state_CamInfo_list.get(getCurrent_State());
    }

    public void updateCur_CamInfo()
    {
        this.cur_CamInfo = getCur_CamInfo();
        if (Main.app != null)
        Main.app.centerCanvas.setCanvasCamera(cur_CamInfo.cam_x_offset,
                cur_CamInfo.cam_y_offset, cur_CamInfo.cam_zoomFactor, cur_CamInfo.cam_prevZoomFactor);
    }

    public void insertCamState() throws CloneNotSupportedException
    {
        CameraInfo cameraInfo = new CameraInfo();

        if (!state_CamInfo_list.isEmpty())
            cameraInfo = (CameraInfo) state_CamInfo_list.get(getCurrent_State()-1).clone();

        state_CamInfo_list.add(getCurrent_State(), cameraInfo);
        updateCur_CamInfo();
    }

    public void deleteCamState(int state)
    {
        state_CamInfo_list.remove(state);
        updateCur_CamInfo();
    }
}

class CameraInfo implements Cloneable
{
    public double cam_x_offset = 0;
    public double cam_y_offset = 0;
    public double cam_zoomFactor = 1;
    public double cam_prevZoomFactor = 1;

    public CameraInfo() {}

    public CameraInfo(double cam_x_offset, double cam_y_offset, double cam_zoomFactor, double cam_prevZoomFactor)
    {
        this.cam_x_offset = cam_x_offset;
        this.cam_y_offset = cam_y_offset;
        this.cam_zoomFactor = cam_zoomFactor;
        this.cam_prevZoomFactor = cam_prevZoomFactor;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
