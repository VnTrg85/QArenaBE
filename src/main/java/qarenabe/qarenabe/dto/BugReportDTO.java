package qarenabe.qarenabe.dto;

import java.util.Date;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import qarenabe.qarenabe.entity.BugType;
import qarenabe.qarenabe.entity.TestFeature;


@AllArgsConstructor
public class BugReportDTO {
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String status;

    @Getter
    @Setter
    private Date reported_at;

    @Getter
    @Setter
    private String bugType;

    @Getter
    @Setter
    private String testFeature;

    @Getter
    @Setter
    private UserDTO user;

}
