package qarenabe.qarenabe.entity;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BugReport {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    @Lob
    @Column(name = "description", length = 2000000000)
    private String description;

    @Getter
    @Setter
    private String[] reproductionSteps;

    @Getter
    @Setter
    private String[] screenshotUrl;

    @Getter
    @Setter
    private String status;

    @Getter
    @Setter
    private Date reported_at;

    @Getter
    @Setter
    private String device;

    @ManyToOne
    @JoinColumn(name = "bug_type_Id")
    @Getter
    @Setter
    private BugType bugType;

    @ManyToOne
    @JoinColumn(name = "test_project_Id")
    @Getter
    @Setter
    private TestProject testProject;

}
