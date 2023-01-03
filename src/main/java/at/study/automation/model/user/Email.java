package at.study.automation.model.user;

import at.study.automation.db.requests.EmailRequests;
import at.study.automation.model.Creatable;
import at.study.automation.model.CreatableEntity;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import static at.study.automation.utils.StringUtils.randomEmail;

@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
public class Email extends CreatableEntity implements Creatable<Email> {

    private Integer userId;
    private String address = randomEmail();
    private Boolean isDefault = true;
    private Boolean notify = true;

    public Email(User user) {
        this.userId = user.getId();
        user.getEmails().add(this);
    }

    /**
     * Метод создает запись о почтовом адресе в таблице public.email_addresses в бд.
     *
     * @return возвращает почтовый адрес.
     */
    @Override
    @Step("Создан email в бд")
    public Email create() {
        new EmailRequests().create(this);
        return this;
    }
}
