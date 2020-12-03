package prezoom.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Zhijie Lan<p>
 * create date: 2020/12/2
 **/
class MethodFactoryTest
{
    Object testObj;

    @BeforeEach
    void setUp()
    {
        testObj = new Object(){
            Integer a = 1, b=1, c=null, d=1,e = null;

            public Integer getA()
            {
                return a;
            }

            public void setA(Integer a)
            {
                this.a = a;
            }

            public Integer getB()
            {
                return b;
            }

            public void setB(Integer b)
            {
                this.b = b;
            }

            public Integer getC()
            {
                return c;
            }

            public void setC(Integer c)
            {
                this.c = c;
            }

            public Integer getD()
            {
                return d;
            }

            public void setD(Integer d)
            {
                this.d = d;
            }

            public Integer getE()
            {
                return e;
            }

            public void setE(Integer e)
            {
                this.e = e;
            }
        };
    }

    @Test
    void getNonNullProperties()
    {
        Map<String, Object> mp = assertDoesNotThrow(
                ()->MethodFactory.getNonNullProperties(testObj));

        assertNotNull(mp);
        assertEquals(3, mp.size());

        for (Map.Entry<String, Object> entry : mp.entrySet())
        {
            assertNotNull(entry.getKey());
            assertNotNull(entry.getValue());
            assertEquals(1, entry.getValue());
        }
    }

    @Test
    void getNonNullGetters()
    {
        Map<String, Method> mp = assertDoesNotThrow(
                ()->MethodFactory.getNonNullGetters(testObj));

        assertNotNull(mp);
        assertEquals(3, mp.size());

        for (Map.Entry<String, Method> entry : mp.entrySet())
        {
            assertNotNull(entry.getKey());
            assertNotNull(entry.getValue());
        }
    }

    @Test
    void getNonNullSetters()
    {
        Map<String, Method> mp = assertDoesNotThrow(
                ()->MethodFactory.getNonNullGetters(testObj));

        assertNotNull(mp);
        assertEquals(3, mp.size());

        for (Map.Entry<String, Method> entry : mp.entrySet())
        {
            assertNotNull(entry.getKey());
            assertNotNull(entry.getValue());
        }
    }
}