package qarenabe.qarenabe.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE )
@NoArgsConstructor
@AllArgsConstructor
public class UserLessonResponseDTO {
      Long id;
      String title;
      Long courseId;
      String courseName;
      String linkImg;
      String description;
      String LessonLink;
}
