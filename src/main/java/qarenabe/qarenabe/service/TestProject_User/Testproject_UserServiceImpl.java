package qarenabe.qarenabe.service.TestProject_User;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.hibernate.action.internal.EntityAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import qarenabe.qarenabe.dto.TestProjectUserResponse;
import qarenabe.qarenabe.dto.TestprojectDTO;
import qarenabe.qarenabe.entity.TestProject;
import qarenabe.qarenabe.entity.TestProject_User;
import qarenabe.qarenabe.entity.User;
import qarenabe.qarenabe.repository.TestProjectRepository;
import qarenabe.qarenabe.repository.TestProject_UserRepository;
import qarenabe.qarenabe.repository.UserRepository;
import qarenabe.qarenabe.service.PayoutBug.PayoutBugService;
import qarenabe.qarenabe.service.TestFeature.TestFeatureService;
import qarenabe.qarenabe.service.User.UserService;

@Service
public class Testproject_UserServiceImpl implements TestProject_UserService{
    @Autowired
    private TestProject_UserRepository testProject_UserRepository;
    @Autowired
    private TestFeatureService testFeatureService;
    @Autowired
    private PayoutBugService payoutBugService;
    @Autowired 
    private UserRepository userRepository;
    @Autowired
    private TestProjectRepository testProjectRepository;
    @Override
    public List<TestProjectUserResponse> getProjectByUserId(Long userId) {
        try {
            List<TestProject_User> lists = testProject_UserRepository.findByUserId(userId);
            List<TestProjectUserResponse> resLists = new ArrayList<>();
            
            for (TestProject_User item : lists) {
                TestProject selectedTest = item.getTestProject();
                TestprojectDTO secondDTO = new TestprojectDTO(selectedTest.getId(),selectedTest.getProjectName(),selectedTest.getDescription(),selectedTest.getGoal(),selectedTest.getPlatform(),selectedTest.getCreate_at(),selectedTest.getEnd_at(),selectedTest.getStatus(),selectedTest.getLanguage(),testFeatureService.getFeaturesByTestProject(selectedTest.getId()),payoutBugService.getPayoutBugByProject(selectedTest.getId()));
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
    public TestProjectUserResponse createTestProject(TestProject_User entity) {
        try {
            User user = userRepository.findById(entity.getUser().getId()).orElseThrow(() -> new EntityNotFoundException("No user with ID " + entity.getUser().getId()));
            TestProject testProject = testProjectRepository.findById(entity.getTestProject().getId()).orElseThrow(() -> new EntityNotFoundException("No TestProject with ID " + entity.getTestProject().getId()));
            entity.setUser(user);
            entity.setTestProject(testProject);
            TestProject_User res =  testProject_UserRepository.save(entity);
            TestProject selectedTest = res.getTestProject();
            TestprojectDTO secondDTO = new TestprojectDTO(selectedTest.getProjectName(),selectedTest.getDescription(),selectedTest.getGoal(),selectedTest.getPlatform(),selectedTest.getCreate_at(),selectedTest.getEnd_at(),selectedTest.getStatus(),selectedTest.getLanguage(),testFeatureService.getFeaturesByTestProject(selectedTest.getId()),payoutBugService.getPayoutBugByProject(selectedTest.getId()));
            TestProjectUserResponse val = new TestProjectUserResponse(res.getId(), res.getStatus(), secondDTO);
            return val;
        }
        catch (EntityNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred", e);
        }
    }
}
