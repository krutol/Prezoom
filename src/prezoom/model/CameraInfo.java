package prezoom.model;

/** the base class for camera info
 * @author Zhijie Lan<p>
 * create date: 2020/11/4
 **/
public class CameraInfo implements Cloneable
{
    /**
     * x offset
     */
    public double cam_x_offset = 0;
    /**
     * y offset
     */
    public double cam_y_offset = 0;
    /**
     * zoom index
     */
    public double cam_zoomFactor = 1;
    /**
     * previous zoom index
     */
    public double cam_prevZoomFactor = 1;

    /**
     * the default value constructor, x = y = 0, zoomFactor = 1;
     */
    public CameraInfo() {}

    /**
     * constructor with parameters
     * @param cam_x_offset x offset
     * @param cam_y_offset y offset
     * @param cam_zoomFactor zoom index
     * @param cam_prevZoomFactor previous zoom index
     */
    public CameraInfo(double cam_x_offset, double cam_y_offset, double cam_zoomFactor, double cam_prevZoomFactor)
    {
        this.cam_x_offset = cam_x_offset;
        this.cam_y_offset = cam_y_offset;
        this.cam_zoomFactor = cam_zoomFactor;
        this.cam_prevZoomFactor = cam_prevZoomFactor;
    }

    /**
     * The camera info can be clone
     * @return the cloned camera info
     * @throws CloneNotSupportedException nothing
     */
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
