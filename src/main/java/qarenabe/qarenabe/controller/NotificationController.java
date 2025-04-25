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

import qarenabe.qarenabe.dto.MessageDTO;
import qarenabe.qarenabe.dto.NotificationDTO;
import qarenabe.qarenabe.entity.Notification;
import qarenabe.qarenabe.service.Notification.NotificationService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/get/unread/{id}")
    public ResponseEntity<?> getNumberUnRead(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Long number = notificationService.getNumberUnReadByUser(id);
            response.put("status", "success");
            response.put("data", number);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    @GetMapping("/get/user/{id}")
    public ResponseEntity<?> getNotiByUser(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<NotificationDTO> listNoti = notificationService.getListNotificationByReceiver(id);
            response.put("status", "success");
            response.put("data", listNoti);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/mark/user/{id}")
    public ResponseEntity<?> markAllRead(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            notificationService.markAllRead(id);
            response.put("status", "success");
            response.put("data", "Mark all read success");
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
}
