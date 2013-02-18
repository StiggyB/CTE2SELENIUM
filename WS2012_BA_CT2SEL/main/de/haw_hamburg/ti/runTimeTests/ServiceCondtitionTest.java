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
    private HashMap<Integer, String>    markClassMap;
    private HashMap<Integer, String>    markClassificationMap;
    private HashMap<Integer, String>    markCompositionMap;

    public ServiceCondtitionTest(Integer id, String name, Integer[] marks,
            HashMap<Integer, String> markClassMap,
            HashMap<Integer, String> markClassificationMap,
            HashMap<Integer, String> markCompositionMap) {
        this.name = name;
        this.id = id;
        this.markClassMap = markClassMap;
        this.markClassificationMap = markClassificationMap;
        this.markCompositionMap = markCompositionMap;
    }

    @Parameters(name = "{index}: {1}")
    public static List<Object[]> data() {

        ArrayList<CteTestCase> tcs = Cast.as(ArrayList.class, FileHandler
                .loadObjectsFromFile(new File("Service_condition.cttc")));
        Object[][] data = new Object[tcs.size()][tcs.size()];
        int i = 0;
        for (Iterator<CteTestCase> iterator = tcs.iterator(); iterator
                .hasNext();) {
            data[i] = iterator.next().asArray();
            i++;
        }
        System.out.println("Parameter method called");
        return Arrays.asList(data);
    }

    private void printActualTestInfo() {
        System.out.println(id + ": " + name);
        System.out.println("Compositions: " + markCompositionMap);
        System.out.println("Classifications: "
                + markClassificationMap.toString());
        System.out.println("MarkClassMap: " + markClassMap.toString());
        System.out.println("-----------------------------------------------");
    }

    @Before
    public void setUp() throws Exception {
        System.out.println(this.getClass().getSimpleName() + "-> setting up");
    }

    @Test
    public void test() {
        System.out.println(this.getClass().getSimpleName() + "-> test");
        printActualTestInfo();
        scp.inputMaxAllowableWorkingPresure();
        scp.inputSetPressure();
        scp.inputSuperimposedBackPressure();
        scp.inputOverpressure();
        scp.clickCalculate();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println(this.getClass().getSimpleName() + "-> tear down");
        System.out.println(this.getClass().getSimpleName() + "-> click next+back");
        scp.clickNextButton().clickNextButton();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        System.out.println("# " + ServiceCondtitionTest.class.getSimpleName()
                + "-> TEAR DOWN AFTER CLASS e.g. SCP TEST ENDED ##########");
         scp.clickBackButton();
    }

    public static junit.framework.Test suite(ServiceConditionPage scp) {
        System.out.println(ServiceCondtitionTest.class.getSimpleName()
                + "-> suite up");
        ServiceCondtitionTest.scp = scp;
        return new JUnit4TestAdapter(ServiceCondtitionTest.class);
    }

}
