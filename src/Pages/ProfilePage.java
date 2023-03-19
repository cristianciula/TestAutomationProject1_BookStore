package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testData.BookData;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ProfilePage {
	WebDriver driver;
	public static BookData bookData = new BookData("bookData");

	public ProfilePage(WebDriver driver) {
		this.driver = driver;
	}

	//LOCATORS
	private By usernameLabel = By.id("userName-value");
	private By logoutButton = By.id("submit");
	private By headerLabel = By.xpath("//div[@class=\"main-header\"]");
	private By titleColumn = By.xpath("//div[@role=\"rowgroup\"]/descendant::div[@role=\"gridcell\"][2]");
	private By removeButton(String bookTitle) {
		return By.xpath("//div[@role=\"gridcell\"][contains(.,\""+bookTitle+"\")]/following-sibling::div/descendant::span[@id=\"delete-record-undefined\"]");
	}
	private By confirmationModalText = By.xpath("//div[@class=\"modal-body\"]");
	private By confirmationModalOkButton = By.id("closeSmallModal-ok");

	//ACTIONS
	public String getUsernameValue() {
		return driver.findElement(usernameLabel).getText();
	}

	public boolean logoutButtonIsDisplayed() {
		return driver.findElement(logoutButton).isDisplayed();
	}
	public String getPageTitle() {
		return driver.findElement(headerLabel).getText();
	}
	public List<String> getTitleResults() {
		List<WebElement> titleCells = driver.findElements(titleColumn);
		List<String> titleValues = new ArrayList<String>();

		for (WebElement titleCell : titleCells) {
			if (!titleCell.getText().isBlank()) {
				titleValues.add(titleCell.getText());
			}
		}
		return titleValues;
	}
	public boolean removeIconIsDisplayed() {
		return driver.findElement(removeButton(bookData.getTitle())).isDisplayed();
	}
	public void removeBookFromCollection() {
		driver.findElement(removeButton(bookData.getTitle())).click();
	}
	public String getConfirmationModalText() {
		return driver.findElement(confirmationModalText).getText();
	}
	public void agreeConfirmationModal() {
		driver.findElement(confirmationModalOkButton).click();
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

