package de.haw_hamburg.ti.c2s.com.valvestar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class InletPipePage extends ControlMenu {

    private WebDriver driver;

    public InletPipePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.haw_hamburg.ti.c2s.com.valvestar.ControlMenu#clickNextButton()
     */
    @Override
    public OutletPipeComponentsPage clickNextButton() {
        super.clickNextButton();
        return PageFactory.initElements(driver,
                OutletPipeComponentsPage.class);
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
