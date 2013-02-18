package de.haw_hamburg.ti.runTimeTests;

import static org.junit.Assert.assertTrue;

import java.io.File;
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
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import de.haw_hamburg.ti.c2s.com.valvestar.HomePage;
import de.haw_hamburg.ti.c2s.com.valvestar.LoginPage;
import de.haw_hamburg.ti.c2s.com.valvestar.MainPage;
import de.haw_hamburg.ti.c2s.com.valvestar.MediumSelectionPage;
import de.haw_hamburg.ti.c2s.com.valvestar.ServiceConditionPage;
import de.haw_hamburg.ti.c2s.com.valvestar.SizingTypeAndMediumSelectionPage;
import de.haw_hamburg.ti.cte.xmlObjects.CteTestCase;
import de.haw_hamburg.ti.tools.Cast;
import de.haw_hamburg.ti.tools.FileHandler;

@RunWith(Parameterized.class)
public class SizingTypeAndMediumSelectionTest {

    /**
     * TODO WEBDRIVER SINGLETON OBJECT
     */
    private static WebDriver                        driver;
    private static File                             cttcfile;
    private Integer                                 id;
    private String                                  name;
    private HashMap<Integer, String>                markClassMap;
    private HashMap<Integer, String>                markClassificationMap;
    private HashMap<Integer, String>                markCompositionMap;
    /**
     * SizingTypeAndMediumSelectionPage
     */
    private static SizingTypeAndMediumSelectionPage stams;
    private static boolean                          externalCall = false;

    public SizingTypeAndMediumSelectionTest(Integer id, String name,
            Integer[] marks, HashMap<Integer, String> markClassMap,
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
        if (!externalCall) {
            cttcfile = new File("Sizing_Type_and_Medium_Section.cttc");
        }
        ArrayList<CteTestCase> tcs = Cast.as(ArrayList.class, FileHandler
                .loadObjectsFromFile(cttcfile));
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
        String baseUrl = "http://www.valvestar.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(baseUrl);
        driver.manage().deleteAllCookies();

        LoginPage loginPage = PageFactory.initElements(driver,
                LoginPage.class);

        MainPage mainPage = loginPage.loginAs("BenjaminBurchard", "password");
        stams = mainPage.addNewSizing().clickNextButton();
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
        System.out.println("-----------------------------------------------");
    }

    @Test
    public void testSTAMS() {
        System.out.println(id + ": " + name);
        // printActualTestInfo();

        stams.selectMedium(markClassMap);
        // wait(1);
        stams.selectSizingStandard(markClassMap);
        // wait(1);
        stams.checkCdtpBox(markClassMap, markClassificationMap);
        // wait(1);

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
        } else {
            for (Entry<Integer, String> mcmEntry : markCompositionMap
                    .entrySet()) {
                if (mcmEntry.getValue().equalsIgnoreCase(
                        "Additional calculations")
                        || mcmEntry.getValue().equalsIgnoreCase("Noise")
                        || mcmEntry.getValue().equalsIgnoreCase(
                                "Reaction force")) {
                    if (markClassMap.containsKey(mcmEntry.getKey())) {
                        try {
                            assertTrue(markClassMap.get(mcmEntry.getKey())
                                    .equalsIgnoreCase("none")
                                    || markClassMap.get(mcmEntry.getKey())
                                            .equalsIgnoreCase("false"));
                        } catch (AssertionError e) {
                            // System.err.println("TestCase " + name
                            // + " not accepted.");
                            throw e;
                        }
                    }
                }
            }
        }
    }

    /**
     * TODO STH WITH CLICK BACK AND FORTH GOING WRONG
     * 
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        org.junit.runner.JUnitCore jc = new JUnitCore();
        Result r = new Result();
        System.out.println("Next auf STAMS");
        Thread.sleep(2000);
        HomePage hp = stams.clickNextButton();
        if (hp instanceof ServiceConditionPage) {
            System.out.println("scp");
            ServiceConditionPage scp = (ServiceConditionPage) hp;
            scp.setMedium(stams.getMedium());
            scp.setFireCase(stams.getFireCase());
            r = jc.run(ServiceCondtitionTest.suite(scp));
            System.out.println("Back auf STAMS");
            Thread.sleep(2000);
            stams = (SizingTypeAndMediumSelectionPage) scp.clickBackButton();
        } else {
            System.out.println("msp");
            MediumSelectionPage msp = (MediumSelectionPage) hp;
            msp.setMedium(stams.getMedium());
            msp.setFireCase(stams.getFireCase());
            r = jc.run(MediumSelectionTest.suite(msp));
            // junit.textui.TestRunner.run(MediumSelectionTest.suite(msp,
            // stams.getMedium()));
            stams = msp.clickBackButton();
        }
        if (r.getFailureCount() > 0) {
            System.out.println(this.getClass().getSimpleName() + "->nof: "
                    + r.getFailureCount() + " nor: " + r.getRunCount()
                    + " time: " + r.getRunTime() + " failuredescr.: "
                    + r.getFailures().get(0).getTrace() + " message: "
                    + r.getFailures().get(0).getMessage());
        }
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        driver.close();
    }

    public static junit.framework.Test suite(File file) {
        cttcfile = file;
        externalCall = true;
        return new JUnit4TestAdapter(SizingTypeAndMediumSelectionTest.class);
    }

}
