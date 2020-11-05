import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.HashMap;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/4
 **/
class CameraManager
{
    HashMap<Integer, CameraInfo> state_CamInfo_map = new HashMap<>();
    CameraInfo cur_CamInfo;

    public CameraManager()
    {
        CameraInfo cameraInfo = new CameraInfo();
        state_CamInfo_map.put(getCurrent_State(), cameraInfo);
        updateCur_CamInfo();
    }

//    public void setCamInfo(double cam_x_offset, double cam_y_offset, double zoomFactor)
//    {
//        cur_CamInfo.cam_x_offset = cam_x_offset;
//        cur_CamInfo.cam_y_offset = cam_y_offset;
//        cur_CamInfo.am_zoomFactor = zoomFactor;
//    }

    public void moveCamera(Graphics2D g2, double cam_x_offset, double cam_y_offset, double zoomFactor)
    {
        cur_CamInfo.cam_x_offset = cam_x_offset;
        cur_CamInfo.cam_y_offset = cam_y_offset;
        cur_CamInfo.am_zoomFactor = zoomFactor;

        AffineTransform at = new AffineTransform();
        at.translate(cur_CamInfo.cam_x_offset, cur_CamInfo.cam_y_offset);
        at.scale(cur_CamInfo.am_zoomFactor, cur_CamInfo.am_zoomFactor);
        g2.transform(at);
    }

    public int getCurrent_State()
    {
        return StateManager.current_State;
    }

    public CameraInfo getCur_CamInfo()
    {
        return state_CamInfo_map.get(getCurrent_State());
    }

    public void updateCur_CamInfo()
    {
        this.cur_CamInfo = getCur_CamInfo();
    }
}

class CameraInfo
{
    public double cam_x_offset = 0;
    public double cam_y_offset = 0;
    public double am_zoomFactor = 1;

    public CameraInfo() {}

    public CameraInfo(double cam_x_offset, double cam_y_offset, double am_zoomFactor)
    {
        this.cam_x_offset = cam_x_offset;
        this.cam_y_offset = cam_y_offset;
        this.am_zoomFactor = am_zoomFactor;
    }
}
