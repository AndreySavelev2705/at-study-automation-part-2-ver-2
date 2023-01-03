package at.study.automation.db.requests;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public abstract class BaseRequests {

    /**
     * Метод проводит преобразование Timestamp в объект LocalDateTime.
     *
     * @param timestamp - параметр, который хранит в себе данные из БД,
     *                  для дальнейшего преобразования в тип LocaleDateTime.
     * @return Возвращает полученный в ходе преобразования объект LocaleDateTime.
     */
    protected LocalDateTime toLocalDate(Object timestamp) {
        Timestamp ts = (Timestamp) timestamp;
        return ts.toLocalDateTime();
    }
}
