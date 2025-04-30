package qarenabe.qarenabe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import qarenabe.qarenabe.entity.Message;
@Repository
public interface MessageRepository extends JpaRepository<Message,Long>{

    List<Message> findAllByBugReportId(Long id);

    List<Message> findAllByTestProjectId(Long id);
    
}
