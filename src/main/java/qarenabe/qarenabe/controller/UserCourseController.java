package qarenabe.qarenabe.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qarenabe.qarenabe.dto.ApiResponse;
import qarenabe.qarenabe.dto.UserCourseResponseDTO;
import qarenabe.qarenabe.service.Security.SecurityService;
import qarenabe.qarenabe.service.UserCourse.UserCourseService;

import java.util.List;

@RestController
@RequestMapping("/userCourse")
public class UserCourseController {

    UserCourseService userCourseService;
    SecurityService securityService;

    @GetMapping("/getAll")
    ApiResponse<List<UserCourseResponseDTO>> getAllUserCourses() {
        Long userId= securityService.getCurrentUserId();
        return ApiResponse.<List<UserCourseResponseDTO>>builder()
                .data(userCourseService.getAllUserCourse(userId))
                .build();
    }
}
