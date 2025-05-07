package qarenabe.qarenabe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import qarenabe.qarenabe.dto.PayoutBugDTO;
import qarenabe.qarenabe.entity.PayoutBug;
import qarenabe.qarenabe.service.PayoutBug.PayoutBugService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;





@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/payoutBug")
public class PayoutBugController {
    @Autowired
    private PayoutBugService payoutBugService;
    @GetMapping("/get/{testProjectId}/testproject")
    public ResponseEntity<?> getBugPayoutByTestProject(@PathVariable Long testProjectId) {
        
        Map<String, Object> response = new HashMap<>();
        try {
            List<PayoutBugDTO> res = payoutBugService.getPayoutBugByProject(testProjectId);
            response.put("status", "success");
            response.put("data", res);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    @PostMapping("/create")
    public ResponseEntity<?> createPayoutBug(@RequestBody PayoutBug payoutBug) {
        Map<String, Object> response = new HashMap<>();
        try {
            PayoutBugDTO res = payoutBugService.createPayoutBug(payoutBug);
            response.put("status", "success");
            response.put("data", res);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePayoutBug(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Boolean res = payoutBugService.deletePayoutBug(id);
            response.put("status", "success");
            response.put("data", res);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
}
