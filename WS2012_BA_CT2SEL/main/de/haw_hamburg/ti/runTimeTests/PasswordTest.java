package de.haw_hamburg.ti.runTimeTests;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import de.haw_hamburg.ti.cte.xmlObjects.CteTestCase;
import de.haw_hamburg.ti.tools.Cast;

@RunWith(Parameterized.class)
public class PasswordTest {

    WebDriver                            driver;
    private String                       numeric;
    private String                       uppercase;
    private String                       length;
    private static ArrayList<CteTestCase> testcases;

    public PasswordTest(String numeric, String uppercase, String length) {
        this.numeric = numeric;
        this.uppercase = uppercase;
        this.length = length;
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

    @Before
    public void setUp() throws Exception {
        driver = new HtmlUnitDriver();
        driver.get("http://www.fcmuckefuck.de");
    }

    @Test
    public void testPassword() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.partialLinkText("Homepage")).click();
        driver.findElement(By.id("loginButton")).click();
        if (numeric.equals("true") && uppercase.equals("true")
                && length.length() >= 8) {
            driver.findElement(By.name("loginUsername")).sendKeys("Benny");
            driver.findElement(By.name("loginPassword")).sendKeys("wirzer");
            driver.findElement(By.name("loginPassword")).submit();

            new WebDriverWait(driver, 10)
                    .until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver d) {
                            return d.getTitle()
                                    .equalsIgnoreCase(
                                            "Startseite - FC Muckefuck Bergedorf ..::Freizeitfuﬂball::..");
                        }
                    });
            assertEquals("Angemeldet als Benny.",
                    driver.findElement(By.id("userNote")).getText());
        } else {
            driver.findElement(By.name("loginUsername")).sendKeys("Benny");
            driver.findElement(By.name("loginPassword")).sendKeys("bla");
            driver.findElement(By.name("loginPassword")).submit();

            new WebDriverWait(driver, 10)
                    .until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver d) {
                            return d.getTitle()
                                    .equalsIgnoreCase(
                                            "Anmelden - FC Muckefuck Bergedorf ..::Freizeitfuﬂball::..");
                        }
                    });
            assertEquals("Sie sind nicht angemeldet.",
                    driver.findElement(By.id("userNote")).getText());
        }
    }

    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(PasswordTest.class);
    }

    @After
    public void tearDown() throws Exception {
        driver.close();
    }

}
