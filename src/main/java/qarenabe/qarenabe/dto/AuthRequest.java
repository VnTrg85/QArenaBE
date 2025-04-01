package qarenabe.qarenabe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    private String email;
    private String name;
    private String password;
    private Long roleId;
    private String token;
    private String check;
}
