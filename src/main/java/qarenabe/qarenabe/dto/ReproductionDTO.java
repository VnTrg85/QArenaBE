package qarenabe.qarenabe.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReproductionDTO {
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String proofLink;
    @Getter
    @Setter
    private String device;
    @Getter
    @Setter
    private String browswer;
    @Getter
    @Setter
    private Date time_created;
    @Getter
    @Setter
    private Long bugReportId;
    @Getter
    @Setter
    private Long userId;
}
