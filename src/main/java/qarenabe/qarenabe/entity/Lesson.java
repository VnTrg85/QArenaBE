package qarenabe.qarenabe.entity;

import jakarta.persistence.*;
import lombok.*;
import qarenabe.qarenabe.enums.LessonEnum;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lessons")
public class Lesson extends BaseEntity {


    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private String link;

    @Enumerated(EnumType.STRING)
    private LessonEnum status = LessonEnum.LOCKED;

    @ManyToOne
    @JoinColumn(name = "previous_lesson_id")
    private Lesson previousLesson;
}