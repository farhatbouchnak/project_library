package fr.d2factory.libraryapp.library;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.member.EnumLevel;
import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.member.Resident;
import fr.d2factory.libraryapp.member.Student;

/**
 * This class is the implementation of functionalities helping in managing the
 * books
 *
 * The books are available via the
 * {@link fr.d2factory.libraryapp.book.BookRepository}
 */
public class LibraryImpl implements Library {

	private BookRepository bookRepository;

	/**
	 * constructor injection for avoiding nullpointerexception when calling
	 * bookrepository
	 * 
	 * @param bookRepository
	 */
	public LibraryImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	/**
	 * a member try to borrow a book at a defined time.
	 */
	@Override
	public Book borrowBook(long isbnCode, Member member, LocalDate borrowedAt) throws HasLateBooksException {

		Book book = null;
		if (!member.isHasAbook()) {
			book = bookRepository.findBook(isbnCode);
			bookRepository.saveBookBorrow(book, borrowedAt);
			member.setHasAbook(Boolean.TRUE);
		}
		return book;
	}

	/**
	 * a member returning a book after finishing with it and paying his bills
	 */
	@Override
	public void returnBook(Book book, Member member) {
		LocalDate localDate = bookRepository.findBorrowedBookDate(book);
		long days = ChronoUnit.DAYS.between(LocalDate.now(), localDate);
		member.payBook((int) days);
		member.setHasAbook(Boolean.FALSE);
	}

}
