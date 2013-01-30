package de.haw_hamburg.ti.c2s.com.valvestar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import de.haw_hamburg.ti.tools.Javascript;

/**
 * Represents the Control Menu of the VALVESTAR Website. The Header Menu as well
 * as other Menu Buttons which interact with the Site.
 * 
 * @author Benjamin
 * 
 */
public class ControlMenu {

    private final WebDriver driver;
    private boolean         sizingMenuOpen = false;
    @FindBy(id = "ctl00_HeaderPlaceHolder_HeaderPageControl_HeaderMenu_9")
    private WebElement      Sizing;
    @FindBy(css = "img[alt=\"Add new sizing...\"]")
    private WebElement      addSizing;
    @FindBy(css = "#ctl00_WorkspacePlaceHolder_NextButton")
    private WebElement      nextButton;
    @FindBy(id = "ctl00_WorkspacePlaceHolder_CancelButton")
    private WebElement      cancelButton;
    @FindBy(id = "ctl00_WorkspacePlaceHolder_BackButton")
    private WebElement      backButton;

    private Javascript      js;

    public ControlMenu(WebDriver driver) {
        this.driver = driver;
        js = new Javascript(driver);
    }

    /**
     * Open the Sizing Menu to add a new Sizing.
     */
    public void openSizingMenu() {
        Actions builder = new Actions(driver);
        // Sizing = driver
        // .findElement(By
        // .id("ctl00_HeaderPlaceHolder_HeaderPageControl_HeaderMenu_9"));
        builder.moveToElement(Sizing).build().perform();
        sizingMenuOpen = true;
    }

    /**
     * Add a new Sizing.
     */
    public void addNewSizing() {
        if (sizingMenuOpen)
            addSizing.click();
        else
            throw new IllegalStateException("Sizing Menu not opened");
    }

    /**
     * Next page.
     */
    public void clickNextButton() {
        js.click("#ctl00_WorkspacePlaceHolder_NextButton");
    }

    /**
     * Previous page.
     */
    public void clickBackButton() {
        try {
            js.click("#ctl00_WorkspacePlaceHolder_BackButton");
        } catch (WebDriverException wde) {
            wde.printStackTrace();
            System.err.println("WDE");
        }
    }

    /**
     * Cancel.
     */
    public void clickCancelButton() {
        js.click("#ctl00_WorkspacePlaceHolder_CancelButton");
    }

}
