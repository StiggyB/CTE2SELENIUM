package de.haw_hamburg.ti.c2s.com.valvestar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class FireCasePage extends ControlMenu {


    public FireCasePage(WebDriver driver) {
        super(driver);
    }

    /* (non-Javadoc)
     * @see de.haw_hamburg.ti.c2s.com.valvestar.ControlMenu#clickNextButton()
     */
    @Override
    public ServiceConditionPage clickNextButton() {
        super.clickNextButton();
        return PageFactory.initElements(driver, ServiceConditionPage.class);
    }

    /* (non-Javadoc)
     * @see de.haw_hamburg.ti.c2s.com.valvestar.ControlMenu#clickBackButton()
     */
    @Override
    public ExternalFirePage clickBackButton() {
        super.clickBackButton();
        return PageFactory.initElements(driver, ExternalFirePage.class);
    }
    
    

}
