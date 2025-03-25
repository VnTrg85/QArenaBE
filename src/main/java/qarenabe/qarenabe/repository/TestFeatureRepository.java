package qarenabe.qarenabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import qarenabe.qarenabe.entity.TestFeature;
@Repository
public interface TestFeatureRepository extends JpaRepository<TestFeature,Long> {
    
}
