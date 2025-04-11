package qarenabe.qarenabe.service.BugReport;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import qarenabe.qarenabe.dto.BugReportDTO;
import qarenabe.qarenabe.dto.UserDTO;
import qarenabe.qarenabe.entity.BugReport;
import qarenabe.qarenabe.repository.BugReportRepository;
import qarenabe.qarenabe.service.LessonService.LessonServiceImpl;

@Service
public class BugReportServiceImpl implements BugReportService {

    @Autowired
    private BugReportRepository bugReportRepository;

    @Override
    public List<BugReportDTO> getListBugByProject(Long projectId) {
       try {
            List<BugReport>  bugReports = bugReportRepository.findAll();
            List<BugReportDTO> resBugReports = new ArrayList<>();

            for (BugReport bugReport : bugReports) {
                if(bugReport.getTestProject().getId() == projectId) {
                    UserDTO user = new UserDTO(bugReport.getUser().getId(), bugReport.getUser().getName(), bugReport.getUser().getAvatar());
                    BugReportDTO bugReportDTO = new BugReportDTO(bugReport.getId(), bugReport.getTitle(), bugReport.getStatus(), bugReport.getReported_at(), bugReport.getBugType().getIcon_link(), bugReport.getBugType().getName(), user);
                    resBugReports.add(bugReportDTO);
                }
            }
            return resBugReports;
       } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
       }
    }
    
}
