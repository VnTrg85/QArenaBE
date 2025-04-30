package qarenabe.qarenabe.service.UserLesson;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qarenabe.qarenabe.dto.UserLessonResponseDTO;
import qarenabe.qarenabe.entity.Certificate;
import qarenabe.qarenabe.entity.Lesson;
import qarenabe.qarenabe.entity.UserCourse;
import qarenabe.qarenabe.entity.UserLesson;
import qarenabe.qarenabe.enums.ErrorCodeEnum;
import qarenabe.qarenabe.enums.SuccessCodeEnum;
import qarenabe.qarenabe.exception.AppException;
import qarenabe.qarenabe.mapper.UserLessonMapper;
import qarenabe.qarenabe.repository.CertificateRepository;
import qarenabe.qarenabe.repository.LessonRepository;
import qarenabe.qarenabe.repository.UserCourseRepository;
import qarenabe.qarenabe.repository.UserLessonRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Slf4j
public class UserLessonServiceImpl implements UserLessonService {
    LessonRepository lessonRepository;
    UserCourseRepository userCourseRepository;
    UserLessonMapper userLessonMapper;


    @Override
    public List<UserLessonResponseDTO> getAllLessonsByUserAndCourse(Long userId, Long courseId) {
        userCourseRepository.findByUserIdAndCourseId(userId, courseId)
                .orElseThrow(() -> new AppException(ErrorCodeEnum.INVALID_COURSE_ID));

        List<Lesson> allLessons = lessonRepository.findByCourseId(courseId);

        return allLessons.stream()
                .map(lesson -> {
                    UserLessonResponseDTO dto = userLessonMapper.toLessonResponseUserDTO(lesson);
                    dto.setCourseId(courseId);
                    dto.setCourseName(lesson.getCourse().getTitle());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}

