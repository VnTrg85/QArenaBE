package qarenabe.qarenabe.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import qarenabe.qarenabe.dto.AuthRequest;
import qarenabe.qarenabe.dto.AuthResponse;
public interface AuthService {
    public AuthResponse login(AuthRequest params);
    public String logout(HttpServletResponse response);
    public AuthResponse verify_token(AuthRequest reuqest);
}
