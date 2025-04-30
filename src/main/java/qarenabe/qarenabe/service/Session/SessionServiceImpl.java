package qarenabe.qarenabe.service.Session;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import qarenabe.qarenabe.entity.Session;
import qarenabe.qarenabe.entity.TestProject;
import qarenabe.qarenabe.entity.User;
import qarenabe.qarenabe.repository.SessionRepository;
import qarenabe.qarenabe.repository.TestProjectRepository;
import qarenabe.qarenabe.repository.UserRepository;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired 
    private SessionRepository sessionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestProjectRepository testProjectRepository;

    @Override
    public Session createSession(Session session) {
       try {
            User user = userRepository.findById(session.getUser().getId()).orElseThrow(() -> new EntityNotFoundException("User can not found with ID"));
            TestProject testProject = testProjectRepository.findById(session.getTestProject().getId()).orElseThrow(() -> new EntityNotFoundException("Test project not found with ID"));
            session.setUser(user);
            session.setTestProject(testProject);
            sessionRepository.save(session);
            Session resSession = new Session(session.getId(), session.getStartAt(), session.getEndAt(), session.getStatus(),new TestProject(session.getTestProject().getId()), null,null);
            return resSession;
       } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
       }
    }

    @Override
    public Session endSession(Long sessionId) {
        try {
            Session session = sessionRepository.findById(sessionId).orElseThrow(() -> new EntityNotFoundException("Session not found with ID"));
            session.setEndAt(new Date());
            session.setStatus("done");
            sessionRepository.save(session);
            Session resSession = new Session(session.getId(), session.getStartAt(), session.getEndAt(),session.getStatus(), new TestProject(session.getTestProject().getId()), null,null);
            return resSession;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Session> getSessionsByProjectId(Long userId, Long testProjectId) {
        try {
            List<Session> sessions = sessionRepository.findAll();
            List<Session> sessionsRes = new ArrayList<>();
            for (Session session : sessions) {
                if(session.getUser().getId() == userId && session.getTestProject().getId() == testProjectId) {
                    Session resSession = new Session(session.getId(), session.getStartAt(), session.getEndAt(),session.getStatus(), new TestProject(session.getTestProject().getId()), null,null);
                    sessionsRes.add(resSession);
                }   
            }
            return sessionsRes;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Session getSessionsDoingByProjectId(Long userId, Long testProjectId) {
        try {
            List<Session> sessions = sessionRepository.findAll();
            for (Session session : sessions) {
                if(session.getUser().getId() == userId && session.getTestProject().getId() == testProjectId && session.getStatus().equalsIgnoreCase("doing")) {
                    Session resSession = new Session(session.getId(), session.getStartAt(), session.getEndAt(),session.getStatus(), new TestProject(session.getTestProject().getId()), null,null);
                    return resSession;
                }   
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    
    @Scheduled(fixedRate = 60000)
    public void autoEndSessions() {
        List<Session> sessions = sessionRepository.findByStatusNotAndEndAtBefore("done", LocalDateTime.now());

        for (Session session : sessions) {
            session.setStatus("done");
        }
        sessionRepository.saveAll(sessions);
    }
}
