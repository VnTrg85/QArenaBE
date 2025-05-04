package qarenabe.qarenabe.service.UserPayoutBug;

import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import qarenabe.qarenabe.dto.MonthlyPayoutDTO;
import qarenabe.qarenabe.dto.TestprojectDTO;
import qarenabe.qarenabe.dto.UserPayoutBugDTO;
import qarenabe.qarenabe.entity.BugReport;
import qarenabe.qarenabe.entity.TestProject;
import qarenabe.qarenabe.entity.UserPayoutBug;
import qarenabe.qarenabe.repository.BugReportRepository;
import qarenabe.qarenabe.repository.TestProjectRepository;
import qarenabe.qarenabe.repository.UserPayoutBugRepository;
import qarenabe.qarenabe.repository.UserRepository;
import qarenabe.qarenabe.service.PayoutBug.PayoutBugService;

@Service
public class UserPayoutBugServiceImpl implements UserPayoutBugService {
    @Autowired
    private UserPayoutBugRepository userPayoutBugRepository;
    @Autowired 
    private TestProjectRepository testProjectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BugReportRepository bugReportRepository;
    @Autowired
    private PayoutBugService payoutBugService;
    @Override
    public UserPayoutBugDTO createUserPayoutBug(UserPayoutBug userPayoutBugDTO) {
        try {
            BugReport bugReport = bugReportRepository.findById(userPayoutBugDTO.getBugReport().getId()).orElseThrow(() -> new EntityNotFoundException("Bug report not found with ID"));
            userPayoutBugDTO.setBugReport(bugReport);
            UserPayoutBug saved =  userPayoutBugRepository.save(userPayoutBugDTO);
            UserPayoutBugDTO res = new UserPayoutBugDTO(saved.getId(), saved.getDateCreated(),saved.getBugReport().getId(),saved.getReproduction().getId());
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public UserPayoutBugDTO findByBugReport(Long id) {
        try {
            UserPayoutBug userPayoutBug = userPayoutBugRepository.findByBugReportId(id);
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Boolean deleteUserPayoutBugByBugReport(Long id) {
        try {
            int res = userPayoutBugRepository.deleteByBugReportId(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Boolean deleteUserPayoutBugByReproduction(Long id) {
        try {
            int res = userPayoutBugRepository.deleteByReproductionId(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<MonthlyPayoutDTO> getAllPayoutsByUser(Long userId) {
        try {
            List<UserPayoutBug> listUserPayoutBugs = userPayoutBugRepository.findAll();
            Map<YearMonth, Float> payoutByMonth = new HashMap<>();

            for (UserPayoutBug userPayoutBug : listUserPayoutBugs) {
                BugReport bugReport = userPayoutBug.getBugReport();

                Date dateCreated = userPayoutBug.getDateCreated();
                YearMonth yearMonth = YearMonth.from(dateCreated.toInstant().atZone(ZoneId.systemDefault()));
                if (bugReport.getUser().getId().equals(userId)) {
                    Long amount = payoutBugService.getAmountForProjectAndBugType(
                        bugReport.getTestProject().getId(),
                        bugReport.getBugType().getId()
                    );
                    payoutByMonth.put(yearMonth, payoutByMonth.getOrDefault(yearMonth, 0F) + amount.floatValue());
                }
                if (userPayoutBug.getReproduction() != null && userPayoutBug.getReproduction().getUser().getId().equals(userId)) {
                    Long amount = payoutBugService.getAmountForProjectAndBugType(
                        bugReport.getTestProject().getId(),
                        bugReport.getBugType().getId()
                    );
                    payoutByMonth.put(yearMonth, payoutByMonth.getOrDefault(yearMonth, 0F) +  amount.floatValue() * 0.10F);
                }

            }

            // Chuyển YearMonth => String để JSON dễ đọc
            return payoutByMonth.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .map(entry -> new MonthlyPayoutDTO(entry.getKey().toString(), entry.getValue()))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Long getAllPayoutByProject(Long projectId) { 
        try {
            List<UserPayoutBug> listUserPayoutBugs = userPayoutBugRepository.findAll();
            Long totalNumber = (long) 0;
            for (UserPayoutBug userPayoutBug : listUserPayoutBugs) {
                BugReport bugReport = userPayoutBug.getBugReport();

                if (bugReport.getId().equals(projectId)) {
                    Long amount = payoutBugService.getAmountForProjectAndBugType(
                        bugReport.getTestProject().getId(),
                        bugReport.getBugType().getId()
                    );
                    totalNumber += amount;
                }
            }
            return totalNumber;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    
}
