package qarenabe.qarenabe.service.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public String addUser(User user) {
        try {
            String encodePass = securityService.encode(user.getPassword());
            user.setPassword(encodePass);
            Long roleId = user.getUserRole().getId();
            UserRole userRole = userRoleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("UserRole not found with id:" + roleId));
            user.setUserRole(userRole);
            userRepository.save(user);
            return ""+user.getId();
        } catch (Exception e) {
            return e.getMessage();
        }
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
