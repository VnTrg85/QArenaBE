package qarenabe.qarenabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import qarenabe.qarenabe.entity.BugReport;
@Repository
public interface BugReportRepository extends JpaRepository<BugReport,Long> {
    
}

