package at.study.automation.tests;

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
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

import static at.study.automation.api.rest_assured.GsonProvider.GSON;

public class CreateUserByNotAdminUserTest {
    private RestApiClient apiClient;
    private RestRequest request;

    @BeforeMethod
    public void prepareFixtures() {
        User user = new User() {{
            setTokens(Collections.singletonList(new Token(this)));
        }}.create();

        apiClient = new RestAssuredClient(user);

        UserInfoDto dto = new UserInfoDto(
                new UserDto()
                        .setLogin("jplang2510")
                        .setLastName("Jean-Philippe2510")
                        .setFirstName("Lang2510")
                        .setMail("jp_lang2710@yahoo.fr")
                        .setPassword("secret2510")
        );
        String body = GSON.toJson(dto);

        request = new RestAssuredRequest(RestMethod.POST, "/users.json", null, null, body);
    }

    @Test
    public void createUserByNotAdminUserTest() {
        RestResponse response = apiClient.execute(request);

        Assert.assertEquals(response.getStatusCode(), 403);
    }
}
