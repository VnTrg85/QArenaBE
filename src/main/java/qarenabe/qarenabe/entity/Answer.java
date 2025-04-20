package qarenabe.qarenabe.entity;

import jakarta.persistence.*;
import lombok.*;



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

    @Column(columnDefinition = "TEXT", nullable = false,length = 1000)
    private String content;

    @Column(name = "is_correct", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isCorrect = false;


}
