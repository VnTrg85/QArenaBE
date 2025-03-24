package qarenabe.qarenabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import qarenabe.qarenabe.entity.TestProject;
@Repository
public interface TestProjectRepository extends JpaRepository<TestProject,Long>  {
    
}
