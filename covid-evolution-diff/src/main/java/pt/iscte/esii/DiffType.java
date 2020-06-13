package pt.iscte.esii;

public enum DiffType {
	ADDITION("addition"), DELETION("deletion"), NEUTRAL("neutral");

	private String str;

	private DiffType(String str) {
		this.str = str;
	}

	public String getStr() {
		return str;
	}
}
