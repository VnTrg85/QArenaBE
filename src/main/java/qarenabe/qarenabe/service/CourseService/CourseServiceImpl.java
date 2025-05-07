package qarenabe.qarenabe.service.CourseService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qarenabe.qarenabe.dto.CourseRequestDTO;
import qarenabe.qarenabe.dto.CourseResponseDTO;
import qarenabe.qarenabe.entity.Course;
import qarenabe.qarenabe.enums.ErrorCodeEnum;
import qarenabe.qarenabe.enums.SuccessCodeEnum;
import qarenabe.qarenabe.exception.AppException;
import qarenabe.qarenabe.mapper.CourseMapper;
import qarenabe.qarenabe.mapper.UserCourseMapper;
import qarenabe.qarenabe.repository.CourseRepository;
import qarenabe.qarenabe.repository.UserCourseRepository;

import java.util.List;
import java.util.stream.Collectors;

import static qarenabe.qarenabe.enums.ErrorCodeEnum.COURSE_DEPENDENCY;

@Transactional
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CourseServiceImpl implements CourseService {

    CourseRepository courseRepository;
    CourseMapper courseMapper;

    @Override
    public List<CourseResponseDTO> getAllCourse() {
        return courseRepository.findAll().stream()
                .map(courseMapper::toCourseResponse)
                .collect(Collectors.toList());
    }

    // Phương thức cho Admin: Lấy khóa học theo ID
    @Override
    public CourseResponseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCodeEnum.COURSE_NOT_EXISTED));
        return courseMapper.toCourseResponse(course);
    }

    private boolean hasCircularDependency(Course target, Course newCourse) {
        Course current = target;
        while (current != null) {
            if (current.equals(newCourse)) {
                return true;
            }
            current = current.getRequiredCourse();
        }
        return false;
    }
    // Thêm khóa học mới
    @Override
    public CourseResponseDTO addCourse(CourseRequestDTO courseRequestDTO) {
        Course course = courseMapper.toCourse(courseRequestDTO);

        if (course.getLinkImg() == null) {
            throw new AppException(ErrorCodeEnum.IMG_IS_NULL);
        }
        if (courseRequestDTO.getRequiredCourseId() != null) {
            Course requiredCourse = courseRepository.findById(courseRequestDTO.getRequiredCourseId())
                    .orElseThrow(() -> new AppException(ErrorCodeEnum.REQUIRED_COURSE_NOT_FOUND));

            if (hasCircularDependency(requiredCourse, course)) {
                throw new AppException(COURSE_DEPENDENCY);
            }

            course.setRequiredCourse(requiredCourse);
        }

        try {
            courseRepository.save(course);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCodeEnum.COURSE_EXISTED);
        }

        return courseMapper.toCourseResponse(course);
    }

    // Xóa khóa học theo danh sách IDs
    @Override
    public String deleteCourseByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new AppException(ErrorCodeEnum.INVALID_COURSE_ID);
        }

        List<Course> coursesToDelete = courseRepository.findAllById(ids);
        if (coursesToDelete.size() != ids.size()) {
            throw new AppException(ErrorCodeEnum.COURSE_NOT_EXISTED);
        }

        // Tìm các khóa học phụ thuộc vào những khóa học này
        List<Course> dependentCourses = courseRepository.findByRequiredCourseIdIn(ids);

        if (!dependentCourses.isEmpty()) {
            // Lấy tên các khóa học phụ thuộc
            String dependentNames = dependentCourses.stream()
                    .map(Course::getTitle)
                    .collect(Collectors.joining(", "));

            throw new AppException(ErrorCodeEnum.COURSE_DEPENDENCY);
        }

        courseRepository.deleteAll(coursesToDelete);
        return SuccessCodeEnum.DELETE_SUCCESS.getMsg();
    }

    @Override
    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO courseRequestDTO) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCodeEnum.COURSE_NOT_EXISTED));

        courseMapper.updateCourse(course, courseRequestDTO);

        Course updatedCourse = courseRepository.save(course);

        return courseMapper.toCourseResponse(updatedCourse);
    }
}
