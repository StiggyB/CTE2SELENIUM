package de.haw_hamburg.ti.c2s.com.valvestar;

import org.apache.commons.logging.impl.Log4JLogger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import de.haw_hamburg.ti.tools.Javascript;

/**
 * Represents the Control Menu of the VALVESTAR Website. The Header Menu as well
 * as other Menu Buttons which interact with the Site.
 * 
 * @author Benjamin
 * 
 */
public abstract class ControlMenu implements HomePage {

    @FindBy(css = "#ctl00_WorkspacePlaceHolder_NextButton")
    @CacheLookup
    private WebElement       nextButton;
    @FindBy(id = "ctl00_WorkspacePlaceHolder_CancelButton")
    @CacheLookup
    private WebElement       cancelButton;
    @FindBy(id = "ctl00_WorkspacePlaceHolder_BackButton")
    @CacheLookup
    private WebElement       backButton;

    private final Javascript jscript;
    // TODO: LOGGER INTEGRATION
    private Log4JLogger      logger;
    private WebDriver        driver;

    public ControlMenu(final WebDriver driver) {
        this.driver = driver;
        jscript = new Javascript(driver);
    }

    /**
     * Next page.
     */
    @Override
    public HomePage clickNextButton() {
        if (nextButton.isEnabled()) {
            jscript.click("#ctl00_WorkspacePlaceHolder_NextButton");
        } else {
            System.err.println("Formal error");
        }
        return this;
    }

    /**
     * Previous page.
     * 
     * @return
     */
    @Override
    public HomePage clickBackButton() {
        jscript.click("#ctl00_WorkspacePlaceHolder_BackButton");
        return this;
    }

    /**
     * Cancel.
     */
    public HomePage clickCancelButton() {
        jscript.click("#ctl00_WorkspacePlaceHolder_CancelButton");
        return this;
    }
    
    public HomePage clickFinishButton() {
        jscript.click("#ctl00_WorkspacePlaceHolder_UpperFinishButton");
        return this;
    }
    
    /**
     * @return the nextButton
     */
    protected WebElement getNextButton() {
        return nextButton;
    }

    /**
     * @param nextButton the nextButton to set
     */
    protected void setNextButton(WebElement nextButton) {
        this.nextButton = nextButton;
    }
    
}
