package qarenabe.qarenabe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import qarenabe.qarenabe.dto.CourseRequestDTO;
import qarenabe.qarenabe.dto.CourseResponseDTO;
import qarenabe.qarenabe.entity.Course;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseResponseDTO toCourseResponse(Course course);

    Course toCourse(CourseRequestDTO courseRequestDTO);

    @Mapping(target = "id", ignore = true)
    void updateCourse(@MappingTarget Course course, CourseRequestDTO request);
}