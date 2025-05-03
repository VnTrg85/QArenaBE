package qarenabe.qarenabe.entity;



import java.util.Date;

import jakarta.persistence.*;
import lombok.*;



@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserPayoutBug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private Date dateCreated;


    @ManyToOne
    @JoinColumn(name = "bug_id")
    @Getter
    @Setter
    private BugReport bugReport;

}
