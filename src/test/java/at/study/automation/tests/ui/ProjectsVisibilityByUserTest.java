package at.study.automation.tests.ui;

import at.study.automation.model.project.Project;
import at.study.automation.model.role.Permission;
import at.study.automation.model.role.Role;
import at.study.automation.model.user.User;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

import static at.study.automation.allure.AllureAssert.assertEquals;
import static at.study.automation.allure.AllureAssert.assertFalse;
import static at.study.automation.ui.browser.BrowserUtils.click;
import static at.study.automation.ui.browser.BrowserUtils.isElementPresent;

public class ProjectsVisibilityByUserTest extends BaseUiTest {
    private User user;

    private Project project1;
    private Project project2;
    private Project project3;

    @BeforeMethod(description = "В системе заведен пользователь без администратора, " +
            "но подтвержденный администратором и не заблокированный. " +
            "В системе заведена роль пользователя с правами на просмотр задач. " +
            "В системе заведены 3 проекта: 2 приватных и 1 публичный. " +
            "Добавление пользователю доступа к одному приватному проекту. " +
            "Открыт браузер на главной странице.")
    public void prepareFixtures() {

        List<Permission> permissions = Collections.singletonList(
                Permission.VIEW_ISSUES
        );

        Role role = new Role() {{
            setPermissions(permissions);
        }}.create();

        project1 = new Project().create();

        project2 = new Project() {{
            setIsPublic(false);
        }}.create();

        project3 = new Project() {{
            setIsPublic(false);
        }};

        user = new User() {{
            addProject(project3, Collections.singletonList(role));
        }}.create();

        project3.addUser(user, Collections.singletonList(role));
        project3.create();

        openBrowser();
    }

    @Test(description = "Видимость проекта. Приватный проект. Администратор")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Савельев Андрей Владимирович")
    public void projectsVisibilityByUserTest() {
        click(headerPage.loginButton);
        loginPage.login(user);

        assertEquals(
                homePage.homePageHeader.getText(),
                "Домашняя страница",
                "Текст элемента \"Домашняя страница\""
        );

        click(headerPage.projects);

        assertEquals(
                projectsPage.getProject(project1.getName()).getText(),
                project1.getName(),
                "Имя проекта " + "\"" + project1.getName() + "\""
        );
        assertEquals(
                projectsPage.getProjectDescription(project1.getDescription()).getText(),
                project1.getDescription(),
                "Описание проекта " + "\"" + project1.getDescription() + "\""
        );
        assertFalse(
                isElementPresent(project2.getName()),
                "Элемент не отображается"
        );
        assertEquals(
                projectsPage.getProject(project3.getName()).getText(),
                project3.getName(),
                "Имя проекта " + "\"" + project3.getName() + "\""
        );
        assertEquals(projectsPage.getProjectDescription(project3.getDescription()).getText(),
                project3.getDescription(),
                "Описание проекта " + "\"" + project3.getDescription() + "\""
        );
    }
}
