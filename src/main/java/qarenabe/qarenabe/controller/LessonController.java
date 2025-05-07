package qarenabe.qarenabe.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import qarenabe.qarenabe.dto.ApiResponse;
import qarenabe.qarenabe.dto.LessonRequestDTO;
import qarenabe.qarenabe.dto.LessonResponseDTO;
import qarenabe.qarenabe.service.Lesson.LessonService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lesson")
@FieldDefaults(level = AccessLevel. PRIVATE, makeFinal = true)
@RequiredArgsConstructor

public class LessonController {
    LessonService lessonService;

    @GetMapping("/getAll")
    ApiResponse<List<LessonResponseDTO>> getAllLesson() {
        return ApiResponse.<List<LessonResponseDTO>>builder()
                .data(lessonService.getAllLesson())
                .build();
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    ApiResponse<String> createLesson(@RequestBody LessonRequestDTO lessonRequestDTO) {
        return ApiResponse.<String>builder().
                data(lessonService.addLesson(lessonRequestDTO))
                .build();
    }


//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{ids}")
    ApiResponse<String> deleteLesson(@PathVariable String ids) {
        List<Long> idList = Arrays.stream(ids.split(",")).map(Long::parseLong).toList();
        return ApiResponse.<String>builder()
                .data(lessonService.deleteLessonByIds(idList))
                .build();
    }
//    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping ("/update/{lessonId}")
    ApiResponse<String> updateLesson(@PathVariable Long lessonId ,@RequestBody LessonRequestDTO lessonRequestDTO) {
        return ApiResponse.<String>builder()
                .data(lessonService.updateLesson(lessonId,lessonRequestDTO))
                .build();
    }

}
