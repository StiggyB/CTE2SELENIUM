package de.haw_hamburg.ti.c2s.com.valvestar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class CreateNewSizingWizardPage extends ControlMenu {

    public CreateNewSizingWizardPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public SizingTypeAndMediumSelectionPage clickNextButton() {
        super.clickNextButton();
        return PageFactory.initElements(driver, SizingTypeAndMediumSelectionPage.class);
    }
    
    @Override
    public MainPage clickBackButton() {
        super.clickBackButton();
        return PageFactory.initElements(driver, MainPage.class);
    }

}
