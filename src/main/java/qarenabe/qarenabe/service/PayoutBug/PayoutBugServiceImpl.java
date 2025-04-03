package qarenabe.qarenabe.service.PayoutBug;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import qarenabe.qarenabe.dto.PayoutBugDTO;
import qarenabe.qarenabe.entity.PayoutBug;
import qarenabe.qarenabe.repository.PayoutBugRepository;

@Service
public class PayoutBugServiceImpl implements PayoutBugService{
    @Autowired
    private PayoutBugRepository payoutBugRepository;
    @Override
    public List<PayoutBugDTO> getPayoutBugByProject(Long testProjectId) {
        try {
            List<PayoutBug> listData = payoutBugRepository.findAllByTestProjectId(testProjectId);
            List<PayoutBugDTO> listRes = new ArrayList<>();
            for (PayoutBug payoutBug : listData) {
                PayoutBugDTO val = new PayoutBugDTO(payoutBug.getId(),payoutBug.getAmount());
                listRes.add(val);
            }
            return listRes;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public PayoutBugDTO createPayoutBug(PayoutBug payoutBug) {
        try {
            PayoutBug res = payoutBugRepository.save(payoutBug);
            PayoutBugDTO data = new PayoutBugDTO(res.getId(), res.getAmount());
            return data;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
}
