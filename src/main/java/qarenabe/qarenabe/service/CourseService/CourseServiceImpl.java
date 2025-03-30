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

import java.util.List;
@Transactional
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CourseServiceImpl implements CourseService {

    CourseRepository courseRepository;
    CourseMapper courseMapper;

    @Override
    public List<CourseResponseDTO> getAllCourse() {
        return courseRepository.findAll().stream().map(courseMapper::toCourseResponse).toList();
    }

    @Override
    public CourseResponseDTO getCourseById(Long id) {
        return courseMapper.toCourseResponse(
                courseRepository.findById(id).orElseThrow(() -> new AppException(ErrorCodeEnum.COURSE_NOT_EXISTED)));
    }

    @Override
    public CourseResponseDTO addCourse(CourseRequestDTO courseRequestDTO) {
        Course course = courseMapper.toCourse(courseRequestDTO);
        try {
            courseRepository.save(course);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCodeEnum.COURSE_EXISTED);
        }
        return courseMapper.toCourseResponse(course);
    }

    @Override
    public String deleteCourseByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new AppException(ErrorCodeEnum.INVALID_COURSE_ID);
        }
        List<Course> courses = courseRepository.findAllById(ids);
        if (courses.size() != ids.size()) {
            throw new AppException(ErrorCodeEnum.COURSE_NOT_EXISTED);
        }
        courseRepository.deleteByIdIn(ids);
        return SuccessCodeEnum.DELETE_SUCCESS.getMsg();
    }

    @Override
    public CourseResponseDTO updateCourse(Long id,CourseRequestDTO courseRequestDTO) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCodeEnum.COURSE_NOT_EXISTED));

        courseMapper.updateCourse(course, courseRequestDTO); // Nếu có sử dụng MapStruct để update
        courseRepository.save(course);
        return courseMapper.toCourseResponse(courseRepository.save(course));
    }

}
