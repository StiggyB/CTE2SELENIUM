package de.haw_hamburg.ti.c2s.com.valvestar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class ValveFinderPage extends ControlMenu {

    private WebDriver driver;
    
    public ValveFinderPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    /* (non-Javadoc)
     * @see de.haw_hamburg.ti.c2s.com.valvestar.ControlMenu#clickNextButton()
     */
    @Override
    public InletPipeComponentsPage clickNextButton() {
        super.clickNextButton();
        return PageFactory.initElements(driver, InletPipeComponentsPage.class);
    }

    /* (non-Javadoc)
     * @see de.haw_hamburg.ti.c2s.com.valvestar.ControlMenu#clickBackButton()
     */
    @Override
    public ValveSelectionPage clickBackButton() {
        super.clickBackButton();
        return  PageFactory.initElements(driver, ValveSelectionPage.class);
    }

    
}
