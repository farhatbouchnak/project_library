package fr.d2factory.libraryapp.book;

public class ISBN {
    private long isbnCode;

    
    public ISBN() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ISBN(long isbnCode) {
        this.isbnCode = isbnCode;
    }

	public long getIsbnCode() {
		return isbnCode;
	}

	public void setIsbnCode(long isbnCode) {
		this.isbnCode = isbnCode;
	}
    
    
}
