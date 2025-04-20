package qarenabe.qarenabe.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_courses")
public class UserCourse extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
      User user;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
      Course course;

    @OneToMany(mappedBy = "userCourse", cascade = CascadeType.ALL)
      List<UserLesson> userLessons;

    @Column(name = "is_completed", columnDefinition = "BOOLEAN DEFAULT FALSE")
      Boolean isCompleted = false;
}
