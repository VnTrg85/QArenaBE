package qarenabe.qarenabe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class AuthResponse {
    private String role;
    private String email;
    private String token;
}
