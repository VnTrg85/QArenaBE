package qarenabe.qarenabe.entity;

import java.util.Date;
import java.util.List;

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
import jakarta.persistence.OneToMany;
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
      Long id;

    @Getter
    @Setter
      String projectName;

    @Getter
    @Setter
    @Lob
    @Column(name = "description", length = 2000000000)
      String description;

    @Getter
    @Setter
      String outScope;

    @Getter
    @Setter
      String goal;

    @Getter
    @Setter
      String additionalRequirement;

    @Getter
    @Setter
      String link;

    @Getter
    @Setter
    @Convert(converter = StringArrayConverter.class)
    @Column(columnDefinition = "TEXT")
    private String[] platform;

    @Getter
    @Setter
      Date create_at;

    @Getter
    @Setter
      Date end_at;

    @Getter
    @Setter
      String status;

    @Getter
    @Setter
    @Convert(converter = StringArrayConverter.class)
    @Column(columnDefinition = "TEXT")
    private String[] language;

    @ManyToOne
    @JoinColumn(name = "userId")
    @Getter
    @Setter
      User user;


    @OneToMany
    @JoinColumn(name = "deviceId")
    @Getter
    @Setter
    private List<Device> devices;

    public TestProject(Long id) {
        this.id = id;
    }

}
