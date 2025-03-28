package qarenabe.qarenabe.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import qarenabe.qarenabe.dto.AuthRequest;
import qarenabe.qarenabe.dto.AuthResponse;
import qarenabe.qarenabe.entity.User;
import qarenabe.qarenabe.service.AuthService.AuthService;
import qarenabe.qarenabe.service.User.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
         try {
            AuthResponse authResponse = authService.login(request);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", authResponse);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        try {
            User newUser = userService.addUser(request);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", newUser.getId());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("data", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        return ResponseEntity.ok(authService.logout(response));
    }

    @PostMapping("/verify-token")
    public ResponseEntity<?> verify(@RequestBody AuthRequest request) {
        try {
            AuthResponse res = authService.verify_token(request);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", "Token is valid");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("data", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

}
