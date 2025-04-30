package qarenabe.qarenabe.entity;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String content;

    @Getter
    @Setter
    private Date time_created;

    
    @ManyToOne
    @JoinColumn(name = "bug_report_Id", nullable = true)
    @Getter
    @Setter
    private BugReport bugReport;

    @ManyToOne
    @JoinColumn(name = "test_project_Id", nullable = true)
    @Getter
    @Setter
    private TestProject testProject;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    @Getter
    @Setter
    private User user;
}
