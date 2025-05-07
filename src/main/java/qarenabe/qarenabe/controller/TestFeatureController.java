package qarenabe.qarenabe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import qarenabe.qarenabe.dto.TestFeatureDTO;
import qarenabe.qarenabe.entity.TestFeature;
import qarenabe.qarenabe.service.TestFeature.TestFeatureService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;






@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/testFeature")
public class TestFeatureController {
    @Autowired
    private TestFeatureService testFeatureService;

    @GetMapping("/get/project/{id}")
    public ResponseEntity<?> getTestFeatureByProjectId(@PathVariable Long id) {
        try {
            List<TestFeatureDTO> listRes = testFeatureService.getFeaturesByTestProject(id);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", listRes);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    @GetMapping("/getDetail/project/{id}")
    public ResponseEntity<?> getDetailTestFeatureByProjectId(@PathVariable Long id) {
        try {
            List<TestFeatureDTO> listRes = testFeatureService.getDetailFeaturesByTestProject(id);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", listRes);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getTestFeatureById(@PathVariable Long id) {
        try {
            TestFeatureDTO res = testFeatureService.getFeatureById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", res);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    @PostMapping("/create")
    public ResponseEntity<?> createTestFeature(@RequestBody TestFeature testFeature) {
        try {
            TestFeatureDTO res = testFeatureService.createTestFeature(testFeature);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", res);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTestFeature(@PathVariable Long id) {
        try {
            Boolean res = testFeatureService.deleteTestFeature(id);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", res);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
