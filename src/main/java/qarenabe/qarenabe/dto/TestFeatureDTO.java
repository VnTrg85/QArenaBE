package qarenabe.qarenabe.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import qarenabe.qarenabe.entity.BugType;

@AllArgsConstructor
public class TestFeatureDTO {
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String input;
    @Getter
    @Setter
    private String output;
    @Getter
    @Setter
    private List<BugType> bugType;
    public TestFeatureDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
