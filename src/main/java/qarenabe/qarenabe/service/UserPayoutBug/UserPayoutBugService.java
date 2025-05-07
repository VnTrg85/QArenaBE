package qarenabe.qarenabe.service.UserPayoutBug;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import qarenabe.qarenabe.dto.MonthlyPayoutDTO;
import qarenabe.qarenabe.dto.UserPayoutBugDTO;
import qarenabe.qarenabe.entity.UserPayoutBug;

public interface UserPayoutBugService {
    public UserPayoutBugDTO createUserPayoutBug(UserPayoutBug userPayoutBugDTO);
    public UserPayoutBugDTO findByBugReport(Long id);
    public Boolean deleteUserPayoutBugByBugReport(Long id);
    public Boolean deleteUserPayoutBugByReproduction(Long id);
    public List<MonthlyPayoutDTO> getAllPayoutsByUser(Long userId);
    public Long getAllPayoutByProject(Long projectId, Long userId);
}
