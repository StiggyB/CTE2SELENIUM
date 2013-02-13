package de.haw_hamburg.ti.c2s.com.valvestar;

import org.apache.commons.logging.impl.Log4JLogger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import de.haw_hamburg.ti.tools.Javascript;



/**
 * Represents the Control Menu of the VALVESTAR Website. The Header Menu as well
 * as other Menu Buttons which interact with the Site.
 * 
 * @author Benjamin
 * 
 */
public abstract class ControlMenu implements HomePage{

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
        jscript = new Javascript(driver);
    }

    /**
     * Next page.
     */
    public HomePage clickNextButton() {
        jscript.click("#ctl00_WorkspacePlaceHolder_NextButton");
        return null;
    }

    /**
     * Previous page.
     * @return 
     */
    public HomePage clickBackButton() {
//        try {
            jscript.click("#ctl00_WorkspacePlaceHolder_BackButton");
//        } catch (WebDriverException wde) {
//            logger.error("Web Driver Exception");
//        }
        return null;
    }

    /**
     * Cancel.
     */
    public void clickCancelButton() {
        jscript.click("#ctl00_WorkspacePlaceHolder_CancelButton");
    }

}
