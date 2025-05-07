package qarenabe.qarenabe.entity;
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
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import qarenabe.qarenabe.converter.LongListConverter;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TestFeature {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @Lob
    @Column(name = "input", length = 2000000000)
    private String input;

    @Getter
    @Setter
    @Lob
    @Column(name = "output", length = 2000000000)
    private String output;

    @Setter
    @Getter
    @Convert(converter = LongListConverter.class)
    private List<Long> bugTypeId;

    @ManyToOne
    @JoinColumn(name = "test_project_Id")
    @Getter
    @Setter
    private TestProject testProject;

    public TestFeature(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
