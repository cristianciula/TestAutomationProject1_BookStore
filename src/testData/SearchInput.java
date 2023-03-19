package testData;

import utils.Reader;

public class SearchInput {

    private String bookTitle;
    private String bookAuthor;
    private String bookPublisher;

    public SearchInput(String fileName) {
        this.bookTitle = Reader.json(fileName).get("title").toString();
        this.bookAuthor = Reader.json(fileName).get("author").toString();
        this.bookPublisher = Reader.json(fileName).get("publisher").toString();
    }

    public String getBookTitle() {
        return bookTitle;
    }
    public String getAuthor() {
        return bookAuthor;
    }
    public String getPublisher() {
        return bookPublisher;
    }

}
