package de.haw_hamburg.ti.c2s.com.valvestar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class ValveFinderPage extends ControlMenu {

    public ValveFinderPage(WebDriver driver) {
        super(driver);
    }

    public ValveFinderPage clickFirstAddButton() {
        /**
         * TODO TRANSLATE TO JAVASCRIPT
         */
        driver.findElement(By.name("ctl00$WorkspacePlaceHolder$ctl00$ValveLayoutTable$ctl02$ctl02")).click();
        return this;
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
