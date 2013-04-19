package de.haw_hamburg.ti.runTimeTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeFalse;

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
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.haw_hamburg.ti.c2s.com.valvestar.CreateNewSizingWizardPage;
import de.haw_hamburg.ti.c2s.com.valvestar.InletPipeComponentsPage;
import de.haw_hamburg.ti.cte.xmlObjects.CteObject;
import de.haw_hamburg.ti.cte.xmlObjects.CteTestCase;
import de.haw_hamburg.ti.tools.Cast;
import de.haw_hamburg.ti.tools.FileHandler;
import de.haw_hamburg.ti.tools.TimeAssist;
import de.haw_hamburg.ti.tools.tree.Tree;

@RunWith(Parameterized.class)
public class InletPipeComponentsTest implements PageTest {

    private static InletPipeComponentsPage ipcp;
    private static Tree<CteObject>         tree;
    private int                            noT                = 5;
    private String                         name;
    private Integer                        id;
    private Integer[]                      marks;
    private static int                     evaluatedTestcases = 0;
    private static int                     testSuites;
    private static ArrayList<File>         cttcfiles;
    private static boolean                 allPreviousTestsFinished;

    @Rule
    public NewPageRule                     newPage            = new NewPageRule(
                                                                      noT,
                                                                      this);

    public InletPipeComponentsTest(Integer id, String name, Integer[] marks,
            HashMap<Integer, String> markClassMap,
            HashMap<Integer, String> markClassificationMap,
            HashMap<Integer, String> markCompositionMap) {
        this.name = name;
        this.id = id;
        this.marks = marks;
    }

    @Parameters(name = "{index}: {1}")
    public static List<Object[]> data() {
        assertFalse(cttcfiles.isEmpty());
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

    private void printActualTestInfo() {
        System.out.println(id + ": " + name);
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        tree = Cast.as(Tree.class, FileHandler.loadObjectsFromFile());
        FileHandler.closeFile();
        ipcp.setTree(tree);
    }

    @Before
    public void setUp() throws Exception {
        printActualTestInfo();
        ipcp.setMarks(marks);
        assumeFalse("Skipped...", newPage.isFailed());
    }

    @Test
    public void testSelectComponent() {
        assertNotNull(ipcp.selectComponent().clickSelect());
    }

    @Test
    public void testInputCount() {
        assertNotNull(ipcp.inputCount());
    }

    @Test
    public void testInputLengthOfInletPipe() {
        assertNotNull(ipcp.inputLengthOfInletPipe());
    }

    @Test
    public void testInputEquivalentPipeRoughness() {
        assertNotNull(ipcp.inputEquivalentPipeRoughness());
    }

    @Test
    public void testInputAllowedPressureLoss() {
        assertNotNull(ipcp.inputAllowedPressureLoss());
    }

    @After
    public void tearDown() throws Exception {
        newPage.updateFinishedTestcases();
        // System.out.println(this.id + "-> " + "Succeeded: "
        // + newPage.getSucceededTestCases() + "("
        // + newPage.getNoOfTestcases() + " of " + noT + ")[Failed?: "
        // + newPage.isFailed() + "]");
        // if (newPage.getNoOfTestcases() == noT) {
        // if (newPage.getSucceededTestCases() == noT) {
        // newPage.reset();
        //
        // } else {
        // newPage.reset();
        // }
        // }
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // ipcp.clickBackButton().clickBackButton().clickBackButton();
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

    @Override
    public void evalNextPage() {
        evaluatedTestcases++;
        if (!cttcfiles.isEmpty()) {
            // TODO : THIS
            // org.junit.runner.JUnitCore jc = new JUnitCore();
            // Result r = new Result();
            // InletPipePage ipp = ipcp.clickNextButton();
            // r = jc.run(InletPipeTest.suite(ipcp, cttcfiles));
            // printResult(r);
        } else {
            System.out.println("########### evaluatedTestcases: "
                    + evaluatedTestcases + "=?=" + testSuites
                    + " :testSuites ###########");
            if (evaluatedTestcases == testSuites) {
                if (allPreviousTestsFinished)
                    ipcp.clickFinishButton().clickLastFinishButton();
            } else {
                CreateNewSizingWizardPage cnswp = (CreateNewSizingWizardPage) ipcp
                        .clickFinishButton().createAnotherSizing()
                        .clickLastFinishButton();
                cnswp.clickNextButton().inputTagNo(
                        "Test#" + ipcp.finishedProjects + " - "
                                + TimeAssist.getDate()).clickNextButton()
                        .clickNextButton().clickNextButton()
                        .clickNextButton();
            }
        }
    }

    public static junit.framework.Test suite(InletPipeComponentsPage ipcp,
            ArrayList<File> cttcfiles, boolean allTestsFinished) {
        InletPipeComponentsTest.ipcp = ipcp;
        InletPipeComponentsTest.cttcfiles = cttcfiles;
        InletPipeComponentsTest.allPreviousTestsFinished = allTestsFinished;
        return new JUnit4TestAdapter(InletPipeComponentsTest.class);
    }

}
