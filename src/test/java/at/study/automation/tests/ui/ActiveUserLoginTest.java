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

public class ActiveUserLoginTest extends BaseUiTest {

    private User activeUser;

    @BeforeMethod(description = "В системе заведен пользователь без прав администратора. Открыт браузер на главной странице.")
    public void prepareFixtures() {
        activeUser = new User().create();

        openBrowser();
    }

    @Test(description = "Авторизация подтвержденным пользователем")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Савельев Андрей Владимирович")
    public void testActiveUserLogin() {
        click(headerPage.loginButton);
        loginPage.login(activeUser);

        assertEquals(
                homePage.homePageHeader.getText(),
                "Домашняя страница",
                "Текст элемента \"Домашняя страница\""
        );
        assertEquals(
                headerPage.userActive.getText(),
                "Вошли как " + activeUser.getLogin(),
                "Текст элемента \"Вошли как \"" + activeUser.getLogin()
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
        assertFalse(isElementPresent(
                        headerPage.administration),
                "Элемент не отображается"
        );
        assertFalse(isElementPresent(
                        headerPage.loginButton),
                "Элемент не отображается"
        );
        assertFalse(isElementPresent(
                        headerPage.register),
                "Элемент не отображается"
        );
        assertTrue(isElementPresent(
                        headerPage.search),
                "Элемент отображается"
        );
    }
}
