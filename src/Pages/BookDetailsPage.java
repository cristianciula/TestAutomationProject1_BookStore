package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BookDetailsPage {

    WebDriver driver;

    public BookDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    //LOCATORS
    private By isbnLabel = By.xpath("//label[@id=\"ISBN-label\"]/../following-sibling::div/label");
    private By titleLabel = By.xpath("//label[@id=\"title-label\"]/../following-sibling::div/label");
    private By subTitle = By.xpath("//label[@id=\"subtitle-label\"]/../following-sibling::div/label");
    private By author = By.xpath("//label[@id=\"author-label\"]/../following-sibling::div/label");
    private By publisher = By.xpath("//label[@id=\"publisher-label\"]/../following-sibling::div/label");
    private By totalPages = By.xpath("//label[@id=\"pages-label\"]/../following-sibling::div/label");
    private By description = By.xpath("//label[@id=\"description-label\"]/../following-sibling::div/label");
    private By website = By.xpath("//label[@id=\"website-label\"]/../following-sibling::div/label");
    private By addToCollectionButton = By.id("addNewRecordButton");

    //ACTIONS
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
    public boolean addToCollectionButtonIsDisplayed() {
        return driver.findElement(addToCollectionButton).isDisplayed();
    }
    public void clickAddToCollection() {
        int x = driver.findElement(addToCollectionButton).getLocation().getX()+10;
        int y = driver.findElement(addToCollectionButton).getLocation().getY()+10;

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("Javascript:window.scrollBy("+x+","+y+")");

        driver.findElement(addToCollectionButton).click();
    }
    public void waitForAlert() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.alertIsPresent());
    }
    public String getAlertText() {
        return driver.switchTo().alert().getText();
    }
    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }
}
