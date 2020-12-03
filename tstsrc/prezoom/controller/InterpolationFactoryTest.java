package prezoom.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prezoom.model.AttributeMap;
import prezoom.model.AttributeMapI;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/12/2
 **/
class InterpolationFactoryTest
{
    @Test
    void buildInterpolation()
    {
        TestClass testObjStart = new TestClass();
        TestClass testObjEnd = new TestClass();
        int startInt = 10, endInt = 1000;
        testObjStart.setA(startInt);
        testObjEnd.setA(endInt);

        assertEquals(endInt, testObjEnd.getA());
        InterpolationFactory.buildInterpolation(testObjStart, testObjEnd);
        assertEquals(startInt, testObjEnd.getA());

        int loopTimes = 0;

        while (testObjEnd.getA() != 1000)
        {
            loopTimes++;
            assertTrue(testObjEnd.getA()>=startInt);
            assertTrue(testObjEnd.getA()<=endInt);
        }

        assertEquals(endInt, testObjEnd.getA());
        assertTrue(loopTimes>10000);

    }

    public static class TestClass extends AttributeMap implements AttributeMapI
    {
        Integer a=1;

        public Integer getA()
        {
            return a;
        }

        public void setA(Integer a)
        {
            this.a = a;
        }
    }
}
