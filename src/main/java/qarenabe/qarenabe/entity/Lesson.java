package qarenabe.qarenabe.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lessons")
public class Lesson extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
      Course course;

    @Column(nullable = false)
      String title;

    @Column(nullable = false)
      String link;

    @ManyToOne
    @JoinColumn(name = "lesson_required_id")
    Lesson lessonRequired;

    @Column(name = "is_completed", columnDefinition = "BOOLEAN DEFAULT FALSE")
      Boolean isCompleted = false;

    @Column(name = "is_blocked", columnDefinition = "BOOLEAN DEFAULT TRUE")
      Boolean isBlocked = true;
}