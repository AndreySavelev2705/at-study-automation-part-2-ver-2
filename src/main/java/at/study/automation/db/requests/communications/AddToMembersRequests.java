package at.study.automation.db.requests.communications;

import at.study.automation.db.connection.PostgresConnection;
import at.study.automation.model.project.Project;
import at.study.automation.model.role.Role;
import at.study.automation.model.user.User;
import io.qameta.allure.Step;
import lombok.SneakyThrows;

import java.time.LocalDateTime;

public class AddToMembersRequests {

    /**
     * Метод выполняет запрос в базу данных для добавления записи в таблицу public.members.
     *
     * @param user    - пользователь чей id используется в запросе в бд.
     * @param project - проект, чей id используется в запросе в бд.
     * @return возвращает id добавленной записи в бд.
     */
    @Step("Связывание пользователя с Id {0} с проектом с Id {1} - создание участника проекта")
    public Integer addMember(User user, Project project) {
        String query = "INSERT INTO public.members\n" +
                "(id, user_id, project_id, created_on, mail_notification)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?) RETURNING id;\n";

        return (Integer) PostgresConnection.INSTANCE.executeQuery(
                query,
                user.getId(),
                project.getId(),
                LocalDateTime.now(),
                false
        ).get(0).get("id");
    }

    /**
     * Метод выполняет запрос в базу данных для добавления записи в таблицу public.member_roles.
     *
     * @param memberId - id записи из таблицы public.members.
     * @param role     - роль, чей id используется в запросе в бд.
     */
    @SneakyThrows
    @Step("Связывание участника проекта с Id {0} и его ролью с Id {1} на этом проекте")
    public void addMemberRole(Integer memberId, Role role) {
        String query = "INSERT INTO public.member_roles\n" +
                "(id, member_id, role_id, inherited_from)\n" +
                "VALUES(DEFAULT, ?, ?, ?);\n";

        PostgresConnection.INSTANCE.executeQuery(
                query,
                memberId,
                role.getId(),
                null
        );
    }
}
