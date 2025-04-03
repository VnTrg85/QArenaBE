package qarenabe.qarenabe.service.User;

import java.util.List;

import qarenabe.qarenabe.dto.AuthRequest;
import qarenabe.qarenabe.entity.User;

public interface UserService {
    public List<User> getUsers();
    public User getUser(Long id);
    public User addUser(AuthRequest user);
    public String updateUser(User user);
    public String deleteUser(Long id);
}
