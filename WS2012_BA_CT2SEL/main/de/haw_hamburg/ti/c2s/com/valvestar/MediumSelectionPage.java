package de.haw_hamburg.ti.c2s.com.valvestar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import de.haw_hamburg.ti.tools.Javascript;

public class MediumSelectionPage extends ControlMenu {

    private WebDriver driver;
    private Javascript jscript;
    private String medium;
    private String selectMediumBtnId = "ctl00_WorkspacePlaceHolder_ctl00_DbMediumSelectButton";
    private String selectMediumBtnCSS = "#" + selectMediumBtnId;
    

    public MediumSelectionPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.jscript = new Javascript(driver);
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
    public ServiceConditionPage clickNextButton() {
        super.clickNextButton();
        return PageFactory.initElements(driver, ServiceConditionPage.class);
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

    public String getPageName() {
        return driver.findElement(By.id("ctl00_WorkspacePlaceHolder_PageNameLabel")).getText();
    }
}
