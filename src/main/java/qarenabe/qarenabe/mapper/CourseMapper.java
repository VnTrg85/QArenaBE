package qarenabe.qarenabe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import qarenabe.qarenabe.dto.CourseRequestDTO;
import qarenabe.qarenabe.dto.CourseResponseDTO;
import qarenabe.qarenabe.entity.Course;

@Mapper(componentModel = "spring", uses = {LessonMapper.class})
public interface CourseMapper {
    @Mapping(target = "lessons", source = "lessons")
    CourseResponseDTO toCourseResponse(Course course);

    Course toCourse(CourseRequestDTO courseResponseDTO);
    
    @Mapping(target = "id", ignore = true)
    void updateCourse(@MappingTarget Course course, CourseRequestDTO request);
}
