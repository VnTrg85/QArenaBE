package qarenabe.qarenabe.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class CertificateResponseDTO {
    Long id;
    String titleCertificate;
    String description;
    String imagePath;
    Long courseId;
    String courseTitle;
    String courseImage;
}
