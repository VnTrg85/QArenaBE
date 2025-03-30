package qarenabe.qarenabe.service.CourseService;

import qarenabe.qarenabe.dto.CourseRequestDTO;
import qarenabe.qarenabe.dto.CourseResponseDTO;
import qarenabe.qarenabe.entity.Course;

import java.util.List;

public interface CourseService {
    List<CourseResponseDTO> getAllCourse();
    CourseResponseDTO getCourseById(Long id);
    CourseResponseDTO addCourse(CourseRequestDTO course);
    String deleteCourseByIds(List<Long> ids);
    CourseResponseDTO updateCourse(Long id,CourseRequestDTO course);
}
