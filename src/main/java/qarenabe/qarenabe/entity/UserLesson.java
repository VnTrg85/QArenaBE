package qarenabe.qarenabe.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_lessons")
public class UserLesson extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_course_id", nullable = false)
      UserCourse userCourse;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
      Lesson lesson;

    @Column(name = "is_completed", columnDefinition = "BOOLEAN DEFAULT FALSE")
      Boolean isCompleted = false;

    @Column(name = "completed_at")
      LocalDateTime completedAt;
}
