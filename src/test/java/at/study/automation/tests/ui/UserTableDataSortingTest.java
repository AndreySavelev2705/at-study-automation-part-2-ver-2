package at.study.automation.tests.ui;

import at.study.automation.model.user.User;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static at.study.automation.ui.browser.BrowserUtils.click;
import static at.study.automation.ui.browser.BrowserUtils.getElementsText;
import static at.study.automation.utils.CompareUtils.assertListSortedByDateAsc;
import static at.study.automation.utils.CompareUtils.assertListSortedByDateDesc;

public class UserTableDataSortingTest extends BaseUiTest {

    @BeforeMethod(description = "Создание пользователя с правами администратора и переход на страницу \"Пользователи\". " +
            "Открыт браузер на главной странице")
    public void prepareFixtures() {
        User admin = new User() {{
            setIsAdmin(true);
        }}.create();

        openBrowser("/login");
        loginPage.login(admin);
        click(headerPage.administration);
        click(administrationPage.users);
    }

    @Test(description = "Администрирование. Пользователи. Проверка сортировки списка дат в таблице пользователей")
    public void testUsersuTableDateSorting() {
        click(userTablePage.button("Создано"));
        List<String> creationDatesByDesc = getElementsText(userTablePage.creationDates);
        assertListSortedByDateDesc(creationDatesByDesc);

        click(userTablePage.button("Создано"));
        List<String> creationDatesByAsc = getElementsText(userTablePage.creationDates);
        assertListSortedByDateAsc(creationDatesByAsc);
    }
}
