package qarenabe.qarenabe.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import qarenabe.qarenabe.entity.TestProject;
@Repository
public interface TestProjectRepository extends JpaRepository<TestProject,Long>  {


    List<TestProject> findByEndAtBefore(Date now);

    
    List<TestProject> findByUser_Id(Long userId);
}
