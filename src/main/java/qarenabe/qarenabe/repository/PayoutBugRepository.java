package qarenabe.qarenabe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import qarenabe.qarenabe.entity.PayoutBug;
@Repository
public interface PayoutBugRepository extends JpaRepository<PayoutBug,Long> {

    List<PayoutBug> findAllByTestProjectId(Long testProjectId);

    @Query("SELECT p.amount FROM PayoutBug p WHERE p.testProject.id = :testProjectId AND p.bugType.id = :bugTypeId")
    Optional<Long> getAmountByProjectAndBugType(@Param("testProjectId") Long testProjectId, @Param("bugTypeId") Long bugTypeId);

}
