package de.haw_hamburg.ti.test.code;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.haw_hamburg.ti.cte.CTEParser;
import de.haw_hamburg.ti.cte.xmlObjects.CteObject;

@RunWith(Parameterized.class)
public class CTEParserTest {

    private CTEParser cp;
    private String    s;

    public CTEParserTest(String s) {
        this.s = s;
    }

    @Parameters
    public static List<Object[]> data() {
        Object[][] data = new Object[][] { { "Composition" },
                { "Classification" }, { "TestCase" } };
        return Arrays.asList(data);
    }

    @Before
    public void setUp() throws Exception {
        cp = new CTEParser(new File("Sizing_Type_and_Medium_Section.cte"));
    }

    @Test
    public void testGetCteObjectByName() {
        int i = 0;
        ArrayList<CteObject> cteObjects = cp.getCteObjectByName(s);
        assertThat(cteObjects, is(ArrayList.class));
        for (Iterator<?> iterator = cteObjects.iterator(); iterator.hasNext();) {
            CteObject o = (CteObject) iterator.next();
            assertThat(o, is(CteObject.class));
            ++i;
            System.out.println(o);
        }
        System.out.println("No. of " + s + " Objects: " + i);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testGetCteObjectByNameException() {
            cp.getCteObjectByName("someObj");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testGetCteObjectByNameException2() {
            cp.getCteObjectByName("Class");
    }

}
