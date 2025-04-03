package qarenabe.qarenabe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
    private String bugType;
    @Getter
    @Setter
    private Long testProjectId;
    public TestFeatureDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
