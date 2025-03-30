package qarenabe.qarenabe.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonResponseDTO {
    Long id ;
    String name ;
}
