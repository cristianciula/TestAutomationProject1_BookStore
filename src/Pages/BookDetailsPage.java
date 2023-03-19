package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BookDetailsPage {

    WebDriver driver;

    public BookDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    //Locators
    private By isbnLabel = By.xpath("//label[@id=\"ISBN-label\"]/../following-sibling::div/label");
    private By titleLabel = By.xpath("//label[@id=\"title-label\"]/../following-sibling::div/label");
    private By subTitle = By.xpath("//label[@id=\"subtitle-label\"]/../following-sibling::div/label");
    private By author = By.xpath("//label[@id=\"author-label\"]/../following-sibling::div/label");
    private By publisher = By.xpath("//label[@id=\"publisher-label\"]/../following-sibling::div/label");
    private By totalPages = By.xpath("//label[@id=\"pages-label\"]/../following-sibling::div/label");
    private By description = By.xpath("//label[@id=\"description-label\"]/../following-sibling::div/label");
    private By website = By.xpath("//label[@id=\"website-label\"]/../following-sibling::div/label");

    //Actions
    public String getISBN() {
        return driver.findElement(isbnLabel).getText();
    }
    public String getTitle() {
        return driver.findElement(titleLabel).getText();
    }
    public String getSubTitle() {
        return driver.findElement(subTitle).getText();
    }
    public String getAuthor() {
        return driver.findElement(author).getText();
    }
    public String getPublisher() {
        return driver.findElement(publisher).getText();
    }
    public String getTotalPages() {
        return driver.findElement(totalPages).getText();
    }
    public String getDescription() {
        return driver.findElement(description).getText();
    }
    public String getWebsite() {
        return driver.findElement(website).getText();
    }
}
