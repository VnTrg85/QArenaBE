package qarenabe.qarenabe.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE )
public class UserCourseResponseDTO {
    Long id;
    String title;
    String description;
    Boolean isRequired;
    Boolean isBlocked;
    String linkImg;
    String type;
    String requiredCourse;
    Boolean isComplete;
    List<LessonResponseDTO> lessons;
}
