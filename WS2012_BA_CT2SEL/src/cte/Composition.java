package cte;

import java.util.ArrayList;

public class Composition extends CteObject {

	ArrayList<Classification> classifications;
	
	public Composition(String name, String id, ArrayList<Classification> classifications) {
		super(name, id);
		this.classifications = classifications;
	}
	
	public ArrayList<Classification> getClassifications() {
		return classifications;
	}

	@Override
	public String toString() {
		return "Composition [classifications=" + classifications + ", getId()="
				+ getId() + ", getName()=" + getName() + "]";
	}
	
}
