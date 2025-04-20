package qarenabe.qarenabe.service.Security;


public interface SecurityService {
    public String encode(String text);
    public String decode(String text);
    public Boolean verifyToken(String token,String subject);
    public String generateToken(String subject);
    Long getCurrentUserId();
}