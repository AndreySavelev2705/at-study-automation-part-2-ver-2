package at.study.automation.model.role;

import at.study.automation.db.requests.RoleRequests;
import at.study.automation.model.Creatable;
import at.study.automation.model.Entity;
import at.study.automation.utils.StringUtils;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
public class Role extends Entity implements Creatable<Role> {
    private String name = "Savelev_default_role" + StringUtils.randomEnglishString(5);
    private Integer position;
    private Boolean assignable = false;
    private Builtin builtin = Builtin.CURRENT_ROLE;
    private List<Permission> permissions = new ArrayList<>();
    private VisibilityOfTasks visibilityOfTasks = VisibilityOfTasks.ALL;
    private UsersVisibility usersVisibility = UsersVisibility.ALL;
    private TimeEntriesVisibility timeEntriesVisibility = TimeEntriesVisibility.ALL;
    private Boolean allRolesManaged = true;
    private String settings = "--- !ruby/hash:ActiveSupport::HashWithIndifferentAccess\n" +
            "permissions_all_trackers: !ruby/hash:ActiveSupport::HashWithIndifferentAccess\n" +
            "  view_issues: '1'\n" +
            "  add_issues: '1'\n" +
            "  edit_issues: '1'\n" +
            "  add_issue_notes: '1'\n" +
            "  delete_issues: '1'\n" +
            "permissions_tracker_ids: !ruby/hash:ActiveSupport::HashWithIndifferentAccess\n" +
            "  view_issues: []\n" +
            "  add_issues: []\n" +
            "  edit_issues: []\n" +
            "  add_issue_notes: []\n" +
            "  delete_issues: []\n";

    /**
     * Метод создает запись о роли в таблице public.roles в бд.
     *
     * @return возвращает созданную роль.
     */
    @Override
    @Step("Создана роль в бд")
    public Role create() {
        new RoleRequests().create(this);
        return this;
    }

    /**
     * Метод возвращает имя роли.
     *
     * @return возвращает имя роли.
     */
    @Override
    public String toString() {
        return name;
    }
}
