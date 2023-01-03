package at.study.automation.tests.redmine_auto_tests;

import at.study.automation.api.client.RestApiClient;
import at.study.automation.api.client.RestMethod;
import at.study.automation.api.client.RestRequest;
import at.study.automation.api.client.RestResponse;
import at.study.automation.api.dto.errors.ErrorInfoDto;
import at.study.automation.api.dto.users.UserDto;
import at.study.automation.api.dto.users.UserInfoDto;
import at.study.automation.api.rest_assured.RestAssuredClient;
import at.study.automation.api.rest_assured.RestAssuredRequest;
import at.study.automation.db.requests.UserRequests;
import at.study.automation.model.user.Token;
import at.study.automation.model.user.User;
import at.study.automation.utils.StringUtils;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

import static at.study.automation.allure.AllureAssert.*;
import static at.study.automation.allure.AllureUtils.*;
import static at.study.automation.api.rest_assured.GsonProvider.GSON;

public class CrudUserByAdminTest {

    private RestApiClient apiClient;
    private UserInfoDto dto;
    private final String userEndpoint = "/users/%d.json";

    @BeforeMethod(description = "В системе заведен пользователь с правами администратора. " +
            "У пользователя есть доступ к API и ключ API. " +
            "В памяти создан пользователь, чьи данные будут использоваться для составления тела запроса. " +
            "Создан api-клиент для отправки запроса на сервер пользователем без прав администратора.")
    public void prepareFixtures() {

        User admin = new User() {{
            setIsAdmin(true);
            setTokens(Collections.singletonList(new Token(this)));
        }}.create();

        dto = initDto(new UserInfoDto(
                        new UserDto()
                                .setLogin("SavelevAutoLogin" + StringUtils.randomEnglishString(10))
                                .setLastName("SavelevAuto" + StringUtils.randomEnglishString(6))
                                .setFirstName("SavelevAuto" + StringUtils.randomEnglishString(8))
                                .setMail(StringUtils.randomEmail())
                                .setPassword(StringUtils.randomEnglishString(8))
                                .setStatus(2)
                )
        );

        apiClient = createApiClient(new RestAssuredClient(admin));
    }

    @Test(description = "Создание, изменение, получение, удаление пользователя. Администратор системы.")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Савельев Андрей Владимирович")
    public void crudUserTest() {
        Integer createdUserId = createAndValidateUser();

        createAndValidateDuplicatedUser();
        createAndValidateUserWithShortPasswordAndBadEmail();
        updateAndValidateUserWithNewStatus(createdUserId);
        getUserWithNewStatus(createdUserId);
        deleteUser(createdUserId);
        checkUserWasDeleted(createdUserId);
    }

    @Step("Создание и проверка пользователя")
    private Integer createAndValidateUser() {
        RestRequest request = generatingRequest(
                new RestAssuredRequest(RestMethod.POST, "users.json", null, null, GSON.toJson(dto))
        );

        RestResponse response = apiClient.execute(request);

        UserDto userFromResponse = initDtoFromResponse(response.getPayload(UserInfoDto.class).getUser());

        assertEquals(
                response.getStatusCode(),
                201,
                "Статус код = 201. Статус коды совпадают"
        );
        assertTrue(
                userFromResponse.getId() > 0,
                "Id пользователя не пустой"
        );
        assertEquals(
                userFromResponse.getLogin(),
                dto.getUser().getLogin(),
                "Логины совпадают"
        );
        assertEquals(
                userFromResponse.getIsAdmin(),
                Boolean.FALSE,
                "Отсутствие прав администратора совпадает"
        );
        assertEquals(
                userFromResponse.getFirstName(),
                dto.getUser().getFirstName(),
                "Имена совпадают"
        );
        assertEquals(
                userFromResponse.getLastName(),
                dto.getUser().getLastName(),
                "Фамилии совпадают"
        );
        assertEquals(
                userFromResponse.getMail(),
                dto.getUser().getMail(),
                "Email-ы совпадают"
        );
        assertNotNull(
                userFromResponse.getCreatedOn(),
                "Дата создания существует и не null");
        assertNull(
                userFromResponse.getLastLoginOn(),
                "Дата последнего логина не существует и равна null"
        );
        assertTrue(
                userFromResponse.getApiKey().matches("^[0-9a-fA-F]*$"),
                "Api-ключ имеет шестнадцатеричный формат"
        );
        assertEquals(
                userFromResponse.getStatus(),
                dto.getUser().getStatus(),
                "Статус пользователя равен: " + dto.getUser().getStatus()
        );

        User user = new UserRequests().read(userFromResponse.getId());

        assertEquals(
                userFromResponse.getId(),
                user.getId(),
                "ID пользователя равен: " + user.getId()
        );

        return userFromResponse.getId();
    }

