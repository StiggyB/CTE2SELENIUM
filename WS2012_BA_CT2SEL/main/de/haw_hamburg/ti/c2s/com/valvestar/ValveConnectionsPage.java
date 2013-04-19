package de.haw_hamburg.ti.c2s.com.valvestar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class ValveConnectionsPage extends ControlMenu {

    public ValveConnectionsPage(WebDriver driver) {
        super(driver);
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.haw_hamburg.ti.c2s.com.valvestar.ControlMenu#clickNextButton()
     */
    @Override
    public HomePage clickNextButton() {
        super.clickNextButton();
        return PageFactory.initElements(driver, ValveAccessoriesPage.class);
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
