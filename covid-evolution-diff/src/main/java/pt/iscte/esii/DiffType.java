package pt.iscte.esii;

public enum DiffType {
	ADDITION("addition"), DELETION("deletion"), NEUTRAL("neutral");

	/**
	 * String version of the Enum
	 */
	private String str;

	/**
	 * Constructor
	 * @param str String version of enum
	 */
	private DiffType(String str) {
		this.str = str;
	}

	/**
	 * @return The String version of the Enum
	 */
	public String getStr() {
		return str;
	}
}
