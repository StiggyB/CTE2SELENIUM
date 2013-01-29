package de.haw_hamburg.ti.tools;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class Javascript {

    private WebDriver driver;

    public Javascript(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Uncheck RadioButton or CheckBox by id
     * 
     * @param id
     */
    public void uncheck(String id) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        StringBuilder stringBuilder = new StringBuilder();
        // stringBuilder.append("var x = $(\'" + cssSelector + "\');");
        // stringBuilder.append("x.checked = false");
        stringBuilder.append("document.getElementById(\"" + id
                + "\").checked = false;");
        js.executeScript(stringBuilder.toString());
    }

    /**
     * Click Button or CheckBox by CSS
     * 
     * @param cssSelector
     */
    public void click(String cssSelector) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("var x = $(\'" + cssSelector + "\');");
        stringBuilder.append("x.click();");
        jse.executeScript(stringBuilder.toString());
    }

    public void select(String listId, String value) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        StringBuilder sb = new StringBuilder();
        sb.append("var obj = document.getElementById(\"" + listId + "\");");
        sb.append("obj.value = \"" + value + "\";");
        /**
         * TODO: Element has do be clicked or sth.
         */
        sb.append("var idx=0;");
        sb.append("while(obj[idx]) {");
        sb.append("if(obj[idx].value==\"" + value
                + "\") obj[idx].setAttribute(\'selected\',true);");
//                + "obj[idx].value = \"" + value + "\";");
//                + "obj[idx].selected = true;");
        sb.append("else  obj[idx].removeAttribute(\'selected\');");
        sb.append("idx++;}");
        // Trigger onChange event
        sb.append("$(\"#" + listId +"\").trigger(\"change\");");
        // sb.append("for (var i = 0; i < dd.options.length; i++) { if (dd.options[i].text === \""
        // + value + "\") { dd.selectedIndex = i; break;}}");
        jse.executeScript(sb.toString());
    }

}
