package at.study.automation.ui.browser;

import at.study.automation.model.property.Property;
import com.google.common.collect.ImmutableMap;
import lombok.SneakyThrows;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.Map;

public class DriverFactory {

    private static final String BROWSER_NAME = Property.getStringProperty("browser");
    private static final Boolean IS_REMOTE = Property.getBooleanProperty("remote");
    private static final String BROWSER_VERSION = Property.getStringProperty("browser.version");
    private static final Boolean ENABLE_VNC = Property.getBooleanProperty("enable.vnc");
    private static final Boolean ENABLE_VIDEO = Property.getBooleanProperty("enable.video");
    private static final String SELENOID_HUB_URL = Property.getStringProperty("selenoid.hub.url");

    /**
     * Метод позволяет получить драйвер браузера, указанного в файле конфигурации.
     *
     * @return возвращает драйвер браузера.
     */
    @SneakyThrows
    static WebDriver getDriver() {

        if (IS_REMOTE) {
            MutableCapabilities capabilities = new MutableCapabilities();
            capabilities.setCapability("browserName", BROWSER_NAME);
            capabilities.setCapability("browserVersion", BROWSER_VERSION);

            Map<String, Object> selenoidOptions = ImmutableMap.of(
                    "enableVNC", ENABLE_VNC,
                    "enableVideo", ENABLE_VIDEO
            );

            capabilities.setCapability("selenoid:options", selenoidOptions);

            return new RemoteWebDriver(
                    new URL(SELENOID_HUB_URL),
                    capabilities
            );
        } else {
            switch (BROWSER_NAME) {
                case "chrome":
                    System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
                    return new ChromeDriver();
                case "firefox":
                    System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver.exe");
                    return new FirefoxDriver();
                default:
                    throw new IllegalArgumentException("Неизвестный браузер" + BROWSER_NAME);
            }
        }
    }
}
