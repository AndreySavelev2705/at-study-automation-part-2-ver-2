package at.study.automation.tests.ui;

import at.study.automation.ui.browser.Browser;
import at.study.automation.ui.browser.BrowserManager;
import at.study.automation.ui.pages.*;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;

public class BaseUiTest {
    protected Browser browser;
    protected HeaderPage headerPage;
    protected LoginPage loginPage;
    protected HomePage homePage;
    protected AdministrationPage administrationPage;
    protected UserTablePage userTablePage;
    protected ProjectsPage projectsPage;
    protected CreateNewUserPage createNewUserPage;

    @Step("Открыт браузер на главной странице")
    protected void openBrowser() {
        browser = BrowserManager.getBrowser();
        initPages();
    }

    @Step("Открыт браузер на странице {0}")
    protected void openBrowser(String uri) {
        browser = BrowserManager.getBrowser(uri);
        initPages();
    }

    private void initPages() {
        headerPage = Page.getPage(HeaderPage.class);
        loginPage = Page.getPage(LoginPage.class);
        homePage = Page.getPage(HomePage.class);
        administrationPage = Page.getPage(AdministrationPage.class);
        userTablePage = Page.getPage(UserTablePage.class);
        projectsPage = Page.getPage(ProjectsPage.class);
        createNewUserPage = Page.getPage(CreateNewUserPage.class);
    }

    @AfterMethod(description = "Закрытие браузера")
    public void tearDown() {
        BrowserManager.closeBrowser();
    }
}
