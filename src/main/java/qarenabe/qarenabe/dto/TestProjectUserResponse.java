package qarenabe.qarenabe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import qarenabe.qarenabe.entity.TestProject;

@AllArgsConstructor
public class TestProjectUserResponse {
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String status;
    @Getter
    @Setter
    private TestprojectDTO testProject;
}
