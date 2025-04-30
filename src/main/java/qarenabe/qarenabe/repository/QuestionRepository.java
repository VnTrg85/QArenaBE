package qarenabe.qarenabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qarenabe.qarenabe.entity.Question;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
