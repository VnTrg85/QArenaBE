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

    private String findNameCourseDenpen(Long id){
        return courseRepository.findById(id).get().getTitle();
    }
    @Override
    public List<CourseResponseDTO> getAllCourse() {
        return courseRepository.findAll().stream()
                .map(course -> {
                    CourseResponseDTO dto = courseMapper.toCourseResponse(course);
                    if (course.getRequiredCourse() != null ) {
                        String dependenceCourseName = findNameCourseDenpen(course.getRequiredCourse().getId());
                        dto.setDependenceCourse(dependenceCourseName);
                    } else {
                        dto.setDependenceCourse(null);
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

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
                return true; // Tạo vòng lặp
            }
            current = current.getRequiredCourse();
        }
        return false;
    }
    @Override
    public CourseResponseDTO addCourse(CourseRequestDTO courseRequestDTO) {
        Course course = courseMapper.toCourse(courseRequestDTO);

        if (course.getLinkImg() == null) {
            throw new AppException(ErrorCodeEnum.IMG_IS_NULL);
        }

        // Nếu có requiredCourseId thì tìm và gán
        if (courseRequestDTO.getRequiredCourseId() != null) {
            Course requiredCourse = courseRepository.findById(courseRequestDTO.getRequiredCourseId())
                    .orElseThrow(() -> new AppException(ErrorCodeEnum.REQUIRED_COURSE_NOT_FOUND));

            // Kiểm tra vòng lặp phụ thuộc nếu cần
            if (hasCircularDependency(requiredCourse, course)) {
                throw new AppException(COURSE_DEPENDENCY);
            }

            course.setRequiredCourse(requiredCourse);
        }
        String dependenceCourseName = null;
        if (course.getRequiredCourse() != null) {
            dependenceCourseName = findNameCourseDenpen(course.getRequiredCourse().getId());
        }

        try {
            courseRepository.save(course);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCodeEnum.COURSE_EXISTED);
        }
        CourseResponseDTO courseResponseDTO = courseMapper.toCourseResponse(course);
        courseResponseDTO.setDependenceCourse(dependenceCourseName);
        return courseResponseDTO;
    }


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

            // Gợi ý: bạn có thể log hoặc throw exception ở đây
            throw new AppException(ErrorCodeEnum.COURSE_DEPENDENCY);
        }

        courseRepository.deleteAll(coursesToDelete);
        return SuccessCodeEnum.DELETE_SUCCESS.getMsg();
    }


    @Override
    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO courseRequestDTO) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCodeEnum.COURSE_NOT_EXISTED));

        courseMapper.updateCourse(course, courseRequestDTO); // Update course using the mapper

        // Gán tên khóa học phụ thuộc
        String dependenceCourseName = null;
        if (course.getRequiredCourse() != null) {
            dependenceCourseName = findNameCourseDenpen(course.getRequiredCourse().getId());
        }

        // Only save once after the update
        Course updatedCourse = courseRepository.save(course);

        // Set DependenceCourse in response DTO
        CourseResponseDTO courseResponseDTO = courseMapper.toCourseResponse(updatedCourse);
        courseResponseDTO.setDependenceCourse(dependenceCourseName);
        return courseResponseDTO;
    }
}
