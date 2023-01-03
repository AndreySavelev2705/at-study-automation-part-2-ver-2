package at.study.automation.model.role;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum VisibilityOfTasks {
    ALL("Все задачи"),
    DEFAULT("Только общие задачи"),
    OWN("Задачи созданные или назначенные пользоватлею");

    public final String databaseValue;
}
