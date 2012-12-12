package cte.xmlObjects;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CteTestCase extends CteObject {

	private String[] marks;
	private Pattern idPattern = Pattern.compile("\\d+");

	public CteTestCase(String name, String id, String marks) {
		super(name, id);
		this.setMarks(splitMarks(marks));
	}

	private String[] splitMarks(String marks) {
		String[] markarr = marks.split(" ");
		return markarr;
	}

	public Integer[] getMarks() {
		Integer[] intMarks = new Integer[marks.length];
		for (int i = 0; i < marks.length; i++) {
			Matcher idMatcher = idPattern.matcher(marks[i]);
			idMatcher.find();
			intMarks[i] = Integer.parseInt(idMatcher.group());
		}
		return intMarks;
	}

	private void setMarks(String[] marks) {
		this.marks = marks;
	}

	@Override
	public String toString() {
		return "CteTestCase [marks=" + Arrays.toString(getMarks()) + ", getId()="
				+ getId() + ", getName()=" + getName() + "]";
	}

}
