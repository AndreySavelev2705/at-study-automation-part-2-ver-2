package at.study.automation.tests.redmine_auto_tests;

import at.study.automation.api.client.RestApiClient;
import at.study.automation.api.client.RestMethod;
import at.study.automation.api.client.RestRequest;
import at.study.automation.api.client.RestResponse;
import at.study.automation.api.rest_assured.RestAssuredClient;
import at.study.automation.api.rest_assured.RestAssuredRequest;
import at.study.automation.db.requests.UserRequests;
import at.study.automation.model.user.Token;
import at.study.automation.model.user.User;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

import static at.study.automation.allure.AllureAssert.assertEquals;
import static at.study.automation.allure.AllureAssert.assertNotNull;
import static at.study.automation.allure.AllureUtils.generatingRequest;

public class DeleteUserFromDbByNotAdminTest {

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

    @Test(description = "Удаление пользователей. Пользователь без прав администратора")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Савельев Андрей Владимирович")
    public void deleteUserFromDbByNotAdminTest() {
        deleteUserFromDb(user.getId());
        deleteUserWithApiKeyFromDb(notAdmin.getId());
    }

    @Step("Удаление пользователя с Api-ключом")
    private void deleteUserWithApiKeyFromDb(Integer userId) {
        RestRequest request = generatingRequest(
                new RestAssuredRequest(RestMethod.DELETE, String.format(userEndpoint, userId), null, null, null)
        );

        RestResponse response = apiClient.execute(request);

        User userFromDb = new UserRequests().read(userId);

        assertEquals(
                response.getStatusCode(),
                403,
                "Статус код = 403. Статус коды совпадают"
        );
        assertNotNull(
                userFromDb.getId(),
                "Id пользователя не пустой, пользователь не удален."
        );
    }

    @Step("Удаление пользователя без айпи ключа")
    private void deleteUserFromDb(Integer userId) {
        RestRequest request = generatingRequest(
                new RestAssuredRequest(RestMethod.DELETE, String.format(userEndpoint, userId), null, null, null)
        );
        ;

        RestResponse response = apiClient.execute(request);

        User userFromDb = new UserRequests().read(userId);

        assertEquals(
                response.getStatusCode(),
                403,
                "Статус код = 403. Статус коды совпадают"
        );
        assertNotNull(
                userFromDb.getId(),
                "Id пользователя не пустой, пользователь не удален."
        );
    }
}
