package qarenabe.qarenabe.service.TestProject;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.enums.TypeNotification;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import qarenabe.qarenabe.dto.NotificationDTO;
import qarenabe.qarenabe.dto.TestprojectDTO;
import qarenabe.qarenabe.dto.UserDTO;
import qarenabe.qarenabe.entity.TestProject;
import qarenabe.qarenabe.entity.TestProject_User;
import qarenabe.qarenabe.entity.User;
import qarenabe.qarenabe.repository.TestProjectRepository;
import qarenabe.qarenabe.repository.TestProject_UserRepository;
import qarenabe.qarenabe.repository.UserRepository;
import qarenabe.qarenabe.service.Notification.NotificationService;
import qarenabe.qarenabe.service.PayoutBug.PayoutBugService;
import qarenabe.qarenabe.service.TestFeature.TestFeatureService;

@Service
@RequiredArgsConstructor
public class TestProjectServiceImpl implements TestProjectService {
    private final SimpMessagingTemplate messagingTemplate;
    @Autowired
    private TestProjectRepository testProjectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestProject_UserRepository testProject_UserRepository;
    @Autowired
    private TestFeatureService testFeatureService;
    @Autowired 
    private PayoutBugService payoutBugService;
    @Autowired
    private NotificationService notificationService;
    
    public List<TestprojectDTO> getAllProjects() {
        return testProjectRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<TestprojectDTO> getProjectById(Long id) {
        return testProjectRepository.findById(id).map(this::convertToDTO);
    }

    public List<TestprojectDTO> getAllProjectsByUserId(Long userId) {
        List<TestProject> projects = testProjectRepository.findByUser_Id(userId);
        return projects.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public TestprojectDTO createProject(TestprojectDTO dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        TestProject project = convertToEntity(dto, user);
        return convertToDTO(testProjectRepository.save(project));
    }

    @Transactional
    public Optional<TestprojectDTO> updateProject(Long id, TestprojectDTO dto) {
        return testProjectRepository.findById(id).map(project -> {
            project.setProjectName(dto.getProjectName());
            project.setDescription(dto.getDescription());
            project.setOutScope(dto.getOutScope());
            project.setGoal(dto.getGoal());
            project.setAdditionalRequirement(dto.getAdditionalRequirement());
            project.setLink(dto.getLink());
            project.setPlatform(dto.getPlatform());
            project.setCreate_at(dto.getCreate_At());
            project.setEndAt(dto.getEnd_At());
            project.setStatus(dto.getStatus());
            project.setLanguage(dto.getLanguage());
            project.setDevices(dto.getDevices());
            return convertToDTO(testProjectRepository.save(project));
        });
    }

    @Transactional
    public void deleteProject(Long id) {
        testProjectRepository.deleteById(id);
    }

    private TestprojectDTO convertToDTO(TestProject project) {
        return new TestprojectDTO(project.getId(),
            project.getProjectName(), project.getDescription(), project.getOutScope(),
            project.getAdditionalRequirement(), project.getLink(), project.getGoal(),
            project.getPlatform(), project.getCreate_at(), project.getEndAt(),
            project.getStatus(), project.getLanguage(), project.getUser().getId()
        );
    }

    private TestProject convertToEntity(TestprojectDTO dto, User user) {
        return new TestProject(
            dto.getId(), dto.getProjectName(), dto.getDescription(), dto.getOutScope(),
            dto.getGoal(), dto.getAdditionalRequirement(), dto.getLink(),
            dto.getPlatform(), dto.getCreate_At(), dto.getEnd_At(),
            dto.getStatus(), dto.getLanguage(), dto.getDevices(),user
        );
    }

    @Override
    public TestprojectDTO getProjectDetailById(Long id) {
       try {
        TestProject testproject = testProjectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Project not found with ID"));
        TestprojectDTO secondDTO = new TestprojectDTO(testproject.getId(),testproject.getProjectName(),testproject.getDescription(),testproject.getAdditionalRequirement(),testproject.getGoal(),testproject.getPlatform(),testproject.getCreate_at(),testproject.getEndAt(),testproject.getLink(),testproject.getOutScope(),testproject.getStatus(),testproject.getLanguage(),testproject.getDevices(),testFeatureService.getDetailFeaturesByTestProject(testproject.getId()),payoutBugService.getPayoutBugByProject(testproject.getId()));
        return secondDTO;
       } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
       }
    }

    @Scheduled(fixedRate = 600000) // chạy mỗi 60 giây
    public void updateTestProjectUsersStatus() {
        Date now = new Date();
        List<TestProject> endedProjects = testProjectRepository.findByEndAtBefore(now);
        for (TestProject project : endedProjects) {
            List<TestProject_User> tpUsers = testProject_UserRepository.findAllByTestProjectId(project.getId());
            for (TestProject_User tpUser : tpUsers) {
                if (!"Done".equals(tpUser.getStatus())) {
                    tpUser.setStatus("Done");
                    testProject_UserRepository.save(tpUser);
                }
            }
        }
    }

    @Override
    public Boolean requestAcceptTestProject(Long projectId) {
        try {
            TestProject testProject = testProjectRepository.findById(projectId).orElseThrow(() -> new EntityNotFoundException("Project not found with ID"));
            testProject.setStatus("awaiting");
            testProjectRepository.save(testProject);
            UserDTO sender = new UserDTO(testProject.getUser().getId(),null,null);
            UserDTO receiver = new UserDTO(Long.parseLong("5"),null,null);
            NotificationDTO notiDto = new NotificationDTO(null, TypeNotification.TEST_PROJECT, "New project is awating for accept", projectId.toString(), sender, receiver);
            NotificationDTO notiDtoRes = notificationService.createNotification(notiDto);
            messagingTemplate.convertAndSend(
                "/user/" + notiDtoRes.getReceiver().getId() + "/notify",
                notiDtoRes
            );
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }    

    @Override
    public Boolean acceptProject(Long projectId) {
        try {
            TestProject testProject = testProjectRepository.findById(projectId).orElseThrow(() -> new EntityNotFoundException("Project not found with ID"));
            testProject.setStatus("doing");
            testProjectRepository.save(testProject);
            UserDTO sender = new UserDTO(Long.parseLong("5"),null,null);
            UserDTO receiver = new UserDTO(testProject.getUser().getId(),null,null);
            NotificationDTO notiDto = new NotificationDTO(null, TypeNotification.TEST_PROJECT, "Your project is doing", projectId.toString(), sender, receiver);
            NotificationDTO notiDtoRes = notificationService.createNotification(notiDto);
            messagingTemplate.convertAndSend(
                "/user/" + notiDtoRes.getReceiver().getId() + "/notify",
                notiDtoRes
            );
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    } 
    @Override
    public Boolean rejectProject(Long projectId) {
        try {
            TestProject testProject = testProjectRepository.findById(projectId).orElseThrow(() -> new EntityNotFoundException("Project not found with ID"));
            testProject.setStatus("rejected");
            testProjectRepository.save(testProject);
            UserDTO sender = new UserDTO(Long.parseLong("5"),null,null);
            UserDTO receiver = new UserDTO(testProject.getUser().getId(),null,null);
            NotificationDTO notiDto = new NotificationDTO(null, TypeNotification.TEST_PROJECT, "Your project has been rejected", projectId.toString(), sender, receiver);
            NotificationDTO notiDtoRes = notificationService.createNotification(notiDto);
            messagingTemplate.convertAndSend(
                "/user/" + notiDtoRes.getReceiver().getId() + "/notify",
                notiDtoRes
            );
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    } 
}
