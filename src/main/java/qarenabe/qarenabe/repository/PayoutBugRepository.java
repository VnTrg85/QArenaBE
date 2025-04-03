package qarenabe.qarenabe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import qarenabe.qarenabe.entity.PayoutBug;
@Repository
public interface PayoutBugRepository extends JpaRepository<PayoutBug,Long> {

    List<PayoutBug> findAllByTestProjectId(Long testProjectId);
    
}
