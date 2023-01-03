package steps;

import at.study.automation.context.Context;
import at.study.automation.cucumber.PageObjectHelper;
import at.study.automation.model.project.Project;
import at.study.automation.model.user.User;
import at.study.automation.ui.browser.BrowserUtils;
import at.study.automation.ui.pages.HeaderPage;
import at.study.automation.ui.pages.LoginPage;
import at.study.automation.ui.pages.ProjectsPage;
import at.study.automation.ui.pages.UserTablePage;
import at.study.automation.utils.CompareUtils;
import at.study.automation.utils.StringUtils;
import cucumber.api.java.ru.*;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static at.study.automation.allure.AllureAssert.*;
import static at.study.automation.ui.browser.BrowserUtils.*;
import static at.study.automation.ui.pages.LoginPage.getPage;
import static at.study.automation.utils.CompareUtils.assertListSortedByDateDesc;

public class UiSteps {

    /**
     * Метод позволяет выполнить авторизацию юзером.
     *
     * @param userStashId - ключ по которому будет доставаться юзер из хранилища Context.
     */
    @И("Авторизоваться как пользователь \"(.+)\"")
    public void authByUser(String userStashId) {
        User user = Context.getStash().get(userStashId, User.class);
        getPage(LoginPage.class).login(user);
    }

    /**
     * Метод позволяет выполнить авторизацию по логину и паролю.
     *
     * @param login    - логин, который нужен для авторизации.
     * @param password - пароль, который нужен для авторизации.
     */
    @И("Авторизоваться по логину \"(.+)\" и паролю \"(.+)\"")
    public void authByLoginAndPassword(String login, String password) {
        getPage(LoginPage.class).login(login, password);
    }

    /**
     * Метод позволяет проверить отображение элемента на странице.
     *
     * @param pageName    - имя страницы, по которому можно будет получить объект этой страницы.
     * @param elementName - имя элемента, по которому можно будет получить веб-элемент.
     */
    @И("На странице \"(.+)\" отображается элемент \"(.+)\"")
    public void assertPageElementIsDeployed(String pageName, String elementName) {

        assertTrue(
                isElementPresent(PageObjectHelper.findElement(pageName, elementName)),
                "Элемент отображается"
        );
    }

    /**
     * Метод позволяет проверить, что элемент не отображается на странице.
     *
     * @param pageName    - имя страницы, по которому можно будет получить объект этой страницы.
     * @param elementName - имя элемента, по которому можно будет получить веб-элемент.
     */
    @И("На странице \"(.+)\" не отображается элемент \"(.+)\"")
    public void assertPageElementIsNotDeployed(String pageName, String elementName) {

        assertFalse(
                isElementPresent(PageObjectHelper.findElement(pageName, elementName)),
                "Элемент не отображается"
        );
    }

    /**
     * Метод позволяет проверить текстовку с информаицей, под кем был выпполнен вход в систему.
     *
     * @param userStashId - ключ по которому будет доставаться юзер из хранилища Context.
     */
    @Также("В заголовке страницы текст элемента Вошли как \"(.+)\" верный логин текущего администратора")
    public void assertEnteredAs(String userStashId) {
        User admin = Context.getStash().get(userStashId, User.class);
        assertEquals(
                getPage(HeaderPage.class).userActive.getText(),
                "Вошли как " + admin.getLogin(),
                "Текст элемента \"Вошли как \"" + admin.getLogin()
        );
    }

    /**
     * Метод позволяет выполнить клик по нужному элементу на странице.
     *
     * @param pageName    - имя страницы, по которому можно будет получить объект этой страницы.
     * @param elementName - имя элемента, по которому можно будет получить веб-элемент.
     */
    @Если("На странице \"(.+)\" нажать на элемент \"(.+)\"")
    public void clickOnElementOnPage(String pageName, String elementName) {
        PageObjectHelper.findElement(pageName, elementName).click();
    }

    /**
     * Метод позволяет ввести текст в поле ввода на странице.
     *
     * @param pageName     - имя страницы, по которому можно будет получить объект этой страницы.
     * @param elementName  - имя элемента, по которому можно будет получить веб элемент.
     * @param charSequence - текст, который нужно ввести в поле ввода elementName.
     */
    @И("На странице \"(.+)\" в поле \"(.+)\" ввести текст \"(.+)\"")
    public void sendKeysToElementOnPage(String pageName, String elementName, String charSequence) {
        PageObjectHelper.findElement(pageName, elementName).sendKeys(charSequence);
    }

    /**
     * Метод позволяет проверить, что веб элемент со списком дат отсортирован по дате по убыванию.
     *
     * @param pageName     - имя страницы, по которому можно будет получить объект этой страницы.
     * @param elementsName - имя элемента, по которому можно будет получить веб элемент со списком дат.
     */
    @И("На странице \"(.+)\" тексты элементов \"(.+)\" отсортированы по дате по убыванию")
    public void assertElementsTextsIsSortedByDateDesc(String pageName, String elementsName) {
        List<WebElement> elements = PageObjectHelper.findElements(pageName, elementsName);
        List<String> elementsText = getElementsText(elements);
        assertListSortedByDateDesc(elementsText);
    }

    /**
     * Метод позволяет нажать на кнопку "Войти" на странице HeaderPage.
     */
    @Когда("Нажать на кнопку Войти")
    public void clickOnLoginButton() {
        click(getPage(HeaderPage.class).loginButton);
    }


