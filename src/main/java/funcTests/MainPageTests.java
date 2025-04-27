package funcTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainPageTests {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        Path driverPath = Paths.get("drivers", "chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", driverPath.toAbsolutePath().toString());

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--force-device-scale-factor=0.75");

        // Запуск в инкогнито, чтобы алерты не бесили
        options.addArguments("--incognito");

        driver = new ChromeDriver(options);
        driver.get("http://localhost:4200");

        driver.findElement(By.id("username")).sendKeys("kirill");
        driver.findElement(By.id("password")).sendKeys("12345");
        driver.findElement(By.id("submit")).click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(webDriver -> webDriver.getCurrentUrl().equals("http://localhost:4200/home"));

        driver.manage().window().maximize();
    }

    // 14
    @Test
    public void testLogout() {
        WebElement logoutBtn = driver.findElement(By.id("logout-button"));
        logoutBtn.click();

        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        Long localStorageLength = (Long) jsExecutor.executeScript("return window.localStorage.length;");
        assertEquals(0, localStorageLength, "localStorage isn't empty");

    }

    // 15
    @Test
    public void testHitByClick() {
        // x = 1 | y = 1.5 | r = 5
        WebElement canvas = driver.findElement(By.id("coordinate-plane"));
        WebElement table = driver.findElement(By.id("points-table"));

        List<WebElement> rowsBefore = table.findElements(By.cssSelector("tbody tr"));
        int initialRowCount = rowsBefore.size();

        Actions actions = new Actions(driver);
        actions.moveToElement(canvas, 60, -90).click().perform();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(
                        By.cssSelector("table tbody tr"), initialRowCount
                ));

        List<WebElement> updatedRows = table.findElements(By.cssSelector("tbody tr"));
        WebElement newRow = updatedRows.get(0);

        List<WebElement> cells = newRow.findElements(By.tagName("td"));

        assertEquals("1", cells.get(0).getText());
        assertEquals("1.5", cells.get(1).getText());
        assertEquals("5", cells.get(2).getText());
        assertEquals("Hit", cells.get(3).getText());
    }

    // 16
    @Test
    public void testHitByInputs() {
        // x = -2 | y = -2 | r = 3
        WebElement table = driver.findElement(By.id("points-table"));

        WebElement xFieldset = driver.findElement(By.id("xFieldset"));
        WebElement xRadioButton = xFieldset.findElement(By.cssSelector("input[type='radio'][ng-reflect-value='-2']"));
        xRadioButton.click();

        WebElement ySlider = driver.findElement(By.id("ySlider"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input'));", ySlider, "-2");

        WebElement rFieldset = driver.findElement(By.id("rFieldset"));
        WebElement rRadioButton = rFieldset.findElement(By.cssSelector("input[type='radio'][ng-reflect-value='3']"));
        rRadioButton.click();

        List<WebElement> rowsBefore = table.findElements(By.cssSelector("tbody tr"));
        int initialRowCount = rowsBefore.size();

        driver.findElement(By.id("submit")).click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(
                        By.cssSelector("table tbody tr"), initialRowCount
                ));

        List<WebElement> updatedRows = table.findElements(By.cssSelector("tbody tr"));
        WebElement newRow = updatedRows.get(0);

        List<WebElement> cells = newRow.findElements(By.tagName("td"));

        assertEquals("-2", cells.get(0).getText());
        assertEquals("-2", cells.get(1).getText());
        assertEquals("3", cells.get(2).getText());
        assertEquals("Miss", cells.get(3).getText());
    }

    // 17
    @Test
    public void testHitTrue() {
        // x = -2 | y = -1 | r = 4
        WebElement table = driver.findElement(By.id("points-table"));

        WebElement xFieldset = driver.findElement(By.id("xFieldset"));
        WebElement xRadioButton = xFieldset.findElement(By.cssSelector("input[type='radio'][ng-reflect-value='-2']"));
        xRadioButton.click();

        WebElement ySlider = driver.findElement(By.id("ySlider"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input'));", ySlider, "-1");

        WebElement rFieldset = driver.findElement(By.id("rFieldset"));
        WebElement rRadioButton = rFieldset.findElement(By.cssSelector("input[type='radio'][ng-reflect-value='4']"));
        rRadioButton.click();

        List<WebElement> rowsBefore = table.findElements(By.cssSelector("tbody tr"));
        int initialRowCount = rowsBefore.size();

        driver.findElement(By.id("submit")).click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(
                        By.cssSelector("table tbody tr"), initialRowCount
                ));

        List<WebElement> updatedRows = table.findElements(By.cssSelector("tbody tr"));
        WebElement newRow = updatedRows.get(0);

        List<WebElement> cells = newRow.findElements(By.tagName("td"));

        assertEquals("-2", cells.get(0).getText());
        assertEquals("-1", cells.get(1).getText());
        assertEquals("4", cells.get(2).getText());
        assertEquals("Hit", cells.get(3).getText());
    }

    // 18
    @Test
    public void testHitFalse() {
        // x = 3 | y = -1 | r = 2
        WebElement table = driver.findElement(By.id("points-table"));

        WebElement xFieldset = driver.findElement(By.id("xFieldset"));
        WebElement xRadioButton = xFieldset.findElement(By.cssSelector("input[type='radio'][ng-reflect-value='3']"));
        xRadioButton.click();

        WebElement ySlider = driver.findElement(By.id("ySlider"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input'));", ySlider, "-1");

        WebElement rFieldset = driver.findElement(By.id("rFieldset"));
        WebElement rRadioButton = rFieldset.findElement(By.cssSelector("input[type='radio'][ng-reflect-value='2']"));
        rRadioButton.click();

        List<WebElement> rowsBefore = table.findElements(By.cssSelector("tbody tr"));
        int initialRowCount = rowsBefore.size();

        driver.findElement(By.id("submit")).click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(
                        By.cssSelector("table tbody tr"), initialRowCount
                ));

        List<WebElement> updatedRows = table.findElements(By.cssSelector("tbody tr"));
        WebElement newRow = updatedRows.get(0);

        List<WebElement> cells = newRow.findElements(By.tagName("td"));

        assertEquals("3", cells.get(0).getText());
        assertEquals("-1", cells.get(1).getText());
        assertEquals("2", cells.get(2).getText());
        assertEquals("Miss", cells.get(3).getText());
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
