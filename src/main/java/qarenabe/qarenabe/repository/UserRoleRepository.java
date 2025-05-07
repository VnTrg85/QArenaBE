package qarenabe.qarenabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import qarenabe.qarenabe.entity.UserRole;
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
}
