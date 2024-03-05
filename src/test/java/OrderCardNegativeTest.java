import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderCardNegativeTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    public void afterEach() {
        driver.quit();
        driver = null;

    }

    @Test
    void shouldSendFailNameTest() {
        driver.findElement(By.cssSelector("[data-test-id= name] input")).sendKeys("Petrov Petr");
        driver.findElement(By.cssSelector("[data-test-id= phone] input")).sendKeys("+79854567895");
        driver.findElement(By.cssSelector("[data-test-id= agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim());

    }

    @Test
    void shouldSendFailPhoneTest() {
        driver.findElement(By.cssSelector("[data-test-id= name] input")).sendKeys("Петров Петр");
        driver.findElement(By.cssSelector("[data-test-id= phone] input")).sendKeys("+75456446465464545");
        driver.findElement(By.cssSelector("[data-test-id= agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        var actualTextElement = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub"));
        var actualText = actualTextElement.getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actualText);
        assertTrue(actualTextElement.isDisplayed());
    }

    @Test
    void shouldSendEmptyNameTest() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id= phone] input")).sendKeys("+79854567895");
        driver.findElement(By.cssSelector("[data-test-id= agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        var actualTextElement = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub"));
        var actualText = actualTextElement.getText().trim();
        assertEquals("Поле обязательно для заполнения", actualText);
        assertTrue(actualTextElement.isDisplayed());
    }

    @Test
    void shouldSendWithNoCheckboxTest() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id= name] input")).sendKeys("Петров Петр");
        driver.findElement(By.cssSelector("[data-test-id= phone] input")).sendKeys("+79854567895");
        driver.findElement(By.cssSelector("button.button")).click();
        assertTrue(driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid")).isDisplayed());
    }
    @Test
    void shouldSendEmptyPhoneTest() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id= name] input")).sendKeys("Петров Петр");;
        driver.findElement(By.cssSelector("[data-test-id= agreement]")).click();
        driver.findElement(By.cssSelector("button")).click();
        var actualTextElement = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub"));
        var actualText = actualTextElement.getText().trim();
        assertEquals("Поле обязательно для заполнения", actualText);
        assertTrue(actualTextElement.isDisplayed());
    }
}