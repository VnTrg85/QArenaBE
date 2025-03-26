package qarenabe.qarenabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qarenabe.qarenabe.entity.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
