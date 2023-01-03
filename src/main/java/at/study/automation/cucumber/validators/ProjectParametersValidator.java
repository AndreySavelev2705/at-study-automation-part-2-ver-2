package at.study.automation.cucumber.validators;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ProjectParametersValidator {

    /**
     * Метод проверяет валидность переданного в него множества с параметрами.
     *
     * @param permissions - множество, хранящее в себе параметры, над которыми нужно провести валидацию.
     */
    public static void validateProjectParameters(Set<String> permissions) {
        List<String> allowedKeys = Arrays.asList(
                "Публичный"
        );

        boolean allKeysAreValid = allowedKeys.containsAll(permissions);

        if (!allKeysAreValid) {
            throw new IllegalArgumentException("Среди переданных разрешений для проекта есть недопустимые параметры.");
        }
    }
}
