package fr.d2factory.libraryapp.library;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.book.ISBN;
import fr.d2factory.libraryapp.member.EnumLevel;
import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.member.Resident;
import fr.d2factory.libraryapp.member.Student;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.engine.discovery.predicates.IsNestedTestClass;

/**
 * Do not forget to consult the README.md :)
 */
public class LibraryTest {

	private Library library;
	private BookRepository bookRepository;
	private static List<Book> books;

	@BeforeEach
	void setup() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		File booksJson = new File("src/test/resources/books.json");
		books = mapper.readValue(booksJson, new TypeReference<List<Book>>() {
		});
		bookRepository = new BookRepository();
		library = new LibraryImpl(bookRepository);
	}

	@Test
	void member_can_borrow_a_book_if_book_is_available() {
		bookRepository.addBooks(books);
		Member member = new Resident();
		member.setHasAbook(Boolean.FALSE);
		Book book = library.borrowBook(3326456467846l, member, LocalDate.now());
		assertTrue(book != null);

	}

	@Test
	void borrowed_book_is_no_longer_available() {
		Book book = new Book();
		book.setAuthor("Ali");
		book.setTitle("Other Life");
		book.setIsbn(new ISBN(148756231));
		bookRepository.saveBookBorrow(book, LocalDate.now().minusDays(2));
		Book bb = bookRepository.findBook(148756231);
		assertTrue(bb == null);
	}

	@Test
	void residents_are_taxed_10cents_for_each_day_they_keep_a_book() {
		bookRepository.addBooks(books);
		Member resident = new Resident();
		resident.setWallet(1000);
		Book book = library.borrowBook(3326456467846l, resident, LocalDate.now().minusDays(63));
		library.returnBook(book, resident);
		assertTrue(resident.getWallet() == 370);
	}

	@Test
	void students_pay_10_cents_the_first_30days() {
		bookRepository.addBooks(books);
		Member student = new Student();
		student.setWallet(100);
		((Student) student).setLevel(EnumLevel.NON_FIRST_YEAR.toString());
		Book book = library.borrowBook(3326456467846l, student, LocalDate.now().minusDays(3));
		library.returnBook(book, student);
		assertTrue(student.getWallet() == 70);
	}

	@Test
	void students_in_1st_year_are_not_taxed_for_the_first_15days() {
		bookRepository.addBooks(books);
		Member student = new Student();
		student.setWallet(100);
		((Student) student).setLevel(EnumLevel.FIRST_YEAR.toString());
		Book book = library.borrowBook(3326456467846l, student, LocalDate.now().minusDays(3));
		library.returnBook(book, student);
		assertTrue(student.getWallet() == 100);
	}

	@Test
	void residents_pay_20cents_for_each_day_they_keep_a_book_after_the_initial_60days() {
		bookRepository.addBooks(books);
		Member resident = new Resident();
		resident.setWallet(1000);
		Book book = library.borrowBook(3326456467846l, resident, LocalDate.now().minusDays(63));
		library.returnBook(book, resident);
		assertTrue(resident.getWallet() == 370);
	}

	@Test
	void members_cannot_borrow_book_if_they_have_late_books() {
		Member member = new Student();
		member.setHasAbook(Boolean.TRUE);
		Book book = library.borrowBook(54879621, member, LocalDate.now());
		assertTrue(book == null);
	}
}
