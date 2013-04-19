package de.haw_hamburg.ti.c2s.com.valvestar;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import de.haw_hamburg.ti.cte.xmlObjects.CteObject;
import de.haw_hamburg.ti.tools.tree.Knot;

public class ServiceConditionPage extends ControlMenu {

    @FindBy(tagName = "input")
    private List<WebElement> inputs;
    @FindBy(tagName = "select")
    private List<WebElement> selects;

    private String           medium;
    private String           calculateBtnId  = "ctl00_WorkspacePlaceHolder_ctl00_CalculateButton";
    private String           calculateBtnCSS = "#" + calculateBtnId;
    private boolean          fireCase;

    public ServiceConditionPage(WebDriver driver) {
        super(driver);
    }

    public ServiceConditionPage inputMaxAllowableWorkingPresure() {
        return (ServiceConditionPage) findAndMatch("MawpPressure",
                "Maximum allowable working pressure");
    }

    public ServiceConditionPage inputSetPressure() {
        return (ServiceConditionPage) findAndMatch("SetPressure",
                "Set pressure");
    }

    public ServiceConditionPage inputSuperimposedBackPressure() {
        return (ServiceConditionPage) findAndMatch("Backpressure",
                "Superimposed back pressure");
    }

    public ServiceConditionPage inputOverpressure() {
        return (ServiceConditionPage) findAndMatch("Overpressure",
                "Overpressure");
    }

    public ServiceConditionPage inputTemperature() {
        return (ServiceConditionPage) findAndMatch("Temperature",
                "Temperature");
    }
    
    public ServiceConditionPage clickRadioRequiredMassflow() {
        this.click(getWebElementId(findInputElement("RequiredMassFlowRadioButton")));
        return this;
    }

    public ServiceConditionPage inputRequiredMassflow() {
        return (ServiceConditionPage) findAndMatch("massflowtext",
                "Required massflow");
    }
    
    public ServiceConditionPage clickRadioVolumeFlowToBeDischarged() {
        this.click(getWebElementId(findInputElement("VolumeFlowToBeDischargedWorkingRadioButton")));
        return this;
    }

    public ServiceConditionPage inputVolumeFlowToBeDischarged() {
        return (ServiceConditionPage) findAndMatch("VolumeFlowToBeDischargedWorkingTextBox",
                "Volume flow to be discharged");
    }

    public ServiceConditionPage selectSteamDataAccordingTo() {
        WebElement s = findSelect("KTable");
        if (s == null) {
            return null;
        }
        for (WebElement webElement : (new Select(s)).getOptions()) {
            for (Knot<CteObject> k : getNodeList()) {
                if (k.getContent().getName().equalsIgnoreCase(
                        replaceUmlaut(webElement.getText()))) {
                    for (int j = 0; j < getMarks().length; j++) {
                        if (getMarks()[j].equals(k.getContent().getId())) {
                            jscript.select(getWebElementId(s), webElement
                                    .getAttribute("value"));
                            return this;
                        }
                    }
                }
            }
        }
        return null;

    }

    /**
     * NYI
     * 
     * @return ServiceConditionPage
     */
    public ServiceConditionPage inputRatioSpecificHeats() {
        return this;
    }

    /**
     * NYI
     * 
     * @return ServiceConditionPage
     */
    public ServiceConditionPage inputSpecificVolume() {
        return this;
    }

    public ControlMenu clickCalculate() {
        jscript.click(calculateBtnCSS);
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
}
