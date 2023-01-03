package at.study.automation.db.requests;

import at.study.automation.db.connection.PostgresConnection;
import at.study.automation.model.user.Token;
import at.study.automation.model.user.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
public class TokenRequests extends BaseRequests implements Create<Token>, ReadAll<Token> {
    private User user;

    /**
     * Метод создает запись токена в бд в таблице public.tokens, на основе полученного в параметрах
     * объекта типа Token.
     *
     * @param token - параметр метода типа Token на основе которого создается запись о токене в бд.
     */
    @Override
    public void create(Token token) {
        String query = "INSERT INTO public.tokens\n" +
                "(id, user_id, \"action\", value, created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?) RETURNING id;\n";
        List<Map<String, Object>> queryResult = PostgresConnection.INSTANCE.executeQuery(
                query,
                token.getUserId(),
                token.getAction().name().toLowerCase(),
                token.getValue(),
                token.getCreatedOn(),
                token.getUpdatedOn()
        );
        Integer tokenId = (Integer) queryResult.get(0).get("id");
        token.setId(tokenId);
    }

    /**
     * Метод считывает все записи с токенами клиента из базы данных в таблице public.tokens.
     *
     * @return возвращает список с токенами конкретного юзера.
     */
    @Override
    public List<Token> readAll() {
        Integer userId = Objects.requireNonNull(user.getId());
        String query = "SELECT * FROM tokens WHERE user_id = ?";
        List<Map<String, Object>> queryResult = PostgresConnection.INSTANCE.executeQuery(
                query,
                userId
        );
        return queryResult.stream()
                .map(data -> from(data, user))
                .collect(Collectors.toList());
    }

    /**
     * Метод делает из мапы, с результатом запроса из бд в таблице public.tokens, объект класса Token, связывает его с юзером и возвращаем его.
     *
     * @param data мапа с записью. из таблицы public.tokens в бд.
     * @param user юзер с которым нужно связать объект типа Token.
     * @return возвращает, привязанный к юзеру, инициализированный объект типа Token.
     */
    private Token from(Map<String, Object> data, User user) {
        return (Token) new Token(user)
                .setAction(
                        Token.TokenType.valueOf(data.get("action").toString().toUpperCase())
                )
                .setValue("value")
                .setCreatedOn(toLocalDate(data.get("created_on")))
                .setCreatedOn(toLocalDate(data.get("updated_on")))
                .setId((Integer) data.get("id"));
    }
}
