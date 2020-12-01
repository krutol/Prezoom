package prezoom.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/16<p>
 **/
class CameraManagerTest
{
    Graphics2D g2;

    @BeforeEach
    void setUp()
    {
        BufferedImage p = new BufferedImage(100,100,BufferedImage.TYPE_INT_RGB);
        g2 = (Graphics2D) p.getGraphics();

    }

    @AfterEach
    void tearDown()
    {
    }

    @Test
    void moveCameraWithParameter()
    {
        AffineTransform aff_old = g2.getTransform();

        CameraManager.moveCamera(g2,100,200,1.5);

        AffineTransform aff_new = g2.getTransform();

        assertNotEquals(aff_old, aff_new);

        assertEquals(100,aff_new.getTranslateX());
        assertEquals(200,aff_new.getTranslateY());
        assertEquals(1.5,aff_new.getScaleX());
        assertEquals(1.5,aff_new.getScaleY());

    }

    @Test
    void moveCamera()
    {
        AffineTransform aff_old = g2.getTransform();

        CameraManager.cur_CamInfo.setOffsetX(50);
        CameraManager.cur_CamInfo.setOffsetY(99);
        CameraManager.cur_CamInfo.setZoomFactor(2.386);

        CameraManager.moveCamera(g2);

        AffineTransform aff_new2 = g2.getTransform();

        assertNotEquals(aff_old, aff_new2);

        assertEquals(50,aff_new2.getTranslateX());
        assertEquals(99,aff_new2.getTranslateY());
        assertEquals(2.386,aff_new2.getScaleX());
        assertEquals(2.386,aff_new2.getScaleY());
    }

    @Test
    void getCur_CamInfo()
    {
        assertNotNull(CameraManager.getCorrectCamera());
    }
}