package at.study.automation.context;

public class Context {
    private static ThreadLocal<Stash> stash = new ThreadLocal<>();

    /**
     * Метод позволяет получить хранилище, если оно есть,
     * либо же создать его и получить, если изначально хранилища не было.
     *
     * @return - возвращает объект хранилища.
     */
    public static Stash getStash() {
        if (stash.get() == null) {
            stash.set(new Stash());
        }
        return stash.get();
    }

    /**
     * Метод позволяет отчистить хранилище.
     */
    public static void clearStash() {
        stash.set(null);
    }
}
