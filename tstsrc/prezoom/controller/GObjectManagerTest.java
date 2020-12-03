package prezoom.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prezoom.model.GObject.*;
import prezoom.view.MainWindow;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/16<p>
 **/
class GObjectManagerTest
{
    static MainWindow mainWindow = new MainWindow("");
    static GObject gLine, gOval, gRec, gCircle, gText;

    @BeforeAll
    static void setUp()
    {
        mainWindow.setVisible(false);
        gRec = new GRectangle(1000,1000,100,100,null,null,5 );
        gOval = new GOval(730,730,100,100,null,null,5 );
        gLine = new GLine(120,120,600,600,null,5 );
        gCircle = new GCircle(1000,2000,100,Color.RED, false, 5);
        gText = new GText("text", 134.0,2475.0, Color.blue,1283.0,587.0,
                "Default", Font.ITALIC, 20.0);
    }

    @Test
    void findSelected()
    {
        addGObject();
        assertTrue(GObjectManager.findSelected(1050,1050) instanceof GRectangle);
        assertTrue(GObjectManager.findSelected(750,750) instanceof GOval);
        assertTrue(GObjectManager.findSelected(360,360) instanceof GLine);

    }

    @Test
    void addGObject()
    {
        assertDoesNotThrow(()->GObjectManager.addGObject(gRec));
        assertDoesNotThrow(()->GObjectManager.addGObject(gOval));
        assertDoesNotThrow(()->GObjectManager.addGObject(gLine));
        assertDoesNotThrow(()->GObjectManager.addGObject(gCircle));
        assertDoesNotThrow(()->GObjectManager.addImageObject(new BufferedImage(120,120,BufferedImage.TYPE_INT_RGB)));
        assertDoesNotThrow(()->GObjectManager.addTextArea((GText) gText));
    }

    @Test
    void deleteGObject()
    {
        addGObject();

        assertDoesNotThrow(()->GObjectManager.deleteGObject(gText));
        assertDoesNotThrow(()->GObjectManager.deleteGObject(gLine));
        assertDoesNotThrow(GObjectManager::deleteAllTextArea);
    }

    @Test
    void resizeShape()
    {
        GRectangle rec = new GRectangle(100,100,200,200, Color.black, false, 5);

        Point2D[] p = rec.getResizePoints();
        assertNotNull(p);
        assertEquals(100, p[0].getX());
        assertEquals(100, p[0].getY());
        assertEquals(100+200, p[1].getX());
        assertEquals(100+200, p[1].getY());

        Point2D newP = new Point2D.Double(500,500);

        GObjectManager.updateResizing(p[0], newP, rec, "Rectangle");

        assertEquals(400,rec.getCurrentAttributes().getWidth());

        GObjectManager.resizePointObj = rec;
        BufferedImage img = new BufferedImage(100,100,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) img.getGraphics();

        assertDoesNotThrow(()->GObjectManager.drawResizePoints(g2));
        assertDoesNotThrow(()->GObjectManager.updateResizablePoint(newP));



    }
}