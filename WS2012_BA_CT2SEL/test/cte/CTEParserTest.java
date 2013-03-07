package cte;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.haw_hamburg.ti.cte.CTEParser;
import de.haw_hamburg.ti.cte.CteObj;
import de.haw_hamburg.ti.cte.xmlObjects.CteObject;
import de.haw_hamburg.ti.tools.tree.Tree;

@RunWith(Parameterized.class)
public class CTEParserTest {

    private CTEParser cp;
    private File      file;

    public CTEParserTest(File file) {
        this.file = file;
    }

    @Parameters
    public static List<Object[]> data() {
        Object[][] data = new Object[][] {
                { new File("Sizing_Type_and_Medium_Selection.cte") },
                { new File("Service_condition.cte") } };
        return Arrays.asList(data);
    }

    @Before
    public void setUp() throws Exception {
        cp = new CTEParser(file);
    }

    @Test
    public void testGetTree() {
        Tree<CteObject> cteTree = cp.getTree();

        assertFalse(cteTree.getRootNode().getChilds().isEmpty());
        System.out.println("MYTREE:");
        cteTree.getRootNode().print();
    }

    @Test
    @Ignore
    public void testGetCteObjectByName() {
        for (CteObj obj : CteObj.values()) {
            ArrayList<CteObject> cteObjects = cp.getCteObjectByName(obj
                    .name());
            assertThat(cteObjects, isA(ArrayList.class));
            for (Iterator<?> iterator = cteObjects.iterator(); iterator
                    .hasNext();) {
                CteObject o = (CteObject) iterator.next();
                assertThat(o, isA(CteObject.class));
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(o.toString());
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCteObjectByNameException() {
        cp.getCteObjectByName("someObj");
    }

    @Ignore
    @Test(expected = IllegalArgumentException.class)
    public void testGetCteObjectByNameException2() {
        cp.getCteObjectByName("Class");
    }

}
