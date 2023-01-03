package at.study.automation.model.role;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TimeEntriesVisibility {
    ALL("Все трудозатраты"),
    ONLY_YOUR_OWN_LABOR_COSTS("Только собственные трудозатраты");

    public final String timeEntriesVisibilityValue;
}
