package qarenabe.qarenabe.service.UserLesson;

import qarenabe.qarenabe.dto.LessonResponseUserDTO;

import java.util.List;

public interface UserLessonService {
    Object completeLessonAndUnlockNext(Long userId, Long lessonId);
    List<LessonResponseUserDTO> getAllLessonsByUserAndCourse(Long userId, Long courseId);
}
