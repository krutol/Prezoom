package prezoom.model;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * The interface of the camera infomation
 *
 * @author Zhijie Lan<p>
 * create date: 2020/11/17<p>
 **/
public interface CameraInfoI extends Cloneable, MethodMapI
{
    Object clone() throws CloneNotSupportedException;

    double getOffsetX();

    void setOffsetX(double offsetX);

    double getOffsetY();

    void setOffsetY(double offsetY);

    double getZoomFactor();

    void setZoomFactor(double zoomFactor);

    double getPreZoomFactor();

    void setPreZoomFactor(double preZoomFactor);
}
