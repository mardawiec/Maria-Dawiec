import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class MetalShop {

    static WebDriver driver;
    static final By myAccount = By.xpath("//a[text() = 'Moje konto']");

    @BeforeAll
        static void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);}

    @AfterAll
    static void closeBrowser() {
        driver.quit();}

    @BeforeEach
    void returnToMainPage() {
        driver.findElement(By.xpath("//a [text() = 'Softie Metal Shop']")).click();
    }

    @Test
    @Order(5) //@Ewelina, mam wrażenie, że u mnie @Order nie działa:(

    public void emptyUsernameTest() {

        driver.findElement(myAccount).click();
        driver.findElement(By.cssSelector("#password")).sendKeys("aaaaa");
        driver.findElement(By.cssSelector(".woocommerce-form-login__submit")).click();
        Assertions.assertEquals("Błąd: Nazwa użytkownika jest wymagana.", driver.findElement(By.cssSelector(".woocommerce-error")).getText());
    }

    @Test
    @Order(6)
    public void emptyPasswordTest() {
        driver.findElement(myAccount).click();
        driver.findElement(By.cssSelector("#username")).sendKeys("aaaaa");
        driver.findElement(By.cssSelector(".woocommerce-form-login__submit")).click();
        Assertions.assertEquals("Błąd: pole hasła jest puste.", driver.findElement(By.cssSelector(".woocommerce-error")).getText());

    }

    @Test
    @Order(1)
    public void loginPageLogoTest() {
        driver.findElement(myAccount).click();
        Assertions.assertTrue(driver.findElement(By.cssSelector(".site-title")).isDisplayed());
     }

    @Test
    @Order(2)
    public void loginPageSearchEngineTest() {
        driver.findElement(myAccount).click();
        Assertions.assertTrue(driver.findElement(By.cssSelector("#woocommerce-product-search-field-0")).isDisplayed());
    }

    @Test
    @Order(3)
    public void mainPageLogoTest() {
        driver.findElement(myAccount).click();
        Assertions.assertTrue(driver.findElement(By.cssSelector(".site-title")).isDisplayed());
    }

    @Test
    @Order(4)
    public void mainPageSearchEngineTest() {
        driver.findElement(myAccount).click();
        Assertions.assertTrue(driver.findElement(By.cssSelector("#woocommerce-product-search-field-0")).isDisplayed());
    }

    @Test
    @Order(7)
    public void createNewUserTest() {
        Faker faker = new Faker();
        String username = faker.name().username();
        String email = username + faker.random().nextInt(1000) + "@wp.pl";
        String password = "abcd123";

        driver.findElement(By.xpath("//a[text() = 'register']")).click();
        driver.findElement(By.cssSelector("#user_login")).sendKeys(username);
        driver.findElement(By.cssSelector("#user_email")).sendKeys(email);
        driver.findElement(By.cssSelector("#user_pass")).sendKeys(password);
        driver.findElement(By.cssSelector("#user_confirm_password")).sendKeys(password);
        driver.findElement(By.cssSelector(".ur-submit-button")).click();
        Assertions.assertEquals("User successfully registered.",driver.findElement(By.cssSelector(".user-registration-message")).getText());
    }

    @Test
    @Order(9)
    public void contactPageAvailibilityTest () {
        driver.findElement(By.xpath("//a[text() = 'Kontakt']")).click();
        Assertions.assertTrue(driver.findElement(By.xpath("//input [@name = 'your-subject']")).isDisplayed());
    }


    @Test
    @Order(10)
    public void loginPageReturnToMainPageTest() {
        driver.findElement(myAccount).click();
        driver.findElement(By.xpath("//a [text() = 'Softie Metal Shop']")).click();
        Assertions.assertEquals("Sklep", driver.findElement(By.xpath("//h1 [text() = 'Sklep']")).getText());
    }

    @Test
    @Order(11)
    public void sendMessageTest () {
        driver.findElement(By.xpath("//a[text() = 'Kontakt']")).click();
        driver.findElement(By.name("your-name")).sendKeys("Jan Kowalski");
        driver.findElement(By.name("your-email")).sendKeys("jkowal@wp.pl");
        driver.findElement(By.name("your-subject")).sendKeys("Pytanie");
        driver.findElement(By.name("your-message")).sendKeys("Moje pytanie");
        driver.findElement(By.cssSelector(".wpcf7-submit")).click();
        Wait wait = new WebDriverWait(driver,2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".wpcf7-response-output")));
        Assertions.assertEquals("Wystąpił problem z wysłaniem twojej wiadomości. Spróbuj ponownie później.",driver.findElement(By.cssSelector(".wpcf7-response-output")).getText());
    }

    @Test
    @Order(12)
    void emptyCartCheckTest() {
        Actions builder = new Actions(driver);
        WebElement koszyk = driver.findElement(By.cssSelector(".cart-contents"));
        builder.doubleClick(koszyk).build().perform();
        Assertions.assertEquals("Twój koszyk aktualnie jest pusty.", driver.findElement(By.cssSelector(".cart-empty")).getText());
     }

    @Test
    @Order(13)
    void addItemToCartTest() {
        Actions builder = new Actions(driver);
        WebElement addToCart = driver.findElement(By.xpath("//a [@data-product_id = 24]"));
        builder.click(addToCart).build().perform();
        WebElement addedToCart = driver.findElement(By.cssSelector(".added_to_cart"));
        builder.doubleClick(addedToCart).build().perform();
        Assertions.assertEquals("Przejdź do płatności", driver.findElement(By.cssSelector(".checkout-button")).getText());
    }


    @Test
    @Order(14)
    void removeItemFromCartTest() {
        Actions builder = new Actions(driver);
        WebElement koszyk = driver.findElement(By.cssSelector(".cart-contents"));
        builder.doubleClick(koszyk).build().perform();
        WebElement removeItem = driver.findElement(By.cssSelector(".remove"));
        builder.click(removeItem).build().perform();
        Assertions.assertEquals("Twój koszyk aktualnie jest pusty.",driver.findElement(By.cssSelector(".cart-empty")).getText());
    }

    }

