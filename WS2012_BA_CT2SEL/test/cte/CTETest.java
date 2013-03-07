package cte;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.haw_hamburg.ti.cte.CTE;
import de.haw_hamburg.ti.cte.xmlObjects.CteObject;
import de.haw_hamburg.ti.cte.xmlObjects.CteTestCase;
import de.haw_hamburg.ti.tools.Cast;
import de.haw_hamburg.ti.tools.tree.Tree;

@RunWith(Parameterized.class)
public class CTETest {

    CTE          cte;
    private File cteFile;
    private File cttcFile;

    public CTETest(File cteFile, File cttcFile) {
        this.cteFile = cteFile;
        this.cttcFile = cttcFile;
    }

    @Parameters
    public static List<Object[]> data() {
        Object[][] data = new Object[][] {
                { new File("Sizing_Type_and_Medium_Selection.cte"),
                        new File("Sizing_Type_and_Medium_Selection.cttc") },
                { new File("Service_condition.cte"),
                        new File("Service_condition.cttc") } };
        return Arrays.asList(data);
    }

    @Before
    public void setUp() throws Exception {
        cte = new CTE();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    @Ignore
    public void testCte2() throws IOException {
        try {
            cte.getTestData(cteFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        cte.saveTestCasesToFile(cteFile);
    }

    @Test
    public void testSavedFile() {
        ArrayList<CteTestCase> cteBefore;
        try {
//            cteBefore = cte.getTestData(cteFile);
            // System.out.println(this.getClass().getSimpleName() + "-> Before("
            // + cteFile.getName() + ")" + ": " + cteBefore);
            cte.saveTestCasesToFile(cteFile);
            FileInputStream fin = new FileInputStream(cttcFile);
            ObjectInputStream ois = new ObjectInputStream(fin);
            ArrayList<CteTestCase> testcases = Cast.as(ArrayList.class, ois
                    .readObject());
            Tree<CteObject> tree = Cast.as(Tree.class, ois.readObject());
            ois.close();
            System.out.println(this.getClass().getSimpleName() + "->("
                    + cteFile.getName() + ")" + " - Testcases: " + testcases);
            System.out.println(this.getClass().getSimpleName() + "->"
                    + " Tree: " + tree.toString());
            // assertEquals(cteBefore, testcases);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
