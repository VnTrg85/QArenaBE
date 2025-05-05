package qarenabe.qarenabe.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE )
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationStatsDTO {
    List<TesterResponseDTO> testerResponseDTOList;
    List<CustomerResponseDTO> customerResponseDTOList;
}
