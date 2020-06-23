package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.library.Library;

/**
 * A Resident is a person who can borrow and return books to a {@link Library}
 */
public class Resident extends Member {

	/**
	 * implementation of the method payBook
	 * this method is calculating the bills to pay by the Resident
	 */
	public void payBook(int numberOfDays) {

		if (Math.abs(numberOfDays) <= 60) {
			this.setWallet(this.getWallet() - Math.abs(numberOfDays) * 10);
		} else {
			this.setWallet(this.getWallet() - (600 + ((Math.abs(numberOfDays) - 60) * 10)));
		}

	}
}
