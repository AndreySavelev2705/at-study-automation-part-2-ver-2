package at.study.automation.ui.pages;

import at.study.automation.ui.browser.BrowserManager;
import lombok.SneakyThrows;
import org.openqa.selenium.support.PageFactory;

public abstract class Page {

    /**
     * Метод позволяет вернуть объект-страницу и инициализировать ее веб-элементы
     * по переданному классу страницы в метод класса странницы.
     *
     * @param clazz - класс страницы, чей объект нужно вернуть.
     * @param <T>   - указывает какой тип данных должен быть у каждого Т, в методе.
     * @return возвращает объект-страницу с инициализированными веб элементами,
     * которая соответствует классу переданному в параметрах метода.
     */
    @SneakyThrows
    public static <T extends Page> T getPage(Class<T> clazz) {
        T page = clazz.getDeclaredConstructor().newInstance();
        PageFactory.initElements(BrowserManager.getBrowser().getDriver(), page);
        return page;
    }
}
