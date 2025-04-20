package qarenabe.qarenabe.service.UserCourse;

import qarenabe.qarenabe.dto.UserCourseResponseDTO;

import java.util.List;

public interface UserCourseService {
    List<UserCourseResponseDTO> getAllUserCourse(Long userId);
}
