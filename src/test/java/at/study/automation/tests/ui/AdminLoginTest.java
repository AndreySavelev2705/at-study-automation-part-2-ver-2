package at.study.automation.tests.ui;

import at.study.automation.model.user.User;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static at.study.automation.allure.AllureAssert.*;
import static at.study.automation.ui.browser.BrowserUtils.click;
import static at.study.automation.ui.browser.BrowserUtils.isElementPresent;

public class AdminLoginTest extends BaseUiTest {
    private User admin;

    @BeforeMethod(description = "В системе заведен пользователь с праавами администратора. Открыт браузер на главной странице.")
    public void prepareFixtures() {
        admin = new User() {{
            setIsAdmin(true);
        }}.create();

        openBrowser();
    }

    @Test(description = "Авторизация администратором")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Савельев Андрей Владимирович")
    public void positiveAdminLoginTest() {
        click(headerPage.loginButton);
        loginPage.login(admin);

        assertEquals(
                homePage.homePageHeader.getText(),
                "Домашняя страница",
                "Текст элемента \"Домашняя страница\""
        );
        assertEquals(
                headerPage.userActive.getText(),
                "Вошли как " + admin.getLogin(),
                "Текст элемента \"Вошли как \"" + admin.getLogin()
        );
        assertEquals(
                headerPage.homePage.getText(),
                "Домашняя страница",
                "Текст элемента \"Домашняя страница\""
        );
        assertEquals(
                headerPage.myPage.getText(),
                "Моя страница",
                "Текст элемента \"Моя страница\""
        );
        assertEquals(
                headerPage.projects.getText(),
                "Проекты",
                "Текст элемента \"Проекты\""
        );
        assertEquals(
                headerPage.help.getText(),
                "Помощь",
                "Текст элемента \"Помощь\""
        );
        assertEquals(
                headerPage.myAccount.getText(),
                "Моя учётная запись",
                "Текст элемента \"Моя учетная запись\""
        );
        assertEquals(
                headerPage.logout.getText(),
                "Выйти",
                "Текст элемента \"Выйти\""
        );
        assertEquals(
                headerPage.administration.getText(),
                "Администрирование",
                "Текст элемента \"Администрирование\""
        );
        assertFalse(
                isElementPresent(headerPage.loginButton),
                "Элемент не отображается"
        );
        assertFalse(
                isElementPresent(headerPage.register),
                "Элемент не отображается"
        );
        assertTrue(
                isElementPresent(headerPage.search),
                "Элемент отображается"
        );
    }
}
