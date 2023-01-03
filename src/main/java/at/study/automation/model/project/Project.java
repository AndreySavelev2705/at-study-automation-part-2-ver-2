package at.study.automation.model.project;

import at.study.automation.db.requests.ProjectRequests;
import at.study.automation.db.requests.communications.AddToMembersRequests;
import at.study.automation.model.Creatable;
import at.study.automation.model.CreatableEntity;
import at.study.automation.model.Updateable;
import at.study.automation.model.role.Role;
import at.study.automation.model.user.User;
import at.study.automation.utils.StringUtils;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
public class Project extends CreatableEntity implements Creatable<Project>, Updateable<Project> {

    private String name = "Savelev_project_for_testing" + StringUtils.randomEnglishString(5);
    private String description = "Savelev_project_for_testing" + StringUtils.randomEnglishString(5);
    private String homepage;
    private Boolean isPublic = true;
    private Integer parentId;
    private String identifier = name;
    private Status status = Status.OPENED;
    private Integer lft = 111;
    private Integer rgt = 222;
    private Boolean inheritMembers = true;
    private Integer defaultVersionId;
    private Integer defaultAssignedToId;
    private Map<User, List<Role>> users = new HashMap<>();

    /**
     * Метод создает запись проекта в бд в таблице public.projects, на основе полученного в параметрах
     * объекта типа Project.
     *
     * @param - объект на основе которого в бд создается запись об проекте.
     */
    @Override
    @Step("Создан проект в бд")
    public Project create() {
        new ProjectRequests().create(this);

        for (User user : users.keySet()) {
            Integer memberId = new AddToMembersRequests().addMember(user, this);
            List<Role> roles = users.get(user);
            roles.forEach(role -> new AddToMembersRequests().addMemberRole(memberId, role));
        }
        return this;
    }

    /**
     * Метод обновляет данные проекта в бд в таблице public.projects.
     *
     * @return возвращает обновленный проект.
     */
    @Override
    @Step("Обновлен проект в бд")
    public Project update() {
        new ProjectRequests().update(this.id, this);

        return this;
    }

    /**
     * Метод добавляет в проект пользователя и его роли на этом проекте.
     *
     * @param user  - юзер, которому будет доступен проект.
     * @param roles - роли для пользователя на проекте.
     */
    @Step("Добавление проекту пользователя {0} и его ролей {1} на этом проекте")
    public void addUser(User user, List<Role> roles) {
        user.addProject(this, roles);
        users.put(user, roles);
    }

    /**
     * Метод возвращает имя проекта.
     *
     * @return возвращает имя проекта.
     */
    @Override
    public String toString() {
        return name;
    }
}
