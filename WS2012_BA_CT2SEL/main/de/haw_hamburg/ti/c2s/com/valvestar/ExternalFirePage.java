package de.haw_hamburg.ti.c2s.com.valvestar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class ExternalFirePage extends ControlMenu {

    private WebDriver driver;

    public ExternalFirePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    /* (non-Javadoc)
     * @see de.haw_hamburg.ti.c2s.com.valvestar.ControlMenu#clickNextButton()
     */
    @Override
    public FireCasePage clickNextButton() {
        super.clickNextButton();
        return PageFactory.initElements(driver, FireCasePage.class);
    }

    /* (non-Javadoc)
     * @see de.haw_hamburg.ti.c2s.com.valvestar.ControlMenu#clickBackButton()
     */
    @Override
    public MediumSelectionPage clickBackButton() {
        super.clickBackButton();
        return PageFactory.initElements(driver, MediumSelectionPage.class);
    }
    
    

}
