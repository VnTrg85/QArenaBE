package qarenabe.qarenabe.service.TestProject_User;

import java.util.List;

import qarenabe.qarenabe.dto.TestProjectUserResponse;
import qarenabe.qarenabe.dto.UserDTO;
import qarenabe.qarenabe.entity.TestProject_User;
import qarenabe.qarenabe.entity.User;

public interface TestProject_UserService {
        public List<TestProjectUserResponse> getProjectByUserId(Long userId);
        public TestProjectUserResponse createTestProject(TestProject_User entity);
        public Boolean updateStatusProjectUser(Long testProjectId, String status);
        public List<UserDTO> getUserInProject(Long projectId);
        public List<User> getTestLeaderInProject(Long projectId);
}
