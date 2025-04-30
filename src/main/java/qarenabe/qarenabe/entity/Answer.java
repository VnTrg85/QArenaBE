package qarenabe.qarenabe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "answers")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Answer extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
      Question question;

    @Column(columnDefinition = "TEXT", nullable = false,length = 1000)
      String content;

    @Column(name = "is_correct", columnDefinition = "BOOLEAN DEFAULT FALSE")
      Boolean isCorrect = false;
}
