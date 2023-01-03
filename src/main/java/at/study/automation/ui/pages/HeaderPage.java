package at.study.automation.ui.pages;

import at.study.automation.cucumber.validators.annotations.ElementName;
import at.study.automation.cucumber.validators.annotations.PageName;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@PageName("Заголовок страницы")
public class HeaderPage extends Page {

    @ElementName("Войти")
    @FindBy(xpath = "//div[@id='account']//a[@class='login']")
    public WebElement loginButton;

    @ElementName("Моя учётная запись")
    @FindBy(xpath = "//div[@id='account']//a[@class='my-account']")
    public WebElement myAccount;

    @ElementName("Домашняя страница")
    @FindBy(xpath = "//div[@id='top-menu']//a[@class='home']")
    public WebElement homePage;

    @ElementName("Моя страница")
    @FindBy(xpath = "//div[@id='top-menu']//a[@class='my-page']")
    public WebElement myPage;

    @ElementName("Проекты")
    @FindBy(xpath = "//div[@id='top-menu']//a[@class='projects']")
    public WebElement projects;

    @ElementName("Помощь")
    @FindBy(xpath = "//div[@id='top-menu']//a[@class='help']")
    public WebElement help;

    @ElementName("Выйти")
    @FindBy(xpath = "//div[@id='account']//a[@class='logout']")
    public WebElement logout;

    @ElementName("Вошли как")
    @FindBy(xpath = "//div[@id='loggedas']")
    public WebElement userActive;

    @ElementName("Регистрация")
    @FindBy(xpath = "//div[@id='account']//a[@class='register']")
    public WebElement register;

    @ElementName("Администрирование")
    @FindBy(xpath = "//div[@id='top-menu']//a[@class='administration']")
    public WebElement administration;

    @ElementName("Поиск")
    @FindBy(xpath = "//div[@id='quick-search']//input[@class='small']")
    public WebElement search;
}
