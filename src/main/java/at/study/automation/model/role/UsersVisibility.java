package at.study.automation.model.role;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UsersVisibility {
    ALL("все активные пользователи"),
    MEMBERS_OF_VISIBLE_PROJECTS("участники видимых проектов");

    public final String visibilityValue;
}
