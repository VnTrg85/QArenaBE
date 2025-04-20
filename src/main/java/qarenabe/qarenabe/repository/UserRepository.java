package qarenabe.qarenabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qarenabe.qarenabe.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
