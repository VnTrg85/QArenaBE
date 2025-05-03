package qarenabe.qarenabe.dto;

import java.util
.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import qarenabe.qarenabe.entity.BugReport;


@AllArgsConstructor
@NoArgsConstructor
public class UserPayoutBugDTO {
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private Date dateCreated;

    @Getter
    @Setter
    private Long bugReport;

}
