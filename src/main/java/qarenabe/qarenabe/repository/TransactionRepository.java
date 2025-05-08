package qarenabe.qarenabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import qarenabe.qarenabe.entity.TestProject;
import qarenabe.qarenabe.entity.Transaction;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    Transaction findByTestProject_IdAndUser_Id(Long id, Long userId);

}
