package qarenabe.qarenabe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class PayoutBugDTO {
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private Long amount;
}
