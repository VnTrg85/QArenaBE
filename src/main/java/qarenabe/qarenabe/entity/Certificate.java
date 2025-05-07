package qarenabe.qarenabe.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "certificates")
public class Certificate extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
      User user;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
      Course course;

    @Column(nullable = false)
      String titleCertificate;

    @Column(name = "image_path")
      String imagePath; // Lưu đường dẫn ảnh chứng chỉ

    @Column(columnDefinition = "TEXT")
      String description; // Mô tả chứng chỉ


}
