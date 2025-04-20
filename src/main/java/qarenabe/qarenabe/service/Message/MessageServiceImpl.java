package qarenabe.qarenabe.service.Message;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import qarenabe.qarenabe.dto.MessageDTO;
import qarenabe.qarenabe.dto.NotificationDTO;
import qarenabe.qarenabe.dto.UserDTO;
import qarenabe.qarenabe.entity.BugReport;
import qarenabe.qarenabe.entity.Message;
import qarenabe.qarenabe.entity.Notification;
import qarenabe.qarenabe.entity.TestProject;
import qarenabe.qarenabe.entity.User;
import qarenabe.qarenabe.repository.BugReportRepository;
import qarenabe.qarenabe.repository.MessageRepository;
import qarenabe.qarenabe.repository.NotificationRepository;
import qarenabe.qarenabe.repository.TestProjectRepository;
import qarenabe.qarenabe.repository.UserRepository;

import com.example.demo.enums.TypeNotification;
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{

    private final SimpMessagingTemplate messagingTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private BugReportRepository bugReportRepository;
    @Autowired
    private TestProjectRepository testProjectRepository;
    @Autowired 
    private NotificationRepository notificationRepository;
    public MessageDTO saveAndBroadcastBug(MessageDTO messageDto) {
        User user  = userRepository.findById(messageDto.getUser().getId()).orElseThrow(() -> new EntityNotFoundException("User not found with ID"));
        BugReport bugReport = bugReportRepository.findById(messageDto.getBugReportId()).orElseThrow(() -> new EntityNotFoundException("Bug report not found with ID"));
        Message message = new Message();
        message.setContent(messageDto.getContent());
        message.setTime_created(messageDto.getTime_created());
        message.setBugReport(bugReport);
        message.setUser(user);
        Message saved = messageRepository.save(message);
        Long bugId = saved.getBugReport().getId();
        UserDTO sender =new UserDTO(saved.getUser().getId(),saved.getUser().getName(), saved.getUser().getAvatar());
        MessageDTO mes = new MessageDTO(saved.getId(),saved.getContent(),saved.getTime_created(),null,saved.getBugReport().getId(),sender);
        
        // Gửi tới room của bug report
        messagingTemplate.convertAndSend("/topic/bug-report/" + bugId, mes);
        UserDTO owner = new UserDTO(saved.getBugReport().getUser().getId(), saved.getBugReport().getUser().getName(), saved.getBugReport().getUser().getEmail());
        Notification noti = new Notification();
        noti.setType(TypeNotification.BUG_REPORT);
        noti.setContent("You have a new comment on bug report");
        noti.setLink_id(bugId);
        noti.setSender(saved.getUser());
        noti.setReceiver(saved.getBugReport().getUser());
        Notification notiSaved =  notificationRepository.save(noti);
        NotificationDTO notiRes = new NotificationDTO(notiSaved.getId(),notiSaved.getType(),notiSaved.getContent(),notiSaved.getLink_id(),sender,owner);
        // Gửi notification cho chủ bug nếu khác người gửi
        if (owner != null && !owner.getId().equals(message.getUser().getId())) {
            messagingTemplate.convertAndSend(
                "/user/" + owner.getId() + "/notify",
               notiRes
            );
        }
        return mes;
    }

    @Override
    public List<MessageDTO> getAllMessageByBugId(Long id) {
        try {
            List<Message> messages = messageRepository.findAllByBugReportId(id);
            List<MessageDTO> messageRes = new ArrayList<>();
            for (Message message : messages) {
                MessageDTO mes = new MessageDTO(message.getId(),message.getContent(),message.getTime_created(),null,null,new UserDTO(message.getUser().getId(), message.getUser().getName(),message.getUser().getAvatar()));
                messageRes.add(mes);
            }
            return messageRes;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public MessageDTO saveMessageProject(MessageDTO messageDto) {
        User user  = userRepository.findById(messageDto.getUser().getId()).orElseThrow(() -> new EntityNotFoundException("User not found with ID"));
        TestProject testProject = testProjectRepository.findById(messageDto.getTestProjectId()).orElseThrow(() -> new EntityNotFoundException("Bug report not found with ID"));
        Message message = new Message();
        message.setContent(messageDto.getContent());
        message.setTime_created(messageDto.getTime_created());
        message.setTestProject(testProject);
        message.setUser(user);
        Message saved = messageRepository.save(message);
        Long projectId = saved.getTestProject().getId();
        UserDTO owner =new UserDTO(saved.getUser().getId(),saved.getUser().getName(), saved.getUser().getAvatar());
        MessageDTO mes = new MessageDTO(saved.getId(),saved.getContent(),saved.getTime_created(),saved.getBugReport().getId(),null,owner);
        // Gửi tới room của bug report
        messagingTemplate.convertAndSend("/topic/project/" + projectId, mes);
        return mes;
    }

    @Override
    public List<MessageDTO> getAllMessageByProjectId(Long id) {
        try {   
            List<Message> listMess = messageRepository.findAllByTestProjectId(id);
            List<MessageDTO> listMessRes = new ArrayList<>();
            for (Message message : listMess) {
                UserDTO user = new UserDTO(message.getUser().getId(), message.getUser().getName(), message.getUser().getAvatar());
                MessageDTO messEntity = new MessageDTO(message.getId(), message.getContent(), message.getTime_created(), message.getTestProject().getId(), message.getBugReport().getId(),user);
                listMessRes.add(messEntity);
            }
            return listMessRes;
        } catch (Exception e) {
          throw new RuntimeException(e.getMessage());
        }
    }
    
}
