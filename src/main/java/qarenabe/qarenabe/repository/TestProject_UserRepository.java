package qarenabe.qarenabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import qarenabe.qarenabe.entity.TestProject_User;
@Repository
public interface TestProject_UserRepository extends JpaRepository<TestProject_User,Long> {
    
}
