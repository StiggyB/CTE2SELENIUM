package de.haw_hamburg.ti.c2s.com.valvestar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class ServiceConditionPage extends ControlMenu {

    private WebDriver driver;
    private String medium;

    public ServiceConditionPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public HomePage clickBackButton() {
        super.clickBackButton();
        if (medium.matches(".*[Ss]team")) {
            return PageFactory.initElements(driver,
                    SizingTypeAndMediumSelectionPage.class);
        } else {
            return PageFactory.initElements(driver,
                    MediumSelectionPage.class);
        }
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

}
