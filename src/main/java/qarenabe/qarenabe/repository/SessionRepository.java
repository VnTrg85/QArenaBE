package qarenabe.qarenabe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import qarenabe.qarenabe.entity.Session;
@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {

    List<Session> findAllByTestProjectId(Long testProjectId);
    
}
