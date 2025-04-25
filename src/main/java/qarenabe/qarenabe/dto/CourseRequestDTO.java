package qarenabe.qarenabe.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE )
public class CourseRequestDTO {
    String title;
    String description;
    @JsonProperty("isRequired")
    Boolean isRequired ;
    Boolean isBlocked ;
    String type;
    String linkImg;
    @JsonProperty("requiredCourseId")
    Long requiredCourseId;
}
