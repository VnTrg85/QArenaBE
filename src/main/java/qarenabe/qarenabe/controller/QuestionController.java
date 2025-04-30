package qarenabe.qarenabe.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import qarenabe.qarenabe.dto.*;
import qarenabe.qarenabe.service.AnswerService.AnswerService;
import qarenabe.qarenabe.service.QuestionService.QuestionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/question")
public class QuestionController {
    QuestionService questionService;

    @PostMapping("/create")
    ApiResponse<QuestionResponseDTO> createQuestion(@RequestBody QuestionRequestDTO request) {
        return ApiResponse.<QuestionResponseDTO>builder()
                .data(questionService.createQuestion(request))
                .build();
    }

    @GetMapping("/getAll")
    ApiResponse<List<QuestionResponseDTO>> getAllQuestions(@RequestParam Long courseId) {
        return ApiResponse.<List<QuestionResponseDTO>>builder()
                .data(questionService.getAllByCourseId(courseId))
                .build();
    }

    @PutMapping("/update/{id}")
    ApiResponse<QuestionResponseDTO> updateQuestion(@PathVariable Long id, @RequestBody QuestionRequestDTO request) {
        return ApiResponse.<QuestionResponseDTO>builder()
                .data(questionService.updateQuestion(id, request))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    ApiResponse<String> deleteQuestion(@PathVariable List<Long> ids) {
        return ApiResponse.<String>builder()
                .data(questionService.deleteQuestions(ids))
                .build();
    }


}
