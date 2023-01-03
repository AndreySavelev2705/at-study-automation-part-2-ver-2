package at.study.automation.ui.pages;

import at.study.automation.cucumber.validators.annotations.ElementName;
import at.study.automation.cucumber.validators.annotations.PageName;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@PageName("Администрирование")
public class AdministrationPage extends Page {

    @ElementName("Проекты")
    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class, 'projects')]")
    public WebElement projects;

    @ElementName("Пользователи")
    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class, 'users')]")
    public WebElement users;

    @ElementName("Группы")
    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class, 'groups')]")
    public WebElement groups;

    @ElementName("Роли и права доступа")
    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class, 'roles')]")
    public WebElement roles;

    @ElementName("Трекеры")
    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class, 'trackers')]")
    public WebElement trackers;

    @ElementName("Статусы задач")
    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class, 'issue-statuses')]")
    public WebElement issueStatuses;

    @ElementName("Последовательность действий")
    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class, 'workflows')]")
    public WebElement workflows;

    @ElementName("Настраиваемые поля")
    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class, 'custom-fields')]")
    public WebElement customFields;

    @ElementName("Списки значений")
    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class, 'enumerations')]")
    public WebElement enumerations;

    @ElementName("Настройки")
    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class, 'settings')]")
    public WebElement settings;

    @ElementName("Авторизация с помощью LDAP")
    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class, 'ldap-authentication')]")
    public WebElement ldapAuthentication;

    @ElementName("Модули")
    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class, 'plugins')]")
    public WebElement plugins;

    @ElementName("Информация")
    @FindBy(xpath = "//div[@id='admin-menu']//a[contains(@class, 'info')]")
    public WebElement info;

    @ElementName("Администрирование")
    @FindBy(xpath = "//div[@id='main']//h2[text()='Администрирование']")
    public WebElement administrationHeader;
}
