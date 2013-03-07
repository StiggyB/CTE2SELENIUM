package de.haw_hamburg.ti.c2s.com.valvestar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends ControlMenu {

    @FindBy(css = "#ctl00_ProjectExplorerPlaceHolder_ProjectExplorerPageControl_ExplorerTreeView_item_0_expcol > img")
    private static WebElement projectExplorer;
    @FindBy(id = "ctl00_HeaderPlaceHolder_HeaderPageControl_HeaderMenu_9")
    private WebElement      sizing;
    @FindBy(css = "img[alt=\"Add new sizing...\"]")
    private WebElement      addSizing;
    private final WebDriver   driver;
    private transient boolean sizingMenuOpen = false;

    public MainPage(final WebDriver driver) {
        super(driver);
        this.driver = driver;
        // Check that we're on the right page.
        if (!"http://www.valvestar.com/UI/MainForm/MainForm.aspx"
                .equals(driver.getCurrentUrl())) {
            // Alternatively, we could navigate to the login page, perhaps
            // logging out first
            throw new IllegalStateException("This is not the main page");
        }
    }

    /**
     * Open the Sizing Menu to add a new Sizing.
     */
    private void openSizingMenu() {
        final Actions builder = new Actions(driver);
        driver.navigate().refresh();
        builder.moveToElement(sizing).build().perform();
        sizingMenuOpen = true;
    }

    /**
     * Add a new Sizing.
     * 
     * @return
     */
    public CreateNewSizingWizardPage addNewSizing() {
        openSizingMenu();
        if (sizingMenuOpen) {
            addSizing.click();
        } else {
            throw new IllegalStateException("Sizing Menu not opened");
        }
        return PageFactory.initElements(driver,
                CreateNewSizingWizardPage.class);
    }

    /**
     * Open the Project Explorer
     */
    public final void openProjectExplorer() {
        projectExplorer.click();
    }

}
