package qarenabe.qarenabe.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
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

    @PutMapping("/complete/{courseId}")
    public ApiResponse<Object> completeCourseAndUnlockNextCourse(@PathVariable Long courseId,@RequestBody List<Long> selectedAnswerIds) {
        Long userId= 9L;
        return ApiResponse.<Object>builder()
                .data(userCourseService.completeCourseAndUnlockNextCourse(userId,courseId,selectedAnswerIds))
                .build();
    }
}
