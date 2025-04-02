package qarenabe.qarenabe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import qarenabe.qarenabe.dto.AuthResponse;
import qarenabe.qarenabe.dto.TestProjectUserResponse;
import qarenabe.qarenabe.entity.TestProject_User;
import qarenabe.qarenabe.repository.TestProject_UserRepository;
import qarenabe.qarenabe.service.AuthService.AuthService;
import qarenabe.qarenabe.service.TestProject_User.TestProject_UserService;
import qarenabe.qarenabe.service.User.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/testProjectUser")
public class TestProject_UserController {

    @Autowired
    private TestProject_UserService testProject_UserService;
    
    @GetMapping("/get/user")
    public ResponseEntity<?> getTestProjects(@RequestParam Long userId) {
         try {
            List<TestProjectUserResponse> responseList = testProject_UserService.getProjectByUserId(userId);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", responseList);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    

}
