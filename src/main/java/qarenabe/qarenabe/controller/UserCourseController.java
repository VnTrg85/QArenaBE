package qarenabe.qarenabe.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qarenabe.qarenabe.dto.ApiResponse;
import qarenabe.qarenabe.dto.UserCourseResponseDTO;
import qarenabe.qarenabe.service.Security.SecurityService;
import qarenabe.qarenabe.service.UserCourse.UserCourseService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/userCourse")
@FieldDefaults(level = AccessLevel.PRIVATE)
@CrossOrigin(origins = "http://localhost:3000")
public class UserCourseController {

    UserCourseService userCourseService;
    SecurityService securityService;

    @GetMapping("/getAll")
    ApiResponse<List<UserCourseResponseDTO>> getAllUserCourses() {
        Long userId= 9L;
        return ApiResponse.<List<UserCourseResponseDTO>>builder()
                .data(userCourseService.getAllUserCourse(userId))
                .build();
    }
}
