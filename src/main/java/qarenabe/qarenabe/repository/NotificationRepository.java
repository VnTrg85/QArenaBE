package qarenabe.qarenabe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import qarenabe.qarenabe.entity.Notification;
@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    List<Notification> findAllByReceiverId(Long receiverId);
    
}
