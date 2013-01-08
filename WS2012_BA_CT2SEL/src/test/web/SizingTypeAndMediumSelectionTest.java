package test.web;

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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import tools.Cast;
import cte.xmlObjects.CteTestCase;

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
    private static WebElement             AgreementCheckBox;
    private static WebElement             LoginTextBox;
    private static WebElement             PasswordTextBox;
    private static WebElement             SubmitButton;
    private static WebElement             Sizing;
    private static WebElement             AddNewSizing;
    private static WebElement             UpperNextButon;

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

    @Parameters
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
        driver.get(baseUrl
                + "/UI/MainForm/Workspace/Authentication/Authentication.aspx");
        driver.findElement(By.id("ctl00_AgreementCheckBox")).click();
        driver.findElement(By.id("ctl00_WorkspacePlaceHolder_LoginTextBox"))
        .clear();
        driver.findElement(By.id("ctl00_WorkspacePlaceHolder_LoginTextBox"))
        .sendKeys("BenjaminBurchard");
        driver.findElement(
                By.id("ctl00_WorkspacePlaceHolder_PasswordTextBox")).clear();
        driver.findElement(
                By.id("ctl00_WorkspacePlaceHolder_PasswordTextBox"))
                .sendKeys("wirzer");
        driver.findElement(By.id("ctl00_WorkspacePlaceHolder_SubmitButton"))
        .click();
        Actions builder = new Actions(driver);
        Sizing = driver
                .findElement(By
                        .id("ctl00_HeaderPlaceHolder_HeaderPageControl_HeaderMenu_9"));
        builder.moveToElement(Sizing).build().perform();
        driver.findElement(By.cssSelector("img[alt=\"Add new sizing...\"]"))
        .click();
        driver.findElement(
                By.id("ctl00_WorkspacePlaceHolder_UpperNextButton")).click();
    }

    @Before
    public void setUp() throws Exception {


    }

    @Test
    public void test() {
        System.out.println("TC: " + this.name);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Tear Down");
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
