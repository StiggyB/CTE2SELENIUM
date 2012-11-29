package cte;


public class Composition extends CteObject {

	
	public Composition(String name, String id) {
		super(name, id);
	}
	
	@Override
	public String toString() {
		return "Composition [ getId()= "
				+ getId() + ", getName()=" + getName() + "]";
	}
	
}
