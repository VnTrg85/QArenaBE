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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import qarenabe.qarenabe.dto.AuthRequest;
import qarenabe.qarenabe.dto.AuthResponse;
import qarenabe.qarenabe.dto.TestProjectUserResponse;
import qarenabe.qarenabe.dto.UserDeviceDTO;
import qarenabe.qarenabe.dto.UserDeviceRequestDTO;
import qarenabe.qarenabe.entity.User;
import qarenabe.qarenabe.entity.UserDevice;
import qarenabe.qarenabe.service.AuthService.AuthService;
import qarenabe.qarenabe.service.User.UserService;
import qarenabe.qarenabe.service.UserDeviceService.UserDeviceService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("/userDevice")
public class UserDeviceController {
    @Autowired
    private UserDeviceService userDeviceService;
    
    @GetMapping("/get/user/{id}")
    public ResponseEntity<?> getByUserId(@PathVariable Long id) {
         Map<String, Object> response = new HashMap<>();
         try {
            List<UserDeviceDTO> responseList = userDeviceService.getListUserDeviceByUserId(id);
            response.put("status", "success");
            response.put("data", responseList);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response );
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserDeviceRequestDTO userDeviceRequestDTO) {
         Map<String, Object> response = new HashMap<>();
         try {
            UserDeviceDTO res = userDeviceService.updateUserDevice(userDeviceRequestDTO.getId(), userDeviceRequestDTO.getSelectedVersion(), userDeviceRequestDTO.getBrowserIds());
            response.put("status", "success");
            response.put("data", res);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response );
        }
    }


    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UserDevice userDevice) {
         Map<String, Object> response = new HashMap<>();
         try {
            UserDeviceDTO res = userDeviceService.createUserDevice(userDevice);
            response.put("status", "success");
            response.put("data", res);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response );
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> create(@PathVariable Long id) {
         Map<String, Object> response = new HashMap<>();
         try {
            String res = userDeviceService.deleteById(id);
            response.put("status", "success");
            response.put("data", res);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response );
        }
    }
}
