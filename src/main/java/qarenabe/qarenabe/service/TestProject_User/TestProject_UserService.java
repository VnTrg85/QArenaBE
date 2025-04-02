package qarenabe.qarenabe.service.TestProject_User;

import java.util.List;

import qarenabe.qarenabe.dto.TestProjectUserResponse;

public interface TestProject_UserService {
        public List<TestProjectUserResponse> getProjectByUserId(Long userId);
}
