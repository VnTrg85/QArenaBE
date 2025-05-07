package qarenabe.qarenabe.service.BugReport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

import javax.management.RuntimeErrorException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.enums.TypeNotification;

import jakarta.persistence.EntityNotFoundException;
import qarenabe.qarenabe.dto.BugReportDTO;
import qarenabe.qarenabe.dto.BugReportDTOSecond;
import qarenabe.qarenabe.dto.BugReportSumary;
import qarenabe.qarenabe.dto.NotificationDTO;
import qarenabe.qarenabe.entity.Browser;
import qarenabe.qarenabe.entity.BugReport;
import qarenabe.qarenabe.entity.BugType;
import qarenabe.qarenabe.entity.Device;
import qarenabe.qarenabe.entity.Notification;
import qarenabe.qarenabe.entity.Session;
import qarenabe.qarenabe.entity.TestFeature;
import qarenabe.qarenabe.entity.TestProject;
import qarenabe.qarenabe.entity.User;
import qarenabe.qarenabe.entity.UserPayoutBug;
import qarenabe.qarenabe.repository.BrowserRepository;
import qarenabe.qarenabe.repository.BugReportRepository;
import qarenabe.qarenabe.repository.BugTypeRepository;
import qarenabe.qarenabe.repository.DeviceRepository;
import qarenabe.qarenabe.repository.NotificationRepository;
import qarenabe.qarenabe.repository.SessionRepository;
import qarenabe.qarenabe.repository.TestFeatureRepository;
import qarenabe.qarenabe.repository.TestProjectRepository;
import qarenabe.qarenabe.repository.UserRepository;
import qarenabe.qarenabe.service.UserPayoutBug.UserPayoutBugService;
import qarenabe.qarenabe.dto.UserDTO;
@Service
@RequiredArgsConstructor
public class BugReportServiceImpl implements BugReportService {
    private final SimpMessagingTemplate messagingTemplate;
    @Autowired
    private BugReportRepository bugReportRepository;

