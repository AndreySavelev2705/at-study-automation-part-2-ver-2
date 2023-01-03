package at.study.automation.ui.pages;

import at.study.automation.cucumber.validators.annotations.ElementName;
import at.study.automation.cucumber.validators.annotations.PageName;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@PageName("Новый пользователь")
public class CreateNewUserPage extends Page {


    @ElementName("Пользователь")
    @FindBy(xpath = "//div[@class='splitcontent']//input[@id='user_login']")
    public WebElement userLogin;

    @ElementName("Имя")
    @FindBy(xpath = "//div[@class='splitcontent']//input[@id='user_firstname']")
    public WebElement userFirstName;

    @ElementName("Фамилия")
    @FindBy(xpath = "//div[@class='splitcontent']//input[@id='user_lastname']")
    public WebElement userLastName;

    @ElementName("Email")
    @FindBy(xpath = "//div[@class='splitcontent']//input[@id='user_mail']")
    public WebElement userMail;

    @ElementName("Создание пароля")
    @FindBy(xpath = "//div[@id='password_fields']//input[@id='user_generate_password']")
    public WebElement generatePassword;

    @ElementName("Создать")
    @FindBy(xpath = "//div[@id='content']/form[@class='new_user']/p/input[@value='Создать']")
    public WebElement create;

    @ElementName("Сообщение")
    @FindBy(xpath = "//div[@class='flash notice']")
    public WebElement flashNotice;

    @ElementName("Хлебные крошки")
    @FindBy(xpath = "//div[@id='content']/h2")
    public WebElement breadCrumbs;
}
