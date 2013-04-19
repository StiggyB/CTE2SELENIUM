package de.haw_hamburg.ti.c2s.com.valvestar;

public interface HomePage {

    /**
     * Next page.
     * 
     * @return
     */
    public HomePage clickNextButton();

    /**
     * Previous page.
     * 
     * @return
     */
    public HomePage clickBackButton();

    /**
     * Finish sizing.
     * 
     * @return
     */
    public HomePage clickFinishButton();
    
}
