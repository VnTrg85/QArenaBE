package qarenabe.qarenabe.service.Transaction;

import org.hibernate.sql.Update;
import qarenabe.qarenabe.dto.NotificationDTO;
import qarenabe.qarenabe.dto.TransactionRequestDTO;
import qarenabe.qarenabe.dto.TransactionResponseDTO;

import java.util.List;

public interface TransactionService {
    List<TransactionResponseDTO> geAllTransactionResponseDtoList();
    NotificationDTO createTransactionResponseDto(Long tesProjectID,Long UserId);
    String updateStatusTransaction(TransactionRequestDTO transactionResponseDTO);
}
