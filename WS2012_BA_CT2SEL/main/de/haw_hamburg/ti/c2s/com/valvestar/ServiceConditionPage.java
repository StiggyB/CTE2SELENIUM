package de.haw_hamburg.ti.c2s.com.valvestar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import de.haw_hamburg.ti.tools.Javascript;

public class ServiceConditionPage extends ControlMenu {

    private WebDriver  driver;
    private String     medium;
    private Javascript jscript;
    private String     mawpTextBoxId              = "ctl00_WorkspacePlaceHolder_ctl00_ctl05_MawpPressureTextBox";
    private String     calculateBtnId             = "ctl00_WorkspacePlaceHolder_ctl00_CalculateButton";
    private String     calculateBtnCSS            = "#" + calculateBtnId;
    private String     setPressureId              = "ctl00_WorkspacePlaceHolder_ctl00_ctl05_SetPressureTextBox";
    private String     superimposedBackPressureId = "ctl00_WorkspacePlaceHolder_ctl00_ctl05_ConstantBackpressureTextBox";
    private String     overpressureID             = "ctl00_WorkspacePlaceHolder_ctl00_ctl05_OverpressureTextBox";
    private String     temperatureId;
    private boolean    fireCase                   = false;

    public ServiceConditionPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        jscript = new Javascript(driver);
    }

    public ServiceConditionPage inputMaxAllowableWorkingPresure() {
        jscript.clear(mawpTextBoxId);
        jscript.input(mawpTextBoxId, "14");
        return this;
    }

    @Override
    public ValveSelectionPage clickNextButton() {
        super.clickNextButton();
        return PageFactory.initElements(driver, ValveSelectionPage.class);
    }

    @Override
    public HomePage clickBackButton() {
        super.clickBackButton();
        if (medium.matches(".*[Ss]team")) {
            return PageFactory.initElements(driver,
                    SizingTypeAndMediumSelectionPage.class);
        } else {
            if (fireCase) {
                return PageFactory.initElements(driver, FireCasePage.class);
            } else {
                return PageFactory.initElements(driver,
                        MediumSelectionPage.class);
            }
        }
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public void setFireCase(boolean fireCase) {
        this.fireCase = fireCase;
    }

    public ServiceConditionPage inputSetPressure() {
        jscript.clear(setPressureId);
        jscript.input(setPressureId, "10");
        return this;
    }

    public ServiceConditionPage inputSuperimposedBackPressure() {
        jscript.clear(superimposedBackPressureId);
        jscript.input(superimposedBackPressureId, "0");
        return this;
    }

    public ServiceConditionPage inputOverpressure() {
        jscript.clear(overpressureID);
        jscript.input(overpressureID, "50");
        return this;
    }

    public ServiceConditionPage inputTemperature() {
        if (driver.findElement(By.id(temperatureId)).isEnabled()) {
            jscript.clear(temperatureId);
            jscript.input(temperatureId, "500");
        } else {
            System.err.println("Wrong temperature");
        }
        return this;
    }

    public ServiceConditionPage clickCalculate() {
        jscript.click(calculateBtnCSS);
        return this;
    }
}
