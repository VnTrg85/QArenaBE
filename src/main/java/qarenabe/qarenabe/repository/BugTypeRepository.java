package qarenabe.qarenabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import qarenabe.qarenabe.entity.BugType;
@Repository
public interface BugTypeRepository extends JpaRepository<BugType,Long> {
    
}
