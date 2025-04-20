package qarenabe.qarenabe.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "questions")
public class Question extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
      Course course;

    @Column(columnDefinition = "TEXT", nullable = false)
      String content;

    @Column(name = "is_true", columnDefinition = "BOOLEAN DEFAULT FALSE")
      Boolean isTrue = false;
}