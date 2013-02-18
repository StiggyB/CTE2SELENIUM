package de.haw_hamburg.ti.c2s.com.valvestar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class OutletPipeComponentsPage extends ControlMenu {

    private WebDriver driver;

    public OutletPipeComponentsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    /* (non-Javadoc)
     * @see de.haw_hamburg.ti.c2s.com.valvestar.ControlMenu#clickNextButton()
     */
    @Override
    public ValveConnectionsPage clickNextButton() {
        super.clickNextButton();
        return PageFactory.initElements(driver,
                ValveConnectionsPage.class);
    }

    /* (non-Javadoc)
     * @see de.haw_hamburg.ti.c2s.com.valvestar.ControlMenu#clickBackButton()
     */
    @Override
    public HomePage clickBackButton() {
        // TODO Auto-generated method stub
        return super.clickBackButton();
    }

    
}
