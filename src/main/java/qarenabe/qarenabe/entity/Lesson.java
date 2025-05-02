package qarenabe.qarenabe.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    String LessonLink;

    String description;

    String linkImg;

}