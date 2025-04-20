package qarenabe.qarenabe.service.Message;

import java.util.List;

import qarenabe.qarenabe.dto.MessageDTO;

public interface MessageService {
    public MessageDTO saveAndBroadcastBug(MessageDTO message);
    public MessageDTO saveMessageProject(MessageDTO message);
    public List<MessageDTO> getAllMessageByBugId(Long id);
    public List<MessageDTO> getAllMessageByProjectId(Long id);
}
