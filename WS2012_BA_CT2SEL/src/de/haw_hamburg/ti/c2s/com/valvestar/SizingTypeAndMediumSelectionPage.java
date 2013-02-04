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

    private Javascript      js;

    @FindBy(css = "#ctl00_WorkspacePlaceHolder_ctl00_MediumDropDownList")
    private WebElement      mediumDropDownList;
    private String          mediumDDLid              = "ctl00_WorkspacePlaceHolder_ctl00_MediumDropDownList";

    @FindBy(
            css = "#ctl00_WorkspacePlaceHolder_ctl00_SizingStandardDropDownList")
    private WebElement      sizingDropDownList;
    private String          sizingDDLid              = "ctl00_WorkspacePlaceHolder_ctl00_SizingStandardDropDownList";

    @FindBy(id = "ctl00_WorkspacePlaceHolder_ctl00_CdtpCheckBox")
    private WebElement      CdtpCheckBox;
    private String          CdtpCheckBoxId           = "ctl00_WorkspacePlaceHolder_ctl00_CdtpCheckBox";
    private String          CdtpCheckBoxCSS          = "#" + CdtpCheckBoxId;

    @FindBy(id = "ctl00_WorkspacePlaceHolder_ctl00_ReactionForceAd2000A2")
    private WebElement      reactionForceAD2000A2;
    private String          reactionForceAD2000A2Id  = "ctl00_WorkspacePlaceHolder_ctl00_ReactionForceAd2000A2";
    private String          reactionForceAD2000A2CSS = "#"
                                                             + reactionForceAD2000A2Id;
    private String          reactionForceAPI520Id    = "ctl00_WorkspacePlaceHolder_ctl00_ReactionForceApi520";
    private String          reactionForceAPI520CSS   = "#"
                                                             + reactionForceAPI520Id;
    private String          reactionForceIso4126Id   = "ctl00_WorkspacePlaceHolder_ctl00_ReactionForceIso4126";
    private String          reactionForceIso4126CSS  = "#"
                                                             + reactionForceIso4126Id;
    private String          noiseAD2000A2Id          = "ctl00_WorkspacePlaceHolder_ctl00_NoiseAd2000A2";
    private String          noiseAD2000A2CSS         = "#" + noiseAD2000A2Id;
    private String          noiseAPI520Id            = "ctl00_WorkspacePlaceHolder_ctl00_NoiseApi520";
    private String          noiseAPI520CSS           = "#" + noiseAPI520Id;
    private String          pressureDropAD2000A2Id   = "ctl00_WorkspacePlaceHolder_ctl00_PDInletAd2000A2";
    private String          pressureDropAD2000A2CSS  = "#"
                                                             + pressureDropAD2000A2Id;
    private String          pressureDropIso4126Id    = "ctl00_WorkspacePlaceHolder_ctl00_PDInletIso4126";             ;
    private String          pressureDropIso4126CSS   = "#"
                                                             + pressureDropIso4126Id;
    private String          pressureDropNoneId       = "ctl00_WorkspacePlaceHolder_ctl00_PDInletNone";                ;
    private String          pressureDropNoneCSS      = "#"
                                                             + pressureDropNoneId;
    private String          backPressureAD2000A2Id   = "ctl00_WorkspacePlaceHolder_ctl00_BPOutletAd2000A2";
    private String          backPressureAD2000A2CSS  = "#"
                                                             + backPressureAD2000A2Id;
    private String          backPressureIso4126Id    = "ctl00_WorkspacePlaceHolder_ctl00_BPOutletIso4126";            ;
    private String          backPressureIso4126CSS   = "#"
                                                             + backPressureIso4126Id;
    private String          backPressureNoneId       = "ctl00_WorkspacePlaceHolder_ctl00_BPOutletNone";
    private String          backPressureNoneCSS      = "#"
                                                             + backPressureNoneId;
    private String          fireCaseApi520Id         = "ctl00_WorkspacePlaceHolder_ctl00_FirecaseApi520";
    private String          fireCaseApi520CSS        = "#" + fireCaseApi520Id;
    private String          fireCaseNoneId           = "ctl00_WorkspacePlaceHolder_ctl00_FirecaseNone";
    private String          fireCaseNoneCSS          = "#" + fireCaseNoneId;

    public SizingTypeAndMediumSelectionPage(WebDriver driver) {
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
        boolean selected = false;
        for (WebElement webElement : mediumDropDownListOptions) {
            if (selected) {
                return;
            }
            if (map.containsValue(webElement.getText())) {
                js.select(mediumDDLid, webElement.getAttribute("value"));
                selected = true;
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
        return text.replaceAll("�", "UE");
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
            boolean checked = false;
            if (mclacaEntry.getValue().equalsIgnoreCase("CDTP Calculation")) {
                if (checked)
                    return;
                if (Boolean.parseBoolean(markClassMap.get(mclacaEntry
                        .getKey()))) {
                    js.uncheck(CdtpCheckBoxId);
                    js.click(CdtpCheckBoxCSS);
                    checked = true;
                } else {
                    js.uncheck(CdtpCheckBoxId);
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
                                printTestCaseElement(mcomEntry.getValue(),
                                        mclacaEntry.getValue());
                            }
                        } else if (mclacaEntry.getValue().equalsIgnoreCase(
                                "API 520")) {
                            if (Boolean.parseBoolean(markClassMap
                                    .get(mclacaEntry.getKey()))) {
                                js.uncheck(reactionForceAPI520Id);
                                js.click(reactionForceAPI520CSS);
                                printTestCaseElement(mcomEntry.getValue(),
                                        mclacaEntry.getValue());
                            }
                        } else if (mclacaEntry.getValue().equalsIgnoreCase(
                                "ISO / CD 4126-9")) {
                            if (Boolean.parseBoolean(markClassMap
                                    .get(mclacaEntry.getKey()))) {
                                js.uncheck(reactionForceIso4126Id);
                                js.click(reactionForceIso4126CSS);
                                printTestCaseElement(mcomEntry.getValue(),
                                        mclacaEntry.getValue());
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
                                printTestCaseElement(mcomEntry.getValue(),
                                        mclacaEntry.getValue());
                            }
                        } else if (mclacaEntry.getValue().equalsIgnoreCase(
                                "API 520")) {
                            if (Boolean.parseBoolean(markClassMap
                                    .get(mclacaEntry.getKey()))) {
                                js.uncheck(noiseAPI520Id);
                                js.click(noiseAPI520CSS);
                                printTestCaseElement(mcomEntry.getValue(),
                                        mclacaEntry.getValue());
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
                            printTestCaseElement(mclacaEntry.getValue(),
                                    mclEntry.getValue());
                        } else if (mclEntry.getValue().equalsIgnoreCase(
                                "ISO / CD 4126-9")) {
                            js.uncheck(pressureDropIso4126Id);
                            js.click(pressureDropIso4126CSS);
                            printTestCaseElement(mclacaEntry.getValue(),
                                    mclEntry.getValue());
                        } else if (mclEntry.getValue().equalsIgnoreCase(
                                "None")) {
                            js.uncheck(pressureDropNoneId);
                            js.click(pressureDropNoneCSS);
                            printTestCaseElement(mclacaEntry.getValue(),
                                    mclEntry.getValue());
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
                            printTestCaseElement(mclacaEntry.getValue(),
                                    mclEntry.getValue());
                        } else if (mclEntry.getValue().equalsIgnoreCase(
                                "ISO / CD 4126-9")) {
                            js.uncheck(backPressureIso4126Id);
                            js.click(backPressureIso4126CSS);
                            printTestCaseElement(mclacaEntry.getValue(),
                                    mclEntry.getValue());
                        } else if (mclEntry.getValue().equalsIgnoreCase(
                                "None")) {
                            js.uncheck(backPressureNoneId);
                            js.click(backPressureNoneCSS);
                            printTestCaseElement(mclacaEntry.getValue(),
                                    mclEntry.getValue());
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
                            printTestCaseElement(mclacaEntry.getValue(),
                                    mclEntry.getValue());
                        } else if (mclEntry.getValue().equalsIgnoreCase(
                                "None")) {
                            js.uncheck(fireCaseNoneId);
                            js.click(fireCaseNoneCSS);
                            printTestCaseElement(mclacaEntry.getValue(),
                                    mclEntry.getValue());
                        }
                    }
                }
            }
        }
    }

    private void printTestCaseElement(String composition,
            String classification) {
        System.out.println("Selected: " + composition + " -- "
                + classification);
    }

}
