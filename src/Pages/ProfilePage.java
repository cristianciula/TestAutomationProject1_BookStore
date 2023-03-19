package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilePage {
	WebDriver driver;

	public ProfilePage(WebDriver driver) {
		this.driver = driver;
	}

	//Locators
	private By usernameLabel = By.id("userName-value");
	private By logoutButton = By.id("submit");
	private By headerLabel = By.xpath("//div[@class=\"main-header\"]");

	//Actions
	public String getUsernameValue() {
		return driver.findElement(usernameLabel).getText();
	}
	public boolean logoutButtonDisplayed() {
		return driver.findElement(logoutButton).isDisplayed();
	}
	public String getPageTitle() {
		return driver.findElement(headerLabel).getText();
	}
}
