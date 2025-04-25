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
public class LessonResponseDTO {
      Long id;
      String title;
      String link;
      Long courseId;
      String courseName;
      Long lessonRequiredId;
      String lessonRequiredTitle;
}