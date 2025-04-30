package qarenabe.qarenabe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import qarenabe.qarenabe.dto.PayoutBugDTO;
import qarenabe.qarenabe.entity.Session;
import qarenabe.qarenabe.service.Session.SessionService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;





@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/session")
public class SessionController {
    @Autowired
    private SessionService sessionService;
    @GetMapping("/get/userId/{userId}/project/{projectId}")
    public ResponseEntity<?> getByUserProject(@PathVariable Long userId, @PathVariable Long projectId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Session> res = sessionService.getSessionsByProjectId(userId, projectId);
            response.put("status", "success");
            response.put("data", res);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    @GetMapping("/get/userId/{userId}/project/{projectId}/doing")
    public ResponseEntity<?> getDoingSessionUserProject(@PathVariable Long userId, @PathVariable Long projectId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Session res = sessionService.getSessionsDoingByProjectId(userId, projectId);
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
    public ResponseEntity<?> createSession(@RequestBody Session session) {
        Map<String, Object> response = new HashMap<>();
        try {
            Session res = sessionService.createSession(session);
            response.put("status", "success");
            response.put("data", res);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/end/{sessionId}")
    public ResponseEntity<?> endSession(@PathVariable Long sessionId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Session res = sessionService.endSession(sessionId);
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
