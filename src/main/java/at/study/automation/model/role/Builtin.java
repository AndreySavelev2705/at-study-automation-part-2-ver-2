package at.study.automation.model.role;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Builtin {
    CURRENT_ROLE(0),
    NOT_A_MEMBER(1),
    ANONYMOUS(2);

    public final int builtinCode;
}
