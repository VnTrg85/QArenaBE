package qarenabe.qarenabe.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "answers")
public class Answer extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "is_correct", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isCorrect = false;


}
