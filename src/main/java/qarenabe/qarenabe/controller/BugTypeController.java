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

import qarenabe.qarenabe.dto.TestProjectUserResponse;
import qarenabe.qarenabe.entity.BugType;
import qarenabe.qarenabe.service.BugType.BugTypeService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;





@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/bugType")
public class BugTypeController {

    @Autowired
    private BugTypeService bugTypeService;
    @GetMapping("/getAll")
    public ResponseEntity<?> getBugType() {
        Map<String, Object> response = new HashMap<>();
         try {
            List<BugType> responseList = bugTypeService.getBugTypes();
            response.put("status", "success");
            response.put("data", responseList);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBugType(@RequestBody BugType entity) {
        Map<String, Object> response = new HashMap<>();
         try {
            BugType res = bugTypeService.createBugType(entity);
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
