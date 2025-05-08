package qarenabe.qarenabe.service.Transaction;

import com.example.demo.enums.TypeNotification;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qarenabe.qarenabe.dto.NotificationDTO;
import qarenabe.qarenabe.dto.TransactionRequestDTO;
import qarenabe.qarenabe.dto.TransactionResponseDTO;
import qarenabe.qarenabe.dto.UserDTO;
import qarenabe.qarenabe.entity.*;
import qarenabe.qarenabe.enums.SuccessCodeEnum;
import qarenabe.qarenabe.repository.*;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Transactional
@Service
public class TransactionServiceImpl implements TransactionService {
    TestProjectRepository projectRepository;
    BugReportRepository bugReportRepository;
    PayoutBugRepository findAllByTestProjectId;
    TransactionRepository transactionRepository;
    UserRepository userRepository;
    @Override
    public List<TransactionResponseDTO> geAllTransactionResponseDtoList() {
        List<Transaction> transactionList=transactionRepository.findAll();
        return transactionList.stream().map(
                item->{
                    TransactionResponseDTO transactionResponseDTO=new TransactionResponseDTO();
                    transactionResponseDTO.setTestProjectId(item.getId());
                    transactionResponseDTO.setTestProjectName(item.getTestProject().getProjectName());
                    transactionResponseDTO.setAmount(item.getAmount());
                    transactionResponseDTO.setStatus(item.getStatus());
                    transactionResponseDTO.setPayment_date(item.getPayment_date().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate());
                    transactionResponseDTO.setUserName(item.getUser().getName());
                    return transactionResponseDTO;
                }
        ).toList();
    }

    @Override
    public NotificationDTO createTransactionResponseDto(Long tesProjectID,Long UserId) {
        List<BugReport> bugReportList =bugReportRepository.findByTestProjectIdAndStatus(tesProjectID,"accepted");
        List<Long> bugTypeIdList = bugReportList.stream().map(
                item-> item.getBugType().getId()
        ).toList();
        List<PayoutBug> payoutBugs=findAllByTestProjectId.findAllByTestProjectId(tesProjectID);
        double amountTotal = payoutBugs.stream()
                .filter(payout -> bugTypeIdList.contains(payout.getBugType().getId()))
                .mapToDouble(PayoutBug::getAmount)
                .sum();
        double profit = amountTotal * 1.3;
        TestProject testProjectr=projectRepository.findById(tesProjectID).get();
        Transaction transaction=new Transaction();
        transaction.setAmount(profit);
        transaction.setPayment_date(new Date());
        transaction.setStatus("Pending");
        transaction.setUser(testProjectr.getUser());
        transaction.setTestProject(testProjectr);
        transactionRepository.save(transaction);
        NotificationDTO response=new NotificationDTO();
        response.setType(TypeNotification.TEST_PROJECT);
        response.setContent("You have one invoice pending payment. Please review and complete it at your earliest convenience.");
        User sender=userRepository.findById(UserId).get();
        UserDTO senderDTO=new UserDTO(sender.getId(),sender.getName(),sender.getAvatar());
        response.setSender(senderDTO);
        UserDTO receiveDTO=new UserDTO(testProjectr.getUser().getId(),testProjectr.getUser().getName(),testProjectr.getUser().getAvatar());
        response.setReceiver(receiveDTO);
        return response;
    }

    @Override
    public String updateStatusTransaction(TransactionRequestDTO transactionResponseDTO) {
        Long tesProjectID = transactionResponseDTO.getTestProjectId();
        Long userID = transactionResponseDTO.getUserId();
        Transaction transaction=transactionRepository.findByTestProject_IdAndUser_Id(tesProjectID,userID);
        transaction.setStatus(transactionResponseDTO.getStatus());
        transactionRepository.save(transaction);
        return SuccessCodeEnum.UPDATE_SUCCESS.getMsg();
    }
}
