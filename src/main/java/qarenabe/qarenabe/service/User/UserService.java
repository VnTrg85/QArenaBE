package qarenabe.qarenabe.service.User;

import java.util.List;

import qarenabe.qarenabe.dto.AuthRequest;
import qarenabe.qarenabe.dto.UserDTO;
import qarenabe.qarenabe.entity.User;

public interface UserService {
    public List<User> getUsers();
    public User getUser(Long id);
    public User addUser(AuthRequest user);
    public UserDTO updateUser(User user);
    public void updateAvatar(UserDTO user);
    public String deleteUser(Long id);
    public UserDTO getUserByEmail(String email);
    public UserDTO getPayoutInfor(Long id);
    public UserDTO updatePayoutInfor(UserDTO userDTO);
}
