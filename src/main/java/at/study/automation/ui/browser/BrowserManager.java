package at.study.automation.ui.browser;

public class BrowserManager {

    private static ThreadLocal<Browser> browser = new ThreadLocal<>();

    /**
     * Метод позволяет открыть браузер.
     *
     * @return возвращает открытый браузер.
     */
    public static Browser getBrowser() {
        if (browser.get() == null) {
            browser.set(new Browser());
        }
        return browser.get();
    }

    /**
     * Метод позволяет открыть браузер сразу на нужной странице, адрес которой был передан в uri в параметрах метода.
     *
     * @param uri - адрес страницы, который нужно открыть при открытии браузера.
     * @return возвращает браузер открытый на нужной странице, адрес которой был передан в uri в параметрах метода.
     */
    public static Browser getBrowser(String uri) {
        if (browser.get() == null) {
            browser.set(new Browser(uri));
        }
        return browser.get();
    }

    /**
     * Метод позволяет закрыть браузер.
     */
    public static void closeBrowser() {
        if (browser.get() != null) {
            browser.get().takeScreenshot();
            browser.get().getDriver().quit();
            browser.set(null);
        }
    }
}