    /**
     * Метод позволяет проверить, что на странице отображается проект.
     *
     * @param projectStashId - ключ по которому проект будет доставаться из хранилища Context.
     */
    @И("На странице отображается проект \"(.+)\"")
    public void assertProjectIsPresent(String projectStashId) {
        Project project = Context.getStash().get(projectStashId, Project.class);

        assertTrue(
                isElementPresent(
                        project.getName()),
                "Элемент отображается"
        );
    }

    /**
     * Метод позволяет проверить, что на странице не отображается проект.
     *
     * @param projectStashId - ключ по которому проект будет доставаться из хранилища Context.
     */
    @И("На странице не отображается проект \"(.+)\"")
    public void assertProjectIsNotPresent(String projectStashId) {
        Project project = Context.getStash().get(projectStashId, Project.class);

        assertFalse(
                isElementPresent(project.getName()),
                "Проект не отображается"
        );
    }

    /**
     * Метод позволяет проверить, что имя проекта из хранилища совпадает с именем проекта на странице ProjectsPage.
     *
     * @param projectStashId - ключ по которому проект будет доставаться из хранилища Context.
     */
    @И("Имя проекта совпадает с именем проекта \"(.+)\"")
    public void assertProjectStashIdText(String projectStashId) {
        Project project = Context.getStash().get(projectStashId, Project.class);

        assertEquals(
                getPage(ProjectsPage.class).getProject(project.getName()).getText(),
                project.getName(),
                "Имя проекта " + "\"" + project.getName() + "\""
        );
    }

    /**
     * Метод позволяет проверить, что описание проекта из хранилища совпадает с описанием проекта на странице ProjectsPage.
     *
     * @param projectStashId - ключ по которому проект будет доставаться из хранилища Context.
     */
    @И("Описание проекта совпадает с описанием проекта \"(.+)\"")
    public void assertProjectDescriptionText(String projectStashId) {
        Project project = Context.getStash().get(projectStashId, Project.class);

        assertEquals(
                getPage(ProjectsPage.class).getProjectDescription(project.getDescription()).getText(),
                project.getDescription(),
                "Описание проекта " + "\"" + project.getDescription() + "\""
        );
    }

    /**
     * Метод позволяет проверить отображение таблицы с юзерами на странице UserTablePage.
     */
    @Тогда("На странице отображается таблица пользователей")
    public void assertUsersTableIsPresent() {
        UserTablePage userTablePage = getPage(UserTablePage.class);

        assertTrue(
                isElementPresent(userTablePage.usersTable),
                "Таблица с пользователями отображается"
        );
    }

    /**
     * Метод позволяет кликнуть по веб элементу, который является шапкой таблицы с юзерами.
     *
     * @param columnHeadName - текст в заголовке шапки таблицы с юзерами, по которому нужно кликнуть.
     */
    @Тогда("На странице в шапке таблицы нажать на \"(.+)\"")
    public void clickOnColumnHeadName(String columnHeadName) {
        UserTablePage userTablePage = getPage(UserTablePage.class);

        click(userTablePage.button(columnHeadName));
    }

    /**
     * Метод позволяет отсортировать таблицу с юзерами по столбцу,
     * чье имя передается в параметрах метода и является заголовком столбца в шапке таблицы,
     * а также проверить, что столбец отсортирован по типу компаратора, которы, тоже передан в параметрах метода.
     *
     * @param columnHeadName - текст в заголовке шапки таблицы с юзерами, по которому нужно кликнуть.
     * @param comparatorType - тип компаратора по которому нужно провести проверку отсортированности столбца.
     */
    @Тогда("Таблица пользователей отсортирована по столбцу \"(.+)\" по алфавиту \"(.+)\"")
    public void sortingTable(String columnHeadName, String comparatorType) {
        List<WebElement> columnContent = PageObjectHelper.findElements("Пользователи", columnHeadName);

        List<String> columnContentText = BrowserUtils.getElementsText(columnContent);
        Comparator<String> comparator = CompareUtils.getComparator(comparatorType);

        List<String> columnContentTextCopy = new ArrayList<>(columnContentText);
        columnContentTextCopy.sort(comparator);

        assertEquals(columnContentText, columnContentTextCopy);
    }

    /**
     * Метод позволяет заполнить поле ввода на странице случайными английскими символами.
     *
     * @param pageName    - имя страницы, по которому можно будет получить объект этой страницы.
     * @param elementName - имя элемента, по которому можно будет получить веб-элемент.
     */
    @Тогда("На странице \"(.+)\" заполнить поле \"(.+)\" случайными английскими символами")
    public void fillInTheField(String pageName, String elementName) {
        WebElement webElement = PageObjectHelper.findElement(pageName, elementName);

        sendKeys(webElement, StringUtils.randomEnglishString(10));
    }

    /**
     * Метод позволяет заполнить поле для ввода почтового адреса на странице случайным E-Mail адресом.
     *
     * @param pageName    - имя страницы, по которому можно будет получить объект этой страницы.
     * @param elementName - имя элемента, по которому можно будет получить веб-элемент.
     */
    @Тогда("На странице \"(.+)\" заполнить поле \"(.+)\" случайным E-Mail адресом")
    public void fillInTheFieldEmail(String pageName, String elementName) {
        WebElement webElement = PageObjectHelper.findElement(pageName, elementName);

        sendKeys(webElement, StringUtils.randomEmail());
    }
}

