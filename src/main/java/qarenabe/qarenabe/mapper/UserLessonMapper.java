package qarenabe.qarenabe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import qarenabe.qarenabe.dto.UserLessonResponseDTO;
import qarenabe.qarenabe.entity.Lesson;

@Mapper(componentModel = "spring")

public interface UserLessonMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.title", target = "courseName")
    @Mapping(source = "linkImg", target = "linkImg")
    @Mapping(source = "description", target = "description")
    UserLessonResponseDTO toLessonResponseUserDTO(Lesson lesson);
}
