package qarenabe.qarenabe.service.Lesson;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
@FieldDefaults(level = AccessLevel. PRIVATE, makeFinal = true)
@Service
@Slf4j
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
            lessonResponseDTO.setCourseId(lesson.getCourse().getId());
            lessonResponseDTO.setCourseName(lesson.getCourse().getTitle());
            return lessonResponseDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public String addLesson(LessonRequestDTO lessonRequestDTO) {
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
        return SuccessCodeEnum.ADD_SUCCESS.getMsg();
    }

    @Override
    public String deleteLessonByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new AppException(ErrorCodeEnum.INVALID_LESSON_ID);
        }

        // Tìm các bài học cần xóa
        List<Lesson> lessonsToDelete = lessonRepository.findAllById(ids);
        if (lessonsToDelete.size() != ids.size()) {
            throw new AppException(ErrorCodeEnum.LESSON_NOT_EXISTED);
        }

        // Tạo bản đồ để lưu các bài học cần xóa
        Map<Long, Lesson> lessonMap = lessonsToDelete.stream()
                .collect(Collectors.toMap(Lesson::getId, lesson -> lesson));

        // Tìm các bài học phụ thuộc vào bài học cần xóa (có previousLesson là một trong các bài học cần xóa)
        List<Lesson> dependentLessons = lessonRepository.findByPreviousLessonIdIn(ids);

        // Cập nhật các bài học phụ thuộc
        for (Lesson nextLesson : dependentLessons) {
            // Lấy bài học cần xóa
            Lesson deletedPrevious = lessonMap.get(nextLesson.getPreviousLesson().getId());

            // Nếu bài học cần xóa có bài học trước, gán lại previousLesson của nextLesson
            if (deletedPrevious != null) {
                nextLesson.setPreviousLesson(deletedPrevious.getPreviousLesson());

                // Nếu không còn bài học trước thì mở khóa (isBlocked = false)
                if (nextLesson.getPreviousLesson() == null) {
                    nextLesson.setIsBlocked(false);
                }
            }

            // Lưu lại các bài học đã cập nhật
            lessonRepository.save(nextLesson);
        }

        // Xóa các bài học cần xóa
        lessonRepository.deleteAll(lessonsToDelete);

        return SuccessCodeEnum.DELETE_SUCCESS.getMsg();
    }

    @Override
    public String updateLesson(Long id, LessonRequestDTO lessonRequestDTO) {
        Course course = courseRepository.findById(lessonRequestDTO.getCourseId())
                .orElseThrow(() -> new AppException(ErrorCodeEnum.COURSE_NOT_EXISTED));

        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCodeEnum.LESSON_NOT_EXISTED));

        lessonMapper.updateLesson(lesson, lessonRequestDTO);
        lesson.setCourse(course);
        lessonRepository.save(lesson);
        return SuccessCodeEnum.UPDATE_SUCCESS.getMsg();
    }

}
