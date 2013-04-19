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
        int trys = 0;
        click(cssSelector, trys);
    }

    private void click(String cssSelector, int trys) {
        trys++;
        if (trys > 50) {
            System.err.println(cssSelector + " clicking failed");
            try {
                throw new Exception();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("var x = $(\'" + cssSelector + "\');");
        stringBuilder.append("if (x != null) {");
        stringBuilder.append("x.click(); return true; } else {"
                + "return false;}");
        try {
            if (!((boolean) jse.executeScript(stringBuilder.toString()))) {
                click(cssSelector, trys);
            }
        } catch (WebDriverException e) {
            click(cssSelector, trys);
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
            System.err.println("Error selecting element.");
            e.printStackTrace();
            select(listId, value);
        }
    }

    public void clear(String id) {
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
                e1.printStackTrace();
            }
            clear(id);
        }
    }

    public void input(String id, String text) {
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
