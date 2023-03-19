package testData;

public class URL {

    public final static String LOGIN = "https://demoqa.com/login";
    public final static String BOOK_STORE = "https://demoqa.com/books";
    public static final String BOOK_DETAILS (String ISBN) {
        return "https://demoqa.com/books?book=" + ISBN;
    }
}
