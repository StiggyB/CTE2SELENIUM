package de.haw_hamburg.ti.test.web;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import de.haw_hamburg.ti.c2s.com.valvestar.ControlMenu;
import de.haw_hamburg.ti.c2s.com.valvestar.LoginPage;
import de.haw_hamburg.ti.cte.xmlObjects.CteTestCase;
import de.haw_hamburg.ti.tools.Cast;

@RunWith(Parameterized.class)
public class SizingTypeAndMediumSelectionTest {

    private static WebDriver              driver;
    private Integer                       id;
    private String                        name;
    private Integer[]                     marks;
    private HashMap<Integer, String>      markMap;
    private HashMap<Integer, String>      markCompMap;
    private static ArrayList<CteTestCase> testcases;
    private static String                 baseUrl;

    public SizingTypeAndMediumSelectionTest(Integer id, String name,
            Integer[] marks, HashMap<Integer, String> markMap,
            HashMap<Integer, String> markCompMap) {
        this.name = name;
        this.id = id;
        this.marks = marks;
        this.markMap = markMap;
        this.markCompMap = markCompMap;
    }

    private static ArrayList<CteTestCase> loadTestCaseObjectsFromFile() {
        try {
            FileInputStream fin = new FileInputStream("TEST_TC.dat");
            ObjectInputStream ois = new ObjectInputStream(fin);
            testcases = Cast.as(ArrayList.class, ois.readObject());
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return testcases;
    }

    @Parameters//TODO: Java4.11 binden für @Parameters(name="namestring")
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

        loginPage.loginAs("BenjaminBurchard", "wirzer");

        ControlMenu controlMenu = PageFactory.initElements(driver,
                ControlMenu.class);
        controlMenu.openSizingMenu();
        controlMenu.addNewSizing();
        controlMenu.clickNextButton();
    }

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void test() {
        System.out.println("------------- TC: " + this.name);
        System.out.println("------------- TCid: " + this.id);
        // for (Entry<Integer, String> entryMark : markMap.entrySet()) {
        // System.out.println("Mark - " + entryMark.getKey() + ": " +
        // entryMark.getValue());
        // }
        // for (Entry<Integer, String> entryMarkC : markCompMap.entrySet()) {
        // System.out.println("MarkComp - " + entryMarkC.getKey() + ": " +
        // entryMarkC.getValue());
        // }

        for (int i = 0; i < marks.length; i++) {
            System.out.print("Mark: " + marks[i]);
            System.out.print(" Composition Value: "
                    + markCompMap.get(marks[i]));
            System.out.println(" - " + markMap.get(marks[i]));
            if (markCompMap.get(marks[i]).equals("Medium")) {
                if (markMap.get(marks[i]).equals("Liquid")) {
                    new Select(
                            driver.findElement(By
                                    .id("ctl00_WorkspacePlaceHolder_ctl00_MediumDropDownList")))
                            .selectByValue("Liquid");
                } else if (markMap.get(marks[i]).equals("Two-phase flow")) {
                    new Select(
                            driver.findElement(By
                                    .id("ctl00_WorkspacePlaceHolder_ctl00_MediumDropDownList")))
                            .selectByValue("TwoPhaseFlow");
                }
//                driver.navigate().refresh();
            }
        }
        //
        // driver.findElement(By.cssSelector("option[value=\"Liquid\"]")).click();
        // new
        // Select(driver.findElement(By.id("ctl00_WorkspacePlaceHolder_ctl00_SizingStandardDropDownList"))).selectByVisibleText("AD 2000:A2 / TRD 421");
        // driver.findElement(By.cssSelector("option[value=\"TGVzZXIuVmFsdmVzdGFyLkNvcmUuU2l6aW5ncy5BRDIwMDBBMi5BRDIwMDBBMlNpemluZywgTGVzZXIuVmFsdmVzdGFyLkNvcmU=\"]")).click();
        // driver.findElement(By.id("ctl00_WorkspacePlaceHolder_ctl00_CdtpCheckBox")).click();
        // driver.findElement(By.id("ctl00_WorkspacePlaceHolder_ctl00_ReactionForceAd2000A2")).click();
        // driver.findElement(By.id("ctl00_WorkspacePlaceHolder_ctl00_PDInletIso4126")).click();
        // driver.findElement(By.id("ctl00_WorkspacePlaceHolder_ctl00_BPOutletNone")).click();
        // driver.findElement(By.id("ctl00_WorkspacePlaceHolder_NextButton")).click();
        // driver.findElement(By.id("ctl00_WorkspacePlaceHolder_CancelButton")).click();
    }

    @After
    public void tearDown() throws Exception {
        driver.get(baseUrl
                + "/UI/MainForm/Workspace/Sizing/NewSizingWizard.aspx");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        driver.close();
    }

    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(SizingTypeAndMediumSelectionTest.class);
    }
}
