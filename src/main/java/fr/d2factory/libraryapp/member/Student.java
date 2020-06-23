package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.library.Library;

/**
 * A student is a person who can borrow and return books to a {@link Library}
 */
public class Student extends Member {

	long id;
	/**
	 * the level of student to distinguish the amount of bill
	 */
	private String level;

	/**
	 * this method is calculating the bills to pay by the student
	 */
	@Override
	public void payBook(int numberOfDays) {

		Student std = (Student) this;
		if (EnumLevel.NON_FIRST_YEAR.toString().equals(std.getLevel())) {
			std.setWallet(std.getWallet() - Math.abs(numberOfDays) * 10);
		} else {
			if (numberOfDays > 15) {
				std.setWallet(std.getWallet() - (numberOfDays - 15) * 10);
			} else {
				std.setWallet(std.getWallet());
			}
		}

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}
