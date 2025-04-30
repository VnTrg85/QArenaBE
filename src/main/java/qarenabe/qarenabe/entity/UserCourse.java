package qarenabe.qarenabe.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_courses")
public class UserCourse extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "is_completed", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isCompleted = false;


}
