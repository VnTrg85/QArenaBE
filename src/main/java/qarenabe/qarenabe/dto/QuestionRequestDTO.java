package qarenabe.qarenabe.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionRequestDTO {
    private Long courseId;              // ID của khóa học chứa câu hỏi
    private String content;             // Nội dung câu hỏi
    private List<AnswerRequestDTO> answers;
}
