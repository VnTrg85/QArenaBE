package qarenabe.qarenabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import qarenabe.qarenabe.entity.UserCourse;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {
}
