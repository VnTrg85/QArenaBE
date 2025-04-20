package qarenabe.qarenabe.service.UserLesson;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qarenabe.qarenabe.dto.CertificateResponseDTO;
import qarenabe.qarenabe.dto.LessonResponseUserDTO;
import qarenabe.qarenabe.entity.Certificate;
import qarenabe.qarenabe.entity.Lesson;
import qarenabe.qarenabe.entity.UserCourse;
import qarenabe.qarenabe.entity.UserLesson;
import qarenabe.qarenabe.enums.ErrorCodeEnum;
import qarenabe.qarenabe.enums.SuccessCodeEnum;
import qarenabe.qarenabe.exception.AppException;
import qarenabe.qarenabe.mapper.CertificateMapper;
import qarenabe.qarenabe.mapper.LessonMapper;
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
@FieldDefaults(level = AccessLevel. PRIVATE, makeFinal = true)
@Service
@Slf4j
public class UserLessonServiceImpl implements UserLessonService {
    LessonRepository lessonRepository;
    UserCourseRepository userCourseRepository;
    UserLessonRepository userLessonRepository;
    CertificateRepository certificateRepository;
    LessonMapper lessonMapper;
    CertificateMapper certificateMapper;

    @Override
    public Object completeLessonAndUnlockNext(Long userId, Long lessonId) {
        Lesson currentLesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new AppException(ErrorCodeEnum.LESSON_NOT_EXISTED));

        UserCourse userCourse = userCourseRepository
                .findByUserIdAndCourseId(userId, currentLesson.getCourse().getId())
                .orElseThrow(() -> new AppException(ErrorCodeEnum.INVALID_COURSE_ID));

        UserLesson userLesson = userLessonRepository.findByUserCourseAndLesson(userCourse, currentLesson);
        userLesson.setIsCompleted(true);
        userLessonRepository.save(userLesson);

        List<Lesson> allLessons = lessonRepository.findByCourseId(currentLesson.getCourse().getId());

        Optional<Lesson> nextLessonOpt = allLessons.stream()
                .filter(lesson -> lesson.getPreviousLesson() != null &&
                        lesson.getPreviousLesson().getId().equals(currentLesson.getId()))
                .findFirst();
        if (nextLessonOpt.isPresent()) {
            Lesson nextLesson = nextLessonOpt.get();
            UserLesson existingUserLesson = userLessonRepository.findByUserCourseAndLesson(userCourse, nextLesson);
            if (existingUserLesson == null) {
                UserLesson nextUserLesson = new UserLesson();
                nextUserLesson.setUserCourse(userCourse);
                nextUserLesson.setLesson(nextLesson);
                nextUserLesson.setIsCompleted(false);
                userLessonRepository.save(nextUserLesson);
            }
            LessonResponseUserDTO lessonResponseUserDTO = lessonMapper.toLessonResponseUserDTO(nextLesson);
            lessonResponseUserDTO.setCourseId(nextLessonOpt.get().getCourse().getId());
            lessonResponseUserDTO.setCourseName(nextLessonOpt.get().getCourse().getTitle());

            return lessonResponseUserDTO;
        }

        userCourse.setIsCompleted(true);
        userCourseRepository.save(userCourse);

        boolean hasCertificate = certificateRepository.existsByUserAndCourse(userCourse.getUser(), userCourse.getCourse());
        if (!hasCertificate) {
            Certificate certificate = new Certificate();
            certificate.setUser(userCourse.getUser());
            certificate.setCourse(userCourse.getCourse());
            certificate.setTitleCertificate(userCourse.getCourse().getTitle());
            certificate.setDescription("You have successfully completed the course: " + userCourse.getCourse().getTitle());
            certificate.setImagePath(userCourse.getCourse().getLinkImg());
            certificateRepository.save(certificate);
            return SuccessCodeEnum.COMPLETE_COURSE.getMsg();
        } else{
            return SuccessCodeEnum.EXITS_COMPLETE_COURSE.getMsg();
        }
    }

    @Override
    public List<LessonResponseUserDTO> getAllLessonsByUserAndCourse(Long userId, Long courseId) {
        UserCourse userCourse = userCourseRepository
                .findByUserIdAndCourseId(userId, courseId)
                .orElseThrow(() -> new AppException(ErrorCodeEnum.INVALID_COURSE_ID));
        List<Lesson> allLessons = lessonRepository.findByCourseId(courseId);
        List<UserLesson> userLessons = userLessonRepository.findByUserCourse(userCourse);

        Map<Long, UserLesson> userLessonMap = userLessons.stream()
                .collect(Collectors.toMap(ul -> ul.getLesson().getId(), Function.identity()));
        return allLessons.stream()
                .map(lesson -> {
                    LessonResponseUserDTO dto = lessonMapper.toLessonResponseUserDTO(lesson);
                    dto.setCourseId(courseId);
                    dto.setCourseName(lesson.getCourse().getTitle());

                    UserLesson ul = userLessonMap.get(lesson.getId());
                    if (ul != null) {
                        dto.setIsCompleted(ul.getIsCompleted());
                        dto.setIsBlocked(false); // đã mở khóa
                    } else {
                        dto.setIsCompleted(false);
                        dto.setIsBlocked(true); // chưa mở khóa
                    }
                    return dto;
                }).collect(Collectors.toList());
    }
}

