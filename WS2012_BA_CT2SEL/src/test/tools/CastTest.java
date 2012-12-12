package test.tools;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import tools.Cast;

public class CastTest {

    @Test
    public void testAs() {
        Object o = new Object();
        assertNotNull(Cast.as(Object.class, o));
    }
    
    @Test
    public void testAs2() {
        Object o = new Object();
        assertNull(Cast.as(String.class, o));
    }

}
