package at.study.automation.ui.browser;

import at.study.automation.model.property.Property;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class BrowserUtils {

    /**
     * Метод возвращает список с текстом веб-элементов.
     *
     * @param elements - список веб элементов, который нужно преобразовать в список, содержащий текст этих веб-элементов.
     * @return возвращает список с текстом веб-элементов.
     */
    public static List<String> getElementsText(List<WebElement> elements) {
        return elements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Метод проверяет отображение переданного в параметрах веб-элемента, и возвращает логический результат этой проверки.
     *
     * @param webElement - веб-элемент, который нужно проверить на отображаемость.
     * @return возвращает true, если веб-элемент отображается, или false, если веб-элемент не отображается.
     */
    public static boolean isElementPresent(WebElement webElement) {
        try {
            BrowserManager.getBrowser().getDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            return webElement.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        } finally {
            BrowserManager.getBrowser().getDriver().manage().timeouts().implicitlyWait(Property.getIntegerProperty("element.timeout"), TimeUnit.SECONDS);
        }
    }

    /**
     * Метод проверяет отображение веб-элемента, который соответствует проекту по переданному в параметрах имени проекта,
     * и возвращает логический результат этой проверки.
     *
     * @param projectName - имя проекта, по которому производится поиск на странице.
     * @return возвращает true, если веб-элемент отображается, или false, если веб-элемент не отображается.
     */
    public static boolean isElementPresent(String projectName) {
        try {
            BrowserManager.getBrowser().getDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            WebElement webElement = BrowserManager.getBrowser().getDriver().findElement(By.xpath("//div[@id='projects-index']//a[text()='" + projectName + "']"));
            return webElement.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        } finally {
            BrowserManager.getBrowser().getDriver().manage().timeouts().implicitlyWait(Property.getIntegerProperty("element.timeout"), TimeUnit.SECONDS);
        }
    }

    /**
     * Метод позволяет выполнить клик по переданному в параметрах веб-элементу.
     *
     * @param element веб-элемент, по которому необходимо кликнуть.
     */
    @Step("Нажатие на элемент {1}")
    public static void click(WebElement element) {
        element.click();
    }

    /**
     * Метод позволяет ввести текст, переданный в параметрах, в веб-элемент, который также передан в параметрах метода.
     *
     * @param element - веб-элемент, в который нужно ввести переданный в параметрах метода текст.
     * @param text    - текст, который нужно ввести, в переданный в параметрах метода веб-элемент.
     */
    @Step("Ввод: {1}")
    public static void sendKeys(WebElement element, String text) {
        element.sendKeys(element.getText(), text);
    }
}
