package qarenabe.qarenabe.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseResponseDTO {
    Long id;
    String title;
    String description;
    boolean isRequired ;
}
