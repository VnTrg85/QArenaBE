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
      Long id;

    @Getter
    @Setter
      String proofLink;

    @Getter
    @Setter
      String device;

    @Getter
    @Setter
      String browswer;
    
    @Getter
    @Setter
      Date time_created;

    @ManyToOne
    @JoinColumn(name = "bug_report_Id")
    @Getter
    @Setter
      BugReport bugReport;

    @ManyToOne
    @JoinColumn(name = "userId")
    @Getter
    @Setter
      User user;
}
