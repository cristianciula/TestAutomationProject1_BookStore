package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import testData.User;

public class LoginPage {
	WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
	//Locators
	private By usernameInput = By.id("userName");
	private By passwordInput = By.id("password");
	private By loginButton = By.id("login");
	private By errorMessage = By.id("name");
	
	//Action
	public void enterUsername(String username) {
		driver.findElement(usernameInput).sendKeys(username);
	}

	//Action
	public void enterPassword(String password) {
		driver.findElement(passwordInput).sendKeys(password);
	}

	//Action
	public void clickLogin() {
		driver.findElement(loginButton).click();
	}
	//Combined Actions

	public void authenticateValidUser(User User) {
		enterUsername(User.getValidUsername());
		enterPassword(User.getValidPassword());
		clickLogin();
	}

	public void authenticateInvalidUser(User User) {
		enterUsername(User.getInvalidUsername());
		enterPassword(User.getValidPassword());
		clickLogin();
	}

	public String getErrorMessage() {
		return driver.findElement(errorMessage).getText();
	}
}
