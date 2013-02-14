package de.haw_hamburg.ti.c2s.com.valvestar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import de.haw_hamburg.ti.tools.Javascript;

public class SizingTypeAndMediumSelectionPage extends ControlMenu {

    private Javascript js;
    private WebDriver driver;

    /**
     * TODO ALL THIS IN PROPERTIES CLASS?
     */
    @FindBy(css = "#ctl00_WorkspacePlaceHolder_ctl00_MediumDropDownList")
    @CacheLookup
    private WebElement mediumDropDownList;
    private String     mediumDDLid              = "ctl00_WorkspacePlaceHolder_ctl00_MediumDropDownList";

    @FindBy(css = "#ctl00_WorkspacePlaceHolder_ctl00_SizingStandardDropDownList")
    @CacheLookup
    private WebElement sizingDropDownList;
    private String     sizingDDLid              = "ctl00_WorkspacePlaceHolder_ctl00_SizingStandardDropDownList";

    @FindBy(id = "ctl00_WorkspacePlaceHolder_ctl00_CdtpCheckBox")
    private WebElement CdtpCheckBox;
    private String     CdtpCheckBoxId           = "ctl00_WorkspacePlaceHolder_ctl00_CdtpCheckBox";
    private String     CdtpCheckBoxCSS          = "#" + CdtpCheckBoxId;
    boolean cdtpChecked = false;

    @FindBy(id = "ctl00_WorkspacePlaceHolder_ctl00_ReactionForceAd2000A2")
    private WebElement reactionForceAD2000A2;
    private String     reactionForceAD2000A2Id  = "ctl00_WorkspacePlaceHolder_ctl00_ReactionForceAd2000A2";
    private String     reactionForceAD2000A2CSS = "#"
                                                        + reactionForceAD2000A2Id;
    private String     reactionForceAPI520Id    = "ctl00_WorkspacePlaceHolder_ctl00_ReactionForceApi520";
    private String     reactionForceAPI520CSS   = "#" + reactionForceAPI520Id;
    private String     reactionForceIso4126Id   = "ctl00_WorkspacePlaceHolder_ctl00_ReactionForceIso4126";
    private String     reactionForceIso4126CSS  = "#"
                                                        + reactionForceIso4126Id;
    private String     noiseAD2000A2Id          = "ctl00_WorkspacePlaceHolder_ctl00_NoiseAd2000A2";
    private String     noiseAD2000A2CSS         = "#" + noiseAD2000A2Id;
    private String     noiseAPI520Id            = "ctl00_WorkspacePlaceHolder_ctl00_NoiseApi520";
    private String     noiseAPI520CSS           = "#" + noiseAPI520Id;
    private String     pressureDropAD2000A2Id   = "ctl00_WorkspacePlaceHolder_ctl00_PDInletAd2000A2";
    private String     pressureDropAD2000A2CSS  = "#"
                                                        + pressureDropAD2000A2Id;
    private String     pressureDropIso4126Id    = "ctl00_WorkspacePlaceHolder_ctl00_PDInletIso4126";             ;
    private String     pressureDropIso4126CSS   = "#" + pressureDropIso4126Id;
    private String     pressureDropNoneId       = "ctl00_WorkspacePlaceHolder_ctl00_PDInletNone";                ;
    private String     pressureDropNoneCSS      = "#" + pressureDropNoneId;
    private String     backPressureAD2000A2Id   = "ctl00_WorkspacePlaceHolder_ctl00_BPOutletAd2000A2";
    private String     backPressureAD2000A2CSS  = "#"
                                                        + backPressureAD2000A2Id;
    private String     backPressureIso4126Id    = "ctl00_WorkspacePlaceHolder_ctl00_BPOutletIso4126";            ;
    private String     backPressureIso4126CSS   = "#" + backPressureIso4126Id;
    private String     backPressureNoneId       = "ctl00_WorkspacePlaceHolder_ctl00_BPOutletNone";
    private String     backPressureNoneCSS      = "#" + backPressureNoneId;
    private String     fireCaseApi520Id         = "ctl00_WorkspacePlaceHolder_ctl00_FirecaseApi520";
    private String     fireCaseApi520CSS        = "#" + fireCaseApi520Id;
    private String     fireCaseNoneId           = "ctl00_WorkspacePlaceHolder_ctl00_FirecaseNone";
    private String     fireCaseNoneCSS          = "#" + fireCaseNoneId;
    
    private String medium;

