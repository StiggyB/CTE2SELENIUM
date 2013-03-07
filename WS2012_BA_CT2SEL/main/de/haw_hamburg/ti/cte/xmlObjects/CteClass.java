package de.haw_hamburg.ti.cte.xmlObjects;


public class CteClass extends CteObject {

    /**
     * 
     */
    private static final long serialVersionUID = -1781832065234380018L;

    public CteClass(String name, String id) {
        super(name, id);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CteClass [getId()=" + getId() + ", getName()=" + getName()
                + "]";
    }

    
}
