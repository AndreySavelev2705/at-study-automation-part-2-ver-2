package at.study.automation.db.requests;

import at.study.automation.model.Entity;

public interface Read<T extends Entity> {

    T read(Integer id);
}
