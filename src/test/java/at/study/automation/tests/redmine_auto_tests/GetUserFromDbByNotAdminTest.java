package at.study.automation.tests.redmine_auto_tests;

import at.study.automation.api.client.RestApiClient;
import at.study.automation.api.client.RestMethod;
import at.study.automation.api.client.RestRequest;
import at.study.automation.api.client.RestResponse;
import at.study.automation.api.dto.users.UserDto;
import at.study.automation.api.dto.users.UserInfoDto;
import at.study.automation.api.rest_assured.RestAssuredClient;
import at.study.automation.api.rest_assured.RestAssuredRequest;
import at.study.automation.model.user.Token;
import at.study.automation.model.user.User;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

import static at.study.automation.allure.AllureAssert.*;
import static at.study.automation.allure.AllureUtils.generatingRequest;
import static at.study.automation.allure.AllureUtils.initDtoFromResponse;

public class GetUserFromDbByNotAdminTest {

    private RestApiClient apiClient;
    private final String userEndpoint = "/users/%d.json";
    private User notAdmin;
    private User user;

    @BeforeMethod(description = "В системе заведен пользователь без прав администратора. " +
            "У пользователя есть доступ к API и ключ API. " +
            "Заведен еще один пользователь в системе. " +
            "Создан api-клиент для отправки запроса на сервер пользователем без прав администратора.")
    public void prepareFixtures() {
        notAdmin = new User() {{
            setTokens(Collections.singletonList(new Token(this)));
        }}.create();

        user = new User().create();

        apiClient = new RestAssuredClient(notAdmin);
    }

    @Test(description = "Получение пользователей. Пользователь без прав администратора")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Савельев Андрей Владимирович")
    public void getUserFromDbByNotAdminTest() {
        getUserWithApiKeyFromDb(notAdmin.getId());
        getUserFromDb(user.getId());
    }

    @Step("Получение пользователя с Api-ключом из бд")
    private void getUserWithApiKeyFromDb(Integer userId) {
        RestRequest request = generatingRequest(
                new RestAssuredRequest(RestMethod.GET, String.format(userEndpoint, userId), null, null, null)
        );

        RestResponse response = apiClient.execute(request);

        UserDto dto = initDtoFromResponse(response.getPayload(UserInfoDto.class).getUser());

        assertEquals(
                response.getStatusCode(),
                200,
                "Статус код = 200. Статус коды совпадают");
        assertFalse(
                dto.getIsAdmin(),
                "Прав администратора нет"
        );
        assertTrue(dto.getApiKey().matches(
                        "^[0-9a-fA-F]*$"),
                "Api-ключ имеет шестнадцатеричный формат"
        );
    }

    @Step("Получение пользователя без Api-ключа из бд")
    private void getUserFromDb(Integer userId) {
        RestRequest request = generatingRequest(
                new RestAssuredRequest(RestMethod.GET, String.format(userEndpoint, userId), null, null, null)
        );

        RestResponse response = apiClient.execute(request);

        UserDto dto = initDtoFromResponse(response.getPayload(UserInfoDto.class).getUser());

        assertEquals(
                response.getStatusCode(),
                200,
                "Статус код = 200. Статус коды совпадают"
        );
        assertNull(
                dto.getIsAdmin(),
                "Прав администратора нет"
        );
        assertNull(
                dto.getApiKey(),
                "Api-ключ отсутствует"
        );
    }
}
