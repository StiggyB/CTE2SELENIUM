package de.haw_hamburg.ti.c2s.com.valvestar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

public class UsingLoginPage {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // Create a new instance of a driver
        WebDriver driver = new FirefoxDriver();

        // Navigate to the right place
        driver.get("http://www.valvestar.com/");

        // Create a new instance of the search page class
        // and initialise any WebElement fields in it.
        LoginPage loginPage = PageFactory.initElements(driver,
                LoginPage.class);

        // And now do the search.
        MainPage mainPage = loginPage.loginAs("BenjaminBurchard", "wirzer");
        mainPage = PageFactory.initElements(driver, MainPage.class);
        mainPage.openProjectExplorer();
    }

}
