package at.study.automation.tests;

import at.study.automation.api.client.RestApiClient;
import at.study.automation.api.client.RestMethod;
import at.study.automation.api.client.RestRequest;
import at.study.automation.api.client.RestResponse;
import at.study.automation.api.dto.users.UserDto;
import at.study.automation.api.dto.users.UserInfoDto;
import at.study.automation.api.rest_assured.RestAssuredClient;
import at.study.automation.api.rest_assured.RestAssuredRequest;
import at.study.automation.model.user.Status;
import at.study.automation.model.user.Token;
import at.study.automation.model.user.User;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.testng.AssertJUnit.assertEquals;

public class GetUserByIdTest {
    private RestApiClient client;
    private RestRequest request;
    private User createdUser;

    @BeforeMethod
    public void prepareFixtures() {

        User apiUser = new User() {{
            setTokens(Collections.singletonList(new Token(this)));
            setIsAdmin(true);
        }}.create();

        createdUser = new User() {{
            setStatus(Status.LOCKED);
        }}.create();

        client = new RestAssuredClient(apiUser);

        String endpoint = "/users/" + createdUser.getId() + ".json";
        request = new RestAssuredRequest(RestMethod.GET, endpoint, null, null, null);
    }

    @Test
    public void getUserByIdTest() {
        RestResponse response = client.execute(request);

        assertEquals(response.getStatusCode(), 200);

        UserInfoDto responseData = response.getPayload(UserInfoDto.class);
        UserDto responseUser = responseData.getUser();

        assertEquals(responseUser.getLastName(), createdUser.getLastName());
        assertEquals(responseUser.getFirstName(), createdUser.getFirstName());
        assertEquals(responseUser.getStatus().intValue(), Status.LOCKED.statusCode);
    }
}
