package qarenabe.qarenabe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import qarenabe.qarenabe.dto.LessonRequestDTO;
import qarenabe.qarenabe.dto.LessonResponseDTO;
import qarenabe.qarenabe.dto.LessonResponseUserDTO;
import qarenabe.qarenabe.entity.Lesson;

@Mapper(componentModel = "spring")
public interface LessonMapper {
    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.title", target = "courseName")
    LessonResponseDTO toLessonResponse(Lesson lesson);

    Lesson toLesson(LessonRequestDTO lessonRequestDTO);

    @Mapping(target = "id", ignore = true)
    void updateLesson(@MappingTarget Lesson lesson, LessonRequestDTO lessonRequestDTO);

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.title", target = "courseName")
    LessonResponseUserDTO toLessonResponseUserDTO(Lesson lesson);
}
