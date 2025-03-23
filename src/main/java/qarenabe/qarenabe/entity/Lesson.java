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
    private Course course;

    @Column(nullable = false)
    private String link;
}
