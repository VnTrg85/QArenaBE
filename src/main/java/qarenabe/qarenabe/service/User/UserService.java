package qarenabe.qarenabe.service.User;

import java.util.List;

import qarenabe.qarenabe.dto.MemberRequestDTO;
import qarenabe.qarenabe.dto.MemberResponseDTO;
import qarenabe.qarenabe.entity.User;

public interface UserService {
    public List<User> getUsers();
    public User getUser(Long id);
    public String addUser(User user);
    public String updateUser(User user);
    public String deleteUser(Long id);
}
