package qarenabe.qarenabe.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import qarenabe.qarenabe.entity.TestProject;
import qarenabe.qarenabe.entity.User;

@Repository
public interface TestProjectRepository extends JpaRepository<TestProject,Long>  {


    List<TestProject> findByEndAtBefore(Date now);
    @Query("SELECT t FROM TestProject t WHERE FUNCTION('YEAR', t.create_at) = :year AND FUNCTION('MONTH', t.create_at) = :month")
    List<TestProject> findByYearAndMonth(@Param("year") int year, @Param("month") int month);

    List<TestProject> findByStatus(String status);
    
}
