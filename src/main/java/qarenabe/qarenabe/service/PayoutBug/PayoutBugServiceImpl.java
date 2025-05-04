package qarenabe.qarenabe.service.PayoutBug;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import qarenabe.qarenabe.dto.PayoutBugDTO;
import qarenabe.qarenabe.entity.BugType;
import qarenabe.qarenabe.entity.PayoutBug;
import qarenabe.qarenabe.entity.TestProject;
import qarenabe.qarenabe.repository.BugTypeRepository;
import qarenabe.qarenabe.repository.PayoutBugRepository;
import qarenabe.qarenabe.repository.TestProjectRepository;

@Service
public class PayoutBugServiceImpl implements PayoutBugService{
    @Autowired
    private PayoutBugRepository payoutBugRepository;
    @Autowired
    private TestProjectRepository testProjectRepository;
    @Autowired
    private BugTypeRepository bugTypeRepository;
    @Override
    public List<PayoutBugDTO> getPayoutBugByProject(Long testProjectId) {
        try {
            List<PayoutBug> listData = payoutBugRepository.findAllByTestProjectId(testProjectId);
            List<PayoutBugDTO> listRes = new ArrayList<>();
            for (PayoutBug payoutBug : listData) {
                PayoutBugDTO val = new PayoutBugDTO(payoutBug.getId(),payoutBug.getAmount(),payoutBug.getBugType().getName(),payoutBug.getBugType().getIcon_link());
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

            TestProject testProject = testProjectRepository.findById(payoutBug.getTestProject().getId()).orElseThrow( () -> new EntityNotFoundException("Test project not found with ID"));
            BugType bugType = bugTypeRepository.findById(payoutBug.getBugType().getId()).orElseThrow(() -> new EntityNotFoundException("Bug type is not found with ID"));
            payoutBug.setTestProject(testProject);
            payoutBug.setBugType(bugType);
            PayoutBug res = payoutBugRepository.save(payoutBug);
            PayoutBugDTO data = new PayoutBugDTO(res.getId(), res.getAmount(),res.getBugType().getName(),res.getBugType().getIcon_link());
            return data;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
}