    @Step("Создание и проверка пользователя повторно с тем же телом запроса")
    private void createAndValidateDuplicatedUser() {
        RestRequest request = generatingRequest(
                new RestAssuredRequest(RestMethod.POST, "users.json", null, null, GSON.toJson(dto))
        );

        RestResponse response = apiClient.execute(request);

        assertEquals(
                response.getStatusCode(),
                422,
                "Статус код = 422. Статус коды совпадают"
        );

        ErrorInfoDto errorInfoDto = initErrorInfoDtoFromResponse(response.getPayload(ErrorInfoDto.class));

        assertEquals(
                errorInfoDto.getErrors().get(0),
                "Email уже существует"
        );
        assertEquals(
                errorInfoDto.getErrors().get(1),
                "Пользователь уже существует"
        );
    }

    @Step("Создание и проверка пользователя повторно с тем же телом запроса, но с коротким паролем и невалидным email")
    private void createAndValidateUserWithShortPasswordAndBadEmail() {
        String currentPassword = getPassword(dto.getUser().getPassword());
        String currentEmail = getEmail(dto.getUser().getMail());

        setPassword(dto, "kj3k");
        setEmail(dto, "lsdkfjg9834sa");

        RestRequest request = generatingRequest(
                new RestAssuredRequest(RestMethod.POST, "users.json", null, null, GSON.toJson(dto))
        );

        RestResponse response = apiClient.execute(request);

        ErrorInfoDto errorInfoDto = initErrorInfoDtoFromResponse(response.getPayload(ErrorInfoDto.class));

        assertEquals(
                response.getStatusCode(),
                422,
                "Статус код = 422. Статус коды совпадают"
        );
        assertEquals(
                errorInfoDto.getErrors().get(0),
                "Email имеет неверное значение"
        );
        assertEquals(
                errorInfoDto.getErrors().get(1),
                "Пользователь уже существует"
        );
        assertEquals(
                errorInfoDto.getErrors().get(2),
                "Пароль недостаточной длины (не может быть меньше 8 символа)"
        );

        setPassword(dto, currentPassword);
        setEmail(dto, currentEmail);
    }

    @Step("Изменение у существующего пользователя статуса")
    private void updateAndValidateUserWithNewStatus(Integer userId) {
        setStatus(dto, 1);

        RestRequest request = generatingRequest(
                new RestAssuredRequest(RestMethod.PUT, String.format(userEndpoint, userId), null, null, GSON.toJson(dto))
        );

        RestResponse response = apiClient.execute(request);

        assertEquals(
                response.getStatusCode(),
                204,
                "Статус код = 204. Статус коды совпадают"
        );

        User user = new UserRequests().read(userId);

        assertEquals(
                user.getStatus().statusCode,
                dto.getUser().getStatus(),
                "Статусы совпадают"
        );
    }

    @Step("Получение пользователся с новым статусом")
    private void getUserWithNewStatus(Integer userId) {
        RestRequest request = generatingRequest(
                new RestAssuredRequest(RestMethod.GET, String.format(userEndpoint, userId), null, null, null)
        );

        RestResponse response = apiClient.execute(request);

        assertEquals(
                response.getStatusCode(),
                200,
                "Статус код = 200. Статус коды совпадают"
        );

        User user = new UserRequests().read(userId);

        assertEquals(
                user.getStatus().statusCode,
                dto.getUser().getStatus(),
                "Статусы совпадают"
        );
    }

    @Step("Удаление пользователя")
    private void deleteUser(Integer userId) {
        RestRequest request = generatingRequest(
                new RestAssuredRequest(RestMethod.DELETE, String.format(userEndpoint, userId), null, null, null)
        );

        RestResponse response = apiClient.execute(request);

        assertEquals(
                response.getStatusCode(),
                204,
                "Статус код = 204. Статус коды совпадают"
        );
        assertNull(
                new UserRequests().read(userId),
                "Пользователь не существует"
        );
    }

    @Step("Проверка, что нельзя удалить уже удаленного пользователя")
    private void checkUserWasDeleted(Integer userId) {
        RestRequest request = generatingRequest(
                new RestAssuredRequest(RestMethod.DELETE, String.format(userEndpoint, userId), null, null, null)
        );

        RestResponse response = apiClient.execute(request);

        assertEquals(
                response.getStatusCode(),
                404,
                "Пользователь не существует"
        );
    }
}
