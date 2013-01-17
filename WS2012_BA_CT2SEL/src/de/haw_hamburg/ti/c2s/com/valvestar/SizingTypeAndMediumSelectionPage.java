package de.haw_hamburg.ti.c2s.com.valvestar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import de.haw_hamburg.ti.tools.Javascript;

public class SizingTypeAndMediumSelectionPage {

    private final WebDriver driver;
    private Javascript      js;
    @FindBy(css = "#ctl00_WorkspacePlaceHolder_ctl00_MediumDropDownList")
    private WebElement      mediumDropDownList;
    private String          mediumDDLid     = "ctl00_WorkspacePlaceHolder_ctl00_MediumDropDownList";
    @FindBy(
            css = "#ctl00_WorkspacePlaceHolder_ctl00_SizingStandardDropDownList")
    private WebElement      sizingDropDownList;
    private String          sizingDDLid     = "ctl00_WorkspacePlaceHolder_ctl00_MediumDropDownList";
    @FindBy(id = "ctl00_WorkspacePlaceHolder_ctl00_CdtpCheckBox")
    private WebElement      CdtpCheckBox;
    private String          CdtpCheckBoxCSS = "#ctl00_WorkspacePlaceHolder_ctl00_CdtpCheckBox";

    public SizingTypeAndMediumSelectionPage(WebDriver driver) {
        this.driver = driver;
        js = new Javascript(driver);
    }

    // TODO: alle folgende Methoden: test auf falschen Inhalt der maps

    /**
     * Selects a Medium from the Medium Drop Down List
     * 
     * @param map
     *            - the HashMap in which the Medium is stored
     */
    public void selectMedium(HashMap<Integer, String> map) {
        List<WebElement> mediumDropDownListOptions = new ArrayList<>();
        mediumDropDownListOptions = new Select(mediumDropDownList)
                .getOptions();
        for (WebElement webElement : mediumDropDownListOptions) {
            if (map.containsValue(webElement.getText())) {
                js.select(mediumDDLid, webElement.getAttribute("value"));
            }
        }
    }

    /**
     * Selects a Sizing Standard from the Sizing Standard Drop Down List
     * 
     * @param map
     *            - the HashMap in which the Standard is stored
     */
    public void selectSizingStandard(HashMap<Integer, String> map) {
        List<WebElement> sizingDropDownListOptions = new ArrayList<>();
        sizingDropDownListOptions = new Select(sizingDropDownList)
                .getOptions();
        for (WebElement webElement : sizingDropDownListOptions) {
            if (map.containsValue(webElement.getText())) {
                js.select(sizingDDLid, webElement.getAttribute("value"));
            }
        }
    }

    /**
     * TODO: NYI
     * 
     * @param markClassMap
     * @param markCompMap
     */
    public void checkCdtpBox(HashMap<Integer, String> markClassMap,
            HashMap<Integer, String> markClassificationMap) {
        for (Entry<Integer, String> mcmEntry : markClassificationMap.entrySet()) {
            boolean hasCdtp = false;
            if (mcmEntry.getValue().equalsIgnoreCase("CDTP Calculation")) {
                hasCdtp  = true;
                if (markClassMap.get(mcmEntry.getKey()).equalsIgnoreCase("true")) {
//                    CdtpCheckBox.clear();
                    js.uncheck(CdtpCheckBoxCSS);
                    js.click(CdtpCheckBoxCSS);
                } else {
                    js.uncheck(CdtpCheckBoxCSS);
                }
            } else if (!hasCdtp){
                js.uncheck(CdtpCheckBoxCSS);
            }
        }
    }

    /**
     * TODO: NYI
     * 
     * @param markClassMap
     * @param markClassificationMap
     * @param markCompositionMap 
     */
    public void checkReactionForce(HashMap<Integer, String> markClassMap,
            HashMap<Integer, String> markClassificationMap, HashMap<Integer,String> markCompositionMap) {
        for (Entry<Integer, String> mcomEntry : markCompositionMap.entrySet()) {
            if (mcomEntry.getValue().equalsIgnoreCase("Reaction Force")) {
                if (markCompositionMap.get(mcomEntry.getKey()).equalsIgnoreCase("")) {
                    
                }
            }
        }
        // driver.findElement(By.id("ctl00_WorkspacePlaceHolder_ctl00_ReactionForceAd2000A2")).click();
    }

    /**
     * TODO: NYI
     * 
     * @param markClassMap
     * @param markClassificationMap
     * @param markCompositionMap
     */
    public void checkNoise(HashMap<Integer, String> markClassMap,
            HashMap<Integer, String> markClassificationMap,
            HashMap<Integer, String> markCompositionMap) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * TODO: NYI
     * 
     * @param markClassMap
     * @param markCompMap
     */
    public void selectRadioPressureDrop(HashMap<Integer, String> markClassMap,
            HashMap<Integer, String> markClassificationMap) {
        // driver.findElement(By.id("ctl00_WorkspacePlaceHolder_ctl00_PDInletIso4126")).click();
    }

    /**
     * TODO: NYI
     * 
     * @param markClassMap
     * @param markCompMap
     */
    public void selectRadioBackPressure(HashMap<Integer, String> markClassMap,
            HashMap<Integer, String> markClassificationMap) {
        // driver.findElement(By.id("ctl00_WorkspacePlaceHolder_ctl00_BPOutletNone")).click();
    }


}
