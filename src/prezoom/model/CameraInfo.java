package prezoom.model;

import java.lang.reflect.Method;
import java.util.Map;

/** the base class for camera info
 * @author Zhijie Lan<p>
 * create date: 2020/11/4
 **/
public class CameraInfo implements CameraInfoI
{
    /**
     * x offset
     */
    private double offsetX = 0;
    /**
     * y offset
     */
    private double offsetY = 0;
    /**
     * zoom index
     */
    private double zoomFactor = 1;
    /**
     * previous zoom index
     */
    private double preZoomFactor = 1;

    //commented, cuz The Method cannot be serialized to save
    //private final Map<String, Method> getters, setters;

    /**
     * the default value constructor, x = y = 0, zoomFactor = 1;
     */
    public CameraInfo() {
//        this.getters = MethodFactory.getNonNullGetters(this);
//        this.setters = MethodFactory.getNonNullSetters(this);
    }

    /**
     * constructor with parameters
     * @param offsetX x offset
     * @param offsetY y offset
     * @param zoomFactor zoom index
     * @param preZoomFactor previous zoom index
     */
    public CameraInfo(double offsetX, double offsetY, double zoomFactor, double preZoomFactor)
    {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.zoomFactor = zoomFactor;
        this.preZoomFactor = preZoomFactor;
//        this.getters = MethodFactory.getNonNullGetters(this);
//        this.setters = MethodFactory.getNonNullSetters(this);
    }

    /**
     * The camera info can be clone
     * @return the cloned camera info
     * @throws CloneNotSupportedException nothing
     */
    @Override
    public CameraInfo clone() throws CloneNotSupportedException
    {
        return (CameraInfo) super.clone();
    }

    public double getOffsetX()
    {
        return offsetX;
    }

    public void setOffsetX(double offsetX)
    {
        this.offsetX = offsetX;
    }

    public double getOffsetY()
    {
        return offsetY;
    }

    public void setOffsetY(double offsetY)
    {
        this.offsetY = offsetY;
    }

    public double getZoomFactor()
    {
        return zoomFactor;
    }

    public void setZoomFactor(double zoomFactor)
    {
        this.zoomFactor = zoomFactor;
    }

    public double getPreZoomFactor()
    {
        return preZoomFactor;
    }

    public void setPreZoomFactor(double preZoomFactor)
    {
        this.preZoomFactor = preZoomFactor;
    }

    public Map<String, Method> validSetterMap()
    {
        return MethodFactory.getNonNullSetters(this);
    }

    public Map<String, Method> validGetterMap()
    {
        return MethodFactory.getNonNullGetters(this);
    }
}
