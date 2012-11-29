package c2s;

/**
 * Test Case to be put into Selenium/JUnit Test
 * 
 * @author Benjamin
 *
 */
public class TC {

	private String name;
	private boolean numeric;
	private boolean uppercase;
	private String length;
	
	public TC(String name) {
		this.name = name;
	}
	
	public TC(String name, boolean numeric, boolean uppercase, String length) {
		this.name = name;
		this.numeric = numeric;
		this.uppercase = uppercase;
		this.length = length;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the numeric
	 */
	public boolean isNumeric() {
		return numeric;
	}

	/**
	 * @param numeric the numeric to set
	 */
	public void setNumeric(boolean numeric) {
		this.numeric = numeric;
	}

	/**
	 * @return the uppercase
	 */
	public boolean isUppercase() {
		return uppercase;
	}

	/**
	 * @param uppercase the uppercase to set
	 */
	public void setUppercase(boolean uppercase) {
		this.uppercase = uppercase;
	}

	/**
	 * @return the length
	 */
	public String getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(String length) {
		this.length = length;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name + ": numeric=" + numeric + ", uppercase="
				+ uppercase + ", length=" + length;
	}

	
}
