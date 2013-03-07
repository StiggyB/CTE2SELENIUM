package de.haw_hamburg.ti.c2s.com.valvestar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import de.haw_hamburg.ti.cte.xmlObjects.CteObject;
import de.haw_hamburg.ti.tools.Javascript;
import de.haw_hamburg.ti.tools.tree.Knot;
import de.haw_hamburg.ti.tools.tree.Tree;

public class SizingTypeAndMediumSelectionPage extends ControlMenu {

    private Javascript                        js;
    private WebDriver                         driver;

    @FindBy(tagName = "input")
    private List<WebElement>                  inputs;
    @FindBy(tagName = "select")
    private List<WebElement>                  selects;

    /**
     * TODO ALL THIS IN PROPERTIES CLASS?
     */
    private String                            mediumDDLid             = "ctl00_WorkspacePlaceHolder_ctl00_MediumDropDownList";

    private String                            sizingDDLid             = "ctl00_WorkspacePlaceHolder_ctl00_SizingStandardDropDownList";

    @FindBy(id = "ctl00_WorkspacePlaceHolder_ctl00_CdtpCheckBox")
    private WebElement                        CdtpCheckBox;
    boolean                                   cdtpChecked             = false;
    private String                            cdtpId                  = "ctl00_WorkspacePlaceHolder_ctl00_CdtpCheckBox";

    private String                            reactionForceAD2000A2Id = "ctl00_WorkspacePlaceHolder_ctl00_ReactionForceAd2000A2";
    private String                            reactionForceAPI520Id   = "ctl00_WorkspacePlaceHolder_ctl00_ReactionForceApi520";
    private String                            reactionForceIso4126Id  = "ctl00_WorkspacePlaceHolder_ctl00_ReactionForceIso4126";

    private String                            noiseAD2000A2Id         = "ctl00_WorkspacePlaceHolder_ctl00_NoiseAd2000A2";
    private String                            noiseAPI520Id           = "ctl00_WorkspacePlaceHolder_ctl00_NoiseApi520";

    private String                            pressureDropAD2000A2Id  = "ctl00_WorkspacePlaceHolder_ctl00_PDInletAd2000A2";
    private String                            pressureDropIso4126Id   = "ctl00_WorkspacePlaceHolder_ctl00_PDInletIso4126";             ;
    private String                            pressureDropNoneId      = "ctl00_WorkspacePlaceHolder_ctl00_PDInletNone";                ;

    private String                            backPressureAD2000A2Id  = "ctl00_WorkspacePlaceHolder_ctl00_BPOutletAd2000A2";
    private String                            backPressureIso4126Id   = "ctl00_WorkspacePlaceHolder_ctl00_BPOutletIso4126";            ;
    private String                            backPressureNoneId      = "ctl00_WorkspacePlaceHolder_ctl00_BPOutletNone";

    private String                            fireCaseApi520Id        = "ctl00_WorkspacePlaceHolder_ctl00_FirecaseApi520";
    private String                            fireCaseNoneId          = "ctl00_WorkspacePlaceHolder_ctl00_FirecaseNone";

    private String                            medium                  = "none";
    private boolean                           fireCase                = false;
    private Integer[]                         marks;
    private static ArrayList<Knot<CteObject>> nodeList;

    ArrayList<String>                         inputIds                = new ArrayList<>();
    ArrayList<String>                         selectIds               = new ArrayList<>();

