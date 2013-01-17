package de.haw_hamburg.ti.cte.xmlObjects;


public class Composition extends CteObject {

    private static final long serialVersionUID = -8300766669210613807L;
    private Classification[] classifications;

    public Composition(String name, String id) {
		super(name, id);
	}
	
	public Classification[] getClassifications() {
        return classifications;
    }

    public void setClassifications(Classification[] classifications) {
        this.classifications = classifications;
    }

    @Override
	public String toString() {
		return "Composition [ getId()= "
				+ getId() + ", getName()=" + getName() + "]";
	}
	
}
