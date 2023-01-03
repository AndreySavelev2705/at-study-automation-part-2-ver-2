package at.study.automation.api.dto.users;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer id;

    private String login;

    @SerializedName("admin")
    private Boolean isAdmin;

    @SerializedName("firstname")
    private String firstName;

    @SerializedName("lastname")
    private String lastName;

    private String mail;

    private String password;

    @SerializedName("created_on")
    private LocalDateTime createdOn;

    @SerializedName("last_login_on")
    private LocalDateTime lastLoginOn;

    @SerializedName("api_key")
    private String apiKey;

    private Integer status;
}
