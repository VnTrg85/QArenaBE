package qarenabe.qarenabe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import qarenabe.qarenabe.dto.MonthlyPayoutDTO;
import qarenabe.qarenabe.dto.UserDeviceDTO;
import qarenabe.qarenabe.service.UserPayoutBug.UserPayoutBugService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("/userPayoutBug")
public class UserPayoutBugController {
    @Autowired
    private UserPayoutBugService userPayoutBugService;
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getPayoutMonthByUser(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
         try {
            List<MonthlyPayoutDTO> res = userPayoutBugService.getAllPayoutsByUser(userId);
            response.put("status", "success");
            response.put("data", res);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response );
        }
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getPayoutByProject(@PathVariable Long projectId) {
        Map<String, Object> response = new HashMap<>();
         try {
            Long res = userPayoutBugService.getAllPayoutByProject(projectId);
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
