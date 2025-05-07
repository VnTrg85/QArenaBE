package qarenabe.qarenabe.service.Transaction;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import qarenabe.qarenabe.dto.TransactionResponseDTO;
import qarenabe.qarenabe.entity.BugReport;
import qarenabe.qarenabe.entity.PayoutBug;
import qarenabe.qarenabe.entity.TestProject;
import qarenabe.qarenabe.repository.BugReportRepository;
import qarenabe.qarenabe.repository.PayoutBugRepository;
import qarenabe.qarenabe.repository.TestProjectRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {
    TestProjectRepository projectRepository;
    BugReportRepository bugReportRepository;
    PayoutBugRepository findAllByTestProjectId;

    @Override
    public List<TransactionResponseDTO> geAllTransactionResponseDtoList() {
        List<TestProject> projectList = projectRepository.findByStatus("COMPLETED");
        
        LocalDate today = LocalDate.now();
        for (TestProject project : projectList) {
            Long testPRId=project.getId();

            List<BugReport> bugReportList =bugReportRepository.findByTestProjectId(testPRId);
            List<Long> bugTypeIdList = bugReportList.stream().map(
                    item-> item.getBugType().getId()
            ).toList();

            List<PayoutBug> payoutBugs=findAllByTestProjectId.findAllByTestProjectId(testPRId);
            double amountTotal = payoutBugs.stream()

                    .filter(payout -> bugTypeIdList.contains(payout.getBugType().getId()))
                    .mapToDouble(PayoutBug::getAmount)
                    .sum();

            TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();
            transactionResponseDTO.setAmount(amountTotal);
            transactionResponseDTO.setTestProjectId(testPRId);
            transactionResponseDTO.setTestProjectName(project.getProjectName());
            transactionResponseDTO.setPayment_date(today);
        }
        return List.of();
    }
}
