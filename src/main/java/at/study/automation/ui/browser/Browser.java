package at.study.automation.ui.browser;

import at.study.automation.model.property.Property;
import io.qameta.allure.Attachment;
import lombok.Getter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.util.concurrent.TimeUnit.SECONDS;

@Getter
public class Browser {

    private final WebDriver driver;
    private final WebDriverWait wait;

    Browser() {
        this("");
    }

    Browser(String uri) {
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        int timeout = Property.getIntegerProperty("element.timeout");
        driver.manage().timeouts().implicitlyWait(timeout, SECONDS);
        wait = new WebDriverWait(driver, timeout);
        get(uri);
    }

    /**
     * Метод позволяет получить полный uri, необходимый, для доступа к какому-либо
     * ресурсу приложения.
     *
     * @param uri - уникальный эндпоинт для доступа какому-либо ресурсу приложения.
     */
    public void get(String uri) {
        getDriver().get(Property.getStringProperty("url") + uri);
    }

    /**
     * Метод позволяет обновить страницу браузера.
     */
    public void refresh() {
        getDriver().navigate().refresh();
    }

    /**
     * Метод позволяет сделать скриншот страницы, во время прогона теста.
     *
     * @return возвращает поток байтов из которых состоит скриншот.
     */
    @Attachment("Скриншот")
    public byte[] takeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    /**
     * Метод выполняет какой-либо полученный в параметрах скрипт на языке Javascript, подставляя в него аргументы из параметра метода.
     *
     * @param js   - переданный для выполнения в методе скрипт на языке Javascript.
     * @param args - аргументы, которые нужно будет подставить в скрипт на языке Javascript.
     */
    public void executeJavascript(String js, Object... args) {
        ((JavascriptExecutor) driver).executeScript(js, args);
    }

    /**
     * Метод позволяет создать эмуляции сложных пользовательских жестов, не используя клавиатуру или мышь напрямую.
     *
     * @return возвращает объект типа Actions, который представляет собой эмуляцию пользовательского жеста.
     */
    public Actions actions() {
        return new Actions(driver);
    }
}
