package at.study.automation.cucumber.validators;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class UserParametersValidator {

    /**
     * Метод проверяет валидность переданного в него множества с ключами.
     *
     * @param keys - множество, хранящее в себе ключи, над которыми нужно провести валидацию.
     */
    public static void validateUserParameters(Set<String> keys) {
        List<String> allowedKeys = Arrays.asList(
                "Администратор",
                "Статус",
                "Уведомления о новых событиях",
                "E-Mail",
                "Api-ключ"
        );

        boolean allKeysAreValid = allowedKeys.containsAll(keys);

        if (!allKeysAreValid) {
            throw new IllegalArgumentException("Среди переданных параметров пользователя есть недопустимые параметры.");
        }
    }
}
