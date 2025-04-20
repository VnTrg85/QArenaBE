package qarenabe.qarenabe.service.TestProject_User;

import java.util.List;

import qarenabe.qarenabe.dto.TestProjectUserResponse;
import qarenabe.qarenabe.entity.TestProject_User;

public interface TestProject_UserService {
        public List<TestProjectUserResponse> getProjectByUserId(Long userId);
        public TestProjectUserResponse createTestProject(TestProject_User entity);
}
