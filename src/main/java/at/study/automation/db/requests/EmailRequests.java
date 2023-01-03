package at.study.automation.db.requests;

import at.study.automation.db.connection.PostgresConnection;
import at.study.automation.model.user.Email;
import at.study.automation.model.user.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
public class EmailRequests extends BaseRequests implements Create<Email>, ReadAll<Email>, Read<Email>, Update<Email>, Delete<Email> {
    private User user;

    /**
     * Метод создает запись почтового адреса в бд в таблице public.email_addresses, на основе полученного в параметрах
     * объекта типа Email.
     *
     * @param email - объект на основе которого в бд создается запись об почтовом адресе.
     */
    @Override
    public void create(Email email) {
        String query = "INSERT INTO public.email_addresses\n" +
                "(id, user_id, address, is_default, \"notify\", created_on, updated_on)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?) RETURNING id;\n";
        Integer id = (Integer) PostgresConnection.INSTANCE.executeQuery(
                query,
                email.getUserId(),
                email.getAddress(),
                email.getIsDefault(),
                email.getNotify(),
                email.getCreatedOn(),
                email.getUpdatedOn()
        ).get(0).get("id");
        email.setId(id);
    }

    /**
     * Метод позволяет получить список всех почтовых адресов юзера из таблицы public.email_addresses в бд.
     *
     * @return возвращает список с почтовыми адресами конкретного юзера.
     * Integer userId - если не null, значит такой юзер существует.
     */
    @Override
    public List<Email> readAll() {
        Integer userId = Objects.requireNonNull(user.getId());
        String query = "SELECT * FROM public.email_addresses WHERE user_id = ?";
        List<Map<String, Object>> queryResult = PostgresConnection.INSTANCE.executeQuery(query, userId);

        return queryResult.stream()
                .map(data -> from(data, user))
                .collect(Collectors.toList());
    }

    /**
     * Метод позволяет получить почтовый адрес конкретного юзера из таблицы public.email_addresses в бд.
     *
     * @param id - id почтового ящика из бд.
     * @return объект типа Email, который содержит ту же информацию, что и почтовый ящик из бд.
     */
    @Override
    public Email read(Integer id) {
        String query = "SELECT * FROM public.email_addresses WHERE id = ?";
        List<Map<String, Object>> queryResult = PostgresConnection.INSTANCE.executeQuery(query, id);
        return from(queryResult.get(0), user);
    }

    /**
     * Метод позволяет получить почтовый адрес пользователя по его Id из таблицы public.email_addresses в бд.
     *
     * @param userId - id пользователя, чей почтовый адрес нужно вернуть.
     * @return
     */
    public Email readByUserId(Integer userId) {
        String query = "SELECT * FROM public.email_addresses WHERE user_id = ?";
        List<Map<String, Object>> queryResult = PostgresConnection.INSTANCE.executeQuery(query, userId);
        return from(queryResult.get(0), new UserRequests().read(userId));
    }

    /**
     * Метод обновляет данные почтового адреса из таблицы public.email_addresses в бд по полученному id,
     * на основе полученного в параметрах метода объекта типа Email.
     *
     * @param id    - id почтового адреса в бд, который нужно обновить.
     * @param email - объект, на основе которого обновляется email в бд.
     */
    @Override
    public void update(Integer id, Email email) {
        String query = "UPDATE public.email_addresses\n" +
                "SET user_id=?, address=?, is_default=?, \"notify\"=?, created_on=?, updated_on=?\n" +
                "WHERE id=?;\n";
        PostgresConnection.INSTANCE.executeQuery(
                query,
                email.getUserId(),
                email.getAddress(),
                email.getIsDefault(),
                email.getNotify(),
                email.getCreatedOn(),
                email.getUpdatedOn(),
                id
        );
    }

    /**
     * Метод удаляет почтовый адрес с id, полученным в параметрах, из таблицы public.email_addresses в бд.
     *
     * @param id - id адреса почты в бд, который нужно удалить.
     */
    @Override
    public void delete(Integer id) {
        String query = "DELETE FROM public.email_addresses\n" +
                "WHERE id=?;\n";
        PostgresConnection.INSTANCE.executeQuery(query, id);
    }

    /**
     * Метод делает из мапы, с результатом запроса из таблицы public.email_addresses в бд,
     * объект класса Email, связывает его с юзером и возвращаем его.
     *
     * @param data - мапа с записью из таблицы public.email_addresses в бд.
     * @param user - пользователь с которым нужно связать объект типа Email.
     * @return возвращает, привязанный к пользователю, инициализированный объект типа Email.
     */
    private Email from(Map<String, Object> data, User user) {
        return (Email) new Email(user)
                .setAddress((String) data.get("address"))
                .setIsDefault((Boolean) data.get("is_default"))
                .setNotify((Boolean) data.get("notify"))
                .setUpdatedOn(toLocalDate(data.get("updated_on")))
                .setCreatedOn(toLocalDate(data.get("created_on")))
                .setId((Integer) data.get("id"));
    }
}
