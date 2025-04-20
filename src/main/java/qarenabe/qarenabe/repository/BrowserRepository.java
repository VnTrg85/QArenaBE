package qarenabe.qarenabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import qarenabe.qarenabe.entity.Browser;
@Repository
public interface BrowserRepository extends JpaRepository<Browser,Long> {
    
}
