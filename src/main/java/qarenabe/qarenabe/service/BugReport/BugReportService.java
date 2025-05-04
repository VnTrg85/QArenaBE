package qarenabe.qarenabe.service.BugReport;

import java.util.List;

import qarenabe.qarenabe.dto.BugReportDTO;
import qarenabe.qarenabe.dto.BugReportDTOSecond;
import qarenabe.qarenabe.dto.BugReportSumary;
import qarenabe.qarenabe.entity.BugReport;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import qarenabe.qarenabe.dto.BugReportDTO;

public interface BugReportService {
    public List<BugReportDTO> getBugReportsByProjectId(Long projectId);
    public BugReportDTO getBugReportById(Long id);
    public void updateBugStatus(Long id, BugReportDTO dto);
    public ByteArrayInputStream exportBugsToExcel(Long projectId) throws IOException;
    public BugReport createBugReport(BugReport bugReport);
    public List<BugReportDTOSecond> getListBugReportByProject(Long testProjectId);
    public BugReport getBugReportDetail(Long id);
    public List<BugReportSumary> getBugReportsSumaryByUser(Long userId);
}
