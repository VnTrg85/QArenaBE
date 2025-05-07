package qarenabe.qarenabe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import qarenabe.qarenabe.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE FUNCTION('YEAR', u.create_at) = :year AND FUNCTION('MONTH', u.create_at) = :month")
    List<User> findByYearAndMonth(@Param("year") int year, @Param("month") int month);
}
