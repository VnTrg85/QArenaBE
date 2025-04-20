package qarenabe.qarenabe.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import qarenabe.qarenabe.dto.MessageDTO;
import qarenabe.qarenabe.entity.Device;
import qarenabe.qarenabe.entity.Message;
import qarenabe.qarenabe.service.Message.MessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;





@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @MessageMapping("/bug/chat.send")
    public void receiveMessageBug(MessageDTO message) {
        messageService.saveAndBroadcastBug(message);
    }

    @MessageMapping("/project/chat.send")
    public void receiveMessageProject(MessageDTO message) {
        messageService.saveMessageProject(message);
    }

    @GetMapping("/get/bugReport/{id}")
    public ResponseEntity<?> getMessageByBugReport(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<MessageDTO> res = messageService.getAllMessageByBugId(id);
            response.put("status", "success");
            response.put("data", res);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    @GetMapping("/get/project/{id}")
    public ResponseEntity<?> getMessageByProject(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<MessageDTO> res = messageService.getAllMessageByProjectId(id);
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
