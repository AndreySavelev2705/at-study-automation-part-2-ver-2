package at.study.automation.model.user;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum Language {
    RUSSIAN("ru"),
    ENGLISH("en");

    public final String languageCode;

    /**
     * Метод возвращает язык по коду языка.
     *
     * @param languageCode - код языка по которому нужно вернуть язык.
     * @return возвращает язык по коду языка.
     */
    public static Language getLanguageByLanguageCode(String languageCode) {
        return Arrays.stream(Language.values())
                .filter(language -> languageCode.equals(language.languageCode))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Языка по коду: " + languageCode + " не найдено."));
    }
}
