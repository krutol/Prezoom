package prezoom.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prezoom.model.GObject.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/16<p>
 **/
class GObjectTest
{
    GObject gLine, gOval, gRec, gCircle, gImage, gText;

    @BeforeEach
    void setUp()
    {
        gLine = new GLine(10,10,50,50, Color.BLACK,3);
        gOval = new GOval(100,200,36,75,Color.blue,false, 5);
        gRec = new GRectangle(300,200, 350,260, Color.lightGray, false, 6);
        gCircle = new GCircle(1000,2000,100,Color.RED, false, 5);
        gImage = new GImage(new BufferedImage(120,120,BufferedImage.TYPE_INT_RGB),
                350.0,350.0,120.0,120.0);
        gText = new GText("text", 134.0,2475.0, Color.blue,1283.0,587.0,
                "Default", Font.ITALIC, 20.0);
    }

    @Test
    void draw()
    {
        BufferedImage p = new BufferedImage(100,100,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) p.getGraphics();

        assertDoesNotThrow(()->gLine.draw(g2));
        assertDoesNotThrow(()->gOval.draw(g2));
        assertDoesNotThrow(()->gRec.draw(g2));
        assertDoesNotThrow(()->gCircle.draw(g2));
        assertDoesNotThrow(()->gImage.draw(g2));
        assertDoesNotThrow(()->gText.draw(g2));

    }

    @Test
    void inShape()
    {
        assertTrue(gLine.inShape(20,20));
        assertFalse(gLine.inShape(50,10));

        assertTrue(gOval.inShape(120,220));
        assertFalse(gOval.inShape(50,60));

        assertTrue(gRec.inShape(400,400));
        assertFalse(gRec.inShape(20,50));

        assertTrue(gCircle.inShape(1050,2050));
        assertFalse(gCircle.inShape(20,50));

        assertTrue(gImage.inShape(360,360));
        assertFalse(gImage.inShape(20,50));

        assertTrue(gText.inShape(140,2500));
        assertFalse(gText.inShape(20,50));
    }

    @Test
    void getAttributeManager()
    {
        assertNotNull(gLine.getAttributeManager());
        assertNotNull(gOval.getAttributeManager());
        assertNotNull(gRec.getAttributeManager());
        assertNotNull(gCircle.getAttributeManager());
        assertNotNull(gImage.getAttributeManager());
        assertNotNull(gText.getAttributeManager());
    }

    @Test
    void getCurrentAttributes()
    {
        assertNotNull(gLine.getCurrentAttributes());
        assertNotNull(gOval.getCurrentAttributes());
        assertNotNull(gRec.getCurrentAttributes());
        assertNotNull(gCircle.getCurrentAttributes());
        assertNotNull(gImage.getCurrentAttributes());
        assertNotNull(gText.getCurrentAttributes());
    }
}