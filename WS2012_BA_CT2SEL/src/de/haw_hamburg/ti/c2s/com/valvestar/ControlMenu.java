package de.haw_hamburg.ti.c2s.com.valvestar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

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
    @FindBy(id = "ctl00_WorkspacePlaceHolder_NextButton")
    private WebElement      nextButton;
    @FindBy(id = "ctl00_WorkspacePlaceHolder_CancelButton")
    private WebElement cancelButton;
    @FindBy(id = "ctl00_WorkspacePlaceHolder_BackButton")
    private WebElement backButton;

    public ControlMenu(WebDriver driver) {
        this.driver = driver;
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
        nextButton.click();
    }
    
    /**
     * Next page.
     */
    public void clickBackButton() {
        backButton.click();
    }
    
    /**
     * Cancel.
     */
    public void clickCancelButton() {
        cancelButton.click();
    }

}
