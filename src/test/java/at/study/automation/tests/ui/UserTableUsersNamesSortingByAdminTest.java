package at.study.automation.tests.ui;

import at.study.automation.model.user.User;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static at.study.automation.allure.AllureAssert.assertEquals;
import static at.study.automation.allure.AllureAssert.assertTrue;
import static at.study.automation.ui.browser.BrowserUtils.*;
import static at.study.automation.utils.CompareUtils.assertListSortedByAsc;
import static at.study.automation.utils.CompareUtils.assertListSortedByDesc;

public class UserTableUsersNamesSortingByAdminTest extends BaseUiTest {
    private User admin;

    @BeforeMethod(description = "В системе заведен пользователь с правами администратора, " +
            "а также несколько пользователей без прав администратора. Открыт браузер на главной странице.")
    public void prepareFixtures() {
        admin = new User() {{
            setIsAdmin(true);
        }}.create();

        User user1 = new User().create();
        User user2 = new User().create();
        User user3 = new User().create();
        User user4 = new User().create();
        User user5 = new User().create();

        openBrowser();
    }

    @Test(description = "Администрирование. Сортировка списка пользователей по пользователь")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Савельев Андрей Владимирович")
    public void userTableUserNamesSortingByAdminTest() {
        click(headerPage.loginButton);

        loginPage.login(admin);
        assertEquals(
                homePage.homePageHeader.getText(),
                "Домашняя страница",
                "Текст элемента \"Домашняя страница\""
        );

        headerPage.administration.click();
        assertEquals(
                administrationPage.administrationHeader.getText(),
                "Администрирование",
                "Текст элемента \"Администрирование\""
        );

        administrationPage.users.click();

        assertTrue(
                isElementPresent(userTablePage.usersTable),
                "Таблица с пользователями отображается"
        );

        click(userTablePage.button("Пользователь"));
        List<String> usersByDesc = getElementsText(userTablePage.usersLogins);
        assertListSortedByDesc(usersByDesc);

        click(userTablePage.button("Пользователь"));
        List<String> usersByAsc = getElementsText(userTablePage.usersLogins);
        assertListSortedByAsc(usersByAsc);
    }
}
