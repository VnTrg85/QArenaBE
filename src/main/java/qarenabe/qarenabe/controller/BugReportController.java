package qarenabe.qarenabe.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import qarenabe.qarenabe.dto.BugReportDTO;
import qarenabe.qarenabe.dto.BugReportDTOSecond;
import qarenabe.qarenabe.dto.BugReportSumary;
import qarenabe.qarenabe.entity.BugReport;
import qarenabe.qarenabe.entity.BugType;
import qarenabe.qarenabe.service.BugReport.BugReportService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;





@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/bugReport")
public class BugReportController {
    @Autowired
    private BugReportService bugReportService;

    @GetMapping("/{id}")
    public BugReportDTO getBugReportById(@PathVariable Long id) {
        return bugReportService.getBugReportById(id);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateBugStatus(@PathVariable Long id, @RequestBody BugReportDTO dto) {
        bugReportService.updateBugStatus(id, dto);
        return ResponseEntity.ok("Bug status updated successfully");
    }

    @GetMapping("/export/{projectId}")
    public ResponseEntity<InputStreamResource> exportBugsToExcel(@PathVariable Long projectId) throws IOException {
        ByteArrayInputStream in = bugReportService.exportBugsToExcel(projectId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=bug_report_project_" + projectId + ".xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(in));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBugReport(@RequestBody BugReport bugReport) {
         Map<String, Object> response = new HashMap<>();
         try {
            BugReport bugRes = bugReportService.createBugReport(bugReport);
            response.put("status", "success");
            response.put("data", bugRes);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/get/project/{id}")
    public ResponseEntity<?> getListBugs(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
         try {
            List<BugReportDTOSecond> listRes = bugReportService.getListBugReportByProject(id);
            response.put("status", "success");
            response.put("data", listRes);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    @GetMapping("/getDetail/{id}")
    public ResponseEntity<?> getBugDetail(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
         try {
            BugReport res = bugReportService.getBugReportDetail(id);
            response.put("status", "success");
            response.put("data", res);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/getSumary/{id}")
    public ResponseEntity<?> getBugReportSumary(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
         try {
            List<BugReportSumary> res = bugReportService.getBugReportsSumaryByUser(id);
            response.put("status", "success");
            response.put("data", res);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        
    }

    @GetMapping("/get/user/{id}")
    public ResponseEntity<?> getBugReportByUser(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
         try {
            List<BugReportDTOSecond> res = bugReportService.getListBugReportsByUser(id);
            response.put("status", "success");
            response.put("data", res);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            response.put("status", "error");
            response.put("data", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        
    }

    @PostMapping("/update/status/{changedUserId}")
    public ResponseEntity<?> updateBugReportStatus(@RequestBody BugReportDTOSecond bugReportDTOSecond, @PathVariable Long changedUserId) {
        Map<String, Object> response = new HashMap<>();
         try {
            Boolean res = bugReportService.updateStatusOfBugReport(bugReportDTOSecond,changedUserId);
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
