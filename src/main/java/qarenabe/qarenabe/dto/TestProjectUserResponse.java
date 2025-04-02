package qarenabe.qarenabe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import qarenabe.qarenabe.entity.TestProject;

@AllArgsConstructor
public class TestProjectUserResponse {
    private Long id;
    private String status;
    private TestProject testProject;
}
