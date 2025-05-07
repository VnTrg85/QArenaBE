package qarenabe.qarenabe.service.Reproduction;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import jakarta.persistence.EntityNotFoundException;
import qarenabe.qarenabe.dto.ReproductionDTO;
import qarenabe.qarenabe.entity.Browser;
import qarenabe.qarenabe.entity.BugReport;
import qarenabe.qarenabe.entity.Device;
import qarenabe.qarenabe.entity.Reproduction;
import qarenabe.qarenabe.entity.User;
import qarenabe.qarenabe.entity.UserPayoutBug;
import qarenabe.qarenabe.repository.BrowserRepository;
import qarenabe.qarenabe.repository.BugReportRepository;
import qarenabe.qarenabe.repository.DeviceRepository;
import qarenabe.qarenabe.repository.ReproductionRepository;
import qarenabe.qarenabe.repository.UserPayoutBugRepository;
import qarenabe.qarenabe.repository.UserRepository;
import qarenabe.qarenabe.service.UserPayoutBug.UserPayoutBugService;

@Service
public class ReproductionServiceImpl implements ReproductionService {
    @Autowired
    private ReproductionRepository reproductionRepository;
    @Autowired
    private BugReportRepository bugReportRepository;
    @Autowired 
    private BrowserRepository browserRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserPayoutBugRepository userPayoutBugRepository;
    @Autowired
    private UserPayoutBugService userPayoutBugService;
    @Override
    public ReproductionDTO createReproduction(Reproduction reproduction) {
        try {
            BugReport bugReport = bugReportRepository.findById(reproduction.getBugReport().getId()).orElseThrow(() -> new EntityNotFoundException("Bug report not found with ID"));
            Device device = deviceRepository.findById(reproduction.getDevice().getId()).orElseThrow(() -> new EntityNotFoundException("Device not found with ID"));
            Browser browser = browserRepository.findById(reproduction.getBrowser().getId()).orElseThrow(() -> new EntityNotFoundException("Browser not found with ID"));
            User user = userRepository.findById(reproduction.getUser().getId()).orElseThrow(() -> new EntityNotFoundException("User not found with ID"));
            reproduction.setBrowser(browser);
            reproduction.setDevice(device);
            reproduction.setUser(user);
            reproduction.setBugReport(bugReport);
            reproduction.setStatus("awaiting");
            Reproduction repr = reproductionRepository.save(reproduction);
            ReproductionDTO re = new ReproductionDTO(repr.getId(), repr.getProofLink(), repr.getDevice().getName(), browser, repr.getTime_created(), repr.getBugReport().getId(), repr.getUser().getId(),repr.getStatus());
            return re;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<ReproductionDTO> getListReproductByBugReport(Long id) {
       try {
        List<Reproduction> listReproductions = reproductionRepository.findAllByBugReportId(id);
        List<ReproductionDTO> listRes = new ArrayList<>();
        for (Reproduction repr : listReproductions) {
            ReproductionDTO re = new ReproductionDTO(repr.getId(), repr.getProofLink(), repr.getDevice().getName(), repr.getBrowser(), repr.getTime_created(), repr.getBugReport().getId(), repr.getUser().getId(), repr.getStatus());
            listRes.add(re);
        }
        return listRes;
       } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
       }
    }

    @Override
    public List<ReproductionDTO> getListReproductByUser(Long userId) {
        try {
            List<Reproduction> listReproductions = reproductionRepository.findAllByUserId(userId);
            List<ReproductionDTO> listRes = new ArrayList<>();
            for (Reproduction repr : listReproductions) {
                ReproductionDTO re = new ReproductionDTO(repr.getId(), repr.getProofLink(), repr.getDevice().getName(), repr.getBrowser(), repr.getTime_created(), repr.getBugReport().getId(), repr.getUser().getId(), repr.getStatus());
                listRes.add(re);
            }
            return listRes;
           } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
           }
    }

    @Override
    public ReproductionDTO acceptReproduction(Long reproductionId) {
        try {
            Reproduction repr = reproductionRepository.findById(reproductionId).orElseThrow(() -> new EntityNotFoundException("Reproduction not found with ID"));
            repr.setStatus("accepted");
            UserPayoutBug userPayoutBug  = new UserPayoutBug();
            userPayoutBug.setDateCreated(new Date());
            userPayoutBug.setReproduction(repr);
            userPayoutBug.setBugReport(repr.getBugReport());
            userPayoutBugService.createUserPayoutBug(userPayoutBug);
            ReproductionDTO re = new ReproductionDTO(repr.getId(), repr.getProofLink(), repr.getDevice().getName(), repr.getBrowser(), repr.getTime_created(), repr.getBugReport().getId(), repr.getUser().getId(), repr.getStatus());
            return re;
           } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
           }
    }

    @Override
    public ReproductionDTO rejectReproduction(Long reproductionId) {
        try {
            Reproduction repr = reproductionRepository.findById(reproductionId).orElseThrow(() -> new EntityNotFoundException("Reproduction not found with ID"));
            repr.setStatus("rejected");
            userPayoutBugService.deleteUserPayoutBugByReproduction(reproductionId);
            ReproductionDTO re = new ReproductionDTO(repr.getId(), repr.getProofLink(), repr.getDevice().getName(), repr.getBrowser(), repr.getTime_created(), repr.getBugReport().getId(), repr.getUser().getId(), repr.getStatus());
            return re;
           } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
           }
    }
    
    
}
