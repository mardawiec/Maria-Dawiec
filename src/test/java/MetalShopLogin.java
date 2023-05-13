import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class MetalShopLogin {
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

    @Test
    @Order(15)
    public void loginSuccessTest() {
        driver.findElement(myAccount).click();
        driver.findElement(By.cssSelector("#username")).sendKeys("poiu3");
        driver.findElement(By.cssSelector("#password")).sendKeys("abcd123");
        driver.findElement(By.cssSelector(".woocommerce-form-login__submit")).click();
        Assertions.assertTrue(driver.findElement(By.xpath("//a [text() = 'Wyloguj się']")).isDisplayed());
    }
}
