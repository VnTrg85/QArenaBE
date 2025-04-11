package qarenabe.qarenabe.service.LessonService;

import qarenabe.qarenabe.dto.LessonRequestDTO;
import qarenabe.qarenabe.dto.LessonResponseDTO;

import java.util.List;

public interface LessonService {
    List<LessonResponseDTO> getAllLesson();
    LessonResponseDTO getLessonById(Long id);
    LessonResponseDTO addLesson(LessonRequestDTO lessonRequestDTO);
    String deleteLessonByIds(List<Long> ids);
    LessonResponseDTO updateLesson(Long lessonId,LessonRequestDTO lessonRequestDTO);
    Object completeLessonAndUnlockNext(Long userId, Long lessonId);

}
