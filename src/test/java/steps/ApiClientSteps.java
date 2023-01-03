package steps;

import at.study.automation.allure.AllureUtils;
import at.study.automation.api.client.RestApiClient;
import at.study.automation.api.rest_assured.RestAssuredClient;
import at.study.automation.context.Context;
import at.study.automation.model.user.User;
import cucumber.api.java.ru.И;

public class ApiClientSteps {

    @И("Создан api-клиент \"(.+)\" для пользователя \"(.+)\"")
    public void createApiClient(String apiClientStashId, String userStashId) {
        User notAdmin = Context.getStash().get(userStashId, User.class);
        RestApiClient apiClient = AllureUtils.createApiClient(new RestAssuredClient(notAdmin));

        Context.getStash().put(apiClientStashId, apiClient);
    }
}
