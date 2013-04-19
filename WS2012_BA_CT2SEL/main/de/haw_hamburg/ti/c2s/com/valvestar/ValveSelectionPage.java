package de.haw_hamburg.ti.c2s.com.valvestar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Implemented for design, no actual purpose.
 * @author Benjamin
 *
 */
public class ValveSelectionPage extends ControlMenu {

    public ValveSelectionPage(WebDriver driver) {
        super(driver);
    }

    /* (non-Javadoc)
     * @see de.haw_hamburg.ti.c2s.com.valvestar.ControlMenu#clickNextButton()
     */
    @Override
    public ValveFinderPage clickNextButton() {
        super.clickNextButton();
        return PageFactory.initElements(driver, ValveFinderPage.class);
    }

    /* (non-Javadoc)
     * @see de.haw_hamburg.ti.c2s.com.valvestar.ControlMenu#clickBackButton()
     */
    @Override
    public ServiceConditionPage clickBackButton() {
        super.clickBackButton();
        return PageFactory.initElements(driver, ServiceConditionPage.class);
    }
    
    

}
