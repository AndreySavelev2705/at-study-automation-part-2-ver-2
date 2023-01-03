package at.study.automation.allure;

import io.qameta.allure.Step;
import org.testng.Assert;

public class AllureAssert {

    @Step("Проверка равенства: {2}")
    public static void assertEquals(Object object, Object expected, String message) {
        Assert.assertEquals(object, expected, message);
    }

    @Step("Проверка равенства:")
    public static void assertEquals(Object object, Object expected) {
        Assert.assertEquals(object, expected);
    }

    @Step("Проверка на истиность ложь:")
    public static void assertFalse(boolean elementPresent) {
        Assert.assertFalse(elementPresent);
    }

    @Step("Проверка на истиность ложь: {1}")
    public static void assertFalse(boolean elementPresent, String message) {
        Assert.assertFalse(elementPresent);
    }

    @Step("Проверка истинность истина:")
    public static void assertTrue(boolean elementPresent) {
        Assert.assertTrue(elementPresent);
    }

    @Step("Проверка истинность истина: {1}")
    public static void assertTrue(boolean elementPresent, String message) {
        Assert.assertTrue(elementPresent);
    }

    @Step("Проверка на null ложь: ")
    public static void assertNotNull(Object object) {
        Assert.assertNotNull(object);
    }

    @Step("Проверка на null ложь: {1}")
    public static void assertNotNull(Object object, String message) {
        Assert.assertNotNull(object);
    }

    @Step("Проверка на null: ")
    public static void assertNull(Object object) {
        Assert.assertNull(object);
    }

    @Step("Проверка на null: {1}")
    public static void assertNull(Object object, String message) {
        Assert.assertNull(object);
    }
}
