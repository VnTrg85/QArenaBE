package qarenabe.qarenabe.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import qarenabe.qarenabe.dto.ApiResponse;
import qarenabe.qarenabe.dto.CompleteLessonRequest;
import qarenabe.qarenabe.dto.UserLessonResponseDTO;
import qarenabe.qarenabe.service.UserLesson.UserLessonService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel. PRIVATE, makeFinal = true)
@RequestMapping("/userLesson")
@CrossOrigin(origins = "http://localhost:3000")
public class UserLessonController {

    UserLessonService userLessonService;

    @GetMapping("/allLesson")
    public ApiResponse<List<UserLessonResponseDTO>> getLessons(@RequestParam Long courseId) {
        Long userId=9L;
        return ApiResponse.<List<UserLessonResponseDTO>> builder()
                .data(userLessonService.getAllLessonsByUserAndCourse(userId, courseId))
                .build();
    }

}
