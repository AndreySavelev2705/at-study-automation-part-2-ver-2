package at.study.automation.api.rest_assured;

import at.study.automation.api.client.RestResponse;
import io.restassured.http.Header;
import io.restassured.response.Response;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;

import static at.study.automation.api.rest_assured.GsonProvider.GSON;

@Getter
public class RestAssuredResponse implements RestResponse {

    private int statusCode;
    private Map<String, String> headers;
    private String payload;

    public RestAssuredResponse(Response response) {
        this.statusCode = response.getStatusCode();
        this.headers = response.getHeaders().asList().stream()
                .collect(Collectors.toMap(Header::getName, Header::getValue));
        this.payload = response.getBody().asString();
    }

    /**
     * Метод позволяет получить из JSON-a объект переданного в параметрах класса.
     *
     * @param clazz - объект класса, в который нужно преобразовать JSON.
     * @param <T>   - - указывает какой тип данных должен быть у каждого Т в методе.
     * @return - возвращает объект того класса, который был передан в параметрах.
     */
    @Override
    public <T> T getPayload(Class<T> clazz) {
        return GSON.fromJson(payload, clazz);
    }
}
