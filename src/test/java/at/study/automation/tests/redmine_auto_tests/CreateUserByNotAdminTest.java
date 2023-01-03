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
import at.study.automation.utils.StringUtils;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

import static at.study.automation.allure.AllureAssert.assertEquals;
import static at.study.automation.allure.AllureUtils.*;
import static at.study.automation.api.rest_assured.GsonProvider.GSON;

public class CreateUserByNotAdminTest {

    private RestApiClient apiClient;
    private UserInfoDto dto;
    private final String USER_ENDPOINT = "users.json";

    @BeforeMethod(description = "В системе заведен пользователь без прав администратора. " +
            "У пользователя есть доступ к API и ключ API. " +
            "В памяти создан пользователь, чьи данные будут использоваться для составления тела запроса. " +
            "Создан api-клиент для отправки запроса на сервер пользователем без прав администратора.")
    public void prepareFixtures() {
        User notAdmin = new User() {{
            setTokens(Collections.singletonList(new Token(this)));
        }}.create();

        dto = initDto(
                new UserInfoDto(
                        new UserDto()
                                .setLogin("SavelevAutoLogin" + StringUtils.randomEnglishString(10))
                                .setLastName("SavelevAuto" + StringUtils.randomEnglishString(6))
                                .setFirstName("SavelevAuto" + StringUtils.randomEnglishString(8))
                                .setMail(StringUtils.randomEmail())
                                .setPassword(StringUtils.randomEnglishString(8))
                                .setStatus(1)
                )
        );

        apiClient = createApiClient(new RestAssuredClient(notAdmin));
    }

    @Test(description = "Создание пользователя. Пользователь без прав администратора")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Савельев Андрей Владимирович")
    public void createUserTest() {
        RestRequest request = generatingRequest(
                new RestAssuredRequest(RestMethod.POST, USER_ENDPOINT, null, null, GSON.toJson(dto))
        );

        RestResponse response = apiClient.execute(request);

        assertEquals(
                response.getStatusCode(),
                403,
                "Статус код = 403. Статус коды совпадают"
        );
    }
}
