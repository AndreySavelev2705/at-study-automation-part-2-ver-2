package at.study.automation.api.dto.users;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class UsersListDto {

    private List<UserDto> users;

    @SerializedName("total_count")
    private Integer totalCount;

    private Integer offset;
    private Integer limit;
}