    public SizingTypeAndMediumSelectionPage(WebDriver driver) {
        super(driver);
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
    public SizingTypeAndMediumSelectionPage selectMedium(HashMap<Integer, String> map) {
        List<WebElement> mediumDropDownListOptions = new ArrayList<>();
        mediumDropDownListOptions = new Select(mediumDropDownList)
                .getOptions();
        String tmp;
        for (WebElement webElement : mediumDropDownListOptions) {
            if (map.containsValue(tmp = webElement.getText())) {
                medium = tmp;
                js.select(mediumDDLid, webElement.getAttribute("value"));
                return this;
            }
        }
        return this;
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
        boolean selected = false;
        for (WebElement webElement : sizingDropDownListOptions) {
            if (selected) {
                return;
            }
            if (map.containsValue(replaceUmlaut(webElement.getText()))) {
                js.select(sizingDDLid, webElement.getAttribute("value"));
                selected = true;
            }
        }
    }

    private String replaceUmlaut(String text) {
        return text.replaceAll("Ü", "UE");
    }

    /**
     * Clicks CDTP CheckBox if needed
     * 
     * @param markClassMap
     * @param markCompMap
     */
    public void checkCdtpBox(HashMap<Integer, String> markClassMap,
            HashMap<Integer, String> markClassificationMap) {
        for (Entry<Integer, String> mclacaEntry : markClassificationMap
                .entrySet()) {
            if (mclacaEntry.getValue().equalsIgnoreCase("CDTP Calculation")) {
                if (cdtpChecked)
                    return;
                if (Boolean.parseBoolean(markClassMap.get(mclacaEntry
                        .getKey()))) {
                    js.uncheck(CdtpCheckBoxId);
                    js.click(CdtpCheckBoxCSS);
                    cdtpChecked = true;
                } else {
                    js.uncheck(CdtpCheckBoxId);
                    cdtpChecked = false;
                }
            }
        }
    }

    /**
     * Click Reaction Force CheckBoxes if selected in testcase
     * 
     * @param markClassMap
     * @param markClassificationMap
     * @param markCompositionMap
     */
    public void checkReactionForce(HashMap<Integer, String> markClassMap,
            HashMap<Integer, String> markClassificationMap,
            HashMap<Integer, String> markCompositionMap) {

        js.uncheck(reactionForceAD2000A2Id);
        js.uncheck(reactionForceAPI520Id);
        js.uncheck(reactionForceIso4126Id);

        for (Entry<Integer, String> mcomEntry : markCompositionMap.entrySet()) {
            if (mcomEntry.getValue().equalsIgnoreCase("Reaction Force")) {
                for (Entry<Integer, String> mclacaEntry : markClassificationMap
                        .entrySet()) {
                    if (mcomEntry.getKey().equals(mclacaEntry.getKey())) {

                        if (mclacaEntry.getValue().equalsIgnoreCase(
                                "AD2000:A2")) {
                            if (Boolean.parseBoolean(markClassMap
                                    .get(mclacaEntry.getKey()))) {
                                js.uncheck(reactionForceAD2000A2Id);
                                js.click(reactionForceAD2000A2CSS);
                            }
                        } else if (mclacaEntry.getValue().equalsIgnoreCase(
                                "API 520")) {
                            if (Boolean.parseBoolean(markClassMap
                                    .get(mclacaEntry.getKey()))) {
                                js.uncheck(reactionForceAPI520Id);
                                js.click(reactionForceAPI520CSS);
                            }
                        } else if (mclacaEntry.getValue().equalsIgnoreCase(
                                "ISO / CD 4126-9")) {
                            if (Boolean.parseBoolean(markClassMap
                                    .get(mclacaEntry.getKey()))) {
                                js.uncheck(reactionForceIso4126Id);
                                js.click(reactionForceIso4126CSS);
                            }
                        }
                    }
                }
            }
        }
        // driver.findElement(By.id("ctl00_WorkspacePlaceHolder_ctl00_ReactionForceAd2000A2")).click();
    }

    /**
     * Click Noise-CheckBoxes if selected in testcase
     * 
     * @param markClassMap
     * @param markClassificationMap
     * @param markCompositionMap
     */
    public void checkNoise(HashMap<Integer, String> markClassMap,
            HashMap<Integer, String> markClassificationMap,
            HashMap<Integer, String> markCompositionMap) {
        for (Entry<Integer, String> mcomEntry : markCompositionMap.entrySet()) {
            if (mcomEntry.getValue().equalsIgnoreCase("Noise")) {
                for (Entry<Integer, String> mclacaEntry : markClassificationMap
                        .entrySet()) {
                    if (mcomEntry.getKey().equals(mclacaEntry.getKey())) {

                        if (mclacaEntry.getValue().equalsIgnoreCase(
                                "AD2000:A2")) {
                            if (Boolean.parseBoolean(markClassMap
                                    .get(mclacaEntry.getKey()))) {
                                js.uncheck(noiseAD2000A2Id);
                                js.click(noiseAD2000A2CSS);
                            }
                        } else if (mclacaEntry.getValue().equalsIgnoreCase(
                                "API 520")) {
                            if (Boolean.parseBoolean(markClassMap
                                    .get(mclacaEntry.getKey()))) {
                                js.uncheck(noiseAPI520Id);
                                js.click(noiseAPI520CSS);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Selects the given Pressure drop Standard
     * 
     * @param markClassMap
     * @param markCompositionMap
     * @param markCompMap
     */
    public void selectRadioPressureDrop(
            HashMap<Integer, String> markClassMap,
            HashMap<Integer, String> markClassificationMap) {
        for (Entry<Integer, String> mclacaEntry : markClassificationMap
                .entrySet()) {
            if (mclacaEntry.getValue().equalsIgnoreCase(
                    "Pressure drop inlet line")) {
                for (Entry<Integer, String> mclEntry : markClassMap
                        .entrySet()) {
                    if (mclEntry.getKey().equals(mclacaEntry.getKey())) {
                        if (mclEntry.getValue().equalsIgnoreCase("AD2000:A2")) {
                            js.uncheck(pressureDropAD2000A2Id);
                            js.click(pressureDropAD2000A2CSS);
                        } else if (mclEntry.getValue().equalsIgnoreCase(
                                "ISO / CD 4126-9")) {
                            js.uncheck(pressureDropIso4126Id);
                            js.click(pressureDropIso4126CSS);
                        } else if (mclEntry.getValue().equalsIgnoreCase(
                                "None")) {
                            js.uncheck(pressureDropNoneId);
                            js.click(pressureDropNoneCSS);
                        }
                    }
                }
            }
        }
    }

    /**
     * Selects the given Back pressure outlet pipe Standard
     * 
     * @param markClassMap
     * @param markCompMap
     */
    public void selectRadioBackPressure(
            HashMap<Integer, String> markClassMap,
            HashMap<Integer, String> markClassificationMap) {
        for (Entry<Integer, String> mclacaEntry : markClassificationMap
                .entrySet()) {
            if (mclacaEntry.getValue().equalsIgnoreCase(
                    "Built up back pressure outlet pipe")) {
                for (Entry<Integer, String> mclEntry : markClassMap
                        .entrySet()) {
                    if (mclEntry.getKey().equals(mclacaEntry.getKey())) {
                        if (mclEntry.getValue().equalsIgnoreCase("AD2000:A2")) {
                            js.uncheck(backPressureAD2000A2Id);
                            js.click(backPressureAD2000A2CSS);
                        } else if (mclEntry.getValue().equalsIgnoreCase(
                                "ISO / CD 4126-9")) {
                            js.uncheck(backPressureIso4126Id);
                            js.click(backPressureIso4126CSS);
                        } else if (mclEntry.getValue().equalsIgnoreCase(
                                "None")) {
                            js.uncheck(backPressureNoneId);
                            js.click(backPressureNoneCSS);
                        }
                    }
                }
            }
        }
    }

    /**
     * Select Fire Case Radio Button
     * 
     * @param markClassMap
     * @param markClassificationMap
     */
    public void selectRadioFireCase(HashMap<Integer, String> markClassMap,
            HashMap<Integer, String> markClassificationMap) {
        for (Entry<Integer, String> mclacaEntry : markClassificationMap
                .entrySet()) {
            if (mclacaEntry.getValue().equalsIgnoreCase("Fire Case")) {
                for (Entry<Integer, String> mclEntry : markClassMap
                        .entrySet()) {
                    if (mclEntry.getKey().equals(mclacaEntry.getKey())) {
                        if (mclEntry.getValue().equalsIgnoreCase("API520")) {
                            js.uncheck(fireCaseApi520Id);
                            js.click(fireCaseApi520CSS);
                        } else if (mclEntry.getValue().equalsIgnoreCase(
                                "None")) {
                            js.uncheck(fireCaseNoneId);
                            js.click(fireCaseNoneCSS);
                        }
                    }
                }
            }
        }
    }

    public HomePage clickNextButton() {
        super.clickNextButton();
        if (medium.matches(".*[Ss]team")) {
            return PageFactory.initElements(driver, ServiceConditionPage.class);
        } else {
            return PageFactory.initElements(driver, MediumSelectionPage.class);
        }
    }
    
    public boolean getCdtpBoxState() {
        System.out.println("trying to get state of cdtp...");
        return CdtpCheckBox.isSelected();
    }
    
    public String getMedium() {
        return medium;
    }
    
    
}
