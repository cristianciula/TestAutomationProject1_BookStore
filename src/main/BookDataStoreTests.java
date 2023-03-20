package main;

import Pages.BookDetailsPage;
import Pages.BookStorePage;
import Pages.LoginPage;
import Pages.ProfilePage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import testData.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookDataStoreTests {

    static WebDriver driver;
    public static LoginPage loginPage;
    public static ProfilePage profilePage;
    public static BookStorePage bookStorePage;
    public static BookDetailsPage bookDetailsPage;

    public static User user = new User("user");
    public static SearchInput SearchInput = new SearchInput("SearchInput");
    public static BookData bookData = new BookData("bookData");

    @BeforeAll
    public static void beforeAll() {
        System.setProperty("webdriver.chrome.driver", "src/resource/chromedriver");
        ChromeOptions co = new ChromeOptions();
        co.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(co);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);
        bookStorePage = new BookStorePage(driver);
        bookDetailsPage = new BookDetailsPage(driver);

    }
    @BeforeEach
    public void beforeEach() {

    }
    @AfterEach
    public void afterEach() {
        driver.manage().deleteAllCookies();
    }
    @AfterAll
    public static void afterAll() {
        driver.close();
        driver.quit();
    }

    @Order(1)
    @Test
    public void loginInvalidCredentials() throws InterruptedException {
        //Navigate to Login page
        driver.get(URL.LOGIN);

        //Check user is on Login page
        assertEquals(PageTitles.LOGIN_PAGE_TITLE, loginPage.getPageTitle());

        //Login using Invalid User
        loginPage.authenticateInvalidUser(user);
        Thread.sleep(1000);

        //Check user was not logged in
        assertEquals(PageTitles.LOGIN_PAGE_TITLE, loginPage.getPageTitle());
        assertEquals(Errors.INVALID_USER_OR_PASSWORD, loginPage.getErrorMessage());
    }
    @Order(2)
    @Test
    public void loginValidCredentials() throws InterruptedException {
        //Navigate to Login page
        driver.get(URL.LOGIN);

        //Check user is on Login page
        assertEquals(PageTitles.LOGIN_PAGE_TITLE, loginPage.getPageTitle());

        //Login
        loginPage.authenticateValidUser(user);
        Thread.sleep(1000);

        //Check user was logged in and is on Profile page
        assertEquals(PageTitles.PROFILE_PAGE_TITLE, profilePage.getPageTitle());
        assertEquals(user.getValidUsername(), profilePage.getUsernameValue());
        assertTrue(profilePage.logoutButtonIsDisplayed());
    }
    @Order(3)
    @Test
    public void searchBook() throws InterruptedException {
        //Navigate to Login page
        driver.get(URL.LOGIN);

        //Check user is on Login page
        assertEquals(PageTitles.LOGIN_PAGE_TITLE, loginPage.getPageTitle());

        //Login
        loginPage.authenticateValidUser(user);
        Thread.sleep(1000);

        //Check user was logged in and is on Profile page
        assertEquals(PageTitles.PROFILE_PAGE_TITLE, profilePage.getPageTitle());
        assertEquals(user.getValidUsername(), profilePage.getUsernameValue());
        assertTrue(profilePage.logoutButtonIsDisplayed());

        //Navigate to Book Store page
        driver.get(URL.BOOK_STORE);
        Thread.sleep(1000);

        //Check user is on Book Store page
        assertEquals(PageTitles.BOOK_STORE_PAGE_TITLE, bookStorePage.getPageTitle());
        assertTrue(bookStorePage.searchBoxIsDisplayed());

        //Search book by Title
        bookStorePage.searchByKeyword(SearchInput.getBookTitle());
        Thread.sleep(1000);

        //Check all results contain expected keyword in the Title column
        for (int i=0; i<bookStorePage.getTitleResults().size(); i++) {
            assertTrue(bookStorePage.getTitleResults().get(i).contains(SearchInput.getBookTitle()));
        }

        //Clear the Search Box
        bookStorePage.clearSearchBox();

        //Search book by Author
        bookStorePage.searchByKeyword(SearchInput.getAuthor());
        Thread.sleep(1000);

        //Check all results contain expected keyword in the Author column
        for (int i=0; i<bookStorePage.getAuthorResults().size(); i++) {
            assertTrue(bookStorePage.getAuthorResults().get(i).contains(SearchInput.getAuthor()));
        }

        //Clear the Search Box
        bookStorePage.clearSearchBox();

        //Search book by Publisher
        bookStorePage.searchByKeyword(SearchInput.getPublisher());
        Thread.sleep(1000);

        //Check all results contain expected keyword in the Publisher column
        for (int i=0; i<bookStorePage.getPublisherResults().size(); i++) {
            assertTrue(bookStorePage.getPublisherResults().get(i).contains(SearchInput.getPublisher()));
        }
    }
    @Order(4)
    @Test
    public void checkBookDetails() throws InterruptedException {
        //Navigate to Login page
        driver.get(URL.LOGIN);

        //Check user is on Login page
        assertEquals(PageTitles.LOGIN_PAGE_TITLE, loginPage.getPageTitle());

        //Login
        loginPage.authenticateValidUser(user);
        Thread.sleep(1000);

        //Check user was logged in and is on Profile page
        assertEquals(PageTitles.PROFILE_PAGE_TITLE, profilePage.getPageTitle());
        assertEquals(user.getValidUsername(), profilePage.getUsernameValue());
        assertTrue(profilePage.logoutButtonIsDisplayed());

        //Navigate to Book Store page
        driver.navigate().to(URL.BOOK_STORE);
        Thread.sleep(1000);

        //Check user is on Book Store page
        assertEquals(PageTitles.BOOK_STORE_PAGE_TITLE, bookStorePage.getPageTitle());
        assertTrue(bookStorePage.searchBoxIsDisplayed());

        //Search book by Title
        bookStorePage.searchByKeyword(SearchInput.getBookTitle());
        Thread.sleep(1000);

        //Check all results contain expected keyword in the Title column
        for (int i=0; i<bookStorePage.getTitleResults().size(); i++) {
            assertTrue(bookStorePage.getTitleResults().get(i).contains(SearchInput.getBookTitle()));
        }

        //Select book Title in order to navigate to its respective Book Details page
        bookStorePage.selectBook();
        Thread.sleep(1000);

        //Check user is on the expected Book Details page
        assertEquals(URL.BOOK_DETAILS(bookData.getISBN()), driver.getCurrentUrl());

        //Check Book Details
        assertEquals(bookData.getISBN(), bookDetailsPage.getISBN());
        assertEquals(bookData.getTitle(), bookDetailsPage.getTitle());
        assertEquals(bookData.getSubTitle(), bookDetailsPage.getSubTitle());
        assertEquals(bookData.getAuthor(), bookDetailsPage.getAuthor());
        assertEquals(bookData.getPublisher(), bookDetailsPage.getPublisher());
        assertEquals(bookData.getTotalPages(), bookDetailsPage.getTotalPages());
        assertEquals(bookData.getDescription(), bookDetailsPage.getDescription());
        assertEquals(bookData.getWebsite(), bookDetailsPage.getWebsite());
    }
    @Order(5)
    @Test
    public void addBookToCollection() throws InterruptedException {
        //Navigate to Login page
        driver.get(URL.LOGIN);

        //Check user is on Login page
        assertEquals(PageTitles.LOGIN_PAGE_TITLE, loginPage.getPageTitle());

        //Login
        loginPage.authenticateValidUser(user);
        Thread.sleep(1000);

        //Check user was logged in and is on Profile page
        assertEquals(PageTitles.PROFILE_PAGE_TITLE, profilePage.getPageTitle());
        assertEquals(user.getValidUsername(), profilePage.getUsernameValue());
        assertTrue(profilePage.logoutButtonIsDisplayed());

        //Navigate to Book Details page via URL
        driver.navigate().to(URL.BOOK_DETAILS(bookData.getISBN()));
        Thread.sleep(1000);

        //Check user is on the expected Book Details page
        assertEquals(URL.BOOK_DETAILS(bookData.getISBN()), driver.getCurrentUrl());

        //Check Add To Collection button is displayed and enabled
        assertTrue(bookDetailsPage.addToCollectionButtonIsDisplayed());
        assertTrue(bookDetailsPage.addToCollectionButtonIsEnabled());

        //Click on Add To Collection button
        bookDetailsPage.clickAddToCollection();
        Thread.sleep(1000);

        //Check alert is displayed with expected text
        bookDetailsPage.waitForAlert();
        assertEquals(Alerts.BOOK_ADDED_ALERT, bookDetailsPage.getAlertText());

        //Close alert
        bookDetailsPage.acceptAlert();
        Thread.sleep(1000);
    }
    @Order(6)
    @Test
    public void addExistingBookToCollection() throws InterruptedException {
        //Navigate to Login page
        driver.get(URL.LOGIN);

        //Login
        loginPage.authenticateValidUser(user);
        Thread.sleep(1000);

        //Check user was logged in and is on Profile page
        assertEquals(PageTitles.PROFILE_PAGE_TITLE, profilePage.getPageTitle());
        assertEquals(user.getValidUsername(), profilePage.getUsernameValue());
        assertTrue(profilePage.logoutButtonIsDisplayed());

        //Navigate to Book Details page of a Book that is already in Book Collection
        driver.get(URL.BOOK_DETAILS(bookData.getISBN()));
        Thread.sleep(1000);

        //Check user is on the expected Book Details page
        assertEquals(URL.BOOK_DETAILS(bookData.getISBN()), driver.getCurrentUrl());

        //Check Add To Collection button is displayed and enabled
        assertTrue(bookDetailsPage.addToCollectionButtonIsDisplayed());
        assertTrue(bookDetailsPage.addToCollectionButtonIsEnabled());

        //Click on Add To Collection button
        bookDetailsPage.clickAddToCollection();
        Thread.sleep(1000);

        //Check alert is displayed with expected text
        bookDetailsPage.waitForAlert();
        assertEquals(Alerts.BOOK_ALREADY_PRESENT, bookDetailsPage.getAlertText());

        //Close alert
        bookDetailsPage.acceptAlert();
        Thread.sleep(1000);

        bookDetailsPage.logout();
        Thread.sleep(2000);
    }
    @Order(7)
    @Test
    public void removeBookFromCollection() throws InterruptedException {
        //Navigate to Login page
        driver.get(URL.LOGIN);

        //Check user is on Login page
        assertEquals(PageTitles.LOGIN_PAGE_TITLE, loginPage.getPageTitle());

        //Login
        loginPage.authenticateValidUser(user);
        Thread.sleep(1000);

        //Check user was logged in and is on Profile page
        assertEquals(PageTitles.PROFILE_PAGE_TITLE, profilePage.getPageTitle());
        assertEquals(user.getValidUsername(), profilePage.getUsernameValue());
        assertTrue(profilePage.logoutButtonIsDisplayed());

        //Check collection contains expected book title
        for (int i=0; i<profilePage.getTitleResults().size(); i++) {
            assertTrue(profilePage.getTitleResults().contains(bookData.getTitle()));
        }

        //Check removeIcon is displayed
        assertTrue(profilePage.removeIconIsDisplayed());

        //Remove book from Collection
        profilePage.removeBookFromCollection();
        Thread.sleep(1000);

        //Check confirmation modal is displayed with expected text
        assertEquals(Alerts.REMOVE_BOOK_MODAL, profilePage.getConfirmationModalText());

        //Confirm book deletion in the confirmation modal
        profilePage.agreeConfirmationModal();
        Thread.sleep(1000);

        //Check alert is displayed with expected text
        profilePage.waitForAlert();
        assertEquals(Alerts.BOOK_DELETED_ALERT, profilePage.getAlertText());

        //Close alert
        profilePage.acceptAlert();
        Thread.sleep(1000);

        //Check book was removed from Collection
        for (int i=0; i<profilePage.getTitleResults().size(); i++) {
            assertFalse(profilePage.getTitleResults().contains(bookData.getTitle()));
        }
    }
    @Order(8)
    @Test
    public void logout() {
        driver.get(URL.LOGIN);
    }
}
