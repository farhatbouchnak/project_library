package fr.d2factory.libraryapp.book;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The book repository emulates a database via 2 HashMaps
 */
public class BookRepository {
    private Map<ISBN, Book> availableBooks = new HashMap<>();
    private Map<Book, LocalDate> borrowedBooks = new HashMap<>();

    /**
     * add all books to the emulated database.
     * @param books
     */
    public void addBooks(List<Book> books){
        //we iterate over the list and adding every book to the map using put method
    	for(Book book : books) {
    		availableBooks.put(book.getIsbn(), book);
    	}
    }

    /**
     * search a book by isbnCode in the list of books availables
     * @param isbnCode
     * @return Book
     */
	public Book findBook(long isbnCode) {

		for (Entry<ISBN, Book> entries : availableBooks.entrySet()) {
			//We make a test to find the isbn equal to that in parameter 
			if (isbnCode == entries.getKey().getIsbnCode()) {
				return (Book)entries.getValue();
			}
		}
		//otherwise the book does not exist
		return null;
	}

	/**
	 * save the book and the time of its borrowing in the list of borrowed books 
	 * @param book
	 * @param borrowedAt
	 */
    public void saveBookBorrow(Book book, LocalDate borrowedAt){
        borrowedBooks.put(book, borrowedAt);
    }

	public LocalDate findBorrowedBookDate(Book book) {
		LocalDate local = null;
		if (borrowedBooks != null && !borrowedBooks.isEmpty()) {
			for (Entry<Book, LocalDate> entries : borrowedBooks.entrySet()) {
				if (book.equals(entries.getKey())) {
					local = entries.getValue();
					break;
				}
			}
		}

		return local;
	}
	
	
}
