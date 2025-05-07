package qarenabe.qarenabe.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import qarenabe.qarenabe.dto.ApiResponse;
import qarenabe.qarenabe.dto.CourseRequestDTO;
import qarenabe.qarenabe.dto.CourseResponseDTO;
import qarenabe.qarenabe.service.CourseService.CourseService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/course")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CourseController {

    CourseService courseService;

    @GetMapping("/getAll")
    ApiResponse<List<CourseResponseDTO>> getAllCourse() {
        return ApiResponse.<List<CourseResponseDTO>>builder()
                .data(courseService.getAllCourse())
                .build();
    }

    //        @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    ApiResponse<CourseResponseDTO> createCourse(@RequestBody CourseRequestDTO courseRequestDTO) {
        return ApiResponse.<CourseResponseDTO>builder().
                data(courseService.addCourse(courseRequestDTO))
                .build();
    }

    @GetMapping()
    ApiResponse<CourseResponseDTO> getCourse(@RequestParam Long id) {
        return ApiResponse.<CourseResponseDTO>builder()
                .data(courseService.getCourseById(id))
                .build();
    }

    //        @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{ids}")
    public ApiResponse<String> deleteCourses(@PathVariable String ids) {
        List<Long> idList = Arrays.stream(ids.split(",")).map(Long::parseLong).toList();
        return ApiResponse.<String>builder()
                .data(courseService.deleteCourseByIds(idList))
                .build();
    }

    //        @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{courseId}")
    ApiResponse<CourseResponseDTO> updateCourse(@PathVariable Long courseId, @RequestBody CourseRequestDTO courseRequestDTO) {
        return ApiResponse.<CourseResponseDTO>builder()
                .data(courseService.updateCourse(courseId, courseRequestDTO))
                .build();
    }
}
