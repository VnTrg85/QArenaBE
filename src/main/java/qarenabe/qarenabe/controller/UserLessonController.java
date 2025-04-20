package qarenabe.qarenabe.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import qarenabe.qarenabe.dto.ApiResponse;
import qarenabe.qarenabe.dto.CompleteLessonRequest;
import qarenabe.qarenabe.dto.LessonResponseUserDTO;
import qarenabe.qarenabe.service.UserLesson.UserLessonService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel. PRIVATE, makeFinal = true)
@RequestMapping("/userCourse")
public class UserLessonController {

    UserLessonService userLessonService;


    @PostMapping("/completion")
    public ApiResponse<Object> completeLesson(@RequestBody CompleteLessonRequest request) {
        return ApiResponse.<Object>builder()
                .data(userLessonService.completeLessonAndUnlockNext(request.getUserId(), request.getLessonId()))
                .build();
    }

    @GetMapping("/allLesson")
    public ApiResponse<List<LessonResponseUserDTO>> getLessons(@RequestParam Long userId,
                                                               @RequestParam Long courseId) {
        return ApiResponse.<List<LessonResponseUserDTO>> builder()
                .data(userLessonService.getAllLessonsByUserAndCourse(userId, courseId))
                .build();
    }

}
