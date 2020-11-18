package prezoom.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prezoom.model.GLine;
import prezoom.model.GOval;
import prezoom.model.GRectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/16<p>
 **/
class GObjectManagerTest
{

    @BeforeEach
    void setUp()
    {

    }

    @AfterEach
    void tearDown()
    {
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
        assertDoesNotThrow(()->GObjectManager.addGObject(new GRectangle(1000,1000,100,100,null,null,5 )));
        assertDoesNotThrow(()->GObjectManager.addGObject(new GOval(730,730,100,100,null,null,5 )));
        assertDoesNotThrow(()->GObjectManager.addGObject(new GLine(120,120,600,600,null,5 )));
    }
}