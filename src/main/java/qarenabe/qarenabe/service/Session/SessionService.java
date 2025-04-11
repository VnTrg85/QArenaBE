package qarenabe.qarenabe.service.Session;

import java.util.List;

import qarenabe.qarenabe.entity.Session;

public interface SessionService {
    public Session createSession(Session session);
    public Session endSession(Long sessionId);
    public List<Session> getSessionsByProjectId(Long userId, Long testProjectId);
}
