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
      Long id;

    @Getter
    @Setter
      String title;

    @Getter
    @Setter
    @Lob
    @Column(name = "url_test", length = 200)
      String url_test;

    @Getter
    @Setter
    @Lob
    @Column(name = "actual_result", length = 2000)
      String actual_result;

    @Getter
    @Setter
    @Lob
    @Column(name = "expected_result", length = 2000)
      String expected_result;

    @Getter
    @Setter
      String[] reproductionSteps;

    @Getter
    @Setter
      String[] screenshotUrl;

    @Getter
    @Setter
      String status;

    @Getter
    @Setter
      Date reported_at;

    @Getter
    @Setter
      String device;

    @Getter
    @Setter
      String browswer;

    @Getter
    @Setter
      String reasonReject;

    @ManyToOne
    @JoinColumn(name = "bug_type_Id")
    @Getter
    @Setter
      BugType bugType;

    @ManyToOne
    @JoinColumn(name = "test_project_Id")
    @Getter
    @Setter
      TestProject testProject;

}
