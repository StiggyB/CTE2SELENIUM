package de.haw_hamburg.ti.c2s.com.valvestar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage {
    
    private final WebDriver driver;
    @FindBy(css = "#ctl00_ProjectExplorerPlaceHolder_ProjectExplorerPageControl_ExplorerTreeView_item_0_expcol > img")
    private WebElement projectExplorer;

    public MainPage(WebDriver driver) {
        this.driver = driver;

        // Check that we're on the right page.
        if (!"http://www.valvestar.com/UI/MainForm/MainForm.aspx"
                .equals(driver.getCurrentUrl())) {
            // Alternatively, we could navigate to the login page, perhaps
            // logging out first
            throw new IllegalStateException("This is not the main page");
        }
    }

    public void openProjectExplorer() {
        projectExplorer.click();
    }
    
}
