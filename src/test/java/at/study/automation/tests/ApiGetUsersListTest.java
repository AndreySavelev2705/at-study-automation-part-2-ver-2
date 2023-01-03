package at.study.automation.tests;

import at.study.automation.api.client.RestApiClient;
import at.study.automation.api.client.RestMethod;
import at.study.automation.api.client.RestRequest;
import at.study.automation.api.client.RestResponse;
import at.study.automation.api.dto.users.UsersListDto;
import at.study.automation.api.rest_assured.RestAssuredClient;
import at.study.automation.api.rest_assured.RestAssuredRequest;
import at.study.automation.model.user.Token;
import at.study.automation.model.user.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

public class ApiGetUsersListTest {

    private RestApiClient apiClient;
    private RestRequest request;

    @BeforeMethod
    public void prepareFixtures() {
        User user = new User() {{
            setIsAdmin(true);
            setTokens(Collections.singletonList(new Token(this)));
        }}.create();

        apiClient = new RestAssuredClient(user);

        request = new RestAssuredRequest(RestMethod.GET, "/users.json", null, null, null);
    }

    @Test
    public void getUsersByAdminTest() {
        RestResponse response = apiClient.execute(request);

        Assert.assertEquals(response.getStatusCode(), 200);

        UsersListDto responseData = response.getPayload(UsersListDto.class);

        Assert.assertEquals(responseData.getLimit().intValue(), 25);
    }
}
