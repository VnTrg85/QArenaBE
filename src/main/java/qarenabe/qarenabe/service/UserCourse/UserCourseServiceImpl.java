package qarenabe.qarenabe.service.UserCourseService;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import qarenabe.qarenabe.dto.UserCourseResponseDTO;
import qarenabe.qarenabe.entity.UserCourse;
import qarenabe.qarenabe.repository.UserCourseRepository;

import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Service
public class UserCourseServiceImpl implements UserCourseService {
    UserCourseRepository userCourseRepository;

    @Override
    public List<UserCourseResponseDTO> getAllUserCourse(Long userId) {
        List<UserCourse> userCourses = userCourseRepository.findByUserId(userId);
        return userCourses.stream().map(item -> {
            UserCourseResponseDTO responseDTO = new UserCourseResponseDTO();
            responseDTO.setCourseId(item.getCourse().getId());
            responseDTO.setCourseName(item.getCourse().getTitle());
            responseDTO.setCourseDescription(item.getCourse().getDescription());
            return responseDTO;
        }).collect(Collectors.toList());
    }


}
