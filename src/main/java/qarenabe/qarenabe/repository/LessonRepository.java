package qarenabe.qarenabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import qarenabe.qarenabe.entity.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