    // Constructors
    public SizingTypeAndMediumSelectionPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        js = new Javascript(driver);
    }

    public void setMedium() {
        for (Knot<CteObject> k : nodeList) {
            if (k.getContent().getName().equalsIgnoreCase("medium")) {
                for (CteObject c : k.getContentList()) {
                    System.out.println(c.getName());
                    if (Arrays.asList(marks).contains(c.getId())) {
                        medium = c.getName();
                    }
                }
            }
        }
    }

    // Page Specific Methods
    /**
     * NOT WORKING PROPERLY
     * 
     * @param tree
     * @param marks
     * @return this
     */
    public SizingTypeAndMediumSelectionPage selectMedium() {
        List<WebElement> mediumDropDownListOptions = findSelectableElements("Medium");
        if (mediumDropDownListOptions == null) {
            return this;
        }
        for (WebElement webElement : mediumDropDownListOptions) {
            if (medium.equalsIgnoreCase(webElement.getText())) {
                js.select(mediumDDLid, webElement.getAttribute("value"));
                return this;
            }
        }
        return null;
    }

    /**
     * Selects a Sizing Standard from the Sizing Standard Drop Down List
     * 
     * @param marks
     * 
     * @param map
     *            - the HashMap in which the Standard is stored
     * @return
     */
    public SizingTypeAndMediumSelectionPage selectSizingStandard() {
        List<WebElement> sizingDropDownListOptions = findSelectableElements("Sizing");
        if (sizingDropDownListOptions == null) {
            return this;
        }
        for (WebElement webElement : sizingDropDownListOptions) {
            for (int i = 0; i < nodeList.size() - 1; i++) {
                if (nodeList.get(i).getContent().getName().equalsIgnoreCase(
                        replaceUmlaut(webElement.getText()))) {
                    for (int j = 0; j < marks.length; j++) {
                        if (marks[j].equals(nodeList.get(i).getContent()
                                .getId())) {
                            js.select(sizingDDLid, webElement
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
     * Clicks CDTP CheckBox if needed
     * 
     * @param tree
     * @param marks
     * @return this page
     */
    public SizingTypeAndMediumSelectionPage checkCdtpBox() {
        // List<WebElement> we = findInputElement("CdtpCheckBox");

        for (Knot<CteObject> k : nodeList) {
            if (k.getContent().getName().equalsIgnoreCase("CDTP Calculation")) {
                for (Knot<CteObject> ck : k.getChilds()) {
                    for (int j = 0; j < marks.length; j++) {
                        if (marks[j].equals(ck.getContent().getId())) {
                            if (Boolean.parseBoolean((ck.getContent()
                                    .getName()))) {
                                click(cdtpId);
                            } else {
                                js.uncheck(cdtpId);
                            }
                            return this;
                        }
                    }
                }
            }
        }
        return null;
    }

    private String getWebElementId(WebElement we) {
        return we.getAttribute("id");
    }

    /**
     * Click Reaction Force CheckBoxes if selected in testcase
     * 
     * @param markClassMap
     * @param markClassificationMap
     * @param markCompositionMap
     * @return this page
     */
    public SizingTypeAndMediumSelectionPage checkReactionForce() {
        // List<WebElement> we = findInputElement("Reaction");
        for (Knot<CteObject> k : nodeList) {
            if (k.getContent().getName().equalsIgnoreCase("Reaction Force")) {
                for (Knot<CteObject> ck : k.getChilds()) {
                    for (Knot<CteObject> cck : ck.getChilds()) {
                        if (Arrays.asList(marks).contains(
                                cck.getContent().getId())) {
                            if (!rfCheckHelper(ck.getContent().getName(), cck
                                    .getContent().getName())) {
                                return null;
                            }
                        }
                    }
                }
            }
        }
        return this;
    }

    /**
     * Click Noise-CheckBoxes if selected in testcase
     * 
     * @param markClassMap
     * @param markClassificationMap
     * @param markCompositionMap
     * @return this page
     */
    public SizingTypeAndMediumSelectionPage checkNoise() {
        // List<WebElement> we = findInputElement("Noise");
        for (Knot<CteObject> k : nodeList) {
            if (k.getContent().getName().equalsIgnoreCase("Noise")) {
                for (Knot<CteObject> ck : k.getChilds()) {
                    for (Knot<CteObject> cck : ck.getChilds()) {
                        if (Arrays.asList(marks).contains(
                                cck.getContent().getId())) {
                            if (!cnCheckHelper(ck.getContent().getName(), cck
                                    .getContent().getName())) {
                                return null;
                            } else {
                                return this;
                            }
                        }
                    }
                }
            }
        }
        return this;
    }

    /**
     * Selects the given Pressure drop Standard
     * 
     * @param markClassMap
     * @param markCompositionMap
     * @param markCompMap
     * @return this page
     */
    public SizingTypeAndMediumSelectionPage selectRadioPressureDrop() {
        // List<WebElement> we = findInputElement("PressureDrop");
        for (Knot<CteObject> k : nodeList) {
            if (k.getContent().getName().equalsIgnoreCase(
                    "Pressure drop inlet line")) {
                for (Knot<CteObject> ck : k.getChilds()) {
                    if (Arrays.asList(marks)
                            .contains(ck.getContent().getId())) {
                        System.out.println(ck.getContent().getName());
                        if (!radioHelper2(ck)) {
                            return null;
                        }
                    }
                }
            }
        }
        return this;
    }

    /**
     * @param ck
     */
    private boolean radioHelper2(Knot<CteObject> ck) {
        if (ck.getContent().getName().equalsIgnoreCase("AD2000:A2")) {
            return check("", pressureDropAD2000A2Id);
        } else if (ck.getContent().getName().equalsIgnoreCase(
                "ISO / CD 4126-9")) {
            return check("", pressureDropIso4126Id);
        } else if (ck.getContent().getName().equalsIgnoreCase("None")) {
            return check("", pressureDropNoneId);
        }
        return false;
    }

    /**
     * Selects the given Back pressure outlet pipe Standard
     * 
     * @param markClassMap
     * @param markCompMap
     * @return this page
     */
    public SizingTypeAndMediumSelectionPage selectRadioBackPressure() {
        for (Knot<CteObject> k : nodeList) {
            if (k.getContent().getName().equalsIgnoreCase(
                    "Built up back pressure outlet pipe")) {
                for (Knot<CteObject> ck : k.getChilds()) {
                    if (Arrays.asList(marks)
                            .contains(ck.getContent().getId())) {
                        if (!radioHelper(ck)) {
                            return null;
                        }
                    }
                }
            }
        }
        return this;
    }

    /**
     * @param ck
     */
    private boolean radioHelper(Knot<CteObject> ck) {
        if (ck.getContent().getName().equalsIgnoreCase("AD2000:A2")) {
            return check("", backPressureAD2000A2Id);
        } else if (ck.getContent().getName().equalsIgnoreCase(
                "ISO / CD 4126-9")) {
            return check("", backPressureIso4126Id);
        } else if (ck.getContent().getName().equalsIgnoreCase("None")) {
            return check("", backPressureNoneId);
        }
        return false;
    }

    /**
     * Select Fire Case Radio Button
     * 
     * @param markClassMap
     * @param markClassificationMap
     * @return this page
     */
    public SizingTypeAndMediumSelectionPage selectRadioFireCase() {
        for (Knot<CteObject> k : nodeList) {
            if (k.getContent().getName().equalsIgnoreCase("Fire Case")) {
                for (Knot<CteObject> ck : k.getChilds()) {
                    if (Arrays.asList(marks)
                            .contains(ck.getContent().getId())) {
                        if (ck.getContent().getName().equalsIgnoreCase(
                                "API 520")) {
                            System.out.println();
                            if (isMedium("Liquid", ".*[Ss]team")) {
                                return null;
                            } else {
                                check("", fireCaseApi520Id);
                                return this;
                            }
                        } else if (ck.getContent().getName()
                                .equalsIgnoreCase("None")) {
                            if (!isMedium("Liquid", "Wetted steam")) {
                                check("", fireCaseNoneId);
                            }
                            return this;
                        }
                    }
                }
            }
        }
        return null;
    }

    private boolean isMedium(String... s) {
        for (int i = 0; i < s.length; i++) {
            if (medium.matches(s[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public HomePage clickNextButton() {
        super.clickNextButton();
        if (medium.matches(".*[Ss]team")) {
            return PageFactory.initElements(driver,
                    ServiceConditionPage.class);
        } else {
            return PageFactory
                    .initElements(driver, MediumSelectionPage.class);
        }
    }

    // Helper Methods
    private void initElements() {
        for (WebElement we : inputs) {
            inputIds.add(we.getAttribute("id"));
        }
        for (WebElement we : selects) {
            selectIds.add(we.getAttribute("id"));
        }
    }

    /**
     * 
     * @return List over elements or null in none found
     */
    private List<WebElement> findSelectableElements(String element) {
        for (WebElement we : selects) {
            if (we.getAttribute("id").contains(element)) {
                return new Select(we).getOptions();
            }
        }
        return null;
    }

    /**
     * 
     * @return List over elements or null in none found
     */
    private List<WebElement> findInputElement(String element) {
        List<WebElement> elements = new ArrayList<>();
        for (WebElement we : inputs) {
            if (we.getAttribute("id").contains(element)) {
                elements.add(we);
            }
        }
        return elements;
    }

    private String replaceUmlaut(String text) {
        return text.replaceAll("Ü", "UE");
    }

    private boolean rfCheckHelper(String classification, String clazz) {
        if (classification.equalsIgnoreCase("AD2000:A2")) {
            return check(clazz, reactionForceAD2000A2Id);
        } else if (classification.equalsIgnoreCase("API 520")) {
            return check(clazz, reactionForceAPI520Id);
        } else if (classification.equalsIgnoreCase("ISO / CD 4126-9")) {
            return check(clazz, reactionForceIso4126Id);
        }
        return false;
    }

    private boolean cnCheckHelper(String classification, String clazz) {
        if (isMedium("Liquid")) {
            if (Boolean.parseBoolean(clazz)) {
                return false;
            } else {
                return true;
            }
        }
        if (classification.equalsIgnoreCase("AD2000:A2")) {
            return check(clazz, noiseAD2000A2Id);
        } else if (classification.equalsIgnoreCase("API 520")) {
            return check(clazz, noiseAPI520Id);
        }
        return false;
    }

    private boolean check(String clazz, String id) {
        if (Boolean.parseBoolean(clazz)) {
            if (!isMedium("Two-phase flow")) {
                click(id);
            } else
                return false;
        } else if (clazz.equalsIgnoreCase("false")) {
            if (!isMedium("Two-phase flow")) {
                js.uncheck(id);
            }
        } else {
            if (!isMedium("Two-phase flow")) {
                click(id);
            }
        }
        return true;
    }

    private void click(String id) {
        js.uncheck(id);
        js.click("#" + id);
    }

    /*
     * System.out.println(medium); if (Boolean.parseBoolean(clazz)) { if
     * (!isMedium("Two-phase flow")) { js.uncheck(id); js.click("#" + id); }
     * else return false; } else { if (!isMedium("Two-phase flow")) {
     * js.uncheck(id); } } return true;
     */

    public boolean getCdtpBoxState() {
        System.out.println("trying to get state of cdtp...");
        return CdtpCheckBox.isSelected();
    }

    public String getMedium() {
        return medium;
    }

    public boolean getFireCase() {
        return fireCase;
    }

    public void setMarks(Integer[] marks) {
        this.marks = marks;
    }

    public void setTree(Tree<CteObject> tree) {
        nodeList = tree.getRootNode().getNodes();
        initElements();
    }

}
