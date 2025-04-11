package qarenabe.qarenabe.dto;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BugReportDTO {
    private Long id;
    private String title;
    private String url_test;
    private String actual_result;
    private String expected_result;
    private String[] reproductionSteps;
    private String[] screenshotUrl;
    private String status;
    private Date reported_at;
    private String device;
    private String browswer;
    private String reasonReject;
    private Long bugTypeId;
    private Long testProjectId;
}
