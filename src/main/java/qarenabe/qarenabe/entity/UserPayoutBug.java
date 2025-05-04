package qarenabe.qarenabe.entity;



import java.util.Date;

import io.micrometer.common.lang.Nullable;
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
    @Nullable
    @Getter
    @Setter
    private BugReport bugReport;

    @ManyToOne
    @JoinColumn(name = "reproduction_id")
    @Nullable
    @Getter
    @Setter
    private Reproduction reproduction;

}
