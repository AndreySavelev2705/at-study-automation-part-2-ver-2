package steps;

import at.study.automation.context.Context;
import at.study.automation.cucumber.validators.ProjectParametersValidator;
import at.study.automation.cucumber.validators.UserParametersValidator;
import at.study.automation.model.project.Project;
import at.study.automation.model.role.Permission;
import at.study.automation.model.role.Role;
import at.study.automation.model.user.*;
import cucumber.api.java.ru.Пусть;
import cucumber.api.java.ru.Также;
import io.cucumber.datatable.DataTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class PrepareFixtureSteps {

    /**
     * Метод позволяет получить таблицу с почтовыми адресами со страницы,
     * проинициализировать каждый почтовый адрес из таблицы в объект типа Email
     * и преобразовать, как итог, эту таблицу в список почтовых адресов, сохранив их в хранилище Context.
     *
     * @param emailsStashId - Ключ с которым будет связан список почтовых адресов в хранилище Context.
     * @param dataTable     - список почтовых адресов, который можно найти по ключу в хранилище Context.
     */
    @Пусть("Имеется список E-Mail адресов \"(.+)\":")
    public void createEmails(String emailsStashId, DataTable dataTable) {

        List<Map<String, String>> maps = dataTable.asMaps();
        List<Email> emails = new ArrayList<>();

        maps.forEach(map -> {
            String address = map.get("Адрес");
            Boolean isDefault = Boolean.parseBoolean(map.get("По умолчанию"));
            Boolean notify = Boolean.parseBoolean(map.get("Уведомления"));

            Email email = new Email()
                    .setAddress(address)
                    .setIsDefault(isDefault)
                    .setNotify(notify);

            emails.add(email);
        });

        Context.getStash().put(emailsStashId, emails);
    }

    /**
     * Метод позволяет проинициализировать пользователя по переданным в параметрах метода параметрах пользователя
     * и выполнить валидацию этих параметров.
     *
     * @param userStashId - ключ с которым будет связан пользователь при помещении в хранилище Context.
     * @param parameters  - мапа с параметрами, использующиеся при инициализации пользователя,
     *                    и для которых нужно выполнить валидацию.
     */
    @Пусть("В системе есть пользователь \"(.+)\" с параметрами:")
    public void createUser(String userStashId, Map<String, String> parameters) {
        UserParametersValidator.validateUserParameters(parameters.keySet());

        User user = new User();

        if (parameters.containsKey("Администратор")) {
            if (parameters.get("Администратор").equals("true") || parameters.get("Администратор").equals("false")) {
                Boolean isAdmin = Boolean.parseBoolean(parameters.get("Администратор"));
                user.setIsAdmin(isAdmin);
            } else throw new IllegalArgumentException("Неверное значение параметра.");
        }
        if (parameters.containsKey("Статус")) {
            String statusDescription = parameters.get("Статус");
            Status status = Status.of(statusDescription);
            user.setStatus(status);
        }
        if (parameters.containsKey("Уведомления о новых событиях")) {
            String mailNotificationDescription = parameters.get("Уведомления о новых событиях");
            MailNotification mn = MailNotification.of(mailNotificationDescription);
            user.setMailNotification(mn);
        }
        if (parameters.containsKey("E-Mail")) {
            String emailsStashId = parameters.get("E-Mail");
            List<Email> emails = Context.getStash().get(emailsStashId, List.class);
            user.setEmails(emails);
        }
        if (parameters.containsKey("Api-ключ")) {
            user.setTokens(Collections.singletonList(new Token(user)));
        }

        user.create();
        Context.getStash().put(userStashId, user);
    }

    /**
     * Метод позволяет создать роль с каким-либо набором пермишенов.
     *
     * @param roleStashId - ключ с которым будет связана роль при помещении в хранилище Context.
     * @param permissions - список пермишенов, которые необходимо добавить для роли при ее создании.
     */
    @Пусть("В системе существует роль \"(.+)\" с правами:")
    public void createRole(String roleStashId, List<String> permissions) {
        Role role = new Role();

        role.setPermissions(
                permissions.stream().map(description -> Permission.from(description)).collect(toList())
        );
        role.create();
        Context.getStash().put(roleStashId, role);
    }

    /**
     * Метод позволяет создать список ролей, достав их из хранилища Context по их ключу.
     *
     * @param rolesStashId     - ключ с которым будет связан список ролей при помещении в хранилище Context.
     * @param roleNamesStashId - список ключей по которым нужно достать из хранилища роли.
     */
    @Пусть("Список ролей \"(.+)\" содержит роли:")
    public void createRolesList(String rolesStashId, List<String> roleNamesStashId) {
        List<Role> roles = new ArrayList<>();

        for (String roleNameStashId : roleNamesStashId) {
            Role role = Context.getStash().get(roleNameStashId, Role.class);
            roles.add(role);
        }

        Context.getStash().put(rolesStashId, roles);
    }

    /**
     * Метод позволяет проинициализировать проект по переданным в параметрах метода параметрах проекта
     * и выполнить валидацию для этих параметров.
     *
     * @param projectStashId - ключ с которым будет связан проект при помещении в хранилище Context.
     * @param parameters     - мапа с параметрами, использующиеся при инициализации проекта,
     *                       и для которых нужно выполнить валидацию.
     */
    @Также("Существует проект \"(.+)\" с параметрами:")
    public void createProject(String projectStashId, Map<String, String> parameters) {
        ProjectParametersValidator.validateProjectParameters(parameters.keySet());

        Project project;

        if (parameters.containsValue("false")) {
            project = new Project() {{
                setIsPublic(false);
            }}.create();
        } else if (parameters.containsValue("true")) {
            project = new Project().create();
        } else throw new IllegalArgumentException("Невалидное значение для статуса проекта");

        Context.getStash().put(projectStashId, project);
    }

    /**
     * Метод позволяет добавить юзеру проект для него и его роли на это проекте.
     *
     * @param userStashId        - ключ по которому будет доставаться юзеру из хранилища.
     * @param projectNameStashId - ключ по которому будет доставаться проект из хранилища.
     * @param roleStashId        - ключ по которому будет доставаться роль из хранилища.
     */
    @Также("Пользователь \"(.+)\" имеет доступ к проекту \"(.+)\" со списком ролей \"(.+)\"")
    public void linkingProjectAndUserAndRoles(String userStashId, String projectNameStashId, String roleStashId) {
        List<Role> roles = Context.getStash().get(roleStashId, List.class);
        Project project = Context.getStash().get(projectNameStashId, Project.class);
        User user = Context.getStash().get(userStashId, User.class);

        user.addProject(project, roles);
    }
}