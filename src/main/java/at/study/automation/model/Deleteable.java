package at.study.automation.model;

public interface Deleteable<T extends Entity> {

    T delete();
}
