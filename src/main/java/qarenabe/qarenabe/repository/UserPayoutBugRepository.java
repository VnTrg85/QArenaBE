package qarenabe.qarenabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import qarenabe.qarenabe.entity.UserPayoutBug;
@Repository
public interface UserPayoutBugRepository extends JpaRepository<UserPayoutBug,Long> {
    UserPayoutBug findByBugReportId(Long id);
    int deleteByBugReportId(Long id);
    int deleteByReproductionId(Long id);
}
