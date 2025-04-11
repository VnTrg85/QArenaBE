package qarenabe.qarenabe.service.LessonService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qarenabe.qarenabe.dto.LessonRequestDTO;
import qarenabe.qarenabe.dto.LessonResponseDTO;
import qarenabe.qarenabe.entity.*;
import qarenabe.qarenabe.enums.ErrorCodeEnum;
import qarenabe.qarenabe.enums.SuccessCodeEnum;
import qarenabe.qarenabe.exception.AppException;
import qarenabe.qarenabe.mapper.LessonMapper;
import qarenabe.qarenabe.repository.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class LessonServiceImpl implements LessonService {
    LessonRepository lessonRepository;
    LessonMapper lessonMapper;
    UserCourseRepository userCourseRepository;
    UserLessonRepository userLessonRepository;
    CertificateRepository certificateRepository;
    CourseRepository courseRepository;

    @Override
    public List<LessonResponseDTO> getAllLesson() {
        List<Lesson> lessons = lessonRepository.findAll();
        return lessons.stream().map(lesson -> {
            LessonResponseDTO lessonResponseDTO = lessonMapper.toLessonResponse(lesson);
            if (lesson.getPreviousLesson() != null) {
                lessonResponseDTO.setPreviousLessonId(lesson.getPreviousLesson().getId());
            }
            lessonResponseDTO.setCourseId(lesson.getCourse().getId());
            return lessonResponseDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public LessonResponseDTO getLessonById(Long id) {
        Lesson lessonEntity = lessonRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCodeEnum.LESSON_NOT_EXISTED));
        LessonResponseDTO lessonDTO = lessonMapper.toLessonResponse(lessonEntity);
        if (lessonEntity.getCourse() != null) {
            lessonDTO.setCourseId(lessonEntity.getCourse().getId());
        }
        if (lessonEntity.getPreviousLesson() != null) {
            lessonDTO.setPreviousLessonId(lessonEntity.getPreviousLesson().getId());
        }
        return lessonDTO;
    }


    @Override
    public LessonResponseDTO addLesson(LessonRequestDTO lessonRequestDTO) {
        Course course = courseRepository.findById(lessonRequestDTO.getCourseId()).orElseThrow(() -> new AppException(ErrorCodeEnum.COURSE_NOT_EXISTED));
        Lesson lastLesson = lessonRepository.findTopByCourseIdOrderByCreatedAtDesc(lessonRequestDTO.getCourseId());
        Lesson lesson = lessonMapper.toLesson(lessonRequestDTO);
        lesson.setCourse(course);
        if (lastLesson != null) {
            lesson.setPreviousLesson(lastLesson);
        }
        try {
            lessonRepository.save(lesson);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCodeEnum.LESSON_EXISTED);
        }
        LessonResponseDTO lessonResponseDTO = lessonMapper.toLessonResponse(lesson);
        if (lastLesson != null) {
            lessonResponseDTO.setPreviousLessonId(lastLesson.getId());
        }
        lessonResponseDTO.setCourseId(course.getId());
        return lessonResponseDTO;
    }

    @Override
    public String deleteLessonByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new AppException(ErrorCodeEnum.INVALID_LESSON_ID);
        }
        //các bài học cần xóa
        List<Lesson> lessonsToDelete = lessonRepository.findAllById(ids);
        if (lessonsToDelete.size() != ids.size()) {
            throw new AppException(ErrorCodeEnum.LESSON_NOT_EXISTED);
        }
        //các bài học cần xóa
        Map<Long, Lesson> lessonMap = lessonsToDelete.stream()
                .collect(Collectors.toMap(Lesson::getId, lesson -> lesson));

        // các bài học có preless là id cần xóa
        List<Lesson> dependentLessons = lessonRepository.findByPreviousLessonIdIn(ids);

        for (Lesson nextLesson : dependentLessons) {
            //lesson cbi xóa
            Lesson deletedPrevious = lessonMap.get(nextLesson.getPreviousLesson().getId());
            // Gán previousLesson mới
            nextLesson.setPreviousLesson(deletedPrevious.getPreviousLesson());

            // Nếu không còn bài học trước thì mở khóa
            if (nextLesson.getPreviousLesson() == null) {
                nextLesson.setBlocked(false);
            }
            lessonRepository.save(nextLesson);
        }
        lessonRepository.deleteAll(lessonsToDelete);
        return SuccessCodeEnum.DELETE_SUCCESS.getMsg();
    }

    @Override
    public LessonResponseDTO updateLesson(Long id, LessonRequestDTO lessonRequestDTO) {
        Course course = courseRepository.findById(lessonRequestDTO.getCourseId())
                .orElseThrow(() -> new AppException(ErrorCodeEnum.COURSE_NOT_EXISTED));

        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCodeEnum.LESSON_NOT_EXISTED));

        lessonMapper.updateLesson(lesson, lessonRequestDTO);
        lesson.setCourse(course);
        lessonRepository.save(lesson);
        return lessonMapper.toLessonResponse(lessonRepository.save(lesson));
    }

    public Object completeLessonAndUnlockNext(Long userId, Long lessonId) {
        Lesson currentLesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new AppException(ErrorCodeEnum.LESSON_NOT_EXISTED));

        UserCourse userCourse = userCourseRepository
                .findByUserIdAndCourseId(userId, currentLesson.getCourse().getId())
                .orElseThrow(() -> new AppException(ErrorCodeEnum.INVALID_COURSE_ID));

        // Đánh dấu bài học hiện tại là hoàn thành
        UserLesson userLesson = userLessonRepository.findByUserCourseAndLesson(userCourse, currentLesson);
        userLesson.setIsCompleted(true);
        userLessonRepository.save(userLesson);

        // Lấy danh sách bài học trong khóa học
        List<Lesson> allLessons = lessonRepository.findByCourseId(currentLesson.getCourse().getId());

        // Tìm bài học tiếp theo
        Optional<Lesson> nextLessonOpt = allLessons.stream()
                .filter(lesson -> lesson.getPreviousLesson() != null &&
                        lesson.getPreviousLesson().getId().equals(currentLesson.getId()))
                .findFirst();

        // check value
        if (nextLessonOpt.isPresent()) {
            Lesson nextLesson = nextLessonOpt.get();

            // tạo nextLesson
            UserLesson existingUserLesson = userLessonRepository.findByUserCourseAndLesson(userCourse, nextLesson);
            if (existingUserLesson == null) {
                UserLesson nextUserLesson = new UserLesson();
                nextUserLesson.setUserCourse(userCourse);
                nextUserLesson.setLesson(nextLesson);
                nextUserLesson.setIsCompleted(false);
                userLessonRepository.save(nextUserLesson);
            }
            return lessonMapper.toLessonResponse(nextLesson);
        }
        userCourse.setCompleted(true);
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
            return certificate;
        } else {
            return certificateRepository
                    .findByUserAndCourse(userCourse.getUser(), userCourse.getCourse());
        }
    }
}
