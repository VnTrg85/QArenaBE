package qarenabe.qarenabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qarenabe.qarenabe.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
