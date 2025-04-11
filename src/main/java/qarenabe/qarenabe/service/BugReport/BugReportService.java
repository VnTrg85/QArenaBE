package qarenabe.qarenabe.service.BugReport;

import java.util.List;

import qarenabe.qarenabe.dto.BugReportDTO;

public interface BugReportService {
        public List<BugReportDTO> getListBugByProject(Long projectId);
}
