package qarenabe.qarenabe.service.TestProject;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import qarenabe.qarenabe.dto.TestprojectDTO;
import qarenabe.qarenabe.entity.TestProject;
import qarenabe.qarenabe.entity.User;
import qarenabe.qarenabe.repository.TestProjectRepository;
import qarenabe.qarenabe.repository.UserRepository;

@Service
public class TestProjectServiceImpl implements TestProjectService {

    @Autowired
    private TestProjectRepository testProjectRepository;
    @Autowired
    private UserRepository userRepository;

    public List<TestprojectDTO> getAllProjects() {
        return testProjectRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<TestprojectDTO> getProjectById(Long id) {
        return testProjectRepository.findById(id).map(this::convertToDTO);
    }

    public TestprojectDTO createProject(TestprojectDTO dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        TestProject project = convertToEntity(dto, user);
        return convertToDTO(testProjectRepository.save(project));
    }

    @Transactional
    public Optional<TestprojectDTO> updateProject(Long id, TestprojectDTO dto) {
        return testProjectRepository.findById(id).map(project -> {
            project.setProjectName(dto.getProjectName());
            project.setDescription(dto.getDescription());
            project.setOutScope(dto.getOutScope());
            project.setGoal(dto.getGoal());
            project.setAdditionalRequirement(dto.getAdditionalRequirement());
            project.setLink(dto.getLink());
            project.setPlatform(dto.getPlatform());
            project.setCreate_at(dto.getCreate_At());
            project.setEnd_at(dto.getEnd_At());
            project.setStatus(dto.getStatus());
            project.setLanguage(dto.getLanguage());
            return convertToDTO(testProjectRepository.save(project));
        });
    }

    @Transactional
    public void deleteProject(Long id) {
        testProjectRepository.deleteById(id);
    }

    private TestprojectDTO convertToDTO(TestProject project) {
        return new TestprojectDTO(
            project.getProjectName(), project.getDescription(), project.getOutScope(),
            project.getGoal(), project.getAdditionalRequirement(), project.getLink(),
            project.getPlatform(), project.getCreate_at(), project.getEnd_at(),
            project.getStatus(), project.getLanguage(), project.getUser().getId()
        );
    }

    private TestProject convertToEntity(TestprojectDTO dto, User user) {
        return new TestProject(
            null, dto.getProjectName(), dto.getDescription(), dto.getOutScope(),
            dto.getGoal(), dto.getAdditionalRequirement(), dto.getLink(),
            dto.getPlatform(), dto.getCreate_At(), dto.getEnd_At(),
            dto.getStatus(), dto.getLanguage(), user
        );
    }

    @Override
    public TestProjectDTO getProjectDetailById(Long id) {
       try {
        TestProject testproject = testProjectRepository.findById(id).get();
        TestprojectDTO secondDTO = new TestprojectDTO(testproject.getProjectName(),testproject.getDescription(),testproject.getGoal(),testproject.getPlatform(),testproject.getCreate_at(),testproject.getEnd_at(),testproject.getStatus(),testproject.getLanguage(),testFeatureService.getFeaturesByTestProject(testproject.getId()),payoutBugService.getPayoutBugByProject(testproject.getId()));

       } catch (Exception e) {
        // TODO: handle exception
       }
    }
}
