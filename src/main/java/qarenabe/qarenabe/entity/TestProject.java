package qarenabe.qarenabe.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import qarenabe.qarenabe.converter.StringArrayConverter;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TestProject {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String projectName;

    @Getter
    @Setter
    @Lob
    @Column(name = "description", length = 2000000000)
    private String description;

    @Getter
    @Setter
    private String outScope;

    @Getter
    @Setter
    private String goal;

    @Getter
    @Setter
    private String additionalRequirement;

    @Getter
    @Setter
    private String link;

    @Getter
    @Setter
    @Convert(converter = StringArrayConverter.class)
    @Column(columnDefinition = "TEXT")
    private String[] platform;

    @Getter
    @Setter
    private Date create_at;

    @Getter
    @Setter
    private Date end_at;

    @Getter
    @Setter
    private String status;

    @Getter
    @Setter
    @Convert(converter = StringArrayConverter.class)
    @Column(columnDefinition = "TEXT")
    private String[] language;

    @ManyToOne
    @JoinColumn(name = "userId")
    @Getter
    @Setter
    private User user;

}
