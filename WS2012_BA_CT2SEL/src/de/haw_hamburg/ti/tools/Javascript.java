package de.haw_hamburg.ti.tools;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class Javascript {

    private WebDriver driver;
    
    public Javascript(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Uncheck RadioButton or CheckBox
     * 
     * @param cssSelector
     */
    public void uncheck(String cssSelector) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("var x = $(\'" + cssSelector + "\');");
        stringBuilder.append("x.checked = false");
        js.executeScript(stringBuilder.toString());
    }
    
    public void click(String cssSelector) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("var x = $(\'" + cssSelector + "\');");
        stringBuilder.append("x.click();");
        js.executeScript(stringBuilder.toString());
    }
    
    public void select(String listId, String value) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        StringBuilder sb = new StringBuilder();
        sb.append("var element = document.getElementById(\"" + listId + "\");");
        sb.append("element.value = \"" + value + "\";");
        js.executeScript(sb.toString());
    }
    
}
