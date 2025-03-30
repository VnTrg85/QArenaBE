package qarenabe.qarenabe.dto;
import lombok.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestprojectDTO {
    private String projectName;
    private String description;
    private String outScope;
    private String goal;
    private String additionalRequirement;
    private String link;
    private String[] platform;
    private Date create_At;
    private Date end_At;
    private String status;
    private String[] language;
    private Long userId;
}
