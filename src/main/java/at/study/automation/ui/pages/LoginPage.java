package at.study.automation.ui.pages;

import at.study.automation.cucumber.validators.annotations.ElementName;
import at.study.automation.cucumber.validators.annotations.PageName;
import at.study.automation.model.user.User;
import io.qameta.allure.Step;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static at.study.automation.ui.browser.BrowserUtils.click;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@PageName("Страница авторизации")
public class LoginPage extends Page {

    @ElementName("Логин")
    @FindBy(xpath = "//input[@id='username']")
    private WebElement loginInput;

    @ElementName("Пароль")
    @FindBy(xpath = "//input[@id='password']")
    private WebElement passwordInput;

    @ElementName("Вход")
    @FindBy(xpath = "//input[@id='login-submit']")
    private WebElement signInButton;

    @ElementName("Сообщение")
    @FindBy(xpath = "//div[@id='flash_error']")
    public WebElement errorFlash;

    /**
     * Метод выполняет авторизацию по переданному в его параметры логину и паролю.
     *
     * @param login    - логин, который будет использоваться при авторизации
     * @param password - пароль, который будет использоваться при авторизации
     */
    @Step("Авторизация пользователя с логином {0} и паролем {1}")
    public void login(String login, String password) {
        loginInput.sendKeys(login);
        passwordInput.sendKeys(password);
        click(signInButton);
    }

    /**
     * Метод выполняет авторизацию по переданному в его параметры логину и паролю
     * конкретного юзера.
     *
     * @param user - юзер на основе, логина и пароля, которого выполняется авторизация.
     */
    public void login(User user) {
        login(user.getLogin(), user.getPassword());
    }
}
