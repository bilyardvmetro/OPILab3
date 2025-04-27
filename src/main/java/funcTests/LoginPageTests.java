package funcTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public class LoginPageTests {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        Path driverPath = Paths.get("drivers", "chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", driverPath.toAbsolutePath().toString());

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--force-device-scale-factor=0.75");

        driver = new ChromeDriver(options);
        driver.get("http://localhost:4200");
        driver.manage().window().maximize();
    }

    // 1
    @Test
    public void testLoginSuccess() {
        WebElement loginInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement submitBtn = driver.findElement(By.id("submit"));

        loginInput.sendKeys("kirill");
        passwordInput.sendKeys("12345");
        submitBtn.click();

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("/home"));

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:4200/home", currentUrl);
    }

    // 2
    @Test
    public void testLoginFail() {
        WebElement loginInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement submitBtn = driver.findElement(By.id("submit"));

        WebElement alert = driver.findElement(By.id("login-error"));

        loginInput.sendKeys("Oxxxymiron");
        passwordInput.sendKeys("232424234");
        submitBtn.click();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.attributeToBe(alert, "ng-reflect-ng-class", "visible"));

        String alertVisibility = alert.getDomAttribute("ng-reflect-ng-class");
        Assertions.assertEquals("visible", alertVisibility);

        String alertText = alert.getText();
        Assertions.assertEquals("Пользователь с таким логином уже существует или пароль введен неверно", alertText);

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:4200/login", currentUrl);
    }

    // 3
    @Test
    public void testEmptyUsername() {
        WebElement loginInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement submitBtn = driver.findElement(By.id("submit"));

        loginInput.sendKeys("");
        passwordInput.sendKeys("232424234");

        WebElement span = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("username-required")));

        String spanText = span.getText();
        Assertions.assertEquals("Username is required", spanText);

        Assertions.assertNotNull(submitBtn.getDomAttribute("disabled"));

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:4200/login", currentUrl);
    }

    // 4
    @Test
    public void testEmptyPassword() {
        WebElement loginInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement submitBtn = driver.findElement(By.id("submit"));

        loginInput.sendKeys("Serge Klimenkov");
        passwordInput.sendKeys("");
        loginInput.sendKeys("");

        WebElement span = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("password-required")));

        String spanText = span.getText();
        Assertions.assertEquals("Password is required", spanText);

        Assertions.assertNotNull(submitBtn.getDomAttribute("disabled"));

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:4200/login", currentUrl);
    }

    // 5
    @Test
    public void testTooShortUsername() {
        WebElement loginInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement submitBtn = driver.findElement(By.id("submit"));

        loginInput.sendKeys("abc");
        passwordInput.sendKeys("232424234");

        WebElement span = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("too-short-username")));

        String spanText = span.getText();
        Assertions.assertEquals("Username must contain more than 5 characters", spanText);

        Assertions.assertNotNull(submitBtn.getDomAttribute("disabled"));

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:4200/login", currentUrl);
    }

    // 6
    @Test
    public void testTooShortPassword() {
        WebElement loginInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement submitBtn = driver.findElement(By.id("submit"));

        loginInput.sendKeys("abcde");
        passwordInput.sendKeys("23");
        loginInput.sendKeys("");

        WebElement span = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("too-short-password")));

        String spanText = span.getText();
        Assertions.assertEquals("Password must contain more than 5 characters", spanText);

        Assertions.assertNotNull(submitBtn.getDomAttribute("disabled"));

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:4200/login", currentUrl);
    }

    // 7
    @Test
    public void testUnauthorizedUserAccess() {
        WebElement homeLink = driver.findElement(By.id("home-link"));
        homeLink.click();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlContains("/login"));

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:4200/login", currentUrl);
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