    @Autowired
    private TestProjectRepository testProjectRepository;
    @Autowired
    private BugTypeRepository bugTypeRepository;
    @Autowired
    private TestFeatureRepository testFeatureRepository;
    @Autowired 
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private BrowserRepository browserRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired 
    private UserPayoutBugService userPayoutBugService;
    

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
                bugReport.getBrowser().getName(),
                bugReport.getReasonReject(),
                bugType != null ? bugType.getId() : null,
                bugReport.getTestProject().getId()
        );
    }

    @Override
    public BugReport createBugReport(BugReport bugReport) {
        try {
            BugType bugType = bugTypeRepository.findById(bugReport.getBugType().getId()).orElseThrow(() -> new EntityNotFoundException("Bug type not found with ID"));
            TestProject testproject = testProjectRepository.findById(bugReport.getTestProject().getId()).orElseThrow(() -> new EntityNotFoundException("Test project not found with ID"));
            TestFeature testFeature = testFeatureRepository.findById(bugReport.getTestFeature().getId()).orElseThrow(() -> new EntityNotFoundException("Test feature not found with ID"));
            User user = userRepository.findById(bugReport.getUser().getId()).orElseThrow(() -> new EntityNotFoundException("User not found with ID"));
            Session session = sessionRepository.findById(bugReport.getSession().getId()).orElseThrow(() -> new EntityNotFoundException("Session not found with ID"));
            Device device = deviceRepository.findById(bugReport.getDevice().getId()).orElseThrow(() -> new EntityNotFoundException("Device not found with ID"));
            Browser bro =browserRepository.findById(bugReport.getBrowser().getId()).orElseThrow(() -> new EntityNotFoundException("Browser not found with ID"));

            bugReport.setBugType(bugType);
            bugReport.setTestFeature(testFeature);
            bugReport.setTestProject(testproject);
            bugReport.setUser(user);
            bugReport.setSession(session);
            bugReport.setDevice(device);
            bugReport.setBrowser(bro);
            BugReport bugReportRes =  bugReportRepository.save(bugReport);
            BugReport resEntity = new BugReport(bugReportRes.getId(),bugReportRes.getTitle(),bugReportRes.getUrl_test(),bugReportRes.getActual_result(),bugReportRes.getExpected_result(),bugReportRes.getReproductionSteps(),bugReportRes.getScreenshotUrl(),bugReportRes.getStatus(),bugReportRes.getReported_at(),bugReportRes.getBrowser(),bugReportRes.getVersionSelected(),null,bugReportRes.getBugType(),new TestProject(bugReportRes.getTestProject().getId()),new TestFeature(bugReportRes.getTestFeature().getId(),bugReportRes.getTestFeature().getName()),new User(bugReportRes.getUser().getId(),bugReportRes.getUser().getName(),bugReportRes.getUser().getAvatar()),null,new Device(bugReportRes.getDevice().getId(),bugReportRes.getDevice().getName(),bugReportRes.getDevice().getIcon_link()));
            return resEntity;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        
    }

    @Override
    public List<BugReportDTOSecond> getListBugReportByProject(Long testProjectId) {
        try {
            List<BugReport> listReport = bugReportRepository.findAllByTestProjectId(testProjectId);
            List<BugReportDTOSecond> listRes = new ArrayList<>();
            for (BugReport bugReport : listReport) {
                UserDTO user = new UserDTO(bugReport.getUser().getId(), bugReport.getUser().getName(), bugReport.getUser().getAvatar());
                BugReportDTOSecond resEntity = new BugReportDTOSecond(bugReport.getId(), bugReport.getTitle(), bugReport.getStatus(), bugReport.getReported_at(), bugReport.getBugType().getIcon_link(), bugReport.getBugType().getName(),user,bugReport.getTestFeature().getName());
                listRes.add(resEntity);
            }
            return listRes;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    

    @Override
    public BugReport getBugReportDetail(Long id) {
        try {
            BugReport bugReportRes = bugReportRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bug report not found with ID"));
            BugReport resEntity = new BugReport(bugReportRes.getId(),bugReportRes.getTitle(),bugReportRes.getUrl_test(),bugReportRes.getActual_result(),bugReportRes.getExpected_result(),bugReportRes.getReproductionSteps(),bugReportRes.getScreenshotUrl(),bugReportRes.getStatus(),bugReportRes.getReported_at(),bugReportRes.getBrowser(),bugReportRes.getVersionSelected(),null,bugReportRes.getBugType(),new TestProject(bugReportRes.getTestProject().getId()),new TestFeature(bugReportRes.getTestFeature().getId(),bugReportRes.getTestFeature().getName()),new User(bugReportRes.getUser().getId(),bugReportRes.getUser().getName(),bugReportRes.getUser().getAvatar()),null,new Device(bugReportRes.getDevice().getId(),bugReportRes.getDevice().getName(),bugReportRes.getDevice().getIcon_link()));
            return resEntity;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public List<BugReportSumary> getBugReportsSumaryByUser(Long userId) {
        List<BugReport> reports = bugReportRepository.findByUserId(userId);

        // Gom nhóm theo ngày
        Map<String, Long> countsByDate = reports.stream()
                .filter(report -> report.getReported_at() != null)
                .collect(Collectors.groupingBy(
                    report -> new SimpleDateFormat("yyyy-MM-dd").format(report.getReported_at()),
                    Collectors.counting()
                ));
        return countsByDate.entrySet().stream()
                .map(e -> new BugReportSumary(e.getKey(), e.getValue().intValue()))
                .collect(Collectors.toList());
    }
    @Override 
    public Boolean updateStatusOfBugReport(BugReportDTOSecond bugReportDTOSecond, Long changedUserId) {
       try {
            BugReport bugReport = bugReportRepository.findById(bugReportDTOSecond.getId()).orElseThrow(() -> new EntityNotFoundException("Bug report not found with ID"));
            bugReport.setStatus(bugReportDTOSecond.getStatus());
            User changedUser = userRepository.findById(changedUserId).orElseThrow(() -> new EntityNotFoundException("User not found with ID"));
            Notification noti = new Notification();
            if(bugReportDTOSecond.getStatus().equals("accepted")) {
                UserPayoutBug userPayoutBug  = new UserPayoutBug();
                userPayoutBug.setDateCreated(new Date());
                userPayoutBug.setBugReport(bugReport);
                userPayoutBugService.createUserPayoutBug(userPayoutBug);
                noti.setContent("Your bug have been accepted");
            }else {
                userPayoutBugService.deleteUserPayoutBugByBugReport(bugReport.getId());
                noti.setContent("Your bug have been rejected");
            }
            noti.setType(TypeNotification.BUG_REPORT);
            noti.setLink_url(bugReport.getTestProject().getId()+ "/" + bugReport.getId());
            noti.setIsRead(false);
            noti.setSender(changedUser);
            noti.setReceiver(bugReport.getUser());
            UserDTO sender = new UserDTO(changedUser.getId(),changedUser.getName(), changedUser.getAvatar());
            UserDTO owner = new UserDTO(bugReport.getUser().getId(),bugReport.getUser().getName(), bugReport.getUser().getAvatar());
            Notification notiSaved =  notificationRepository.save(noti);
            NotificationDTO notiRes = new NotificationDTO(notiSaved.getId(),notiSaved.getType(),notiSaved.getContent(),notiSaved.getLink_url(),sender,owner);
            // Gửi notification cho chủ bug nếu khác người gửi
            messagingTemplate.convertAndSend(
                "/user/" + bugReport.getUser().getId() + "/notify",notiRes
            );
            return true;
       } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
       }
    }

    @Override
    public List<BugReportDTOSecond> getListBugReportsByUser(Long userId) {
        try {
            List<BugReport> listReport = bugReportRepository.findByUserId(userId);
            List<BugReportDTOSecond> listRes = new ArrayList<>();
            for (BugReport bugReport : listReport) {
                UserDTO user = new UserDTO(bugReport.getUser().getId(), bugReport.getUser().getName(), bugReport.getUser().getAvatar());
                BugReportDTOSecond resEntity = new BugReportDTOSecond(bugReport.getId(), bugReport.getTitle(), bugReport.getStatus(), bugReport.getReported_at(), bugReport.getBugType().getIcon_link(), bugReport.getBugType().getName(),user,bugReport.getTestFeature().getName());
                listRes.add(resEntity);
            }
            return listRes;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<BugReportDTOSecond> getListBugReportAcceptByProject(Long testProjectId) {
        try {
            List<BugReport> listReport = bugReportRepository.findAllByTestProjectId(testProjectId);
            List<BugReportDTOSecond> listRes = new ArrayList<>();
            for (BugReport bugReport : listReport) {
                if(bugReport.getStatus().equals("accepted")) {
                    UserDTO user = new UserDTO(bugReport.getUser().getId(), bugReport.getUser().getName(), bugReport.getUser().getAvatar());
                    BugReportDTOSecond resEntity = new BugReportDTOSecond(bugReport.getId(), bugReport.getTitle(), bugReport.getStatus(), bugReport.getReported_at(), bugReport.getBugType().getIcon_link(), bugReport.getBugType().getName(),user,bugReport.getTestFeature().getName());
                    listRes.add(resEntity);
                }
            }
            return listRes;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
