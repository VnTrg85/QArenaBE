package qarenabe.qarenabe.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String avatar;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String phone;
    @Getter
    @Setter
    private String address;
    @Getter
    @Setter
    private Long role;

    public UserDTO(Long id,String name, String avatar)  {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
    }
}
