package at.study.automation.model;

public interface Updateable<T extends Entity> {
    T update();
}
