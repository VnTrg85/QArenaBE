package qarenabe.qarenabe.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import qarenabe.qarenabe.entity.Browser;
import qarenabe.qarenabe.entity.BugType;
import qarenabe.qarenabe.entity.CategoryDevice;
import qarenabe.qarenabe.service.BrowserService.BrowserService;
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


@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("/browser")
public class BrowserController {

    @Autowired
    private BrowserService browserService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getListBrowser() {
        Map<String, Object> response = new HashMap<>();
         try {
            List<Browser> res = browserService.getBrowsers();
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
    public ResponseEntity<?> createBrowser(@RequestBody Browser browser){ 
        Map<String, Object> response = new HashMap<>();
         try {
            Browser res = browserService.createBrowser(browser);
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
