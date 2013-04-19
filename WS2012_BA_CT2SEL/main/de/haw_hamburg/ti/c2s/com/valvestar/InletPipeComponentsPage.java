package de.haw_hamburg.ti.c2s.com.valvestar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import de.haw_hamburg.ti.cte.xmlObjects.CteObject;
import de.haw_hamburg.ti.tools.tree.Knot;

public class InletPipeComponentsPage extends ControlMenu {

    public InletPipeComponentsPage(WebDriver driver) {
        super(driver);
    }

    public InletPipeComponentsPage selectComponent() {
        WebElement s = findSelect("Component");
        if (s == null) {
            return null;
        }
        for (WebElement webElement : (new Select(s)).getOptions()) {
            for (Knot<CteObject> k : getNodeList()) {
                System.out.println(webElement.getText() + ".eq("
                        + k.getContent().getName() + ")");
                if (containsIgnoreCase(k.getContent().getName(),
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
        return this;
    }

    public InletPipeComponentsPage inputCount() {
        return (InletPipeComponentsPage) findAndMatch("count", "count");
    }

    public InletPipeComponentsPage inputLengthOfInletPipe() {
        return (InletPipeComponentsPage) findAndMatch("lenght", "Length of inlet pipe");
    }

    public InletPipeComponentsPage inputEquivalentPipeRoughness() {
        return (InletPipeComponentsPage) findAndMatch("roughness", "Equivalent Pipe Roughness");
    }

    public InletPipeComponentsPage inputAllowedPressureLoss() {
        return (InletPipeComponentsPage) findAndMatch("PressureLoss", "Allowed Pressure Loss based on p-paf");
    }

    public InletPipeComponentsPage clickSelect() {
        click("ctl00_WorkspacePlaceHolder_ctl00_SelectButton");
//        click(getWebElementId(findInputElement("select")));
        return this;
    }

    public InletPipeComponentsPage clickDelete() {
        click(getWebElementId(findInputElement("delete")));
        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.haw_hamburg.ti.c2s.com.valvestar.ControlMenu#clickNextButton()
     */
    @Override
    public InletPipePage clickNextButton() {
        super.clickNextButton();
        return PageFactory.initElements(driver, InletPipePage.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.haw_hamburg.ti.c2s.com.valvestar.ControlMenu#clickBackButton()
     */
    @Override
    public HomePage clickBackButton() {
        super.clickBackButton();
        return PageFactory.initElements(driver, ValveFinderPage.class);
    }

}
