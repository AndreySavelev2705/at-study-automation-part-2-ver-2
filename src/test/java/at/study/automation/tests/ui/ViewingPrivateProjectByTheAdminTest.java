package at.study.automation.tests.ui;

import at.study.automation.model.project.Project;
import at.study.automation.model.user.User;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static at.study.automation.allure.AllureAssert.assertEquals;
import static at.study.automation.allure.AllureAssert.assertTrue;
import static at.study.automation.ui.browser.BrowserUtils.click;
import static at.study.automation.ui.browser.BrowserUtils.isElementPresent;

public class ViewingPrivateProjectByTheAdminTest extends BaseUiTest {

    private User admin;
    private Project project;

    @BeforeMethod(description = "В системе заведен пользователь с правами администратора, " +
            "Создан приватный проект, не привязанный к пользователю. Открыт браузер на главной странице.")
    public void prepareFixtures() {
        admin = new User() {{
            setIsAdmin(true);
        }}.create();

        project = new Project() {{
            setIsPublic(false);
        }}.create();

        openBrowser();
    }

    // TODO: Нужно дорабатывать
    @Test(description = "Видимость проекта. Приватный проект. Администратор")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Савельев Андрей Владимирович")
    public void viewingPrivateProjectByTheAdminTest() {
        click(headerPage.loginButton);
        loginPage.login(admin);

        assertEquals(
                homePage.homePageHeader.getText(),
                "Домашняя страница",
                "Текст элемента \"Домашняя страница\""
        );

        click(headerPage.projects);

        assertTrue(
                isElementPresent(projectsPage.getProject(project.getName())),
                "Элемент отображается"
        );
        assertEquals(
                projectsPage.getProject(project.getName()).getText(),
                project.getName(),
                "Имя проекта " + "\"" + project.getName() + "\""
        );
        assertEquals(projectsPage.getProjectDescription(project.getDescription()).getText(),
                project.getDescription(),
                "Описание проекта " + "\"" + project.getDescription() + "\""
        );
    }
}
