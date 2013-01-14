package de.haw_hamburg.ti.test.code;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

import de.haw_hamburg.ti.cte.CTE;
import de.haw_hamburg.ti.cte.xmlObjects.CteTestCase;
import de.haw_hamburg.ti.tools.Cast;

public class CTETest {

    CTE cte;

    @Before
    public void setUp() throws Exception {
        cte = new CTE();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    @Ignore
    public void test() {
        try {
            cte.getTestData(new File("Sizing_Type_and_Medium_Section.cte"));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        cte.saveTestCasesToFile();
    }

    @Test
    public void testSavedFile() {
        ArrayList<CteTestCase> cteBefore;
        try {
            cteBefore = cte.getTestData(new File(
                    "Sizing_Type_and_Medium_Section.cte"));
            cte.saveTestCasesToFile();
            FileInputStream fin = new FileInputStream("TEST_TC.dat");
            ObjectInputStream ois = new ObjectInputStream(fin);
            ArrayList<CteTestCase> testcases = Cast.as(ArrayList.class,
                    ois.readObject());
            ois.close();
            assertEquals(cteBefore, testcases);
        } catch (ParserConfigurationException | SAXException | IOException
                | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}