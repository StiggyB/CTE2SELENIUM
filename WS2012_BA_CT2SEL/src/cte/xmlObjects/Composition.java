package cte.xmlObjects;


public class Composition extends CteObject {

    private static final long serialVersionUID = -8300766669210613807L;

    public Composition(String name, String id) {
		super(name, id);
	}
	
	@Override
	public String toString() {
		return "Composition [ getId()= "
				+ getId() + ", getName()=" + getName() + "]";
	}
	
}
