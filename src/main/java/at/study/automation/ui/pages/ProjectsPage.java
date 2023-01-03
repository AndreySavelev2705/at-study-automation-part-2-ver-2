package at.study.automation.ui.pages;

import at.study.automation.cucumber.validators.annotations.ElementName;
import at.study.automation.cucumber.validators.annotations.PageName;
import at.study.automation.ui.browser.BrowserManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@PageName("Проекты")
public class ProjectsPage extends Page {

    @ElementName("Список проектов")
    @FindBy(xpath = "//div[@id='projects-index']//li[@class='root']")
    public List<WebElement> projects;

    @ElementName("Применить")
    @FindBy(xpath = "//div[@id='query_form_with_buttons']//a[@class='icon icon-checked']")
    public WebElement submit;

    @ElementName("Проекты")
    @FindBy(xpath = "//div[@id='main']//h2[text()='Проекты']")
    public WebElement projectsLabel;

    /**
     * Метод позволяет вернуть веб-элемент - проект,
     * который соответствует переданному в параметрах метода имени проекта.
     *
     * @param projectName - имя проекта по которому будет происходить поиск веб-элемента - проекта.
     * @return возвращает веб-элемент - проект.
     */
    public WebElement getProject(String projectName) {
        return BrowserManager.getBrowser()
                .getDriver()
                .findElement(By.xpath("//div[@id='projects-index']//a[text()='" + projectName + "']"));
    }

    /**
     * Метод позволяет вернуть веб-элемент - описание проекта,
     * который соответствует переданному в параметрах метода описанию проекта.
     *
     * @param projectDescriptionName - описание проекта по которому будет происходить поиск веб-элемента - описания проекта.
     * @return возвращает веб-элемент - описание проекта.
     */
    public WebElement getProjectDescription(String projectDescriptionName) {
        return BrowserManager.getBrowser()
                .getDriver()
                .findElement(By.xpath("//div[@id='projects-index']//p[text()='" + projectDescriptionName + "']"));
    }
}
