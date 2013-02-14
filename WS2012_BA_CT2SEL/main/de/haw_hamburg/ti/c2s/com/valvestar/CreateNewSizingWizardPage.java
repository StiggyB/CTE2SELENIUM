package de.haw_hamburg.ti.c2s.com.valvestar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class CreateNewSizingWizardPage extends ControlMenu {

    private WebDriver driver;

    public CreateNewSizingWizardPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public SizingTypeAndMediumSelectionPage clickNextButton() {
        super.clickNextButton();
        return PageFactory.initElements(driver, SizingTypeAndMediumSelectionPage.class);
    }
    
    public MainPage clickBackButton() {
        super.clickBackButton();
        return PageFactory.initElements(driver, MainPage.class);
    }

}
