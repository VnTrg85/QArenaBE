package qarenabe.qarenabe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import qarenabe.qarenabe.dto.AuthRequest;
import qarenabe.qarenabe.entity.User;
import qarenabe.qarenabe.service.User.UserService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;





@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)

public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public List<User> getUsers() {
        return userService.getUsers();
    }
    
    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        if(userService.getUser(id) != null) {
            return ResponseEntity.ok(userService.getUser(id));
        }
        return ResponseEntity.ok(null);
    }
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody AuthRequest request) {
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
    

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }
    

    @DeleteMapping("delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.deleteUser(userId));
    }
}
