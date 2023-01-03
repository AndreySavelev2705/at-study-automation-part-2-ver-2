package at.study.automation.api.rest_assured;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GsonProvider {

    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
            .create();

    private static class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
        /**
         * Метод позволяет задать свои собственные правила десериализации для даты вместо правил по умолчанию.
         *
         * @param jsonElement                - объект над которым будет проводиться десериализация.
         * @param type                       - тип десериализуемого объекта.
         * @param jsonDeserializationContext - контекст десериализации. Содержит один метод deserialize().
         *                                   Этот контекст наследует все настройки Gson-объекта.
         * @return
         * @throws JsonParseException - исключение, которое может выбрасываеться, если невозможно провести парсинг JSON-а.
         */
        @Override
        public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            String value = jsonElement.getAsJsonPrimitive().getAsString();
            return LocalDateTime.parse(value, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }
    }

    private static class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {
        /**
         * Метод позволяет задать свои собственные правила сериализоции для даты вместо пправил по умолчанию.
         *
         * @param localDateTime            - объект над которым будет проводиться сериализация.
         * @param type                     - тип сериализуемого объекта.
         * @param jsonSerializationContext - контекст сериализации. Содержит один метод serialize().
         *                                 Этот контекст наследует все настройки Gson-объекта.
         * @return - возвращает представление примитивных типов вроде строк, чисел и т.д. у JSON-а.
         */
        @Override
        public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
            String value = localDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
            return new JsonPrimitive(value);
        }
    }
}
