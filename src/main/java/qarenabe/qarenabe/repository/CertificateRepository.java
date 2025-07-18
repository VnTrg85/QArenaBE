package qarenabe.qarenabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qarenabe.qarenabe.entity.Certificate;
import qarenabe.qarenabe.entity.Course;
import qarenabe.qarenabe.entity.User;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
   boolean existsByUserAndCourse(User user, Course course);
   Certificate findByUserAndCourse(User user, Course course);
}
