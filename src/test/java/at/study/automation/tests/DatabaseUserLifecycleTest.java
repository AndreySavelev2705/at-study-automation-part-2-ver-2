package at.study.automation.tests;

import at.study.automation.model.user.Email;
import at.study.automation.model.user.Status;
import at.study.automation.model.user.Token;
import at.study.automation.model.user.User;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Random;

public class DatabaseUserLifecycleTest {

    @Test
    public void userLife0cycleTest() {
        User user = new User()
                .setFirstName("Sav_firs_name" + new Random().nextInt(100))
                .setLastName("Sav_last_name" + new Random().nextInt(100))
                .setPassword("kjkszpj33");
        user.setTokens(
                Arrays.asList(
                        new Token(user),
                        new Token(user).setAction(Token.TokenType.SESSION),
                        new Token(user).setAction(Token.TokenType.SESSION)
                )
        ).setEmails(
                Arrays.asList(
                        new Email(user),
                        new Email(user).setIsDefault(false),
                        new Email(user).setIsDefault(false),
                        new Email(user).setIsDefault(false).setAddress("sav_test_project@mail.ru")
                )
        );
        user.create();
        user.setStatus(Status.UNACCEPTED);
        user.update();
        user.setStatus(Status.LOCKED);
        user.update();
        user.delete();
    }
}
