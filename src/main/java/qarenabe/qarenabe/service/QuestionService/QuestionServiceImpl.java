package qarenabe.qarenabe.service.QuestionService;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qarenabe.qarenabe.dto.QuestionRequestDTO;
import qarenabe.qarenabe.dto.QuestionResponseDTO;
import qarenabe.qarenabe.entity.Answer;
import qarenabe.qarenabe.entity.Course;
import qarenabe.qarenabe.entity.Question;
import qarenabe.qarenabe.enums.ErrorCodeEnum;
import qarenabe.qarenabe.enums.SuccessCodeEnum;
import qarenabe.qarenabe.exception.AppException;
import qarenabe.qarenabe.mapper.QuestionMapper;
import qarenabe.qarenabe.repository.CourseRepository;
import qarenabe.qarenabe.repository.QuestionRepository;

import java.util.List;
import java.util.stream.Collectors;
@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    QuestionRepository questionRepository;
    CourseRepository courseRepository;
    QuestionMapper questionMapper;

    @Override
    public List<QuestionResponseDTO> getAllByCourseId(Long courseId) {
        List<Question> questions = questionRepository.findByCourseId(courseId);
        return questions.stream()
                .map(questionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public QuestionResponseDTO createQuestion(QuestionRequestDTO dto) {
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new AppException(ErrorCodeEnum.COURSE_NOT_EXISTED));

        Question question = questionMapper.toEntity(dto);
        question.setCourse(course);

        // Gán ngược question cho mỗi answer (nếu cần thiết)
        if (question.getAnswers() != null) {
            for (Answer answer : question.getAnswers()) {
                answer.setQuestion(question);
            }
        }

        return questionMapper.toDTO(questionRepository.save(question));
    }

    @Override
    public QuestionResponseDTO updateQuestion(Long id, QuestionRequestDTO dto) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCodeEnum.QUESTION_NOT_EXISTED));
        question.setContent(dto.getContent());
        return questionMapper.toDTO(questionRepository.save(question));
    }

    @Override
    public String deleteQuestions(List<Long> ids) {
        try {
            questionRepository.deleteAllById(ids);
            return SuccessCodeEnum.DELETE_SUCCESS.getMsg();
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(ErrorCodeEnum.QUESTION_NOT_EXISTED);
        }
    }
}
