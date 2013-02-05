package de.haw_hamburg.ti.c2s.com.valvestar;

import org.apache.commons.logging.impl.Log4JLogger;
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
    private transient boolean         sizingMenuOpen = false;
    @FindBy(id = "ctl00_HeaderPlaceHolder_HeaderPageControl_HeaderMenu_9")
    private WebElement      sizing;
    @FindBy(css = "img[alt=\"Add new sizing...\"]")
    private WebElement      addSizing;
    @FindBy(css = "#ctl00_WorkspacePlaceHolder_NextButton")
    private WebElement      nextButton;
    @FindBy(id = "ctl00_WorkspacePlaceHolder_CancelButton")
    private WebElement      cancelButton;
    @FindBy(id = "ctl00_WorkspacePlaceHolder_BackButton")
    private WebElement      backButton;

    private final Javascript      jscript;
    //TODO: LOGGER INTEGRATION
    private Log4JLogger logger;

    public ControlMenu(final WebDriver driver) {
        this.driver = driver;
        jscript = new Javascript(driver);
    }

    /**
     * Open the Sizing Menu to add a new Sizing.
     */
    public void openSizingMenu() {
        final Actions builder = new Actions(driver);
        builder.moveToElement(sizing).build().perform();
        sizingMenuOpen = true;
    }

    /**
     * Add a new Sizing.
     */
    public void addNewSizing() {
        if (sizingMenuOpen) {
            addSizing.click();
        } else {
            throw new IllegalStateException("Sizing Menu not opened");
        }
    }

    /**
     * Next page.
     */
    public void clickNextButton() {
        jscript.click("#ctl00_WorkspacePlaceHolder_NextButton");
    }

    /**
     * Previous page.
     */
    public void clickBackButton() {
        try {
            jscript.click("#ctl00_WorkspacePlaceHolder_BackButton");
        } catch (WebDriverException wde) {
            logger.error("Web Driver Exception");
        }
    }

    /**
     * Cancel.
     */
    public void clickCancelButton() {
        jscript.click("#ctl00_WorkspacePlaceHolder_CancelButton");
    }

}
