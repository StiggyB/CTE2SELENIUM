package de.haw_hamburg.ti.c2s.com.valvestar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import de.haw_hamburg.ti.cte.xmlObjects.CteObject;
import de.haw_hamburg.ti.tools.Javascript;
import de.haw_hamburg.ti.tools.tree.Knot;
import de.haw_hamburg.ti.tools.tree.Tree;

/**
 * Represents the Control Menu of the VALVESTAR Website. The Header Menu as well
 * as other Menu Buttons which interact with the Site.
 * 
 * @author Benjamin
 * 
 */
public abstract class ControlMenu implements HomePage {

    @FindBy(css = "#ctl00_WorkspacePlaceHolder_NextButton")
    private WebElement                 nextButton;
    @FindBy(id = "ctl00_WorkspacePlaceHolder_CancelButton")
    private WebElement                 cancelButton;
    @FindBy(id = "ctl00_WorkspacePlaceHolder_BackButton")
    private WebElement                 backButton;
    @FindBy(tagName = "input")
    private List<WebElement>           inputs;
    @FindBy(tagName = "select")
    private List<WebElement>           selects;

    protected final Javascript         jscript;
    protected WebDriver                driver;
    private Integer[]                  marks;
    private ArrayList<Knot<CteObject>> nodeList;

    public int                         finishedProjects = 0;

    public ControlMenu(final WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        jscript = new Javascript(driver);
    }

    /**
     * 
     * @return List over elements or null in none found
     */
    protected List<WebElement> findSelectableElements(String element) {
        for (WebElement we : selects) {
            if (getWebElementId(we).contains(element)) {
                return new Select(we).getOptions();
            } else if (we.getText().contains(element)) {
                return new Select(we).getOptions();
            }
        }
        return null;
    }

    protected WebElement findInputElement(String element) {
        WebElement inputElement = null;
        for (WebElement we : inputs) {
            if ((inputElement = findByIdOrText(element, we)) != null) {
                return inputElement;
            }
        }
        return inputElement;
    }

    protected WebElement findSelect(String element) {
        WebElement selectableElement = null;
        for (WebElement we : selects) {
            if ((selectableElement = findByIdOrText(element, we)) != null) {
                return selectableElement;
            }
        }
        return selectableElement;
    }

    /**
     * @param element
     * @param we
     */
    private WebElement findByIdOrText(String element, WebElement we) {
        if (containsIgnoreCase(element, getWebElementId(we))) {
            return we;
        } else if (containsIgnoreCase(element, we.getText())) {
            return we;
        } else
            return null;
    }

    protected boolean containsIgnoreCase(String element, String text) {
        return Pattern.compile(Pattern.quote(element),
                Pattern.CASE_INSENSITIVE).matcher(text).find();
    }

    protected String getWebElementId(WebElement we) {
        return we.getAttribute("id");
    }

    /**
     * Next page.
     */
    @Override
    public HomePage clickNextButton() {
        if (nextButton.isEnabled()) {
            jscript.click("#ctl00_WorkspacePlaceHolder_NextButton");
        } else {
            System.err.println("ControlMenu-> NextButton not enabled");
        }
        return this;
    }

    /**
     * Previous page.
     * 
     * @return
     */
    @Override
    public HomePage clickBackButton() {
        jscript.click("#ctl00_WorkspacePlaceHolder_BackButton");
        return this;
    }

    /**
     * Cancel.
     */
    public HomePage clickCancelButton() {
        jscript.click("#ctl00_WorkspacePlaceHolder_CancelButton");
        return this;
    }

    public NewSizingCompletedPage clickFinishButton() {
        jscript.click("#ctl00_WorkspacePlaceHolder_UpperFinishButton");
        return PageFactory.initElements(driver, NewSizingCompletedPage.class);
    }

    protected String replaceUmlaut(String text) {
        return text.replaceAll("Ü", "UE");
    }

    /**
     * Click element.
     * 
     * @param id
     */
    protected void click(String id) {
        jscript.uncheck(id);
        jscript.click("#" + id);
    }

    public void setMarks(Integer[] marks) {
        this.marks = marks;
    }

    /**
     * @return the marks
     */
    protected Integer[] getMarks() {
        return marks;
    }

    /**
     * @return the nodeList
     */
    protected ArrayList<Knot<CteObject>> getNodeList() {
        return nodeList;
    }

    public void setTree(Tree<CteObject> tree) {
        nodeList = tree.getRootNode().getNodes();
    }

    /**
     * @param ie
     */
    protected void match(WebElement ie, String classification) {
        for (Knot<CteObject> k : nodeList) {
            if (k.getContent().getName().equalsIgnoreCase(classification)) {
                for (Knot<CteObject> ck : k.getNodes()) {
                    for (int j = 0; j < marks.length; j++) {
                        if (marks[j].equals(ck.getContent().getId())) {
                            System.out.println(classification + " - input: "
                                    + ck.getContent().getName());
                            input(getWebElementId(ie), ck.getContent()
                                    .getName());
                        }
                    }
                }
            }
        }
    }

    /**
     * 
     */
    private void input(String id, String value) {
        jscript.clear(id);
        jscript.input(id, value);
    }

    /**
     * @param idOrText
     * @param exactClassificationName
     * @return
     */
    protected HomePage findAndMatch(String idOrText,
            String exactClassificationName) {
        WebElement ie = null;
        if ((ie = findInputElement(idOrText)) == null) {
            System.out.println(exactClassificationName + ": "
                    + "returns null.");
            return null;
        }
        match(ie, exactClassificationName);
        System.out.println(exactClassificationName + ": " + "returns this.");
        return this;
    }

}
