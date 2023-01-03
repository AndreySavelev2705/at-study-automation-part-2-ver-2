package at.study.automation.model;

public interface Creatable<T extends Entity> {

    T create();
}
