package de.haw_hamburg.ti.c2s.com.valvestar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends ControlMenu {

    @FindBy(id = "ctl00_AgreementCheckBox")
    private WebElement      agreementCheckBox;
    @FindBy(id = "ctl00_WorkspacePlaceHolder_LoginTextBox")
    private WebElement      loginTextBox;
    @FindBy(id = "ctl00_WorkspacePlaceHolder_PasswordTextBox")
    private WebElement      passwordTextBox;
    @FindBy(id = "ctl00_WorkspacePlaceHolder_SubmitButton")
    private WebElement      submitButton;

    public LoginPage(WebDriver driver) {
        super(driver);

        // Check that we're on the right page.
        if (!"http://www.valvestar.com/UI/MainForm/Workspace/Authentication/Authentication.aspx"
                .equals(driver.getCurrentUrl())) {
            // Alternatively, we could navigate to the login page, perhaps
            // logging out first
            throw new IllegalStateException("This is not the login page");
        }
    }

    // Conceptually, the login page offers the user the service of being able to
    // "log into"
    // the application using a user name and password.
    public MainPage loginAs(String username, String password) {
        // This is the only place in the test code that "knows" how to enter
        // these details
        agreementCheckBox.click();
        loginTextBox.clear();
        loginTextBox.sendKeys(username);
        passwordTextBox.clear();
        passwordTextBox.sendKeys(password);
        submitButton.click();

        return driver
                .findElement(By.cssSelector("BODY"))
                .getText()
                .matches(
                        "^[\\s\\S]*Authentication information is incorrect: you cannot log on now\\.[\\s\\S]*$") ? null
                : PageFactory.initElements(driver, MainPage.class);
    }

}
