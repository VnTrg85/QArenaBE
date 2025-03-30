package qarenabe.qarenabe.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import qarenabe.qarenabe.dto.ApiResponse;
import qarenabe.qarenabe.dto.CourseRequestDTO;
import qarenabe.qarenabe.dto.CourseResponseDTO;
import qarenabe.qarenabe.enums.SuccessCodeEnum;
import qarenabe.qarenabe.service.CourseService.CourseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CourseController {

    CourseService courseService;

    @GetMapping
    ApiResponse<List<CourseResponseDTO>> getUsers() {
        return ApiResponse.<List<CourseResponseDTO>>builder()
                .data(courseService.getAllCourse())
                .build();
    }
    @PostMapping
    ApiResponse<CourseResponseDTO> createCourse(@RequestBody CourseRequestDTO courseRequestDTO) {
        return ApiResponse.<CourseResponseDTO>builder().
                data(courseService.addCourse(courseRequestDTO))
                .message(SuccessCodeEnum.ADD_SUCCESS.getMsg())
                .build();
    }
    @GetMapping("/{id}")
    ApiResponse<CourseResponseDTO> getCourse(@PathVariable Long id) {
        return ApiResponse.<CourseResponseDTO>builder()
                .data(courseService.getCourseById(id))
                .build();
    }
    @DeleteMapping("/{ids}")
    ApiResponse<String> deleteCourse(@PathVariable List<Long> ids) {
        return ApiResponse.<String>builder()
                .data(courseService.deleteCourseByIds(ids))
                .build();
    }

    @PutMapping ("/{courseId}")
    ApiResponse<CourseResponseDTO> updateCourse(@PathVariable Long courseId ,@RequestBody CourseRequestDTO courseRequestDTO) {
        return ApiResponse.<CourseResponseDTO>builder()
                .data(courseService.updateCourse(courseId,courseRequestDTO))
                .build();
    }
}
