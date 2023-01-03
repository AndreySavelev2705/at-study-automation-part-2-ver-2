package at.study.automation.model.user;

import at.study.automation.db.requests.TokenRequests;
import at.study.automation.model.Creatable;
import at.study.automation.model.CreatableEntity;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import static at.study.automation.utils.StringUtils.randomHexString;

@Setter
@Getter
@Accessors(chain = true)
public class Token extends CreatableEntity implements Creatable<Token> {

    private Integer userId;
    private TokenType action = TokenType.API;
    private String value = randomHexString(40);

    public enum TokenType {
        SESSION,
        API,
        FEEDS
    }

    public Token(User user) {
        this.userId = user.getId();
        user.getTokens().add(this);
    }

    /**
     * Метод позволяет создать запись о токене в бд в таблице public.tokens.
     *
     * @return возвращает текущий объект.
     */
    @Override
    @Step("Создан токен в бд")
    public Token create() {
        new TokenRequests().create(this);
        return this;
    }
}
