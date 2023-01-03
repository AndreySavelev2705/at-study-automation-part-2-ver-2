package at.study.automation.db.requests;

import at.study.automation.db.connection.PostgresConnection;
import at.study.automation.model.role.Permission;
import at.study.automation.model.role.Role;
import io.qameta.allure.Step;

import java.util.List;

public class RoleRequests extends BaseRequests implements Create<Role>, Update<Role>, Delete<Role> {
    /**
     * Метод создает запись роли в бд в таблице public.roles, на основе полученного в параметрах
     * объекта типа Role.
     *
     * @param role - объект на основе которого в бд создается запись об роли.
     */
    @Override
    @Step("Создание роли с набором пермишенов в бд")
    public void create(Role role) {
        String query = "INSERT INTO public.roles\n" +
                "(id, \"name\", \"position\", assignable, builtin, permissions, issues_visibility, " +
                "users_visibility, time_entries_visibility, all_roles_managed, settings)\n" +
                "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id;\n";

        Integer id = (Integer) PostgresConnection.INSTANCE.executeQuery(
                query,
                role.getName(),
                role.getPosition(),
                role.getAssignable(),
                role.getBuiltin().builtinCode,
                convertPermissionsToString(role.getPermissions()),
                role.getVisibilityOfTasks().databaseValue,
                role.getUsersVisibility().visibilityValue,
                role.getTimeEntriesVisibility().timeEntriesVisibilityValue,
                role.getAllRolesManaged(),
                role.getSettings()
        ).get(0).get("id");
        role.setId(id);
    }

    /**
     * Метод удаляет роль с id полученным в параметрах.
     *
     * @param id - id адреса почты в бд, который нужно удалить.
     */
    @Override
    public void delete(Integer id) {
        String query = "DELETE FROM public.roles\n" +
                "WHERE id=?;\n";
        PostgresConnection.INSTANCE.executeQuery(query, id);
    }

    /**
     * Метод обновляет данные роли в бд по полученному id, на основе полученной в параметрах метода роли.
     *
     * @param id   - id роли в бд, которую нужно обновить.
     * @param role - объект, на основе которого обновляется роль в бд.
     */
    @Override
    public void update(Integer id, Role role) {
        String query = "UPDATE public.roles\n" +
                "SET \"name\"=?, \"position\"=?, assignable=?, builtin=?, " +
                "permissions=?, issues_visibility=?, users_visibility=?, " +
                "time_entries_visibility=?, all_roles_managed=?, settings=?\n" +
                "WHERE id=?;\n";

        PostgresConnection.INSTANCE.executeQuery(
                query,
                role.getName(),
                role.getPosition(),
                role.getAssignable(),
                role.getBuiltin().builtinCode,
                convertPermissionsToString(role.getPermissions()),
                role.getVisibilityOfTasks().databaseValue,
                role.getUsersVisibility().visibilityValue,
                role.getTimeEntriesVisibility().timeEntriesVisibilityValue,
                role.getAllRolesManaged(),
                role.getSettings(),
                id
        );
    }

    /**
     * Метод преобразует список пераишенов в строку.
     *
     * @param permissions - список пермишенов, который нужно преобразовать в строку.
     * @return возвращает строку, в которой содержаться в виде текста пермишены.
     */
    private String convertPermissionsToString(List<Permission> permissions) {
        StringBuilder sb = new StringBuilder();

        sb.append("---\n");

        permissions.forEach(permission -> sb.append("- :").append(permission.name().toLowerCase()).append("\n"));

        return sb.toString();
    }
}
