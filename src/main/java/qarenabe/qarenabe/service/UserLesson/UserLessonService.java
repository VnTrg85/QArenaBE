package qarenabe.qarenabe.service.UserLesson;

import qarenabe.qarenabe.dto.UserLessonResponseDTO;

import java.util.List;

public interface UserLessonService {
    List<UserLessonResponseDTO> getAllLessonsByUserAndCourse(Long userId, Long courseId);
}
