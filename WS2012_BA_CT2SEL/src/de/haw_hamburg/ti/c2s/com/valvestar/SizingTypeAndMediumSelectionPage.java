package de.haw_hamburg.ti.c2s.com.valvestar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.openqa.selenium.By;
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
    private String          noiseAPI520Id            = "ctl00_WorkspacePlaceHolder_ctl00_NoiseApi20";
    private String          noiseAPI520CSS           = "#" + noiseAPI520Id;

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
                System.out.println("Normal: " + webElement.getText()
                        + " - Spell checked: "
                        + replaceUmlaut(webElement.getText()));
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
        for (Entry<Integer, String> mcmEntry : markClassificationMap
                .entrySet()) {
            boolean checked = false;
            if (mcmEntry.getValue().equalsIgnoreCase("CDTP Calculation")) {
                if (checked)
                    return;
                if (Boolean.parseBoolean(markClassMap.get(mcmEntry.getKey()))) {
                    js.uncheck(CdtpCheckBoxId);
                    js.click(CdtpCheckBoxCSS);
                    checked = true;
                    System.out.println("CDTP ++ACTIVE++");
                } else {
                    System.out.println("CDTP --INACTIVE--");
                    js.uncheck(CdtpCheckBoxId);
                }
            }
        }
    }

    /**
     * Click Reaction Force CheckBoxes if needed
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
                                System.out.println("AD2000 ++ACTIVE++");
                            }
                        } else if (mclacaEntry.getValue().equalsIgnoreCase(
                                "API 520")) {
                            if (Boolean.parseBoolean(markClassMap
                                    .get(mclacaEntry.getKey()))) {
                                js.uncheck(reactionForceAPI520Id);
                                js.click(reactionForceAPI520CSS);
                                System.out.println("API520 ++ACTIVE++");
                            }
                        } else if (mclacaEntry.getValue().equalsIgnoreCase(
                                "ISO / CD 4126-9")) {
                            if (Boolean.parseBoolean(markClassMap
                                    .get(mclacaEntry.getKey()))) {
                                js.uncheck(reactionForceAPI520Id);
                                js.click(reactionForceIso4126CSS);
                                System.out.println("Iso4126 ++ACTIVE++");
                            }
                        }
                    }
                }
            }
        }
        // driver.findElement(By.id("ctl00_WorkspacePlaceHolder_ctl00_ReactionForceAd2000A2")).click();
    }

    /**
     * TODO: NYI - FUNZT NOCH NICHT RICHTIG
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
                                System.out.println("AD2000 ++ACTIVE++");
                            }
                        } else if (mclacaEntry.getValue().equalsIgnoreCase(
                                "API 520")) {
                            if (Boolean.parseBoolean(markClassMap
                                    .get(mclacaEntry.getKey()))) {
                                js.uncheck(noiseAPI520Id);
                                js.click(noiseAPI520CSS);
                                System.out.println("API520 ++ACTIVE++");
                            }
                        }

                    }
                }
            }
        }

    }

    /**
     * TODO: NYI
     * 
     * @param markClassMap
     * @param markCompMap
     */
    public void selectRadioPressureDrop(
            HashMap<Integer, String> markClassMap,
            HashMap<Integer, String> markClassificationMap) {
        // driver.findElement(By.id("ctl00_WorkspacePlaceHolder_ctl00_PDInletIso4126")).click();
    }

    /**
     * TODO: NYI
     * 
     * @param markClassMap
     * @param markCompMap
     */
    public void selectRadioBackPressure(
            HashMap<Integer, String> markClassMap,
            HashMap<Integer, String> markClassificationMap) {
        // driver.findElement(By.id("ctl00_WorkspacePlaceHolder_ctl00_BPOutletNone")).click();
    }
}
