package qarenabe.qarenabe.service.UserRole;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import qarenabe.qarenabe.entity.UserRole;
import qarenabe.qarenabe.repository.UserRoleRepository;



@Service
public class UserRoleServiceImpl implements UserRoleService{
    @Autowired
      UserRoleRepository userRoleRepository;

    @Override
    public List<UserRole> getUserRoles() {
        return  userRoleRepository.findAll();
    }

    @Override
    public void addUserRole(UserRole userRole) {
        try {
            userRoleRepository.save(userRole);
        } catch (Exception e) {
        }
    }

    @Override
    public void updateUserRole(UserRole userRole) {
        try {
            userRoleRepository.save(userRole);
        } catch (Exception e) {
        }
    }

    @Override
    public void deleteUserRole(Long userRoleId) {
        try {
            userRoleRepository.deleteById(userRoleId);         
        } catch (Exception e) {
        }
    }

}
