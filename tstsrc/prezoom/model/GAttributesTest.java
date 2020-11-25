package prezoom.model;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/16<p>
 **/
class GAttributesTest
{
    GAttributes gAttributes = new GAttributes(100.0,200.0, Color.blue,
            true, 5, 100.0, 50.0, 120.0, 230.0, true, null, null, null,null);

    @Test
    void testClone()
    {
        GAttributes att_clone = (GAttributes) assertDoesNotThrow(()->gAttributes.clone());

        assertNotSame(gAttributes, att_clone);

        assertEquals(gAttributes.color, att_clone.color);
        assertEquals(gAttributes.height, att_clone.height);
        assertEquals(gAttributes.width, att_clone.width);
        assertEquals(gAttributes.x, att_clone.x);
        assertEquals(gAttributes.x2, att_clone.x2);
        assertEquals(gAttributes.y, att_clone.y);
        assertEquals(gAttributes.y2, att_clone.y2);
        assertEquals(gAttributes.lineWidth, att_clone.lineWidth);
        assertEquals(gAttributes.filled, att_clone.filled);
        assertEquals(gAttributes.visible, att_clone.visible);
        assertEquals(gAttributes.textString, att_clone.textString);
        assertEquals(gAttributes.stroke, att_clone.stroke);
    }
}