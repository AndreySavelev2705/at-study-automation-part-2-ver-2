package at.study.automation.tests.ui;

import at.study.automation.model.user.Status;
import at.study.automation.model.user.User;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static at.study.automation.allure.AllureAssert.assertEquals;
import static at.study.automation.allure.AllureAssert.assertFalse;
import static at.study.automation.ui.browser.BrowserUtils.click;
import static at.study.automation.ui.browser.BrowserUtils.isElementPresent;

public class UnacceptedUserLoginTest extends BaseUiTest {

    private User unacceptedUser;

    @BeforeMethod(description = "Создание пользователя без прав администратора со статусом \"Не подтвержден\". " +
            "Открыт браузер на главной странице")
    public void prepareFixtures() {
        unacceptedUser = new User() {{
            setStatus(Status.UNACCEPTED);
        }}.create();

        openBrowser();
    }

    @Test(description = "Авторизация неподтвержденным пользователем")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Савельев Андрей Владимирович")
    public void testUnacceptedUserLogin() {
        click(headerPage.loginButton);
        loginPage.login(unacceptedUser);


        assertEquals(
                loginPage.errorFlash.getText(),
                "Ваша учётная запись создана и ожидает подтверждения администратора.",
                "Текст элемента \"Ваша учётная запись создана и ожидает подтверждения администратора.\""
        );
        assertFalse(
                isElementPresent(headerPage.myPage),
                "Элемент не отображается"
        );
        assertEquals(
                headerPage.loginButton.getText(),
                "Войти",
                "Текст элемента \"Войти\""
        );
        assertEquals(
                headerPage.register.getText(),
                "Регистрация",
                "Текст элемента \"Регистрация\""
        );
    }
}
