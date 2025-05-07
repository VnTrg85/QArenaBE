package qarenabe.qarenabe.service.AnswerService;

import qarenabe.qarenabe.dto.AnswerRequestDTO;
import qarenabe.qarenabe.dto.AnswerResponseDTO;

import java.util.List;

public interface AnswerService {
    List<AnswerResponseDTO>getAllByQuestionId(Long questionId);
    AnswerResponseDTO createAnswer(AnswerRequestDTO requestDTO);
    AnswerResponseDTO updateAnswer(Long id, AnswerRequestDTO requestDTO);
    String deleteAnswer(Long id);
}
