package at.study.automation.utils;

import java.util.Random;

/**
 * Класс для работы со строками, их генерации.
 */
public class StringUtils {

    private static final String LATIN_PATTERN = "abcdefghijklmnopqrstuvwxyz";
    private static final String HEX_PATTERN = "0123456789abcdef";
    private static final Random RANDOM = new Random();

    /**
     * Генерирует случайный email.
     *
     * @return строка, содержащая сгенерированный email.
     */
    public static String randomEmail() {
        return randomEnglishString(6) + "@" + randomEnglishString(6) + "." + randomEnglishString(3);
    }

    public static String randomHexString(int length) {
        return randomString(HEX_PATTERN, length);
    }

    /**
     * Генерирует случайную строку из латинских символов заданной длины.
     *
     * @param length - целое число, обозначающее длину строки.
     * @return строка из латинских букв определенной длины.
     */
    public static String randomEnglishString(int length) {
        return randomString(LATIN_PATTERN, length);
    }

    /**
     * Генерирует случайную строку из латинских символов заданной длины по переданному паттерну.
     *
     * @param pattern - строка, содержащая набор символов из которых будет проходить генерация рандомной строки.
     * @param length  - целое число, обозначающее длину строки.
     * @return строка из символов, полученных в паттерне, определенной длины.
     */
    public static String randomString(String pattern, int length) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int patternLength = pattern.length();
            int randomIndex = RANDOM.nextInt(patternLength);
            char c = pattern.charAt(randomIndex);
            sb.append(c);
        }

        return sb.toString();
    }
}
