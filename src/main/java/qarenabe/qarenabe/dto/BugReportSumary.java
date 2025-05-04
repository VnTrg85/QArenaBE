package qarenabe.qarenabe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class BugReportSumary {
    @Getter
    @Setter
    private String date;
    @Getter
    @Setter
    private int count;
}
