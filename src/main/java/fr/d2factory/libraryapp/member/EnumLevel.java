package fr.d2factory.libraryapp.member;

/**
 * this enum contains two values describing the level of a student.
 * @author Farhat
 *
 */
public enum EnumLevel {

	FIRST_YEAR("First Value"), NON_FIRST_YEAR("Non First Value");

	private String value;

	EnumLevel(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
