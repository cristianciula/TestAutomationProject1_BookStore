package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import testData.BookData;

import java.util.ArrayList;
import java.util.List;

public class BookStorePage {
    WebDriver driver;
    public static BookData bookData = new BookData("bookData");
    public BookStorePage(WebDriver driver) {
        this.driver = driver;
    }

    //LOCATORS
    private By headerLabel = By.xpath("//div[@class=\"main-header\"]");
    private By searchInput = By.id("searchBox");
    private By titleColumn = By.xpath("//div[@role=\"rowgroup\"]/descendant::div[@role=\"gridcell\"][2]");
    private By authorColumn = By.xpath("//div[@role=\"rowgroup\"]/descendant::div[@role=\"gridcell\"][3]");
    private By publisherColumn = By.xpath("//div[@role=\"rowgroup\"]/descendant::div[@role=\"gridcell\"][4]");
    private By bookLink(String bookTitle) {
        return By.xpath("//a[contains(.,\""+bookTitle+"\")]");
    }

    //ACTIONS
    public String getPageTitle() {
        return driver.findElement(headerLabel).getText();
    }
    public void searchInput(String searchCriteria) {
        driver.findElement(searchInput).sendKeys(searchCriteria);
    }
    public void clearSeachBox() {
        driver.findElement(searchInput).clear();
    }
    public List<String> getTitleResults() {
        List<WebElement> titleCells = driver.findElements(titleColumn);
        List<String> titleValues = new ArrayList<String>();

        for (WebElement titleCell:titleCells) {
            if (!titleCell.getText().isBlank()) {
                titleValues.add(titleCell.getText());
            }
        }
        return titleValues;
    }

    public List<String> getAuthorResults() {
        List<WebElement> authorCells = driver.findElements(authorColumn);
        List<String> authorValues = new ArrayList<String>();

        for (WebElement authorCell:authorCells) {
            if (!authorCell.getText().isBlank()) {
                authorValues.add(authorCell.getText());
            }
        }
        return authorValues;
    }

    public List<String> getPublisherResults() {
        List<WebElement> publisherCells = driver.findElements(publisherColumn);
        List<String> publisherValues = new ArrayList<String>();

        for (WebElement publisherCell:publisherCells) {
            if (!publisherCell.getText().isBlank()) {
                publisherValues.add(publisherCell.getText());
            }
        }
        return publisherValues;
    }
    public void selectBook() {
        driver.findElement(bookLink(bookData.getTitle())).click();
    }
}
