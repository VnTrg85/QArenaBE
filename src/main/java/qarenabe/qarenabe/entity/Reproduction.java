package qarenabe.qarenabe.entity;
import java.util.Date;

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
public class Reproduction {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String proofLink;

    @Getter
    @Setter
    private String status;
    
    @Getter
    @Setter
    private Date time_created;

    @ManyToOne
    @JoinColumn(name = "bug_report_Id")
    @Getter
    @Setter
    private BugReport bugReport;

    @ManyToOne
    @JoinColumn(name = "userId")
    @Getter
    @Setter
    private User user;

    @ManyToOne
    @JoinColumn(name = "browserId")
    @Getter
    @Setter
    private Browser browser;

    @ManyToOne
    @JoinColumn(name = "device_Id")
    @Getter
    @Setter
    private Device device;
}
