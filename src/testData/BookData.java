package testData;

import utils.Reader;

public class BookData {

    private String ISBN;
    private String title;
    private String subTitle;
    private String author;
    private String publisher;
    private String totalPages;
    private String description;
    private String website;

    public BookData(String fileName) {
        this.ISBN = Reader.json(fileName).get("ISBN").toString();
        this.title = Reader.json(fileName).get("title").toString();
        this.subTitle = Reader.json(fileName).get("subTitle").toString();
        this.author = Reader.json(fileName).get("author").toString();
        this.publisher = Reader.json(fileName).get("publisher").toString();
        this.totalPages = Reader.json(fileName).get("totalPages").toString();
        this.description = Reader.json(fileName).get("description").toString();
        this.website = Reader.json(fileName).get("website").toString();
    }

    public String getISBN() {
        return ISBN;
    }
    public String getTitle() {
        return title;
    }
    public String getSubTitle() {
        return subTitle;
    }
    public String getAuthor() {
        return author;
    }
    public String getPublisher() {
        return publisher;
    }
    public String getTotalPages() {
        return totalPages;
    }
    public String getDescription() {
        return description;
    }
    public String getWebsite() {
        return website;
    }
}
