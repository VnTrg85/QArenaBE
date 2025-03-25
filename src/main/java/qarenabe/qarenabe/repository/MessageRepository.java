package qarenabe.qarenabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import qarenabe.qarenabe.entity.Message;
@Repository
public interface MessageRepository extends JpaRepository<Message,Long>{
    
}
