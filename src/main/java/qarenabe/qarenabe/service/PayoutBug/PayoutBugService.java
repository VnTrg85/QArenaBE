package qarenabe.qarenabe.service.PayoutBug;

import java.util.List;

import qarenabe.qarenabe.dto.PayoutBugDTO;
import qarenabe.qarenabe.entity.PayoutBug;

public interface PayoutBugService {
    public List<PayoutBugDTO> getPayoutBugByProject(Long testProjectId);
    public PayoutBugDTO createPayoutBug(PayoutBug payoutBug);
    public Long getAmountForProjectAndBugType(Long testProjectId, Long bugTypeId);
}
