package at.study.automation.allure;

import at.study.automation.api.client.RestApiClient;
import at.study.automation.api.client.RestRequest;
import at.study.automation.api.dto.errors.ErrorInfoDto;
import at.study.automation.api.dto.users.UserDto;
import at.study.automation.api.dto.users.UserInfoDto;
import at.study.automation.api.rest_assured.RestAssuredRequest;
import io.qameta.allure.Step;

public class AllureUtils {

    @Step("Формирование запроса на сервер")
    public static RestRequest generatingRequest(RestAssuredRequest request) {
        return request;
    }

    @Step("Создание в памяти пользователя чьи, данные будут использоваться для составления тела запроса")
    public static UserInfoDto initDto(UserInfoDto dto) {
        return dto;
    }

    @Step("Создание в памяти пользователя на основе данных из ответа на запрос")
    public static UserDto initDtoFromResponse(UserDto dto) {
        return dto;
    }

    @Step("Сформированы данные об ошибках в ответе")
    public static ErrorInfoDto initErrorInfoDtoFromResponse(ErrorInfoDto errorInfoDto) {
        return errorInfoDto;
    }

    @Step("Создание api-клиента")
    public static RestApiClient createApiClient(RestApiClient apiClient) {
        return apiClient;
    }

    @Step("Получение пароля")
    public static String getPassword(String password) {
        return password;
    }

    @Step("Изменение пароля")
    public static void setPassword(UserInfoDto dto, String password) {
        dto.getUser().setPassword(password);
    }

    @Step("Изменение статуса")
    public static void setStatus(UserInfoDto dto, Integer statusCode) {
        dto.getUser().setStatus(statusCode);
    }

    @Step("Получение пароля")
    public static String getEmail(String email) {
        return email;
    }

    @Step("Изменение пароля")
    public static void setEmail(UserInfoDto dto, String email) {
        dto.getUser().setMail(email);
    }
}
