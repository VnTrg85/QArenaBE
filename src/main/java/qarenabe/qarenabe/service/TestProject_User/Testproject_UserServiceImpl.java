package qarenabe.qarenabe.service.TestProject_User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import qarenabe.qarenabe.dto.TestProjectUserResponse;
import qarenabe.qarenabe.dto.TestprojectDTO;
import qarenabe.qarenabe.entity.TestProject;
import qarenabe.qarenabe.entity.TestProject_User;
import qarenabe.qarenabe.repository.TestProject_UserRepository;
import qarenabe.qarenabe.service.PayoutBug.PayoutBugService;
import qarenabe.qarenabe.service.TestFeature.TestFeatureService;

@Service
public class Testproject_UserServiceImpl implements TestProject_UserService{
    @Autowired
    private TestProject_UserRepository testProject_UserRepository;
    @Autowired
    private TestFeatureService testFeatureService;
    @Autowired
    private PayoutBugService payoutBugService;
    @Override
    public List<TestProjectUserResponse> getProjectByUserId(Long userId) {
        try {
            List<TestProject_User> lists = testProject_UserRepository.findByUserId(userId);
            List<TestProjectUserResponse> resLists = new ArrayList<>();
            
            for (TestProject_User item : lists) {
                TestProject selectedTest = item.getTestProject();
                TestprojectDTO secondDTO = new TestprojectDTO(selectedTest.getProjectName(),selectedTest.getDescription(),selectedTest.getGoal(),selectedTest.getPlatform(),selectedTest.getCreate_at(),selectedTest.getEnd_at(),selectedTest.getStatus(),selectedTest.getLanguage(),testFeatureService.getFeaturesByTestProject(selectedTest.getId()),payoutBugService.getPayoutBugByProject(selectedTest.getId()));
                TestProjectUserResponse val = new TestProjectUserResponse(item.getId(), item.getStatus(), secondDTO);
                resLists.add(val);
            }
            return resLists;
        } catch (EntityNotFoundException e) { 
            throw new EntityNotFoundException("User not found with ID: " + userId);
        } catch (DataAccessException e) { 
            throw new RuntimeException("Database error occurred while retrieving project user data", e);
        } catch (Exception e) { 
            throw new RuntimeException("An unexpected error occurred", e);
        }
    }
    @Override
    public TestProjectUserResponse createTestProject(Long userId, Long testProjectId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTestProject'");
    }
}
