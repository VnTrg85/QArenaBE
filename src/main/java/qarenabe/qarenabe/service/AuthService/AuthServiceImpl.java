package qarenabe.qarenabe.service.AuthService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import qarenabe.qarenabe.dto.AuthRequest;
import qarenabe.qarenabe.dto.AuthResponse;
import  qarenabe.qarenabe.entity.*;
import org.springframework.security.authentication.BadCredentialsException;
import  qarenabe.qarenabe.repository.UserRepository;
import  qarenabe.qarenabe.service.Security.SecurityService;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired 
    private UserRepository userRepository;
    @Autowired
    private SecurityService securityService;
    @Override
    public AuthResponse login(AuthRequest params, HttpServletResponse response) {
        List<User> users = userRepository.findAll();
        if(users != null) {
            for (User user : users) {
                if(user.getEmail().equals(params.getEmail())) {
                    String decodePass = securityService.decode(user.getPassword());

                    if(decodePass.equals(params.getPassword())) {
                        // Generate a JWT token
                        String jwtToken = securityService.generateToken(user.getEmail());
                       return new AuthResponse(jwtToken);
                    }
                    else {
                        throw new BadCredentialsException("Invalid email");
                    }
                }
            }
            throw new BadCredentialsException("Invalid password");
        }
        throw new BadCredentialsException("Account is not found");
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

}
