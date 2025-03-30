package qarenabe.qarenabe.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "courses")
public class Course extends BaseEntity {

    @Column(nullable = false ,unique = true)
    private String title;

    @Column(columnDefinition = "TEXT",length = 2000000000 )
    private String description;

    @Column(name = "is_required", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean  isRequired = false;

}
