package at.study.automation.cucumber;

import at.study.automation.cucumber.validators.annotations.ElementName;
import at.study.automation.cucumber.validators.annotations.PageName;
import at.study.automation.ui.pages.Page;
import lombok.SneakyThrows;
import org.openqa.selenium.WebElement;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class PageObjectHelper {

    /**
     * Метод ищет элемент на указанной странице.
     *
     * @param pageName    - имя страницы на которой нужно найти элемент.
     * @param elementName - имя веб-элемента, который нужно найти.
     * @return возвращает найденный веб-элемент.
     */
    public static WebElement findElement(String pageName, String elementName) {
        return getElement(getPage(pageName), elementName);
    }

    /**
     * Метод ищет список элементов на указанной странице.
     *
     * @param pageName     - имя страницы на которой нужно найти список веб-элементов.
     * @param elementsName - имя списка веб-элементов.
     * @return возвращает список веб-элементов.
     */
    public static List<WebElement> findElements(String pageName, String elementsName) {
        return getElements(getPage(pageName), elementsName);
    }

    /**
     * Метод возвращает страницу типа Page или страницу, которая наследуется от класса Page.
     *
     * @param pageName - имя страницы, которое указано в аннотации в классе, описывающем эту страницу.
     * @param <T>      - указывает какой тип данных должен быть у каждого Т в методе.
     * @return - возвращает объект страницы, которая приводится к типу, указанным в Т.
     */
    private static <T extends Page> T getPage(String pageName) {
        Set<Class<? extends Page>> allPages = new Reflections("at.study.automation.ui.pages").getSubTypesOf(Page.class);
        Class<? extends Page> pageObjectClass = allPages.stream()
                .filter(page -> page.isAnnotationPresent(PageName.class))
                .filter(page -> page.getAnnotation(PageName.class).value().equals(pageName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Не найдено страницы с аннотацией @PageName(\"" + pageName + "\")."));
        return (T) Page.getPage(pageObjectClass);
    }

    /**
     * Метод позволяет вернуть веб-элемент, содержащийся на странице.
     *
     * @param page        - объект страницы, на которой нужно найти веб-элемент.
     * @param elementName - имя веб-элемента, которое указано в аннотации, в классе реализации страницы.
     * @return возвращает объект на странице, приводя его в тип WebElement.
     */
    private static WebElement getElement(Page page, String elementName) {
        return (WebElement) getObject(page, elementName);
    }

    /**
     * Метод позволяет вернуть веб-элементы, содержащийся на странице.
     *
     * @param page         - объект страницы, на которой нужно найти веб-элементы.
     * @param elementsName - имя коллекции веб-элементов, которое указано в аннотации, в классе реализации страницы.
     * @return возвращает объект на странице, приводя его в тип List<WebElement>.
     */
    private static List<WebElement> getElements(Page page, String elementsName) {
        return (List<WebElement>) getObject(page, elementsName);
    }

    /**
     * Метод позволяет вернуть веб-элементы, содержащийся на странице в виде объекта типа Object.
     *
     * @param page        - объект страницы, на которой нужно найти веб-элементы.
     * @param elementName - имя элемента, которое указано в аннотации, в классе реализации страницы.
     * @return возвращает объект на странице, в виде объекта типа Object.
     */
    @SneakyThrows
    private static Object getObject(Page page, String elementName) {
        Field[] allFields = page.getClass().getDeclaredFields();
        Field elementField = Stream.of(allFields)
                .filter(field -> field.isAnnotationPresent(ElementName.class))
                .filter(field -> field.getAnnotation(ElementName.class).value().equals(elementName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Не найдено элемента с аннотацией @ElementName(\"" + elementName + "\")"));
        elementField.setAccessible(true);
        return elementField.get(page);
    }
}
