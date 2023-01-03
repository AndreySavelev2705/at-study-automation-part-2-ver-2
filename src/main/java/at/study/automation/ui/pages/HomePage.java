package at.study.automation.ui.pages;

import at.study.automation.cucumber.validators.annotations.ElementName;
import at.study.automation.cucumber.validators.annotations.PageName;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@PageName("Домашняя страница")
public class HomePage extends Page {

    @ElementName("Домашняя страница")
    @FindBy(xpath = "//div[@id='main']//h2[text()='Домашняя страница']")
    public WebElement homePageHeader;
}
