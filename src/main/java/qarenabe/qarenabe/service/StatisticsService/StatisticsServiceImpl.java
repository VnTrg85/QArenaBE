package qarenabe.qarenabe.service.StatisticsService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qarenabe.qarenabe.dto.*;
import qarenabe.qarenabe.entity.TestProject;
import qarenabe.qarenabe.entity.User;
import qarenabe.qarenabe.repository.TestProjectRepository;
import qarenabe.qarenabe.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class StatisticsServiceImpl implements StatisticsService {
    UserRepository userRepository;
    TestProjectRepository projectRepository;

    @Override
    public UserMonthlyStatsResponseDTO getUserRegistrationStatsByMonth(int year, int month) {
        List<User> usersList = userRepository.findByYearAndMonth(year, month);
        List<TesterResponseDTO> testers = new ArrayList<>();
        List<CustomerResponseDTO> customers = new ArrayList<>();
        int testerCount = 0;
        int customerCount = 0;

        for (User user : usersList) {
            String roleName = user.getUserRole().getName();
            if (roleName.equalsIgnoreCase("tester")) {
                testers.add(new TesterResponseDTO(user.getId(), user.getName()));
                testerCount++;
            } else if (roleName.equalsIgnoreCase("customer")) {
                customers.add(new CustomerResponseDTO(user.getId(), user.getName()));
                customerCount++;
            }
        }

        UserRegistrationStatsDTO registrationStats = new UserRegistrationStatsDTO(testers, customers);
        return new UserMonthlyStatsResponseDTO(registrationStats, testerCount, customerCount);
    }

    @Override
    public ProjectMonthlyStatsResponseDTO getProjectStatsByMonth(int year, int month) {
        List<TestProject> testProjectList = projectRepository.findByYearAndMonth(year, month);
        List<ProjectStatsDTO> projectStatsDTOList = new ArrayList<>();
        int testerCount = 0;
        for (TestProject testProject : testProjectList) {
            projectStatsDTOList.add(new ProjectStatsDTO(testProject.getId(),testProject.getProjectName(),testProject.getStatus()));
            testerCount++;
        }
        return new ProjectMonthlyStatsResponseDTO(projectStatsDTOList,testerCount);
    }
}
