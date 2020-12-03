package prezoom.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/16<p>
 **/
class CameraInfoTest
{
    CameraInfo cameraInfo = new CameraInfo();

    @Test
    void testClone()
    {
        CameraInfo cam_clone = assertDoesNotThrow(() -> cameraInfo.clone());

        //CameraInfo cam_clone = (CameraInfo) cameraInfo.clone();

        assertNotSame(cameraInfo, cam_clone);
        assertEquals(cameraInfo.getOffsetX(), cam_clone.getOffsetX());
        assertEquals(cameraInfo.getOffsetY(), cam_clone.getOffsetY());
        assertEquals(cameraInfo.getZoomFactor(), cam_clone.getZoomFactor());
        assertEquals(cameraInfo.getPreZoomFactor(), cam_clone.getPreZoomFactor());
    }

    @Test
    void testSetGetOffsetX()
    {
        cameraInfo.setOffsetX(100);
        assertEquals(cameraInfo.getOffsetX(), 100);
    }

    @Test
    void testSetGetOffsetY()
    {
        cameraInfo.setOffsetY(200);
        assertEquals(cameraInfo.getOffsetY(), 200);
    }

    @Test
    void testSetGetZoomFactor()
    {
        cameraInfo.setZoomFactor(1.68);
        assertEquals(cameraInfo.getZoomFactor(), 1.68);
    }

    @Test
    void testSetGetPreZoomFactor()
    {
        cameraInfo.setPreZoomFactor(3.58);
        assertEquals(cameraInfo.getPreZoomFactor(), 3.58);
    }

}