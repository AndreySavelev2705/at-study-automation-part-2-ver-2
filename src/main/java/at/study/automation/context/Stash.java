package at.study.automation.context;

import java.util.HashMap;
import java.util.Map;

public class Stash {

    private Map<String, Object> entities = new HashMap<>();

    /**
     * Метод позволяет добавить в хранилище объект, связав его с stashId.
     *
     * @param stashId - ключ с которым связан помещенный в хранилище объект.
     *                По этому ключу происходит поиск объекта в хранилище.
     * @param entity  - объект, который помещается в хранилище. Связан с stashId, и находится по нему.
     */
    public void put(String stashId, Object entity) {
        entities.put(stashId, entity);
    }

    /**
     * Метод позволяет получить объект из хранилища по переданному stashId.
     *
     * @param stashId - ключ с которым связан помещенный в хранилище объект.
     *                По этому ключу происходит поиск объекта в хранилище.
     * @return возвращает найденный по stashId объект.
     */
    public Object get(String stashId) {
        return entities.get(stashId);
    }

    /**
     * Метод позволяет получить объект из хранилища по переданному stashId,
     * сразу приведя его в переданный в параметры метода класс.
     *
     * @param stashId - ключ с которым связан помещенный в хранилище объект.
     *                По этому ключу происходит поиск объекта в хранилище.
     * @param clazz   - объект класса, в который нужно привести объект в хранилище, перед возвращением его.
     * @param <T>     - указывает какой тип данных должен быть у каждого Т в методе.
     * @return возвращает найденный по stashId объект, приводя его в класс, который был передан в параметрах метода.
     */
    public <T> T get(String stashId, Class<T> clazz) {
        return (T) get(stashId);
    }
}
