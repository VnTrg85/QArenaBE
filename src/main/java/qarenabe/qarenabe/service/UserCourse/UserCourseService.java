package qarenabe.qarenabe.service.UserCourseService;

import qarenabe.qarenabe.dto.UserCourseResponseDTO;
import qarenabe.qarenabe.entity.UserCourse;

import java.util.List;

public interface UserCourseService {
    List<UserCourseResponseDTO> getAllUserCourse(Long userId);
}
