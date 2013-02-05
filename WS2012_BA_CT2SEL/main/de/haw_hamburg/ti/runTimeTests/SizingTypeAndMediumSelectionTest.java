package de.haw_hamburg.ti.runTimeTests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import de.haw_hamburg.ti.c2s.com.valvestar.ControlMenu;
import de.haw_hamburg.ti.c2s.com.valvestar.LoginPage;
import de.haw_hamburg.ti.c2s.com.valvestar.SizingTypeAndMediumSelectionPage;
import de.haw_hamburg.ti.cte.xmlObjects.CteTestCase;
import de.haw_hamburg.ti.tools.Cast;

@RunWith(Parameterized.class)
public class SizingTypeAndMediumSelectionTest {

    private static WebDriver                        driver;
    private static File                             cttcfile;
    private Integer                                 id;
    private String                                  name;
    private Integer[]                               marks;
    private HashMap<Integer, String>                markClassMap;
    private HashMap<Integer, String>                markClassificationMap;
    private HashMap<Integer, String>                markCompositionMap;
    private static ArrayList<CteTestCase>           testcases;
    private static String                           baseUrl;
    private static ControlMenu                      controlMenu;
    /**
     * SizingTypeAndMediumSelectionPage
     */
    private static SizingTypeAndMediumSelectionPage stams;
    private static boolean externalCall = false;

    public SizingTypeAndMediumSelectionTest(Integer id, String name,
            Integer[] marks, HashMap<Integer, String> markClassMap,
            HashMap<Integer, String> markClassificationMap,
            HashMap<Integer, String> markCompositionMap) {
        this.name = name;
        this.id = id;
        this.marks = marks;
        this.markClassMap = markClassMap;
        this.markClassificationMap = markClassificationMap;
        this.markCompositionMap = markCompositionMap;
    }

    private static ArrayList<CteTestCase> loadTestCaseObjectsFromFile() {
        ObjectInputStream ois = null;
        try {
            if (!externalCall) {
                cttcfile = new File("Sizing_Type_and_Medium_Selection.cttc");
            }
            FileInputStream fin = new FileInputStream(cttcfile);
            ois = new ObjectInputStream(fin);
            testcases = Cast.as(ArrayList.class, ois.readObject());
            // }
            // }
        } catch (FileNotFoundException e) {
            System.err.println("File not found...");
        } catch (ClassNotFoundException e) {
            System.err.println("Loading failed...");
        } catch (IOException e) {
            System.err.println("Read/Write error...");
        } finally {
            try {
                ois.close();
            } catch (IOException e) {
                System.err.println("Read/Write error...");
            }
        }
        return testcases;
    }

    @Parameters(name = "{index}: {1}")
    public static List<Object[]> data() {
        ArrayList<CteTestCase> tcs = loadTestCaseObjectsFromFile();
        Object[][] data = new Object[tcs.size()][tcs.size()];
        int i = 0;
        for (Iterator<CteTestCase> iterator = tcs.iterator(); iterator
                .hasNext();) {
            data[i] = iterator.next().asArray();
            i++;
        }
        return Arrays.asList(data);
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://www.valvestar.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(baseUrl);

        LoginPage loginPage = PageFactory.initElements(driver,
                LoginPage.class);

        loginPage.loginAs("BenjaminBurchard", "password");

        controlMenu = PageFactory.initElements(driver, ControlMenu.class);
        controlMenu.openSizingMenu();
        controlMenu.addNewSizing();
        controlMenu.clickNextButton();

        stams = PageFactory.initElements(driver,
                SizingTypeAndMediumSelectionPage.class);
    }

    @Before
    public void setUp() throws Exception {
        
    }

    @SuppressWarnings("unused")
    private void printActualTestInfo() {
        System.out.println(id + ": " + name);
        System.out.println("Compositions: " + markCompositionMap);
        System.out.println("Classifications: "
                + markClassificationMap.toString());
        System.out.println("MarkClassMap: " + markClassMap.toString());
        System.out.println("Marks: " + Arrays.toString(marks));
        System.out.println("-----------------------------------------------");
    }

    @Test
    public void testSTAMS() {

        // printActualTestInfo();

        stams.selectMedium(markClassMap);
        // wait(1);
        stams.selectSizingStandard(markClassMap);
        // wait(1);
        stams.checkCdtpBox(markClassMap, markClassificationMap);
        // wait(1);

        if (markClassMap.containsValue("Two-phase flow")) {
            for (Entry<Integer, String> mcmEntry : markClassificationMap
                    .entrySet()) {
                if (markClassMap.containsKey(mcmEntry.getKey())
                        && mcmEntry.getValue().equals("Fire Case")) {
                    assertTrue(markClassMap.get(mcmEntry.getKey())
                            .equalsIgnoreCase("none"));
                }
            }
        }

        if (!markClassMap.containsValue("Two-phase flow")) {
            stams.checkReactionForce(markClassMap, markClassificationMap,
                    markCompositionMap);
            // wait(1);
            stams.checkNoise(markClassMap, markClassificationMap,
                    markCompositionMap);
            // wait(1);
            stams.selectRadioPressureDrop(markClassMap, markClassificationMap);
            // wait(1);
            stams.selectRadioBackPressure(markClassMap, markClassificationMap);
            // wait(1);
            if (markClassMap.containsValue("Gas")) {
                stams.selectRadioFireCase(markClassMap, markClassificationMap);
                // wait(1);
            }
        }

        controlMenu.clickNextButton();
        controlMenu.clickBackButton();
    }

    /**
     * Wait some <b>seconds</b>
     * 
     * @param seconds
     */
    @SuppressWarnings("unused")
    private void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        driver.get(baseUrl
                + "/UI/MainForm/Workspace/Sizing/NewSizingWizard.aspx");
        /**
         * TODO: RESET PAGE AND STORE PAGE STATE
         */
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        driver.close();
    }

    public static junit.framework.Test suite(File file) {
        cttcfile = file;
        externalCall  = true;
        return new JUnit4TestAdapter(SizingTypeAndMediumSelectionTest.class);
    }

}
