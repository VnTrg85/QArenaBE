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

import qarenabe.qarenabe.dto.ReproductionDTO;
import qarenabe.qarenabe.entity.Reproduction;
import qarenabe.qarenabe.entity.Session;
import qarenabe.qarenabe.service.Reproduction.ReproductionService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/reproduction")
public class ReproductionController {
    @Autowired
    private ReproductionService reproductionService;
    @GetMapping("/bugreport/{id}")
    public ResponseEntity<?> getAllByBugReport(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<ReproductionDTO> listRes = reproductionService.getListReproductByBugReport(id);
            response.put("status", "success");
            response.put("data", listRes);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    @PostMapping("/create")
    public ResponseEntity<?> createReproduction(@RequestBody Reproduction reproduction) {
        Map<String, Object> response = new HashMap<>();
        try {
            ReproductionDTO res = reproductionService.createReproduction(reproduction);
            response.put("status", "success");
            response.put("data", res);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getAllByUser(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<ReproductionDTO> listRes = reproductionService.getListReproductByUser(id);
            response.put("status", "success");
            response.put("data", listRes);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<?> acceptReproduction(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            ReproductionDTO res = reproductionService.acceptReproduction(id);
            response.put("status", "success");
            response.put("data", res);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    @PostMapping("/reject/{id}")
    public ResponseEntity<?> rejectReproduction(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            ReproductionDTO res = reproductionService.rejectReproduction(id);
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
