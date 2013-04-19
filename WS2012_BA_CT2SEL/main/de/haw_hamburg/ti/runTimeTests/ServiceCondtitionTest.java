package de.haw_hamburg.ti.runTimeTests;

import static org.junit.Assert.*;
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
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.haw_hamburg.ti.c2s.com.valvestar.CreateNewSizingWizardPage;
import de.haw_hamburg.ti.c2s.com.valvestar.InletPipeComponentsPage;
import de.haw_hamburg.ti.c2s.com.valvestar.ServiceConditionPage;
import de.haw_hamburg.ti.cte.xmlObjects.CteObject;
import de.haw_hamburg.ti.cte.xmlObjects.CteTestCase;
import de.haw_hamburg.ti.tools.Cast;
import de.haw_hamburg.ti.tools.FileHandler;
import de.haw_hamburg.ti.tools.TimeAssist;
import de.haw_hamburg.ti.tools.tree.Tree;

@RunWith(Parameterized.class)
public class ServiceCondtitionTest implements PageTest {

    private static ServiceConditionPage scp;
    private Integer                     id;
    private String                      name;
    private Integer[]                   marks;
    private int                         noT                = 9;
    private static Tree<CteObject>      tree;
    private static ArrayList<File>      cttcfiles;
    private static int                  testSuites;
    private static int                  evaluatedTestcases = 0;
    private static boolean              allPreviousTestsFinished;

    @Rule
    public NewPageRule                  newPage            = new NewPageRule(
                                                                   noT, this);

    public ServiceCondtitionTest(Integer id, String name, Integer[] marks,
            HashMap<Integer, String> markClassMap,
            HashMap<Integer, String> markClassificationMap,
            HashMap<Integer, String> markCompositionMap) {
        this.name = name;
        this.id = id;
        this.marks = marks;
    }

    @Parameters(name = "{index}: {1}")
    public static List<Object[]> data() {
        if (!externalCall) {
            // cttcfiles.add(new File("Service_conditionCleaned.cttc"));
            // cttcfiles.add(new File("Inlet_Pipe_Components.cttc"));
        }
        assertFalse(cttcfiles.isEmpty());
        if (allPreviousTestsFinished) {
            FileHandler.setFile(cttcfiles.remove(0));
        } else {
            FileHandler.setFile(cttcfiles.get(0));
        }
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
        scp.setTree(tree);
    }

    @Before
    public void setUp() throws Exception {
        printActualTestInfo();
        scp.setMarks(marks);
        assumeFalse("Skipped...", newPage.isFailed());
        TimeAssist.setInternalStampTwo();
        System.out.println("TIME FROME STAMS TO SCP[" + evaluatedTestcases
                + "]: " + TimeAssist.getInternalTimePassed());
    }

    @Test
    public void testInputMaxAllowableWorkingPresure() {
        System.out.println("mawp");
        assertNotNull(scp.inputMaxAllowableWorkingPresure());
    }

    @Test
    public void testInputSetPressure() {
        System.out.println("sp");
        assertNotNull(scp.inputSetPressure());
    }

    @Test
    public void testInputSuperimposedBackPressure() {
        System.out.println("sibp");
        assertNotNull(scp.inputSuperimposedBackPressure());
    }

    @Test
    public void testInputOverpressure() {
        System.out.println("overp");
        assertNotNull(scp.inputOverpressure());
    }

    @Test
    public void testInputRequiredMassflow() {
        System.out.println("reqmf");
        assertNotNull(scp.clickRadioRequiredMassflow()
                .inputRequiredMassflow());
    }

    @Test
    public void testInputVolumeFlowToBeDischarged() {
        System.out.println("vf");
        assertNotNull(scp.clickRadioVolumeFlowToBeDischarged()
                .inputVolumeFlowToBeDischarged());
    }

    @Test
    public void testSelectSteamDataAccordingTo() {
        System.out.println("sdat");
        assertNotNull(scp.selectSteamDataAccordingTo());
    }

    @Test
    public void testInputRatioSpecificHeats() {
        System.out.println("rsh");
        assertNotNull(scp.inputRatioSpecificHeats());
    }

    @Test
    public void testInputSpecificVolume() {
        System.out.println("sv");
        assertNotNull(scp.inputSpecificVolume());
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
        // scp.clickCalculate();
        // org.junit.runner.JUnitCore jc = new JUnitCore();
        // Result r = new Result();
        // InletPipeComponentsPage ipcp = scp.clickNextButton()
        // .clickNextButton().clickFirstAddButton()
        // .clickNextButton();
        // r = jc.run(InletPipeComponentsTest.suite(ipcp));
        // printResult(r);
        // } else {
        // newPage.reset();
        // }
        // }
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        scp.finishedProjects = 0; // means this "test set" ended. Next Test has
                                  // another name therfore set to zero
        evaluatedTestcases = 0; // all testcases evaluated(what a suprise...)
        System.out
                .println("###########SCP TEAR DOWN AFTER CLASS CALLED########");
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
        boolean allTestsFinished = false;
        System.out.println(">>>>>>SCT-cttcfiles: " + cttcfiles);
        evaluatedTestcases++;
        if (evaluatedTestcases == testSuites) {
            allTestsFinished = true;
        }
        if (cttcfiles.size() >= 2) {
            scp.clickCalculate();
            org.junit.runner.JUnitCore jc = new JUnitCore();
            Result r = new Result();
            InletPipeComponentsPage ipcp = scp.clickNextButton()
                    .clickNextButton().clickFirstAddButton()
                    .clickNextButton();
            r = jc.run(InletPipeComponentsTest.suite(ipcp, cttcfiles,
                    allTestsFinished));
            printResult(r);
        } else {
            scp.clickCalculate();
            System.out.println("########### evaluatedTestcases: "
                    + evaluatedTestcases + "=?=" + testSuites
                    + " :testSuites ###########");
            if (evaluatedTestcases == testSuites) {
                if (allPreviousTestsFinished)
                    scp.clickFinishButton().clickLastFinishButton();
                else {
                    CreateNewSizingWizardPage cnswp = (CreateNewSizingWizardPage) scp
                            .clickFinishButton().createAnotherSizing()
                            .clickLastFinishButton();
                    cnswp.clickNextButton().inputTagNo(
                            "Test#" + scp.finishedProjects + " - "
                                    + TimeAssist.getDate());
                }
            } else {
                scp.finishedProjects++;
                CreateNewSizingWizardPage cnswp = (CreateNewSizingWizardPage) scp
                        .clickFinishButton().createAnotherSizing()
                        .clickLastFinishButton();
                cnswp.clickNextButton().inputTagNo(
                        "Test#" + scp.finishedProjects + " - "
                                + TimeAssist.getDate()).clickNextButton();
            }
        }

    }

    public static junit.framework.Test suite(ServiceConditionPage scp,
            ArrayList<File> cttcfiles, boolean allTestsFinished) {
        ServiceCondtitionTest.cttcfiles = cttcfiles;
        ServiceCondtitionTest.scp = scp;
        ServiceCondtitionTest.allPreviousTestsFinished = allTestsFinished;
        return new JUnit4TestAdapter(ServiceCondtitionTest.class);
    }

}
