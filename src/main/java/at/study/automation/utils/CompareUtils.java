package at.study.automation.utils;

import io.qameta.allure.Step;
import org.testng.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CompareUtils {

    private static final Comparator<String> DATE_DESC_COMPARATOR = (s1, s2) -> {
        LocalDateTime d1 = LocalDateTime.parse(s1, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        LocalDateTime d2 = LocalDateTime.parse(s2, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        return d2.compareTo(d1);
    };
    private static final Comparator<String> DATE_ASC_COMPARATOR = DATE_DESC_COMPARATOR.reversed();

    private static final Comparator<String> COLUMN_DESC_COMPARATOR = (s1, s2) -> s2.compareToIgnoreCase(s1);
    private static final Comparator<String> COLUMN_ASC_COMPARATOR = COLUMN_DESC_COMPARATOR.reversed();

    @Step("Проверка сортировки списка дат по убыванию")
    public static void assertListSortedByDateDesc(List<String> dates) {
        List<String> datesCopy = new ArrayList<>(dates);
        datesCopy.sort(DATE_DESC_COMPARATOR);
        Assert.assertEquals(dates, datesCopy);
    }

    @Step("Проверка сортировки списка дат по врзрастанию")
    public static void assertListSortedByDateAsc(List<String> dates) {
        List<String> datesCopy = new ArrayList<>(dates);
        datesCopy.sort(DATE_ASC_COMPARATOR);
        Assert.assertEquals(dates, datesCopy);
    }

    @Step("Проверка сортировки списка логинов по убыванию")
    public static void assertListSortedByDesc(List<String> usersNames) {
        List<String> usersNamesCopy = new ArrayList<>(usersNames);
        usersNamesCopy.sort(COLUMN_DESC_COMPARATOR);
        Assert.assertEquals(usersNames, usersNamesCopy);
    }

    @Step("Проверка сортировки списка логинов по возрастанию")
    public static void assertListSortedByAsc(List<String> usersNames) {
        List<String> usersNamesCopy = new ArrayList<>(usersNames);
        usersNamesCopy.sort(COLUMN_ASC_COMPARATOR);
        Assert.assertEquals(usersNames, usersNamesCopy);
    }

    @Step("Проверка признака отсортированности списка")
    public static boolean isListSorted(List<String> list) {
        List<String> listCopy = new ArrayList<>(list);
        Collections.sort(listCopy);
        return listCopy.equals(list);
    }

    /**
     * Метод позволяет получить компаратор для проверки того как отсортированы данные.
     *
     * @param comparatorType - тип на основе которого выбирается компаратор.
     * @return возвращает компаратор, по переданному в параметрах метода типу компаратора.
     */
    public static Comparator<String> getComparator(String comparatorType) {
        switch (comparatorType) {
            case ("по возрастанию"):
                return COLUMN_ASC_COMPARATOR;
            case ("по убыванию"):
                return COLUMN_DESC_COMPARATOR;
        }
        throw new IllegalArgumentException("Такого компаратора не существует");
    }
}
