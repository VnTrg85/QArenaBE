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

import qarenabe.qarenabe.dto.TestprojectDTO;
import qarenabe.qarenabe.entity.TestProject;
import qarenabe.qarenabe.service.TestProject.TestProjectService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/testProject")
public class TestProjectController {
    @Autowired
    private TestProjectService testProjectService;

    @GetMapping("/getAll")
    public ResponseEntity<List<TestprojectDTO>> getAllProjects() {
        List<TestprojectDTO> projects = testProjectService.getAllProjects();
        if (projects.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(projects);
        }
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestprojectDTO> getProjectById(@PathVariable Long id) {
        return testProjectService.getProjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/getDetail/{id}")
    public ResponseEntity<?> getDetailProjectById(@PathVariable Long id) {
       HashMap<String,Object> response = new HashMap<>();
       try {
            TestprojectDTO testproject = testProjectService.getProjectDetailById(id);
            response.put("status", "success");
            response.put("data", testproject);
            return ResponseEntity.ok(response);
       } catch (Exception e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response );
       }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TestprojectDTO>> getAllProjectsByUserId(@PathVariable Long userId) {
        List<TestprojectDTO> projects = testProjectService.getAllProjectsByUserId(userId);
        return ResponseEntity.ok(projects);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProject(@RequestBody TestprojectDTO dto) {
        Map<String, Object> response = new HashMap<>();
        try {
            TestprojectDTO createdProject = testProjectService.createProject(dto);
            response.put("status", "success");
            response.put("data", createdProject);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<TestprojectDTO> updateProject(@PathVariable Long id, @RequestBody TestprojectDTO dto) {
        return testProjectService.updateProject(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        testProjectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
