package qarenabe.qarenabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import qarenabe.qarenabe.entity.UserLesson;

@Repository
public interface UserLessonRepository extends JpaRepository<UserLesson, Integer> {
}
