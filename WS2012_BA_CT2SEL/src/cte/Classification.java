package cte;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Classification extends CteObject {

	private String[] testData;
	private Pattern idPattern = Pattern.compile("\\d+");
	
	public Classification(String name, String id, String[] cteClass) {
		super(name, id);
		this.testData = cteClass;
	}

	public String getTestData() {
		return testData[1];
	}
	
	public Integer getTestDataId() {
		Matcher idMatcher = idPattern.matcher(testData[0]);
		idMatcher.find();
		Integer intId;
		intId = Integer.parseInt(idMatcher.group());
		return intId;
	}

	@Override
	public String toString() {
		return "Classification [getTestData()=" + getTestData()
				+ ", getTestDataId()=" + getTestDataId() + ", getId()="
				+ getId() + ", getName()=" + getName() + "]";
	}
	
}
