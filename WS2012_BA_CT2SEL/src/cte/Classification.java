package cte;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Classification extends CteObject {

	private String[][] cteClass;
	private Pattern idPattern = Pattern.compile("\\d+");
	private Matcher idMatcher;
	
	public Classification(String name, String id, String[][] cteClass) {
		super(name, id);
		this.cteClass = cteClass;
	}

	public String[] getTestData() {
		String[] testData = new String[cteClass.length];
		for (int i = 0, j = 1; i < cteClass.length; i++) {
			testData[i] = cteClass[i][j];
		}
		return testData;
	}
	
	public Integer[] getTestDataIds() {
		Integer[] intId = new Integer[cteClass.length];
		for (int i = 0, j = 0; i < intId.length; i++) {
			idMatcher = idPattern.matcher(cteClass[i][j]);
			idMatcher.find();
			intId[i] = Integer.parseInt(idMatcher.group());
		}
		return intId;
	}

	@Override
	public String toString() {
		return "Classification [getTestData()="
				+ Arrays.toString(getTestData()) + ", getTestDataIds()="
				+ Arrays.toString(getTestDataIds()) + ", getId()=" + getId()
				+ ", getName()=" + getName() + "]";
	}
	
}
