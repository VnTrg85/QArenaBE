package qarenabe.qarenabe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import qarenabe.qarenabe.entity.Reproduction;
@Repository
public interface ReproductionRepository extends JpaRepository<Reproduction,Long> {

    List<Reproduction> findAllByBugReportId(Long id);

    List<Reproduction> findAllByUserId(Long userId);
    
}
