package qarenabe.qarenabe.service.UserCourse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import qarenabe.qarenabe.dto.LessonResponseDTO;
import qarenabe.qarenabe.dto.UserCourseResponseDTO;
import qarenabe.qarenabe.entity.Course;
import qarenabe.qarenabe.entity.UserCourse;
import qarenabe.qarenabe.mapper.LessonMapper;
import qarenabe.qarenabe.mapper.UserCourseMapper;
import qarenabe.qarenabe.repository.UserCourseRepository;

import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Service
public class UserCourseServiceImpl implements UserCourseService {
    UserCourseRepository userCourseRepository;
    UserCourseMapper userCourseMapper;  // Sử dụng UserCourseMapper
    LessonMapper lessonMapper;

    @Override
    public List<UserCourseResponseDTO> getAllUserCourse(Long userId) {
        // Lấy danh sách các UserCourse của người dùng
        List<UserCourse> userCourses = userCourseRepository.findByUserId(userId);

        // Chuyển đổi UserCourse thành UserCourseResponseDTO
        return userCourses.stream().map(item -> {
            Course course = item.getCourse();

            // Tạo mới UserCourseResponseDTO và gán dữ liệu
            UserCourseResponseDTO responseDTO = userCourseMapper.toUserCourseResponse(item);

            // Thêm thông tin khóa học phụ thuộc (DependenceCourse)
            if (course.getRequiredCourse() != null) {
                responseDTO.setRequiredCourse(course.getRequiredCourse().getTitle());
            }

            List<LessonResponseDTO> lessons = course.getLessons().stream()
                    .map(lessonMapper::toLessonResponse)
                    .toList();

            responseDTO.setLessons(lessons);

            return responseDTO;
        }).collect(Collectors.toList());
    }
}

