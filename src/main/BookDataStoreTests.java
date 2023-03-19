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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        driver.get(URL.LOGIN);
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
        loginPage.authenticateInvalidUser(user);
        Thread.sleep(1000);
        assertEquals(Errors.INVALID_USER_OR_PASS, loginPage.getErrorMessage());
    }
    @Order(2)
    @Test
    public void loginValidCredentials() throws InterruptedException {
        loginPage.authenticateValidUser(user);
        Thread.sleep(1000);
        assertEquals(user.getValidUsername(), profilePage.getUsernameValue());
    }
    @Order(3)
    @Test
    public void searchBook() throws InterruptedException {
        loginPage.authenticateValidUser(user);
        Thread.sleep(1000);

        //Navigate to BookData Store page
        driver.get(URL.BOOK_STORE);
        Thread.sleep(1000);
        assertEquals(PageTitles.BOOK_STORE_PAGE_TITLE, bookStorePage.getPageTitle());

        //Search bookData by Title
        bookStorePage.searchInput(SearchInput.getBookTitle());
        Thread.sleep(1000);
        for (int i=0; i<bookStorePage.getTitleResults().size(); i++) {
            assertTrue(bookStorePage.getTitleResults().get(i).contains(SearchInput.getBookTitle()));
        }
        bookStorePage.clearSeachBox();

        //Search bookData by Author
        bookStorePage.searchInput(SearchInput.getAuthor());
        Thread.sleep(1000);
        for (int i=0; i<bookStorePage.getAuthorResults().size(); i++) {
            assertTrue(bookStorePage.getAuthorResults().get(i).contains(SearchInput.getAuthor()));
        }
        bookStorePage.clearSeachBox();

        //Search bookData by Publisher
        bookStorePage.searchInput(SearchInput.getPublisher());
        Thread.sleep(1000);
        for (int i=0; i<bookStorePage.getPublisherResults().size(); i++) {
            assertTrue(bookStorePage.getPublisherResults().get(i).contains(SearchInput.getPublisher()));
        }
    }
    @Order(4)
    @Test
    public void checkBookDetails() throws InterruptedException {
        //Login
        loginPage.authenticateValidUser(user);
        Thread.sleep(1000);

        //Navigate to BookData Store page
        driver.navigate().to(URL.BOOK_STORE);
        Thread.sleep(1000);

        //Search bookData by Title
        bookStorePage.searchInput(SearchInput.getBookTitle());
        Thread.sleep(1000);
        for (int i=0; i<bookStorePage.getTitleResults().size(); i++) {
            assertTrue(bookStorePage.getTitleResults().get(i).contains(SearchInput.getBookTitle()));
        }

        //Navigate to BookData Details page
        bookStorePage.selectBook();
        Thread.sleep(1000);
        //Check user is on the expected BookData Details page
        assertEquals(URL.BOOK_DETAILS(bookData.getISBN()), driver.getCurrentUrl());

        //Check BookData Details
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
        loginPage.authenticateValidUser(user);
        Thread.sleep(1000);

        driver.navigate().to(URL.BOOK_DETAILS(bookData.getISBN()));
        Thread.sleep(1000);
        assertTrue(bookDetailsPage.addToCollectionButtonIsDisplayed());

        bookDetailsPage.clickAddToCollection();
        Thread.sleep(1000);
    }
}
