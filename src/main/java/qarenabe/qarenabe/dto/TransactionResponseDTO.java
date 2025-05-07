package qarenabe.qarenabe.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE )
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDTO {
    Long testProjectId;
    Double amount;
    LocalDate payment_date;
    String status;
    String userName;
    String testProjectName;
}
