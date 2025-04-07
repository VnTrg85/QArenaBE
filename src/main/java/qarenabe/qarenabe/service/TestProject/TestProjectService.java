package qarenabe.qarenabe.service.TestProject;
import qarenabe.qarenabe.dto.TestprojectDTO;
import qarenabe.qarenabe.entity.TestProject;

import java.util.List;
import java.util.Optional;

public interface TestProjectService {
    public List<TestprojectDTO> getAllProjects();
    public Optional<TestprojectDTO> getProjectById(Long id);
    public TestProject  getProjectDetailById(Long id);
    public TestprojectDTO createProject(TestprojectDTO dto);
    public Optional<TestprojectDTO> updateProject(Long id, TestprojectDTO dto);
    public void deleteProject(Long id);
}
