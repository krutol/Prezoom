package prezoom.model;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/4
 **/
public class CameraInfo implements Cloneable
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
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
