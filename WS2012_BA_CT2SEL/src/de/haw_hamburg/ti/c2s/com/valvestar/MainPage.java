package de.haw_hamburg.ti.c2s.com.valvestar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage {
    
    @FindBy(css = "#ctl00_ProjectExplorerPlaceHolder_ProjectExplorerPageControl_ExplorerTreeView_item_0_expcol > img")
    private static WebElement projectExplorer;

    public MainPage(final WebDriver driver) {
        // Check that we're on the right page.
        if (!"http://www.valvestar.com/UI/MainForm/MainForm.aspx"
                .equals(driver.getCurrentUrl())) {
            // Alternatively, we could navigate to the login page, perhaps
            // logging out first
            throw new IllegalStateException("This is not the main page");
        }
    }

    /**
     * Open the Project Explorer
     */
    public final void openProjectExplorer() {
        projectExplorer.click();
    }
    
}
