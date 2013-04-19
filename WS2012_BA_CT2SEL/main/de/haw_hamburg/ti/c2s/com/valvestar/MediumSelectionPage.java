package de.haw_hamburg.ti.c2s.com.valvestar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class MediumSelectionPage extends ControlMenu {

    private String     medium;
    private String     selectMediumBtnId  = "ctl00_WorkspacePlaceHolder_ctl00_DbMediumSelectButton";
    private String     selectMediumBtnCSS = "#" + selectMediumBtnId;
    private boolean    fireCase           = false;

    public MediumSelectionPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Selects a Medium (currently always "Air")
     * 
     * @return itself
     */
    public MediumSelectionPage clickSelectButton() {
        jscript.click(selectMediumBtnCSS);
        return this;
    }

    @Override
    public HomePage clickNextButton() {
        super.clickNextButton();
        if (fireCase) {
            return PageFactory.initElements(driver, ExternalFirePage.class);
        } else {
            return PageFactory.initElements(driver, ServiceConditionPage.class);
        }
    }

    @Override
    public SizingTypeAndMediumSelectionPage clickBackButton() {
        super.clickBackButton();
        return PageFactory.initElements(driver,
                SizingTypeAndMediumSelectionPage.class);
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public boolean getFireCase() {
        return fireCase;
    }
    
    public void setFireCase(boolean fireCase) {
        this.fireCase = fireCase;
    }
    
    public String getPageName() {
        return driver.findElement(
                By.id("ctl00_WorkspacePlaceHolder_PageNameLabel")).getText();
    }
}
