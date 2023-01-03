package at.study.automation.model.project;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Status {
    OPENED(1),
    CLOSED(5),
    ARCHIVED(9);

    public final int statusCode;
}
