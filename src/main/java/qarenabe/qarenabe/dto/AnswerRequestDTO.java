package qarenabe.qarenabe.dto;

import lombok.Data;

@Data
public class AnswerRequestDTO {
    private String content;     // Nội dung đáp án
    private Boolean isCorrect;
}
