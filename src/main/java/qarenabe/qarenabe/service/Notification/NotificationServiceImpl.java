package qarenabe.qarenabe.service.Notification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import qarenabe.qarenabe.dto.NotificationDTO;
import qarenabe.qarenabe.dto.UserDTO;
import qarenabe.qarenabe.entity.Notification;
import qarenabe.qarenabe.entity.User;
import qarenabe.qarenabe.repository.NotificationRepository;
import qarenabe.qarenabe.repository.UserRepository;

@Service
public class NotificationServiceImpl implements NotificationService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Override
    public List<NotificationDTO> getListNotificationByReceiver(Long receiverId) {
        try {
            User receiverRes = userRepository.findById(receiverId).orElseThrow(() -> new EntityNotFoundException("User not found with ID"));
            List<Notification> listNoti = notificationRepository.findAllByReceiverId(receiverId);
            List<NotificationDTO> notificationDTOs = new ArrayList<>();
            for (Notification notification : listNoti) {
                UserDTO sender = new UserDTO(notification.getSender().getId(), notification.getSender().getName(), notification.getSender().getAvatar());
                UserDTO receiver = new UserDTO(notification.getReceiver().getId(), notification.getReceiver().getName(), notification.getReceiver().getAvatar());
                NotificationDTO noti = new NotificationDTO(notification.getId(), notification.getType(), notification.getContent(), notification.getLink_id(), sender, receiver);
                notificationDTOs.add(noti);
            }
            return notificationDTOs;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
}
