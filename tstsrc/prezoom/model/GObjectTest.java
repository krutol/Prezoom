package prezoom.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prezoom.model.GObject.GLine;
import prezoom.model.GObject.GObject;
import prezoom.model.GObject.GOval;
import prezoom.model.GObject.GRectangle;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/16<p>
 **/
class GObjectTest
{
    GObject gLine, gOval, gRec;

    @BeforeEach
    void setUp()
    {
        gLine = new GLine(10,10,50,50, Color.BLACK,3);
        gOval = new GOval(100,200,36,75,Color.blue,false, 5);
        gRec = new GRectangle(300,200, 350,260, Color.lightGray, false, 6);
    }

    @Test
    void draw()
    {
        //assertDoesNotThrow();
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
    }

    @Test
    void getAttributeManager()
    {
        assertNotNull(gLine.getAttributeManager());
        assertNotNull(gOval.getAttributeManager());
        assertNotNull(gRec.getAttributeManager());
    }

    @Test
    void getCurrentAttributes()
    {
        assertNotNull(gLine.getCurrentAttributes());
        assertNotNull(gOval.getCurrentAttributes());
        assertNotNull(gRec.getCurrentAttributes());
    }
}