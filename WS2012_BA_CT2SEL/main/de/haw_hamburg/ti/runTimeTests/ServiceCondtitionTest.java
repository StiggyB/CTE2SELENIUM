package de.haw_hamburg.ti.runTimeTests;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;

import de.haw_hamburg.ti.c2s.com.valvestar.ServiceConditionPage;
import de.haw_hamburg.ti.cte.xmlObjects.CteTestCase;
import de.haw_hamburg.ti.tools.Cast;
import de.haw_hamburg.ti.tools.FileHandler;

@RunWith(Parameterized.class)
public class ServiceCondtitionTest {

    private static ServiceConditionPage scp;
    private static WebDriver            driver;
    private static File                 cttcfile;
    private Integer                     id;
    private String                      name;
    private Integer[]                   marks;
    private int                         noT = 1;

    @Rule
    public NewPageRule                  newPage;

    public ServiceCondtitionTest(Integer id, String name, Integer[] marks,
            HashMap<Integer, String> markClassMap,
            HashMap<Integer, String> markClassificationMap,
            HashMap<Integer, String> markCompositionMap) {
        this.name = name;
        this.id = id;
        this.marks = marks;
        this.newPage = new NewPageRule(noT);
    }

    @Parameters(name = "{index}: {1}")
    public static List<Object[]> data() {
        FileHandler.setFile(new File("Service_condition.cttc"));
        ArrayList<CteTestCase> tcs = Cast.as(ArrayList.class, FileHandler
                .loadObjectsFromFile());
        FileHandler.closeFile();
        Object[][] data = new Object[tcs.size()][tcs.size()];
        int i = 0;
        for (Iterator<CteTestCase> iterator = tcs.iterator(); iterator
                .hasNext();) {
            data[i] = iterator.next().asArray();
            i++;
        }
        return Arrays.asList(data);
    }

    private void printActualTestInfo() {
        System.out.println(id + ": " + name);
        System.out.println("-----------------------------------------------");
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() {
        printActualTestInfo();
        scp.inputMaxAllowableWorkingPresure();
        scp.inputSetPressure();
        scp.inputSuperimposedBackPressure();
        scp.inputOverpressure();
        scp.clickCalculate();
    }

    
    /**
     * TODO DOESENT WORK SOMEHOW
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        scp.clickNextButton().clickBackButton();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        System.out.println("# " + ServiceCondtitionTest.class.getSimpleName()
                + "-> TEAR DOWN AFTER CLASS e.g. SCP TEST ENDED ##########");
        scp.clickBackButton();
    }

    public static junit.framework.Test suite(ServiceConditionPage scp) {
        ServiceCondtitionTest.scp = scp;
        return new JUnit4TestAdapter(ServiceCondtitionTest.class);
    }

}
