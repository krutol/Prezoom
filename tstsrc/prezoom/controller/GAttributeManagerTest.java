package prezoom.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/11/16<p>
 **/
class GAttributeManagerTest
{
    GAttributeManager gAttributeManager = new GAttributeManager(null,null,null,null,
            null,null,null,
            null,null,null, null, null, null, null);

    @Test
    void getCur_Attributes()
    {
        assertNotNull(gAttributeManager.getCur_Attributes());
    }

}