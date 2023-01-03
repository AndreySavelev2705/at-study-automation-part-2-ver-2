package at.study.automation.tests.ui;

import at.study.automation.db.requests.UserRequests;
import at.study.automation.model.user.User;
import at.study.automation.utils.StringUtils;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static at.study.automation.allure.AllureAssert.assertEquals;
import static at.study.automation.ui.browser.BrowserUtils.click;
import static at.study.automation.ui.browser.BrowserUtils.sendKeys;

public class CreateNewUserPageByAdminTest extends BaseUiTest {

    private User admin;
    private User userForCreating;

    @BeforeMethod(description = "В системе заведен пользователь с правами администратора. Открыт браузер на главной странице.")
    public void prepareFixtures() {
        admin = new User() {{
            setIsAdmin(true);
        }}.create();

        userForCreating = new User();

        openBrowser();
    }

    @Test(description = "Администрирование. Создание пользователя.")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Савельев Андрей Владимирович")
    public void addNewUserByAdminTest() {
        click(headerPage.loginButton);

        loginPage.login(admin);
        assertEquals(
                homePage.homePageHeader.getText(),
                "Домашняя страница",
                "Текст элемента \"Домашняя страница\""
        );

        click(headerPage.administration);
        assertEquals(
                administrationPage.administrationHeader.getText(),
                "Администрирование",
                "Текст элемента \"Администрирование\""
        );

        click(administrationPage.users);

        click(userTablePage.addNewUser);
        assertEquals(
                createNewUserPage.breadCrumbs.getText(),
                "Пользователи » Новый пользователь",
                "Структура хлебных крошек"
        );

        createUser(userForCreating);

        assertEquals(
                createNewUserPage.flashNotice.getText(),
                "Пользователь " + userForCreating.getLogin() + " создан.",
                "Сообщение \"Пользователь создан\""
        );

        User newUser = new UserRequests().read(userForCreating.getLogin());
        assertEquals(
                newUser.getLogin(),
                userForCreating.getLogin(),
                "Проверка, что пользователь создан в бд"
        );
    }

    private void createUser(User userForCreating) {
        sendKeys(createNewUserPage.userLogin, userForCreating.getLogin());
        sendKeys(createNewUserPage.userFirstName, userForCreating.getFirstName());
        sendKeys(createNewUserPage.userLastName, userForCreating.getLastName());
        sendKeys(createNewUserPage.userMail, StringUtils.randomEmail());
        click(createNewUserPage.generatePassword);
        click(createNewUserPage.create);
    }
}
