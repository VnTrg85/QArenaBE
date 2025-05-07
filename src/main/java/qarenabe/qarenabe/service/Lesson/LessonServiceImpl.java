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
import qarenabe.qarenabe.entity.Course;
import qarenabe.qarenabe.entity.Lesson;
import qarenabe.qarenabe.enums.ErrorCodeEnum;
import qarenabe.qarenabe.enums.SuccessCodeEnum;
import qarenabe.qarenabe.exception.AppException;
import qarenabe.qarenabe.mapper.LessonMapper;
import qarenabe.qarenabe.repository.CourseRepository;
import qarenabe.qarenabe.repository.LessonRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel. PRIVATE, makeFinal = true)
@Service
@Slf4j
public class LessonServiceImpl implements LessonService {
    LessonRepository lessonRepository;
    LessonMapper lessonMapper;

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
        Course course = courseRepository.findById(lessonRequestDTO.getCourseId())
                .orElseThrow(() -> new AppException(ErrorCodeEnum.COURSE_NOT_EXISTED));

        Lesson lesson = lessonMapper.toLesson(lessonRequestDTO);
        lesson.setCourse(course);
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
        List<Lesson> lessonsToDelete = lessonRepository.findAllById(ids);
        if (lessonsToDelete.size() != ids.size()) {
            throw new AppException(ErrorCodeEnum.LESSON_NOT_EXISTED);
        }
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
