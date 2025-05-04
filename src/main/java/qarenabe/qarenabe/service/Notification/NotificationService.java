package qarenabe.qarenabe.service.Notification;

import java.util.List;

import qarenabe.qarenabe.dto.NotificationDTO;

public interface NotificationService {
    public List<NotificationDTO> getListNotificationByReceiver(Long receiverId);
    public Long getNumberUnReadByUser(Long id);
    public void markAllRead(Long id);
}
