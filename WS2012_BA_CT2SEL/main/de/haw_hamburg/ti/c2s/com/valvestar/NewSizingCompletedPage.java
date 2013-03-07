package de.haw_hamburg.ti.c2s.com.valvestar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * 
 * This class needs to interpret failures etc., if a Sizing is ok
 * 
 * @author Benjamin
 * 
 */
public class NewSizingCompletedPage extends ControlMenu {

    private boolean   createAnotherSizing = false;
    private WebDriver driver;

    public NewSizingCompletedPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    /**
     * Clicks Finish Button instead of Next, the Next Button is always shown
     * grayed.
     * 
     * @return MainPage <i>or</i> CreateNewSizingWizardPage
     */
    public HomePage clickNextButton() {
        return clickFinishButton();
    }

    @Override
    public HomePage clickFinishButton() {
        if (createAnotherSizing) {
            return PageFactory.initElements(driver,
                    CreateNewSizingWizardPage.class);
        } else {
            return PageFactory.initElements(driver, MainPage.class);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.haw_hamburg.ti.c2s.com.valvestar.ControlMenu#clickBackButton()
     */
    @Override
    public HomePage clickBackButton() {
        // TODO Auto-generated method stub
        return super.clickBackButton();
    }

}
