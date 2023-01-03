package at.study.automation.model.property;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.util.Properties;

public class Property {

    private static final String propertiesName = System.getProperty("properties", "default.properties");
    private static final Properties properties = new Properties();
    private static boolean isInitialized = false;

    /**
     * Метод считывает содержимое файла конфигурации.
     */
    @SneakyThrows
    private static void init() {
        properties.load(new FileInputStream("src/test/resources/" + propertiesName));
        isInitialized = true;
    }

    /**
     * Метод возвращает строковое значение из файла конфигурации по ключу.
     *
     * @param key - ключ по которому происходит поиск строкового значения в файле конфигурации.
     * @return возвращает строку из файла конфигурации.
     */
    public static String getStringProperty(String key) {
        if (!isInitialized) {
            init();
        }
        if (System.getProperty(key) != null) {
            return System.getProperty(key);
        }
        return properties.getProperty(key);
    }

    /**
     * Метод парсит строковое значение в целое число и возвращает его из файла конфигурации по ключу.
     *
     * @param key - ключ по которому происходит поиск строкового значения в файле конфигурации.
     * @return возвращает целое число из файла конфигурации.
     */
    public static Integer getIntegerProperty(String key) {
        return Integer.parseInt(getStringProperty(key));
    }

    /**
     * Метод парсит строковое значение в булевое значение и возвращает его из файла конфигурации по ключу.
     *
     * @param key - ключ по которому происходит поиск строкового значения в файле конфигурации.
     * @return возвращает булевое значение из файла конфигурации.
     */
    public static Boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(getStringProperty(key));
    }
}
