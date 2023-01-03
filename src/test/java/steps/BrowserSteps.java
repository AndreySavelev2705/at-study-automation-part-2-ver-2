package steps;

import at.study.automation.ui.browser.BrowserManager;
import cucumber.api.java.ru.Дано;

public class BrowserSteps {

    /**
     * Метод открывает браузер на странице, чей uri передан в параметрах метода.
     *
     * @param url - адрес страницы, на который нужно перейти при открытии браузера.
     */
    @Дано("Открыт браузер на странице \"(.+)\"")
    public void openBrowserOnPage(String url) {
        BrowserManager.getBrowser(url);
    }

    /**
     * Метод открывает браузер на странице по умолчанию.
     */
    @Дано("Открыт браузер на главной странице")
    public void openBrowserOnPage() {
        BrowserManager.getBrowser();
    }
}
