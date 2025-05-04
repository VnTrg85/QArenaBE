package qarenabe.qarenabe.service.QuestionService;

import qarenabe.qarenabe.dto.QuestionRequestDTO;
import qarenabe.qarenabe.dto.QuestionResponseDTO;

import java.util.List;

public interface QuestionService {
    QuestionResponseDTO createQuestion(QuestionRequestDTO request);
    List<QuestionResponseDTO> getAllByCourseId(Long courseId);
    QuestionResponseDTO updateQuestion(Long id, QuestionRequestDTO request);
   String deleteQuestions(List<Long> id);
}
