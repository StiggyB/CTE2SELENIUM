package de.haw_hamburg.ti.tools;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

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
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        StringBuilder stringBuilder = new StringBuilder();
        // stringBuilder.append("var x = $(\'" + cssSelector + "\');");
        // stringBuilder.append("x.checked = false");
        stringBuilder.append("if (document.getElementById(\"" + id
                + "\") != null) {");
        stringBuilder.append("document.getElementById(\"" + id
                + "\").checked = false; return true; } else {"
                + "return false}");
        try {
            if (!((boolean) jse.executeScript(stringBuilder.toString()))) {
                uncheck(id);
            }
        } catch (WebDriverException e) {
            uncheck(id);
        }
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
        stringBuilder.append("if (x != null) {");
        stringBuilder.append("x.click(); return true; } else {"
                + "return false;}");
        try {
            if (!((boolean) jse.executeScript(stringBuilder.toString()))) {
                click(cssSelector);
            }
        } catch (WebDriverException e) {
            click(cssSelector);
        }
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
        // + "obj[idx].value = \"" + value + "\";");
        // + "obj[idx].selected = true;");
        sb.append("else  obj[idx].removeAttribute(\'selected\');");
        sb.append("idx++;}");
        // Trigger onChange event
        sb.append("$(\"#" + listId + "\").trigger(\"change\");");
        // sb.append("for (var i = 0; i < dd.options.length; i++) { if (dd.options[i].text === \""
        // + value + "\") { dd.selectedIndex = i; break;}}");
        try {
            jse.executeScript(sb.toString());
        } catch (WebDriverException e) {
            select(listId, value);
        }
    }

    public void clear(String id) {
        System.out.println(this.getClass().getSimpleName() + "-> clear:" + id);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        StringBuilder sb = new StringBuilder();
        sb.append("document.getElementById(\"" + id + "\").value = \"\";");
        try {
            jse.executeScript(sb.toString());
        } catch (WebDriverException e) {
            e.printStackTrace();
            System.out.println("clear again and wait 2 sec");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            clear(id);
        }
    }

    public void input(String id, String text) {
        System.out.println(this.getClass().getSimpleName() + "-> input: " + id);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        StringBuilder sb = new StringBuilder();
        sb.append(" var TheTextBox = document.getElementById(\"" + id
                + "\");" + " TheTextBox.value = TheTextBox.value + \"" + text
                + "\";");
        try {
            jse.executeScript(sb.toString());
        } catch (WebDriverException e) {
            e.printStackTrace();
            input(id, text);
        }
    }

}
