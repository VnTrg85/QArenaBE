package qarenabe.qarenabe.service.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import qarenabe.qarenabe.dto.AuthRequest;
import qarenabe.qarenabe.entity.User;
import qarenabe.qarenabe.entity.UserRole;
import qarenabe.qarenabe.repository.UserRepository;
import qarenabe.qarenabe.repository.UserRoleRepository;
import qarenabe.qarenabe.service.Security.*;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired 
    private SecurityService securityService;
    @Autowired 
    private UserRoleRepository userRoleRepository;
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }


    @Override
    public User addUser(AuthRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email đã tồn tại!");
        }

        UserRole role = userRoleRepository.findById(request.getRoleId())
            .orElseThrow(() -> new RuntimeException("Vai trò không hợp lệ!"));
        String encodePass = securityService.encode(request.getPassword());
        User user = new User();
        user.setPassword(encodePass);
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setUserRole(role);

        return userRepository.save(user);
    }

    @Override
    public String updateUser(User user) {
        try {
            userRepository.save(user);
            return "Updated";
        } catch (Exception e) {
            return "Failed to update user";
        }
    }

    @Override
    public String deleteUser(Long id) {
        try {
            userRepository.deleteById(id);;
            return "Deleted";
        } catch (Exception e) {
            return "Failed to delete user";
        }
    }


    @Override
    public User getUser(Long id) {
        try {
            User user = userRepository.findById(id).get();
            // User userRes = new User();
            // userRes.setName(user.get().getName());
            // userRes.setPhone(user.get().getPhone());
            // userRes.setEmail(user.get().getEmail());
            // userRes.setUserRole(user.get().getUserRole());
            return user;
        } catch (Exception e) {
            return null;
        }

    }

}
