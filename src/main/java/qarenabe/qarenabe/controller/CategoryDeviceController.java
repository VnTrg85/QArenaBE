package qarenabe.qarenabe.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import qarenabe.qarenabe.entity.BugType;
import qarenabe.qarenabe.entity.CategoryDevice;
import qarenabe.qarenabe.service.CategoryDeviceService.CategoryDeviceService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("/categoryDevice")
public class CategoryDeviceController {

    @Autowired
    private CategoryDeviceService categoryDeviceService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getListCategoryDevice() {
        Map<String, Object> response = new HashMap<>();
         try {
            List<CategoryDevice> res = categoryDeviceService.getListCategoryDevice();
            response.put("status", "success");
            response.put("data", res);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCategoryDevice(@RequestBody CategoryDevice categoryDevice){ 
        Map<String, Object> response = new HashMap<>();
         try {
            CategoryDevice res = categoryDeviceService.createCategoryDevice(categoryDevice);
            response.put("status", "success");
            response.put("data", res);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
}
