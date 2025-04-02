package qarenabe.qarenabe.service.TestProject_User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import qarenabe.qarenabe.dto.TestProjectUserResponse;
import qarenabe.qarenabe.entity.TestProject_User;
import qarenabe.qarenabe.repository.TestProject_UserRepository;

@Service
public class Testproject_UserServiceImpl implements TestProject_UserService{
    @Autowired
    private TestProject_UserRepository testProject_UserRepository;
    @Override
    public List<TestProjectUserResponse> getProjectByUserId(Long userId) {
        try {
            List<TestProject_User> lists = testProject_UserRepository.findByUserId(userId);
            List<TestProjectUserResponse> resLists = new ArrayList<>();
            
            for (TestProject_User item : lists) {
                TestProjectUserResponse val = new TestProjectUserResponse(item.getId(), item.getStatus(), item.getTestProject());
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
}
