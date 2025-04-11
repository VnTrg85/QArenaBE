package qarenabe.qarenabe.service.BugReport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import qarenabe.qarenabe.dto.BugReportDTO;
import qarenabe.qarenabe.entity.BugReport;
import qarenabe.qarenabe.entity.BugType;
import qarenabe.qarenabe.repository.BugReportRepository;
import qarenabe.qarenabe.repository.TestProjectRepository;

import qarenabe.qarenabe.dto.BugReportDTO;
import qarenabe.qarenabe.dto.UserDTO;
import qarenabe.qarenabe.entity.BugReport;
import qarenabe.qarenabe.repository.BugReportRepository;
import qarenabe.qarenabe.service.LessonService.LessonServiceImpl;

@Service
public class BugReportServiceImpl implements BugReportService {

    @Autowired
    private BugReportRepository bugReportRepository;


    @Autowired
    private TestProjectRepository testProjectRepository;

    public List<BugReportDTO> getBugReportsByProjectId(Long projectId) {
        return bugReportRepository.findAllByTestProjectId(projectId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BugReportDTO getBugReportById(Long id) {
        Optional<BugReport> report = bugReportRepository.findById(id);
        return report.map(this::convertToDTO).orElse(null);
    }

    public void updateBugStatus(Long id, BugReportDTO dto) {
        BugReport bug = bugReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BugReport not found"));
    
        if ("REJECTED".equalsIgnoreCase(dto.getStatus()) && (dto.getReasonReject() == null || dto.getReasonReject().isBlank())) {
            throw new IllegalArgumentException("Reason for rejection is required.");
        }
    
        bug.setStatus(dto.getStatus());
        bug.setReasonReject(dto.getReasonReject());
        bugReportRepository.save(bug);
    }

    public ByteArrayInputStream exportBugsToExcel(Long projectId) throws IOException {
        List<BugReport> bugs = bugReportRepository.findAllByTestProjectId(projectId);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Bug Reports");
            Row header = sheet.createRow(0);
            String[] titles = {"ID", "Title", "Status", "Severity", "Reported At"};
            for (int i = 0; i < titles.length; i++) header.createCell(i).setCellValue(titles[i]);

            int rowIdx = 1;
            for (BugReport bug : bugs) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(bug.getId());
                row.createCell(1).setCellValue(bug.getTitle());
                row.createCell(2).setCellValue(bug.getStatus());
                row.createCell(3).setCellValue(bug.getBugType() != null ? bug.getBugType().getName() : "N/A");
                row.createCell(4).setCellValue(bug.getReported_at().toString());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    
    private BugReportDTO convertToDTO(BugReport bugReport) {
        BugType bugType = bugReport.getBugType();
        return new BugReportDTO(
                bugReport.getId(),
                bugReport.getTitle(),
                bugReport.getUrl_test(),
                bugReport.getActual_result(),
                bugReport.getExpected_result(),
                bugReport.getReproductionSteps(),
                bugReport.getScreenshotUrl(),
                bugReport.getStatus(),
                bugReport.getReported_at(),
                bugReport.getDevice(),
                bugReport.getBrowswer(),
                bugReport.getReasonReject(),
                bugType != null ? bugType.getId() : null,
                bugReport.getTestProject().getId()
        );
    }
}
