package qarenabe.qarenabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import qarenabe.qarenabe.entity.Lesson;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    void deleteByIdIn(List<Long> ids);

    @Query("SELECT l FROM Lesson l WHERE l.previousLesson.id = :lessonId ORDER BY l.id ASC")
    Lesson findNextLesson(@Param("lessonId") Long lessonId);
    List<Lesson> findByCourseId(Long courseId);
    Lesson findTopByCourseIdOrderByCreatedAtDesc (Long courseId);
    List<Lesson> findByPreviousLessonIdIn(List<Long> ids);
}
