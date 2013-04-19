package de.haw_hamburg.ti.runTimeTests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeFalse;

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
import de.haw_hamburg.ti.tools.TimeAssist;
import de.haw_hamburg.ti.tools.tree.Tree;

@RunWith(Parameterized.class)
public class SizingTypeAndMediumSelectionTest implements PageTest {

    /**
     * TODO WEBDRIVER SINGLETON OBJECT
     */
    private static WebDriver                        driver;
    private static ArrayList<File>                  cttcfiles          = new ArrayList<>();
    private Integer                                 id;
    private String                                  name;
    private Integer[]                               marks;
    private final static int                        noT                = 8;
    private static SizingTypeAndMediumSelectionPage stams;
    private static Tree<CteObject>                  tree;
    private static String                           user               = "BenjaminBurchard";
    private static String                           pass               = "password";
    private static int                              evaluatedTestcases = 0;
    private static int                              testSuites         = 0;

    @Rule
    public NewPageRule                              newPage            = new NewPageRule(
                                                                               noT,
                                                                               this);

    public SizingTypeAndMediumSelectionTest(Integer id, String name,
            Integer[] marks, HashMap<Integer, String> markClassMap,
            HashMap<Integer, String> markClassificationMap,
            HashMap<Integer, String> markCompositionMap) {
        this.name = name;
        this.id = id;
        this.marks = marks;
    }

    @Parameters(name = "{index}: {1}")
    public static List<Object[]> data() {
        if (!externalCall) {
            cttcfiles.add(new File(
                    "SizingTypeAndMediumSelectionAuslegungsStandard.cttc"));
            cttcfiles.add(new File("Service_conditionCleaned.cttc"));
        }
        System.out.println("Used File: " + cttcfiles.get(0));
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
        testSuites = i;
        return Arrays.asList(data);
    }

    static long s1;
    static long s2;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        s1 = TimeAssist.getTimeStamp();
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
        if (mainPage == null) {
            throw new Exception("Wrong username and/or password");
        }
        stams = mainPage.addNewSizing().clickNextButton();
        stams.setTree(tree);
        stams.inputTagNo("Test#" + stams.finishedProjects + " - "
                + TimeAssist.getDate());
    }

    @Before
    public void setUp() throws Exception {
        printActualTestInfo();
        stams.setMarks(marks);
        stams.setMedium();
        assumeFalse("Skipped...", newPage.isFailed());
    }

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

    private void printActualTestInfo() {
        System.out.println(id + ": " + name);
    }

    @After
    public void tearDown() throws Exception {
        newPage.updateFinishedTestcases();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        driver.close();
        s2 = TimeAssist.getTimeStamp();
        System.out.println("TIME: " + TimeAssist.getTimePassed(s1, s2));
    }

    public void evalNextPage() {
        boolean allTestsFinished = false;
        evaluatedTestcases++;
        if (evaluatedTestcases == testSuites) {
            allTestsFinished = true;
        }
        System.out.println("## cttcfiles.isEmpty()=?=" + cttcfiles.isEmpty()
                + " && allTestsFinished=?=" + allTestsFinished + " ##");
        if (!cttcfiles.isEmpty()) {
            org.junit.runner.JUnitCore jc = new JUnitCore();
            Result r = new Result();
            TimeAssist.setInternalStampOne();
            HomePage hp = stams.clickNextButton();
            if (hp instanceof ServiceConditionPage) {
                ServiceConditionPage scp = (ServiceConditionPage) hp;
                scp.setMedium(stams.getMedium());
                scp.setFireCase(stams.getFireCase());
                r = jc.run(ServiceCondtitionTest.suite(scp, cttcfiles,
                        allTestsFinished));
                // stams = (SizingTypeAndMediumSelectionPage)
                // scp.clickBackButton();
            } else {
                MediumSelectionPage msp = (MediumSelectionPage) hp;
                msp.setMedium(stams.getMedium());
                msp.setFireCase(stams.getFireCase());
                // r = jc.run(MediumSelectionTest.suite(msp, cttcfiles));
                System.err.println("Medium Selection is not coverd by a Unit Test");
                stams = msp.clickBackButton();
            }
        }
        /*
         * else { System.out.println("########### evaluatedTestcases: " +
         * evaluatedTestcases + "=?=" + testSuites +
         * " :testSuites ###########"); if (!(evaluatedTestcases == testSuites))
         * { stams.inputTagNo( "Test#" + evaluatedTestcases + " - " +
         * TimeAssist.getDate()); } }
         */
        // printResult(r);
    }

    private void printResult(Result r) {
        if (r.getFailureCount() > 0) {
            System.out.println(SizingTypeAndMediumSelectionTest.class
                    .getSimpleName()
                    + "->nof: "
                    + r.getFailureCount()
                    + " nor: "
                    + r.getRunCount()
                    + " time: "
                    + r.getRunTime()
                    + " failuredescr.: "
                    + r.getFailures().get(0).getTrace()
                    + " message: " + r.getFailures().get(0).getMessage());
        }
    }

    public static junit.framework.Test suite(ArrayList<File> files,
            String username, String password) throws Exception {
        cttcfiles = files;
        user = username;
        pass = password;
        return new JUnit4TestAdapter(SizingTypeAndMediumSelectionTest.class);
    }

}
