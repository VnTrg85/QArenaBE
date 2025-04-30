package qarenabe.qarenabe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import qarenabe.qarenabe.dto.UserCourseResponseDTO;
import qarenabe.qarenabe.entity.UserCourse;

@Mapper(componentModel = "spring")
public interface UserCourseMapper {

    @Mapping(source = "course.id", target = "id")
    @Mapping(source = "course.title", target = "title")
    @Mapping(source = "course.description", target = "description")
    @Mapping(source = "course.isRequired", target = "isRequired")
    @Mapping(source = "course.linkImg", target = "linkImg")
    @Mapping(source = "course.type", target = "type")
    UserCourseResponseDTO toUserCourseResponse(UserCourse userCourse);

}