package at.study.automation.db.requests;

import at.study.automation.model.Entity;

public interface Delete<T extends Entity> {

    void delete(Integer id);
}
