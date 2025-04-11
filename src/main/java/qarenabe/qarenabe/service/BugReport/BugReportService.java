package qarenabe.qarenabe.service.BugReport;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import qarenabe.qarenabe.dto.BugReportDTO;

public interface BugReportService {
    public List<BugReportDTO> getBugReportsByProjectId(Long projectId);
    public BugReportDTO getBugReportById(Long id);
    public void updateBugStatus(Long id, BugReportDTO dto);
    public ByteArrayInputStream exportBugsToExcel(Long projectId) throws IOException;
}
