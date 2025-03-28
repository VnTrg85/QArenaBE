package qarenabe.qarenabe.service.AuthService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import qarenabe.qarenabe.dto.AuthRequest;
import qarenabe.qarenabe.dto.AuthResponse;
import qarenabe.qarenabe.entity.User;
import qarenabe.qarenabe.repository.UserRepository;
import qarenabe.qarenabe.service.Security.SecurityService;


@Service
public class AuthServiceImpl implements AuthService{
    @Autowired 
    private UserRepository userRepository;
    @Autowired
    private SecurityService securityService;
    @Override
    public AuthResponse login(AuthRequest params) {
        User user = userRepository.findByEmail(params.getEmail()).orElseThrow(() -> new BadCredentialsException("Email is not valid"));

        // Kiểm tra mật khẩu
        if (securityService.decode(params.getPassword()).equals(user.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }
        // Tạo JWT token
        String token = securityService.generateToken(user.getEmail());
        return new AuthResponse(user.getUserRole().getName(),user.getEmail(),token);
    }

    @Override
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("auth_token", null);
        cookie.setMaxAge(0);  
        cookie.setPath("/");
        // Add the cookie to the response
        response.addCookie(cookie);
        return "Logout successful";
    }

    @Override
    public AuthResponse verify_token(AuthRequest request) {
        Boolean isValidToken = securityService.verifyToken(request.getToken(), request.getEmail());
        if(isValidToken) {
            return new AuthResponse(null,null,"Token is valid");
        }else {
            throw new BadCredentialsException("Token is not valid");
        }
    }

}
