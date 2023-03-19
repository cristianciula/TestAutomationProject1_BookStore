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
public class BookStoreTests {

    static WebDriver driver;
    public static LoginPage loginPage;
    public static ProfilePage profilePage;
    public static BookStorePage bookStorePage;
    public static BookDetailsPage bookDetailsPage;

    public static User User = new User("User");
    public static searchCriteria searchCriteria = new searchCriteria("searchCriteria");
    public static Book book = new Book("bookData");

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
        loginPage.authenticateInvalidUser(User);
        Thread.sleep(1000);
        assertEquals(Errors.INVALID_USER_OR_PASS, loginPage.getErrorMessage());
    }
    @Order(2)
    @Test
    public void loginValidCredentials() throws InterruptedException {
        loginPage.authenticateValidUser(User);
        Thread.sleep(1000);
        assertEquals(User.getValidUsername(), profilePage.getUsernameValue());
    }
    @Order(3)
    @Test
    public void searchBook() throws InterruptedException {
        loginPage.authenticateValidUser(User);
        Thread.sleep(1000);

        //Navigate to Book Store page
        driver.get(URL.BOOK_STORE);
        Thread.sleep(1000);
        assertEquals(PageTitles.BOOK_STORE_PAGE_TITLE, bookStorePage.getPageTitle());

        //Search book by Title
        bookStorePage.searchInput(searchCriteria.getBookTitle());
        Thread.sleep(1000);
        for (int i=0; i<bookStorePage.getTitleResults().size(); i++) {
            assertTrue(bookStorePage.getTitleResults().get(i).contains(searchCriteria.getBookTitle()));
        }
        bookStorePage.clearSeachBox();

        //Search book by Author
        bookStorePage.searchInput(searchCriteria.getAuthor());
        Thread.sleep(1000);
        for (int i=0; i<bookStorePage.getAuthorResults().size(); i++) {
            assertTrue(bookStorePage.getAuthorResults().get(i).contains(searchCriteria.getAuthor()));
        }
        bookStorePage.clearSeachBox();

        //Search book by Publisher
        bookStorePage.searchInput(searchCriteria.getPublisher());
        Thread.sleep(1000);
        for (int i=0; i<bookStorePage.getPublisherResults().size(); i++) {
            assertTrue(bookStorePage.getPublisherResults().get(i).contains(searchCriteria.getPublisher()));
        }
    }
    @Order(4)
    @Test
    public void checkBookDetails() throws InterruptedException {
        loginPage.authenticateValidUser(User);
        Thread.sleep(1000);

        driver.navigate().to(URL.BOOK_STORE);
        Thread.sleep(1000);

        bookStorePage.searchInput(searchCriteria.getBookTitle());
        Thread.sleep(1000);
        for (int i=0; i<bookStorePage.getTitleResults().size(); i++) {
            assertTrue(bookStorePage.getTitleResults().get(i).contains(searchCriteria.getBookTitle()));
        }

        //Navigate to a specific Book Details page
        bookStorePage.selectBook();
        Thread.sleep(1000);
        //Check user is on the expected Book Details page
        assertEquals(URL.BOOK_DETAILS(book.getISBN()), driver.getCurrentUrl());

        //Check Book Details
        assertEquals(book.getISBN(), bookDetailsPage.getISBN());
        assertEquals(book.getTitle(), bookDetailsPage.getTitle());
        assertEquals(book.getSubTitle(), bookDetailsPage.getSubTitle());
        assertEquals(book.getAuthor(), bookDetailsPage.getAuthor());
        assertEquals(book.getPublisher(), bookDetailsPage.getPublisher());
        assertEquals(book.getTotalPages(), bookDetailsPage.getTotalPages());
        assertEquals(book.getDescription(), bookDetailsPage.getDescription());
        assertEquals(book.getWebsite(), bookDetailsPage.getWebsite());
    }
    @Order(5)
    @Test
    public void addBookToCollection() throws InterruptedException {
        loginPage.authenticateValidUser(User);
        Thread.sleep(1000);

        driver.navigate().to(URL.BOOK_DETAILS(book.getISBN()));
        Thread.sleep(1000);

        bookDetailsPage.clickAddToCollection();
        Thread.sleep(1000);


    }
}
