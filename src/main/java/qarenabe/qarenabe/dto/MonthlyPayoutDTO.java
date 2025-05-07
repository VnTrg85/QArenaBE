package qarenabe.qarenabe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class MonthlyPayoutDTO {
    @Getter
    @Setter
    private String date;
    @Getter
    @Setter
    private Float totalAmount;
}
