package qarenabe.qarenabe.dto;
import lombok.*;
import qarenabe.qarenabe.entity.TestFeature;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestprojectDTO {
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String projectName;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private String outScope;
    @Getter
    @Setter
    private String goal;
    @Getter
    @Setter
    private String additionalRequirement;
    @Getter
    @Setter
    private String link;
    @Getter
    @Setter
    private String[] platform;
    @Getter
    @Setter
    private Date create_At;
    @Getter
    @Setter
    private Date end_At;
    @Getter
    @Setter
    private String status;
    @Getter
    @Setter
    private String[] language;
    @Getter
    @Setter
    private Long userId;
    @Getter
    @Setter
    private List<TestFeatureDTO> testFeatures;
    @Getter
    @Setter
    private List<PayoutBugDTO> payoutBugs;
    @Getter
    @Setter
    private List<BugReportDTO> bugReports;

    public TestprojectDTO(String projectName, String description,String outScope ,String additionalRequirement,String link,String goal, String[] platform,Date create_at, Date end_at, String status, String[] language, Long userId) {
        this.projectName = projectName;
        this.description = description;
        this.outScope  = outScope;
        this.additionalRequirement = additionalRequirement;
        this.link = link;
        this.goal = goal;
        this.platform = platform;
        this.create_At = create_at;
        this.end_At = end_at;
        this.status = status;
        this.language = language;
        this.userId = userId;
    }
    public TestprojectDTO(Long id,String projectName, String description, String goal, String[] platform,Date create_at, Date end_at, String status, String[] language, List<TestFeatureDTO> testFeatures, List<PayoutBugDTO> payoutBugs) {
        this.id = id;
        this.projectName = projectName;
        this.description = description;
        this.goal = goal;
        this.platform = platform;
        this.create_At = create_at;
        this.end_At = end_at;
        this.status = status;
        this.language = language;
        this.testFeatures = testFeatures;
        this.payoutBugs = payoutBugs;
    }
}
