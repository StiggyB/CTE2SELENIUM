package tools;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import de.haw_hamburg.ti.c2s.com.valvestar.ControlMenu;
import de.haw_hamburg.ti.c2s.com.valvestar.LoginPage;
import de.haw_hamburg.ti.tools.Javascript;



public class JavascriptTest {

    private static WebDriver   driver;
    private static Javascript  js;
    private static String      baseUrl;
    private static ControlMenu controlMenu;
    private static WebElement  CdtpCheckBox;

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

        js = new Javascript(driver);
        CdtpCheckBox = driver.findElement(By
                .id("ctl00_WorkspacePlaceHolder_ctl00_CdtpCheckBox"));
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() {
        assertTrue(CdtpCheckBox.isSelected());
        System.out.println("click");
        wait(1);
        js.click("#ctl00_WorkspacePlaceHolder_ctl00_CdtpCheckBox");
        assertFalse(CdtpCheckBox.isSelected());
        System.out.println("click");
        wait(1);
        js.click("#ctl00_WorkspacePlaceHolder_ctl00_CdtpCheckBox");
        assertTrue(CdtpCheckBox.isSelected());
        System.out.println("uncheck");
        wait(1);
        js.uncheck("ctl00_WorkspacePlaceHolder_ctl00_CdtpCheckBox");
        assertFalse(CdtpCheckBox.isSelected());
        
        js.uncheck("ctl00_WorkspacePlaceHolder_ctl00_ReactionForceAd2000A2");

    }

    /**
     * 
     */
    private void wait(int seconds) {
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        driver.close();
    }

}
