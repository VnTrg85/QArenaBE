package qarenabe.qarenabe.service.Lesson;

import qarenabe.qarenabe.dto.LessonRequestDTO;
import qarenabe.qarenabe.dto.LessonResponseDTO;

import java.util.List;

public interface LessonService {
    List<LessonResponseDTO> getAllLesson();
    String addLesson(LessonRequestDTO lessonRequestDTO);
    String deleteLessonByIds(List<Long> ids);
    String updateLesson(Long lessonId,LessonRequestDTO lessonRequestDTO);
}
