package qarenabe.qarenabe.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import qarenabe.qarenabe.dto.BugReportDTO;
import qarenabe.qarenabe.entity.BugReport;
import qarenabe.qarenabe.service.BugReport.BugReportService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/bugReport")
public class BugReportController {
    @Autowired
    private BugReportService bugReportService;

    @GetMapping("/project/{projectId}")
    public List<BugReportDTO> getBugReportsByProject(@PathVariable Long projectId) {
        return bugReportService.getBugReportsByProjectId(projectId);
    }

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
}
