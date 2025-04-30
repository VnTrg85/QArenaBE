package qarenabe.qarenabe.service.User;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import qarenabe.qarenabe.dto.AuthRequest;
import qarenabe.qarenabe.dto.UserDTO;
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
        user.setCreate_at(request.getCreate_at());
        user.setUserRole(role);

        return userRepository.save(user);
    }

    @Override
    public UserDTO updateUser(User user) {
        try {
            User userRes = userRepository.findById(user.getId()).orElseThrow(() -> new EntityNotFoundException("User not found with ID"));
            userRes.setName(user.getName());
            userRes.setPhone(user.getPhone());
            userRes.setAddress(user.getAddress());
            userRes.setCity(user.getCity());
            userRes.setDate_of_birth(user.getDate_of_birth());
            userRepository.save(userRes);
            UserDTO userdto = new  UserDTO(userRes.getId(),userRes.getAvatar(), userRes.getName(), userRes.getEmail(), userRes.getPhone(), userRes.getAddress(),userRes.getCity(),userRes.getDate_of_birth(),userRes.getCreate_at(), userRes.getUserRole().getId());
            return userdto;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
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
            return user;
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public UserDTO getUserByEmail(String email) {
        try {
            User user = userRepository.findByEmail(email).get();
            UserDTO userRes = new  UserDTO(user.getId(),user.getAvatar(), user.getName(), user.getEmail(), user.getPhone(), user.getAddress(),user.getCity(),user.getDate_of_birth(),user.getCreate_at(), user.getUserRole().getId());
            return userRes;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }


    @Override
    public void updateAvatar(UserDTO user) {
        try {
            User userRes = userRepository.findById(user.getId()).orElseThrow(() -> new EntityNotFoundException("User not found with ID"));
            userRes.setAvatar(user.getAvatar());
            userRepository.save(userRes);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
