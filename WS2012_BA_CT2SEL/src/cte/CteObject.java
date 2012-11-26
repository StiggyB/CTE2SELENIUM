package cte;

public abstract class CteObject {

	private String id;
	private String name;
	
	public CteObject(String name, String id) {
		this.name = name;
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return "id=" + id + " name=" + name;
	}

}
