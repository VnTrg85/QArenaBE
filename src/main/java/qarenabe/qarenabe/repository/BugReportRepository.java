package qarenabe.qarenabe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import qarenabe.qarenabe.dto.BugReportDTO;
import qarenabe.qarenabe.entity.BugReport;
@Repository
public interface BugReportRepository extends JpaRepository<BugReport,Long> {
    List<BugReport> findAllByTestProjectId(Long testProjectId);
}

