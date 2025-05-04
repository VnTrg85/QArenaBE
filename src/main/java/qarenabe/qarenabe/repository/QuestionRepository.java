package qarenabe.qarenabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qarenabe.qarenabe.entity.Question;

import java.util.List;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByCourseId(Long courseId);  // Lấy câu hỏi theo courseId

}
