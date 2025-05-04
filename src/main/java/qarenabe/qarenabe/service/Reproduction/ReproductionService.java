package qarenabe.qarenabe.service.Reproduction;

import java.util.List;

import qarenabe.qarenabe.dto.ReproductionDTO;
import qarenabe.qarenabe.entity.Reproduction;

public interface ReproductionService {
    public ReproductionDTO createReproduction(Reproduction reproduction);
    public List<ReproductionDTO> getListReproductByBugReport(Long id);
    public List<ReproductionDTO> getListReproductByUser(Long userId);
    public ReproductionDTO acceptReproduction(Long reproductionId);
    public ReproductionDTO rejectReproduction(Long reproductionId);

}
