package cte;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class CteObject {

	private String id;
	private String name;
	private Pattern idPattern = Pattern.compile("\\d+");
	
	public CteObject(String name, String id) {
		this.name = name;
		this.id = id;
	}
	
	public Integer getId() {
		Matcher idMatcher = idPattern.matcher(id);
		idMatcher.find();
		Integer intId;
		intId = Integer.parseInt(idMatcher.group());
		return intId;
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
