package de.haw_hamburg.ti.runTimeTests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
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
import de.haw_hamburg.ti.cte.xmlObjects.CteObject;
import de.haw_hamburg.ti.cte.xmlObjects.CteTestCase;
import de.haw_hamburg.ti.tools.Cast;
import de.haw_hamburg.ti.tools.FileHandler;
import de.haw_hamburg.ti.tools.tree.Tree;

@RunWith(Parameterized.class)
public class SizingTypeAndMediumSelectionTest {

    /**
     * TODO WEBDRIVER SINGLETON OBJECT
     */
    private static WebDriver                        driver;
    private static ArrayList<File>                  cttcfiles    = new ArrayList<>();
    private Integer                                 id;
    private String                                  name;
    private Integer[]                               marks;
    private final static int                        noT          = 7;
    private static SizingTypeAndMediumSelectionPage stams;
    private static boolean                          externalCall = false;
    private static Tree<CteObject>                  tree;
    private static String                           user         = "BenjaminBurchard";
    private static String                           pass         = "password";

    @Rule
    public NewPageRule                              newPage;

    public SizingTypeAndMediumSelectionTest(Integer id, String name,
            Integer[] marks, HashMap<Integer, String> markClassMap,
            HashMap<Integer, String> markClassificationMap,
            HashMap<Integer, String> markCompositionMap) {
        this.name = name;
        this.id = id;
        this.marks = marks;
        this.newPage = new NewPageRule(noT);
    }

    @Parameters(name = "{index}: {1}")
    public static List<Object[]> data() {
        if (!externalCall) {
            cttcfiles.add(new File("Sizing_Type_and_Medium_Selection.cttc"));
        }
        FileHandler.setFile(cttcfiles.remove(0));
        ArrayList<CteTestCase> tcs = Cast.as(ArrayList.class, FileHandler
                .loadObjectsFromFile());
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
        tree = Cast.as(Tree.class, FileHandler.loadObjectsFromFile());
        FileHandler.closeFile();
        driver = new FirefoxDriver();
        String baseUrl = "http://www.valvestar.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(baseUrl);
        driver.manage().deleteAllCookies();

        LoginPage loginPage = PageFactory.initElements(driver,
                LoginPage.class);

        MainPage mainPage = loginPage.loginAs(user, pass);
        stams = mainPage.addNewSizing().clickNextButton();
        stams.setTree(tree);
    }
    
    @Before
    public void setUp() throws Exception {
        printActualTestInfo();
        stams.setMarks(marks);
        stams.setMedium();
        assumeFalse("Skipped...", newPage.isFailed());
    }

    private void printActualTestInfo() {
        System.out.println(id + ": " + name);
    }

    // @Test
    // public void testSTAMS() {
    // System.out.println(id + ": " + name);
    // // printActualTestInfo();
    //
    // }

    @Test
    public void testSelectMedium() {
        assertNotNull(stams.selectMedium());
    }

    @Test
    public void testSelectSizingStandard() {
        assertNotNull(stams.selectSizingStandard());
    }

    @Test
    public void testCheckCdtpBox() {
        assertNotNull(stams.checkCdtpBox());
    }

    @Test
    public void testCheckReactionForce() {
        assertNotNull(stams.checkReactionForce());
    }

    @Test
    public void testCheckNoise() {
        assertNotNull(stams.checkNoise());
    }

    @Test
    public void testSelectRadioPressureDrop() {
        assertNotNull(stams.selectRadioPressureDrop());
    }

    @Test
    public void testSelectRadioBackPressure() {
        assertNotNull(stams.selectRadioBackPressure());
    }

    /**
     * TODO NOT WORKING FOR LIQUID
     */
    @Test
    public void testSelectRadioFireCase() {
        assertNotNull(stams.selectRadioFireCase());
    }

    private void evalNextPage() {
        org.junit.runner.JUnitCore jc = new JUnitCore();
        Result r = new Result();
        HomePage hp = stams.clickNextButton();
        if (hp instanceof ServiceConditionPage) {
            ServiceConditionPage scp = (ServiceConditionPage) hp;
            scp.setMedium(stams.getMedium());
            scp.setFireCase(stams.getFireCase());
            /**
             * TODO NEXT TEST
             */
            r = jc.run(ServiceCondtitionTest.suite(scp));
            stams = (SizingTypeAndMediumSelectionPage) scp.clickBackButton();
        } else {
            MediumSelectionPage msp = (MediumSelectionPage) hp;
            msp.setMedium(stams.getMedium());
            msp.setFireCase(stams.getFireCase());
            /**
             * TODO NEXT TEST
             */
            // r = jc.run(MediumSelectionTest.suite(msp));
            stams = msp.clickBackButton();
        }
        printResult(r);
    }

    private void printResult(Result r) {
        if (r.getFailureCount() > 0) {
            System.out.println(this.getClass().getSimpleName() + "->nof: "
                    + r.getFailureCount() + " nor: " + r.getRunCount()
                    + " time: " + r.getRunTime() + " failuredescr.: "
                    + r.getFailures().get(0).getTrace() + " message: "
                    + r.getFailures().get(0).getMessage());
        }
    }

    /**
     * TODO SCT NOT WORKING
     * 
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        System.out.println(this.id + ": " + newPage.getSucceededTestCases()
                + "==" + noT);
        if (newPage.getSucceededTestCases() == noT) {
            evalNextPage();
        }
        System.out.println("-----------------------------------------------");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        driver.close();
    }

    public static junit.framework.Test suite(ArrayList<File> files,
            String username, String password) {
        cttcfiles = files;
        user = username;
        pass = password;
        externalCall = true;
        return new JUnit4TestAdapter(SizingTypeAndMediumSelectionTest.class);
    }

}
