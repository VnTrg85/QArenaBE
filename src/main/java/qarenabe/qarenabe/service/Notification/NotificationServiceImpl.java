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
                NotificationDTO noti = new NotificationDTO(notification.getId(), notification.getType(), notification.getContent(), notification.getLink_url(), sender, receiver);
                notificationDTOs.add(noti);
            }
            return notificationDTOs;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public Long getNumberUnReadByUser(Long id) {
        try {
            List<Notification> listNotis = notificationRepository.findAllByReceiverId(id);
            Long number = (long) 0;
            for (Notification notification : listNotis) {
                if(!notification.getIsRead()) {
                    number+=1;
                }
            }   
            return number;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public void markAllRead(Long id) {
        try {
            List<Notification> listNotis = notificationRepository.findAllByReceiverId(id);
            for (Notification notification : listNotis) {
                if(!notification.getIsRead()) {
                    notification.setIsRead(true);
                }
            }   
            notificationRepository.saveAll(listNotis);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public NotificationDTO createNotification(NotificationDTO notificationDTO) {
        try {
            User sender = userRepository.findById(notificationDTO.getSender().getId()).orElseThrow(() -> new EntityNotFoundException("User not foudn with ID"));
            User receiver = userRepository.findById(notificationDTO.getReceiver().getId()).orElseThrow(() -> new EntityNotFoundException("User not foudn with ID"));
            Notification noti = new Notification();
            noti.setContent(notificationDTO.getContent());
            noti.setIsRead(false);
            noti.setLink_url(notificationDTO.getLink_url());
            noti.setReceiver(receiver);
            noti.setSender(sender);
            noti.setType(notificationDTO.getType());
            notificationRepository.save(noti);
            return notificationDTO;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
}
